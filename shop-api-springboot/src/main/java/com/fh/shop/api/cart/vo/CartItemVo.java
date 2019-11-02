package com.fh.shop.api.cart.vo;

import java.io.Serializable;

public class CartItemVo implements Serializable {

    private String mainImg;

    private String productName;

    private  Long productId;

    private Long subTotalCount;

    private String price;

    private String subTotalPrice;

    public String getMainImg() {
        return mainImg;
    }

    public void setMainImg(String mainImg) {
        this.mainImg = mainImg;
    }

    public String getProductName() {

        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getSubTotalCount() {
        return subTotalCount;
    }

    public void setSubTotalCount(Long subTotalCount) {
        this.subTotalCount = subTotalCount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSubTotalPrice() {
        return subTotalPrice;
    }

    public void setSubTotalPrice(String subTotalPrice) {
        this.subTotalPrice = subTotalPrice;
    }
}
