package com.fh.shop.api.member.controller;

import com.fh.shop.api.annontion.Check;
import com.fh.shop.api.conmmons.ServerResponse;
import com.fh.shop.api.member.biz.IMemberService;
import com.fh.shop.api.member.po.Member;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.api.util.KeyUtil;
import com.fh.shop.api.util.RedisUtil;
import com.fh.shop.api.util.SystemConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/members")
@CrossOrigin("*")
public class MemberController {

    @Resource(name = "memberService")
    private IMemberService memberService;

    @Autowired
    private HttpServletRequest request;


    @PostMapping
    public ServerResponse add(Member member){
        return memberService.add(member);
    }

    @GetMapping
    public ServerResponse findMemberName(String memberName){
        return memberService.findMemberName(memberName);
    }
    @GetMapping("/phone")
    public ServerResponse findPhone(String phone){
        return memberService.findPhone(phone);
    }

    @GetMapping("/email")
    public ServerResponse findEmail(String email){
        return memberService.findEmail(email);
    }



    //登录
    @PostMapping("/login")
    public ServerResponse login(Member member){
        return memberService.login(member);
    }

    //免密登录
    @PostMapping("/login2")
    public ServerResponse loginByPhone(Member member){
        return memberService.loginByPhone(member);
    }


    //登录信息
    @GetMapping("/findMember")
    @Check
    public ServerResponse findMember(){
        //从request作用域区处对象
        MemberVo memberVo= (MemberVo) request.getAttribute(SystemConst.MEMBER_Info);
        String realName = memberVo.getRealName();
        return ServerResponse.success(realName);
    }

    //退出
    @GetMapping("/loginOut")
    @Check
    public ServerResponse loginOut( ){
        //从request作用域区处对象
        MemberVo memberVo= (MemberVo) request.getAttribute(SystemConst.MEMBER_Info);
        String memberName = memberVo.getMemberName();
        String uuid = memberVo.getUuid();
        RedisUtil.del(KeyUtil.buildmemberKey(memberName,uuid));
        return ServerResponse.success();
    }



}
