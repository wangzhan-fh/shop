package com.fh.shop.biz.member;

import com.fh.shop.conmmons.DataTableResult;
import com.fh.shop.param.member.MemberParam;

public interface IMemberService {
    DataTableResult findList(MemberParam memberParam);
}
