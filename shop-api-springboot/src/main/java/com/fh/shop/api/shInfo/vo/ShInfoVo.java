package com.fh.shop.api.shInfo.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

@Data
public class ShInfoVo implements Serializable {

    private Long id;
    private String name;
    private String phone;
    private String areaInfo;
    private Integer mrdz;
}
