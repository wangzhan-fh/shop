package com.fh.shop.biz.member;

import com.fh.shop.conmmons.DataTableResult;
import com.fh.shop.mapper.member.IMemberMapper;
import com.fh.shop.param.member.MemberParam;
import com.fh.shop.po.member.Member;
import com.fh.shop.util.DateUtil;
import com.fh.shop.vo.member.MemberVo;
import org.apache.xmlbeans.impl.jam.mutable.MMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("memberService")
public class IMemberServiceImpl implements IMemberService {

    @Autowired
    private IMemberMapper memberMapper;


    @Override
    public DataTableResult findList(MemberParam memberParam) {
        //查询总条数
        Long count = memberMapper.findCount(memberParam);
        //查询list集合
        List<Member> list=memberMapper.findList(memberParam);
        //转vo
        List<MemberVo> listVo = new ArrayList<>();
        for (Member mMember : list) {
            MemberVo memberVo = new MemberVo();
            memberVo.setAreaName(mMember.getAreaName());
            memberVo.setPhone(mMember.getPhone());
            memberVo.setBirthday(DateUtil.data2str(mMember.getBirthday(),DateUtil.Y_M_D));
            memberVo.setEmail(mMember.getEmail());
            memberVo.setRealName(mMember.getRealName());
            memberVo.setMemberName(mMember.getMemberName());
            listVo.add(memberVo);
        }
        return new DataTableResult(memberParam.getDraw(),count,count,listVo);
    }
}
