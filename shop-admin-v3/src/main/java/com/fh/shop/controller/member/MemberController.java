package com.fh.shop.controller.member;

import com.fh.shop.biz.member.IMemberService;
import com.fh.shop.conmmons.DataTableResult;
import com.fh.shop.param.member.MemberParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Resource(name="memberService")
    private IMemberService memberService;


    @RequestMapping("/index")
    public String index(){
        return "member/list";
    }


    @RequestMapping("/findList")
    @ResponseBody
    public DataTableResult findList(MemberParam memberParam){
        return memberService.findList(memberParam);
    }






}
