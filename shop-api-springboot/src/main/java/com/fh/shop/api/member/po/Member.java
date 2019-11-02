package com.fh.shop.api.member.po;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class Member implements Serializable {

    private Long id;
    private String memberName;
    private String realName;
    private String password;
    private String phone;
    @TableField(exist = false)
    private String areaName;

    private Long provinceId;
    private Long cityId;
    private Long countyId;

    @TableField(exist = false)
    private String phoneCode;

    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;


}
