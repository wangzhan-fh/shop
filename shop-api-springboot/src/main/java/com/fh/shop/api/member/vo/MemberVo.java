package com.fh.shop.api.member.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class MemberVo implements Serializable {

    private String memberName;

    private String uuid;

    private Long id;

    private String realName;



}
