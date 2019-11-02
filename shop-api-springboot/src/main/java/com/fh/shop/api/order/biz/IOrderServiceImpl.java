package com.fh.shop.api.order.biz;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.fh.shop.api.cart.vo.CartItemVo;
import com.fh.shop.api.cart.vo.CartVo;
import com.fh.shop.api.conmmons.ResponceEnum;
import com.fh.shop.api.conmmons.ServerResponse;
import com.fh.shop.api.order.mapper.IOrderItemMapper;
import com.fh.shop.api.order.mapper.IOrderMapper;
import com.fh.shop.api.order.po.Order;
import com.fh.shop.api.order.po.OrderItem;
import com.fh.shop.api.order.vo.OrderVo;
import com.fh.shop.api.paylog.mapper.IPaylogMapper;
import com.fh.shop.api.paylog.po.Paylog;
import com.fh.shop.api.product.mapper.IProductMapper;
import com.fh.shop.api.product.po.Product;
import com.fh.shop.api.shInfo.mapper.IShInfoMapper;
import com.fh.shop.api.shInfo.po.Shinfo;
import com.fh.shop.api.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service("orderService")
public class IOrderServiceImpl implements IOrderService {

    @Autowired
    private IOrderMapper orderMapper;

    @Autowired
    private IOrderItemMapper orderItemMapper;

    @Autowired
    private IShInfoMapper shInfoMapper;


    @Autowired
    private IProductMapper productMapper;

    @Autowired
    private IPaylogMapper paylogMapper;

    @Override
    public ServerResponse addOrder(OrderVo orderVo, Long memberId) {
        String hget = RedisUtil.hget(SystemConst.CART_KEY, KeyUtil.buildmemberIdKey(memberId));
        if(hget==null){
            return ServerResponse.error(ResponceEnum.CART_IS_NULL);
        }
        CartVo cartVo = JSONObject.parseObject(hget, CartVo.class);

        Integer zfType = orderVo.getZfType();
        if(zfType==null){
            return ServerResponse.error(ResponceEnum.ZFTYPE_IS_NULL);
        }
        QueryWrapper<Shinfo> shinfoQueryWrapper = new QueryWrapper<>();
        QueryWrapper<Shinfo> mrdz1 = shinfoQueryWrapper.eq("mrdz",1);
        Shinfo shinfo1 = shInfoMapper.selectOne(mrdz1);
        if(shinfo1==null){
            return ServerResponse.error(ResponceEnum.SHINFO_IS_NULL);
        }

        String timeId = IdWorker.getTimeId();

        //创建详情表
        List<CartItemVo> list = cartVo.getList();
        List<CartItemVo> voList= new ArrayList<>();
        for (Iterator<CartItemVo> iterator=list.iterator();iterator.hasNext();) {
            CartItemVo cartItemVo = iterator.next();
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(timeId);
            orderItem.setMemberId(memberId);
            orderItem.setCount(cartItemVo.getSubTotalCount());
            orderItem.setImage(cartItemVo.getMainImg());
            orderItem.setPrice(new BigDecimal(cartItemVo.getPrice()));
            orderItem.setProductName(cartItemVo.getProductName());
            orderItem.setProductId(cartItemVo.getProductId());



            //查询商品数量
            Product product = productMapper.selectById(orderItem.getProductId());
            //判断商品数量库存
            if(product.getStock()>=orderItem.getCount()){
                //减少库存量
                Long aLong = productMapper.updateStock(product.getId(), orderItem.getCount());
                if(aLong==0){
                    //把数据放到集合中
                    voList.add(cartItemVo);
                    //从购物车中移除
                    iterator.remove();
                }else{
                    //插入到数据库
                    orderItemMapper.insert(orderItem);
                }
            }else {
                //把数据放到集合中
                voList.add(cartItemVo);
                //从购物车中移除
                iterator.remove();
            }
            if(list.size()==0){
                return ServerResponse.error(ResponceEnum.CART_ALL_IS_NULL);
            }

            //更新数据
            Long totalCount = 0l;
            BigDecimal subTotalPrice = new BigDecimal(0);
            for (CartItemVo itemVo : list) {
                totalCount += itemVo.getSubTotalCount();
                subTotalPrice = BigdecimalUtil.add(subTotalPrice.toString(), itemVo.getSubTotalPrice());
            }
            cartVo.setTotalCount(totalCount);
            cartVo.setToralPrice(subTotalPrice.toString());
        }

        //创建订单
        Order order = new Order();
        order.setId(timeId);
        order.setMemberId(memberId);
        order.setZfType(orderVo.getZfType());
        order.setOrderCount(cartVo.getTotalCount());
        order.setTotalPrice(new BigDecimal(cartVo.getToralPrice()));
        order.setCreateTime(new Date());
        order.setPhone(shinfo1.getPhone());
        order.setName(shinfo1.getName());
        order.setShArea(shinfo1.getAreaInfo());
        orderMapper.insert(order);

        //插入支付日志表
        Paylog paylog = new Paylog();
        paylog.setOutId(IdUtil.getTimeId());
        paylog.setCreateTime(new Date());
        paylog.setOrderId(timeId);
        paylog.setMemberId(memberId);
        paylog.setPayType(orderVo.getZfType());
        paylog.setPayShelves(0);
        paylog.setPayPrice(order.getTotalPrice());
        paylogMapper.insert(paylog);

        String jsonString = JSONObject.toJSONString(paylog);
        //数据存到redis中
        RedisUtil.setEx(KeyUtil.buildpayLogKey(memberId),SystemConst.MEMBER_EXPIRE,jsonString);

        //清空购物车
        RedisUtil.hdel(SystemConst.CART_KEY,KeyUtil.buildmemberIdKey(memberId));
        return ServerResponse.success(voList);
    }
}
