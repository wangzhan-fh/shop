package com.fh.shop.api.pay.biz;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.api.conmmons.ResponceEnum;
import com.fh.shop.api.conmmons.ServerResponse;
import com.fh.shop.api.order.mapper.IOrderMapper;
import com.fh.shop.api.order.po.Order;
import com.fh.shop.api.pay.vo.PayVo;
import com.fh.shop.api.paylog.mapper.IPaylogMapper;
import com.fh.shop.api.paylog.po.Paylog;
import com.fh.shop.api.util.BigdecimalUtil;
import com.fh.shop.api.util.DateUtil;
import com.fh.shop.api.util.KeyUtil;
import com.fh.shop.api.util.RedisUtil;
import com.github.wxpay.sdk.MyConfig;
import com.github.wxpay.sdk.WXPay;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("payService")
public class IPayServiceImpl implements IPayService {

    @Autowired
    private IOrderMapper orderMapper;

    @Autowired
    private IPaylogMapper paylogMapper;

    @Override
    public ServerResponse createNative(Long id) {
        String payLogJson = RedisUtil.get(KeyUtil.buildpayLogKey(id));
        Paylog paylog = JSONObject.parseObject(payLogJson, Paylog.class);
        if(paylog==null){
            return ServerResponse.error(ResponceEnum.PAY_IS_NULL);
        }
        String outId = paylog.getOutId();
        int maneoy = BigdecimalUtil.mul(paylog.getPayPrice().toString(), "100").intValue();

        Date date = DateUtils.addMinutes(new Date(), 5);
        String str = DateUtil.data2str(date, DateUtil.YYYYMMDDHHMMSS);

        try {
            MyConfig config = new MyConfig();
            WXPay wxpay = new WXPay(config);
            Map<String, String> data = new HashMap<String, String>();
            data.put("body", "飞狐电商——订单支付中心");
            data.put("out_trade_no", outId);
            data.put("fee_type", "CNY");
            data.put("total_fee", maneoy+"");
            data.put("spbill_create_ip", "123.12.12.123");
            data.put("time_expire", str);
            data.put("notify_url", "http://www.example.com/wxpay/notify");
            data.put("trade_type", "NATIVE");  // 此处指定为扫码支付
            Map<String, String> resp = wxpay.unifiedOrder(data);


            //判断return_code是否成功
            String return_code = resp.get("return_code");
            String return_msg = resp.get("return_msg");
            if(!return_code.equalsIgnoreCase("success")){
                return ServerResponse.error(5001,"微信支付平台错误："+return_msg);
            }

            //判断result_code是否成功
            String result_code = resp.get("result_code");
            String err_code_des = resp.get("err_code_des");
            if(!result_code.equalsIgnoreCase("success")){
                return ServerResponse.error(5001,"微信支付平台错误："+err_code_des);
            }

            String code_url = resp.get("code_url");
            PayVo payVo = new PayVo();
            payVo.setCodeUrl(code_url);
            payVo.setOutId(outId);
            payVo.setMemony(paylog.getPayPrice().toString());

            //获取二维码
               return ServerResponse.success(payVo);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error();
        }
    }

    @Override
    public ServerResponse queryNative(Long id) {
        String payLogJson = RedisUtil.get(KeyUtil.buildpayLogKey(id));
        if(StringUtils.isEmpty(payLogJson)){
            return ServerResponse.error(ResponceEnum.OUTID_PAY_IS_NULL);
        }
        Paylog paylog = JSONObject.parseObject(payLogJson, Paylog.class);
        String outId = paylog.getOutId();
        MyConfig config = new MyConfig();
        try {
            WXPay wxpay = new WXPay(config);
            Map<String, String> data = new HashMap<String, String>();
            data.put("out_trade_no", outId);
            int count=0;
            while (true){
                Map<String, String> resp = wxpay.orderQuery(data);
                //判断return_code是否成功
                String return_code = resp.get("return_code");
                String return_msg = resp.get("return_msg");
                System.out.println(resp);
                if(!return_code.equalsIgnoreCase("success")){
                    return ServerResponse.error(5001,"微信支付平台错误："+return_msg);
                }

                //判断result_code是否成功
                String result_code = resp.get("result_code");
                String err_code_des = resp.get("err_code_des");
                if(!result_code.equalsIgnoreCase("success")){
                    return ServerResponse.error(5001,"微信支付平台错误："+err_code_des);
                }

                //判断支付是否成功
                String trade_state = resp.get("trade_state");
                String s = resp.get("transaction_id");
                if(trade_state.equalsIgnoreCase("success")){

                    //更新支付日志表
                    Date date = new Date();
                    paylog.setPayShelves(1);
                    paylog.setPayTime(date);
                    paylog.setLsId(s);
                    paylogMapper.updateById(paylog);
                    //更新订单表
                    Order order = new Order();
                    order.setZfTime(date);
                    order.setId(paylog.getOrderId());
                    order.setOrderType(20);
                    orderMapper.updateById(order);
                    //清除redis订单信息
                    RedisUtil.del(KeyUtil.buildpayLogKey(id));

                    return ServerResponse.success();
                }

                count++;
                Thread.sleep(3000);
                if(count>=100){
                    return ServerResponse.error(5001,"支付失败");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error();
        }

    }
}
