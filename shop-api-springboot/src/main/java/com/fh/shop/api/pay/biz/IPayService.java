package com.fh.shop.api.pay.biz;

import com.fh.shop.api.conmmons.ServerResponse;

public interface IPayService {
    ServerResponse createNative(Long id);

    ServerResponse queryNative(Long id);
}
