package com.fh.shop.api.order.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Order implements Serializable {


    //订单id:   雪花算法
    @TableId(type = IdType.INPUT)
    private String id;

    // 用户id
    private Long memberId;

    //支付方式
    private Integer zfType;

     //订单商品总数量
    private Long orderCount;

    //总价格
    private BigDecimal totalPrice;

     //创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    //支付时间
    @DateTimeFormat(pattern = "yyyy-MM-dd  HH:mm:ss")
    private Date zfTime;

   // 订单状态：未支付   翼支付   已发货   交易成功   交易关闭   一评论
     private Integer orderType;

    //订单状态描述
    private String typeInfo;

    //关闭时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date closeTime;

    //发货时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date fhTime;

     //交易成功时间
     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     private Date successTime;

    //完成评价时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date pjTime;

    //收件人手机号
    private String phone;

   // 收件人姓名
    private String name;

   //收货地址
    private String shArea;

    //是否发票
    private Integer invoice;
    // 邮编
    private String postcode;

   // 邮费
    private BigDecimal postage;



}
