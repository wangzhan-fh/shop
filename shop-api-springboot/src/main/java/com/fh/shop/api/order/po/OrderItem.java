package com.fh.shop.api.order.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@TableName("t_orderitem")
public class OrderItem implements Serializable {

    private String orderId;

    private Long productId;

    private Long memberId;

    private String productName;

    private BigDecimal price;

    private Long count;

    private String image;


}
