package com.fh.shop.api.cart.biz;

import com.fh.shop.api.conmmons.ServerResponse;

public interface ICartService {

    ServerResponse add(Long productId, Long count, Long memberId);

    ServerResponse findCart(Long memberId);

    ServerResponse deleteCart(Long productId, Long memberId);
}
