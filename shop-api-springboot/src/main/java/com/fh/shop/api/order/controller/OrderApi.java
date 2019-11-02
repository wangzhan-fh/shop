package com.fh.shop.api.order.controller;

import com.fh.shop.api.annontion.ApiIdempotent;
import com.fh.shop.api.annontion.Check;
import com.fh.shop.api.conmmons.ServerResponse;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.api.order.biz.IOrderService;
import com.fh.shop.api.order.vo.OrderVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/orders")
public class OrderApi {


    @Resource(name="orderService")
    private IOrderService orderService;


    @PostMapping
    @Check
    @ApiIdempotent
    public ServerResponse addOrder(OrderVo orderVo, MemberVo memberVo){
        Long memberId = memberVo.getId();
        return orderService.addOrder(orderVo,memberId);
    }









}
