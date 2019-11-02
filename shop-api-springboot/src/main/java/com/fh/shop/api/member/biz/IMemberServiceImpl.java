package com.fh.shop.api.member.biz;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.conmmons.ResponceEnum;
import com.fh.shop.api.conmmons.ServerResponse;
import com.fh.shop.api.member.mapper.IMemberMapper;
import com.fh.shop.api.member.po.Member;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.api.util.KeyUtil;
import com.fh.shop.api.util.Md5Util;
import com.fh.shop.api.util.RedisUtil;
import com.fh.shop.api.util.SystemConst;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.UUID;
import java.util.regex.Pattern;

@Service("memberService")
public class IMemberServiceImpl implements IMemberService {

    @Autowired
    private IMemberMapper memberMapper;

    @Override
    public ServerResponse add(Member member) {
        //非空判断
        String phoneCode = member.getPhoneCode();
        String phone = member.getPhone();
        String email = member.getEmail();
        String memberName = member.getMemberName();
        //验证码
        if(StringUtils.isEmpty(phoneCode)){
            return ServerResponse.error(ResponceEnum.PHONECODE_IS_NULL);
        }
        //手机号
        if(StringUtils.isEmpty(phone)){
            return ServerResponse.error(ResponceEnum.PHONE_IS_NULL);
        }
        String patter ="^1(3|5|7|8)\\d{9}";
        boolean matches = Pattern.matches(patter, phone);
        if(matches==false){
            return ServerResponse.error(ResponceEnum.PHONE_IS_ERROR);
        }
        //email
        if(StringUtils.isEmpty(email)){
            return ServerResponse.error(ResponceEnum.EMAIL_IS_NULL);
        }
        //用户名
        if(StringUtils.isEmpty(memberName)){
            return ServerResponse.error(ResponceEnum.MEMBER_IS_NULL);
        }

        //判断验证码的正确性
        String code = RedisUtil.get(KeyUtil.buildphoneKey(phone));
        if(StringUtils.isEmpty(code)){
            return ServerResponse.error(ResponceEnum.CODE_IS_ERROR);
        }
        if(!code.equals(phoneCode)){
            return ServerResponse.error(ResponceEnum.PHONECODE_IS_ERROE);
        }
        
        //判断唯一性
        QueryWrapper<Member> membername = new QueryWrapper<Member>();
        membername.eq("memberName",memberName);
        Member member1 = memberMapper.selectOne(membername);
        if(member1!=null){
            return ServerResponse.error(ResponceEnum.MEMBER_IS_CF);
        }

        QueryWrapper<Member> phonea = new QueryWrapper<Member>();
        phonea.eq("phone",phone);
        Member member2 = memberMapper.selectOne(phonea);
        if(member2!=null){
            return ServerResponse.error(ResponceEnum.PHONE_IS_CF);
        }

        QueryWrapper<Member> emaila = new QueryWrapper<Member>();
        emaila.eq("email",email);
        Member member3 = memberMapper.selectOne(emaila);
        if(member3!=null){
            return ServerResponse.error(ResponceEnum.EMAIL_IS_CF);
        }
        memberMapper.insert(member);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse findMemberName(String memberName) {
        //用户名
        if(StringUtils.isEmpty(memberName)){
            return ServerResponse.error(ResponceEnum.MEMBER_IS_NULL);
        }


        //判断唯一性
        QueryWrapper<Member> membername = new QueryWrapper<Member>();
        membername.eq("memberName",memberName);
        Member member1 = memberMapper.selectOne(membername);
        if(member1==null){
            return ServerResponse.error(ResponceEnum.MEMBER_IS_CF);
        }
        return ServerResponse.success();
    }

    @Override
    public ServerResponse findPhone(String phone) {
        //手机号
        if(StringUtils.isEmpty(phone)){
            return ServerResponse.error(ResponceEnum.PHONE_IS_NULL);
        }
        String patter ="^1(3|5|7|8)\\d{9}";
        boolean matches = Pattern.matches(patter, phone);
        if(matches==false){
            return ServerResponse.error(ResponceEnum.PHONE_IS_ERROR);
        }

        QueryWrapper<Member> phonea = new QueryWrapper<Member>();
        phonea.eq("phone",phone);
        Member member2 = memberMapper.selectOne(phonea);
        if(member2!=null){
            return ServerResponse.error(ResponceEnum.PHONE_IS_CF);
        }
        return ServerResponse.success();
    }

    @Override
    public ServerResponse findEmail(String email) {
        //email
        if(StringUtils.isEmpty(email)){
            return ServerResponse.error(ResponceEnum.EMAIL_IS_NULL);
        }
        QueryWrapper<Member> emaila = new QueryWrapper<Member>();
        emaila.eq("email",email);
        Member member3 = memberMapper.selectOne(emaila);
        if(member3==null){
            return ServerResponse.error(ResponceEnum.EMAIL_IS_CF);
        }
        return ServerResponse.success();
    }

    @Override
    public ServerResponse login(Member member) {
        String memberName = member.getMemberName();
        String password = member.getPassword();
        //非空判断
        if(StringUtils.isEmpty(memberName)){
            return ServerResponse.error(ResponceEnum.MEMBER_IS_NULL);
        }
        if(StringUtils.isEmpty(password)){
            return ServerResponse.error(ResponceEnum.PASSWORD_IS_NULL);
        }
        //根据用户名查询
        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
        memberQueryWrapper.eq("memberName",memberName);
        Member selectOne = memberMapper.selectOne(memberQueryWrapper);
        if(selectOne==null){
            return ServerResponse.error(ResponceEnum.MEMBER_IS_ERROE);
        }
        //验证密码
        if(!password.equals(selectOne.getPassword())){
            return ServerResponse.error(ResponceEnum.PASSWORD_IS_ERROR);
        }

        //转存用户信息
        MemberVo memberVo = new MemberVo();
        memberVo.setMemberName(selectOne.getMemberName());
        String uuid= UUID.randomUUID().toString();
        memberVo.setId(selectOne.getId());
        memberVo.setRealName(selectOne.getRealName());
        memberVo.setUuid(uuid);
        //装换为json格式字符串
        String jsonString = JSONObject.toJSONString(memberVo);
        //转为base64
        String encode = Base64.getEncoder().encodeToString(jsonString.getBytes());
        //签名
        String signMember = Md5Util.signMember(encode, SystemConst.MEMBER_SIGN);
        //签名进行base64编码
        String encodeToString = Base64.getEncoder().encodeToString(signMember.getBytes());
        //设置过期时间，方便续命
        RedisUtil.setEx(KeyUtil.buildmemberKey(selectOne.getMemberName(),uuid),SystemConst.MEMBER_EXPIRE,"sssssss");
        return ServerResponse.success(encode+"."+encodeToString);
    }

    @Override
    public ServerResponse loginByPhone(Member member) {

        String phone = member.getPhone();
        String phoneCode = member.getPhoneCode();
        //非空判断
        if(StringUtils.isEmpty(phone)){
            return ServerResponse.error(ResponceEnum.PHONE_IS_NULL);
        }
        String patter ="^1(3|5|7|8)\\d{9}";
        boolean matches = Pattern.matches(patter, phone);
        if(matches==false){
            return ServerResponse.error(ResponceEnum.PHONE_IS_ERROR);
        }
        if(StringUtils.isEmpty(phoneCode)){
            return ServerResponse.error(ResponceEnum.PHONECODE_IS_NULL);
        }

        //验证验证码
        //判断验证码的正确性
        String code = RedisUtil.get(KeyUtil.buildphoneKey(phone));
        if(StringUtils.isEmpty(code)){
            return ServerResponse.error(ResponceEnum.CODE_IS_ERROR);
        }
        if(!code.equals(phoneCode)){
            return ServerResponse.error(ResponceEnum.PHONECODE_IS_ERROE);
        }

        //判断手机号是否存在
        QueryWrapper<Member> phonea = new QueryWrapper<Member>();
        phonea.eq("phone",phone);
        Member selectOne = memberMapper.selectOne(phonea);
        if(selectOne==null){
            return ServerResponse.error(ResponceEnum.ZHUCE_INFO);
        }


        //转存用户信息
        MemberVo memberVo = new MemberVo();
        memberVo.setMemberName(selectOne.getMemberName());
        String uuid= UUID.randomUUID().toString();
        memberVo.setId(selectOne.getId());
        memberVo.setRealName(selectOne.getRealName());
        memberVo.setUuid(uuid);
        //装换为json格式字符串
        String jsonString = JSONObject.toJSONString(memberVo);
        //转为base64
        String encode = Base64.getEncoder().encodeToString(jsonString.getBytes());
        //签名
        String signMember = Md5Util.signMember(encode, SystemConst.MEMBER_SIGN);
        //签名进行base64编码
        String encodeToString = Base64.getEncoder().encodeToString(signMember.getBytes());
        //设置过期时间，方便续命
        RedisUtil.setEx(KeyUtil.buildmemberKey(selectOne.getMemberName(),uuid),SystemConst.MEMBER_EXPIRE,"sssssss");
        return ServerResponse.success(encode+"."+encodeToString);

    }
}
