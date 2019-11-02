package com.fh.shop.api.shInfo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

@Data
public class Shinfo implements Serializable {

    private Long id;

    private String name;

    private String phone;

    @TableField(exist = false)
    private String areaInfo;

    private Long provinceId;
    private Long cityId;
    private Long countyId;
    private String info;

    private Integer mrdz;







}
