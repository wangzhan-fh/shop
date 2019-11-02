package com.fh.shop.param.member;

import com.fh.shop.conmmons.Page;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class MemberParam extends Page {

    private String memberName;
    private String realName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date minTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date maxTime;
    private Long a1;
    private Long a2;
    private Long a3;

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Date getMinTime() {
        return minTime;
    }

    public void setMinTime(Date minTime) {
        this.minTime = minTime;
    }

    public Date getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(Date maxTime) {
        this.maxTime = maxTime;
    }

    public Long getA1() {
        return a1;
    }

    public void setA1(Long a1) {
        this.a1 = a1;
    }

    public Long getA2() {
        return a2;
    }

    public void setA2(Long a2) {
        this.a2 = a2;
    }

    public Long getA3() {
        return a3;
    }

    public void setA3(Long a3) {
        this.a3 = a3;
    }
}
