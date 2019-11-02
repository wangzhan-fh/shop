package com.fh.shop.api.order.biz;

import com.fh.shop.api.conmmons.ServerResponse;
import com.fh.shop.api.order.vo.OrderVo;

public interface IOrderService  {
    ServerResponse addOrder(OrderVo orderVo, Long memberId);
}
