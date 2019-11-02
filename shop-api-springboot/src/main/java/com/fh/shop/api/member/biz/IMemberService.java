package com.fh.shop.api.member.biz;

import com.fh.shop.api.conmmons.ServerResponse;
import com.fh.shop.api.member.po.Member;

public interface IMemberService {
    ServerResponse add(Member member);

    ServerResponse findMemberName(String memberName);

    ServerResponse findPhone(String phone);

    ServerResponse findEmail(String email);

    ServerResponse login(Member member);

    ServerResponse loginByPhone(Member member);
}
