package com.fh.shop.mapper.member;

import com.fh.shop.param.member.MemberParam;
import com.fh.shop.po.member.Member;
import org.apache.xmlbeans.impl.jam.mutable.MMember;

import java.util.List;

public interface IMemberMapper{
    public Long findCount(MemberParam memberParam) ;

    List<Member> findList(MemberParam memberParam);
}
