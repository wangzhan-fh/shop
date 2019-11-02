package com.fh.shop.param.product;


import com.fh.shop.conmmons.Page;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ProductSearchParam extends Page implements Serializable {
    private String productName;//商品名称
    private BigDecimal price;//价格
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date producedDate;
    //生产日期条件查询
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date minTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date maxTime;

    private Long brandId;

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getProducedDate() {
        return producedDate;
    }

    public void setProducedDate(Date producedDate) {
        this.producedDate = producedDate;
    }
}
