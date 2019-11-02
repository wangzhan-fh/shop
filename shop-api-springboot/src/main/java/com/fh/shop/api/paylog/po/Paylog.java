package com.fh.shop.api.paylog.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Paylog implements Serializable {

    @TableId(type = IdType.INPUT)
    private String outId;

    private String orderId;

    private String lsId;


    private Long memberId;

    private Date createTime;

    private Date payTime;

    private Integer payType;

    private Integer payShelves;

    private BigDecimal payPrice;




}
