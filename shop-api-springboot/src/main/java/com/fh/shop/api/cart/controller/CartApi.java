package com.fh.shop.api.cart.controller;

import com.fh.shop.api.annontion.Check;
import com.fh.shop.api.cart.biz.ICartService;
import com.fh.shop.api.conmmons.ServerResponse;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.api.util.SystemConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/carts")
public class CartApi {

    @Autowired
    private HttpServletRequest request;

    @Resource(name = "cartService")
    private ICartService cartService;

    @PostMapping("/add")
    @Check
    public ServerResponse add(Long productId,Long count){
        MemberVo memberVo = (MemberVo) request.getAttribute(SystemConst.MEMBER_Info);
        Long memberId = memberVo.getId();
        return cartService.add(productId,count,memberId);
    }

    @GetMapping("/findCart")
    @Check
    public ServerResponse findCart(){
        MemberVo memberVo = (MemberVo) request.getAttribute(SystemConst.MEMBER_Info);
        Long memberId = memberVo.getId();
        return cartService.findCart(memberId);
    }

    @DeleteMapping("/{id}")
    @Check
    public ServerResponse deleteCart(@PathVariable("id") Long productId,MemberVo memberVo){
        Long memberId = memberVo.getId();
        return cartService.deleteCart(productId,memberId);
    }




}
