package com.fh.shop.api.sms.biz;

import com.fh.shop.api.conmmons.ServerResponse;

public interface ISmsService {
    ServerResponse sendMsg(String phone);
}
