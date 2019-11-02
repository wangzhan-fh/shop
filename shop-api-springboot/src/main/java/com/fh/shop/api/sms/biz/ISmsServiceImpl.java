package com.fh.shop.api.sms.biz;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.api.conmmons.Code;
import com.fh.shop.api.conmmons.ResponceEnum;
import com.fh.shop.api.conmmons.ServerResponse;
import com.fh.shop.api.util.KeyUtil;
import com.fh.shop.api.util.RedisUtil;
import com.fh.shop.api.util.SMSUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service("smsService")
public class ISmsServiceImpl implements ISmsService {


    @Override
    public ServerResponse sendMsg(String phone) {
        //非空判断
        if(StringUtils.isEmpty(phone)){
            return ServerResponse.error(ResponceEnum.PHONE_IS_NULL);
        }
        //手机号正则验证
        String pattern = "^1(3|5|7|8)\\d{9}";//^1(3|5|7|8)\d{9} //java手机号正则
        boolean isMatch = Pattern.matches(pattern, phone);
        if(isMatch==false){
            return ServerResponse.error(ResponceEnum.PHONE_IS_ERROR);
        }

        //验证码进行判断
        String s = SMSUtil.smsPost(phone);
        Code code = JSONObject.parseObject(s, Code.class);
        if(!code.getCode().equals("200")){
            return ServerResponse.error(ResponceEnum.PHONE_IS_NULL);
        }
        RedisUtil.setEx(KeyUtil.buildphoneKey(phone),60,code.getObj());

        return ServerResponse.success();
    }
}
