package com.fh.shop.api.cart.biz;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.api.cart.vo.CartItemVo;
import com.fh.shop.api.cart.vo.CartVo;
import com.fh.shop.api.conmmons.ResponceEnum;
import com.fh.shop.api.conmmons.ServerResponse;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.api.product.mapper.IProductMapper;
import com.fh.shop.api.product.po.Product;
import com.fh.shop.api.util.BigdecimalUtil;
import com.fh.shop.api.util.KeyUtil;
import com.fh.shop.api.util.RedisUtil;
import com.fh.shop.api.util.SystemConst;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service("cartService")
public class ICartServiceImpl implements ICartService {

    @Autowired
    private IProductMapper productMapper;


    @Override
    public ServerResponse add(Long productId, Long count, Long memberId) {
        //判断商品是否存在
        Product product = productMapper.selectById(productId);
        if(product==null){
            return ServerResponse.error(ResponceEnum.PRODUCT_IS_NULL);
        }
        //判断商品的状态
        Integer shelves = product.getShelves();
        if(shelves==SystemConst.PRODUCT_SHELVES_IS_DOWN){
            return ServerResponse.error(ResponceEnum.SHELVES_IS_DOWN);
        }
        //判断商品有没有购物车，
        String hget = RedisUtil.hget(SystemConst.CART_KEY, KeyUtil.buildmemberIdKey(memberId));
        if(StringUtils.isEmpty(hget)){
            //没有则 创建购物车
            CartVo cartVo = new CartVo();
            //添加商品
            CartItemVo cartItemVo = buildCartItemVo(count, product);
            //添加购物车
            cartVo.getList().add(cartItemVo);
            //更新数据
            updateCart(cartVo,memberId);
            //返回数据
            return ServerResponse.success();
        }
        //有购物车，判断是否有商品
        CartVo cartVo = JSONObject.parseObject(hget, CartVo.class);
        List<CartItemVo> list = cartVo.getList();
        CartItemVo product1 = getProduct(productId, list);
        //如果没有商品
        if(product1==null){
            //添加商品
            CartItemVo cartItemVo = buildCartItemVo(count, product);
            //添加购物车
            cartVo.getList().add(cartItemVo);
            //更新数据
            updateCart(cartVo,memberId);
            //返回数据
            return ServerResponse.success();
        }
        //有商品进行更新
        if(product1.getSubTotalCount()+ count==0){
            deleteItem(productId, list);
        }else {
            product1.setSubTotalCount(product1.getSubTotalCount() + count);
            BigDecimal mul = BigdecimalUtil.mul(product1.getSubTotalCount().toString(), product.getPrice().toString());
            product1.setSubTotalPrice(mul.toString());
        }
        updateCart(cartVo, memberId);
        return ServerResponse.success();
    }

    private void deleteItem(Long productId, List<CartItemVo> list) {
        for (CartItemVo cartItemVo : list) {
            if(cartItemVo.getProductId()==productId){
                list.remove(cartItemVo);
                break;
            }

        }
    }

    @Override
    public ServerResponse findCart(Long memberId) {
        String hget = RedisUtil.hget(SystemConst.CART_KEY, KeyUtil.buildmemberIdKey(memberId));
        if(hget==null){
            return ServerResponse.error(ResponceEnum.CART_IS_NULL);
        }
        CartVo cartVo = JSONObject.parseObject(hget, CartVo.class);

        return ServerResponse.success(cartVo);
    }

    //删除
    @Override
    public ServerResponse deleteCart(Long productId, Long memberId) {
        //判断商品有没有购物车，
        String hget = RedisUtil.hget(SystemConst.CART_KEY, KeyUtil.buildmemberIdKey(memberId));
        if(StringUtils.isEmpty(hget)){
            return ServerResponse.error(ResponceEnum.ITEM_IS_NULL);
        }
        CartVo cartVo = JSONObject.parseObject(hget, CartVo.class);
        List<CartItemVo> list = cartVo.getList();
        deleteItem(productId, list);
        updateCart(cartVo,memberId);
        return ServerResponse.success();
    }


    private  CartItemVo getProduct(Long productId, List<CartItemVo> list) {
        CartItemVo product = null;
        for (CartItemVo cartItemVo : list) {
            if(cartItemVo.getProductId()==productId){
                product=cartItemVo;
                break;
            }
        }
        return product;
    }

    private CartItemVo buildCartItemVo(Long count, Product product) {
        //给购物车添加商品
        CartItemVo cartItemVo = new CartItemVo();
        cartItemVo.setMainImg(product.getMainImagePath());
        cartItemVo.setPrice(product.getPrice().toString());
        cartItemVo.setProductId(product.getId());
        cartItemVo.setProductName(product.getProductName());
        cartItemVo.setSubTotalCount(count);
        String  bigDecimal = BigdecimalUtil.mul(cartItemVo.getPrice().toString(), cartItemVo.getSubTotalCount().toString()).toString();
        cartItemVo.setSubTotalPrice(bigDecimal);
        return cartItemVo;
    }

    private void updateCart(CartVo cartVo,Long memberId) {
        if(cartVo.getList().size()==0){
            RedisUtil.hdel(SystemConst.CART_KEY,KeyUtil.buildmemberIdKey(memberId));
        }else {
            //更新购物车
            List<CartItemVo> list = cartVo.getList();
            Long totalCount = 0l;
            BigDecimal subTotalPrice = new BigDecimal(0);
            for (CartItemVo itemVo : list) {
                totalCount += itemVo.getSubTotalCount();
                subTotalPrice = BigdecimalUtil.add(subTotalPrice.toString(), itemVo.getSubTotalPrice());
            }
            cartVo.setTotalCount(totalCount);
            cartVo.setToralPrice(subTotalPrice.toString());

            //存到redis中
            String toJSONString = JSONObject.toJSONString(cartVo);
            RedisUtil.hset(SystemConst.CART_KEY, KeyUtil.buildmemberIdKey(memberId), toJSONString);
        }
    }
}
