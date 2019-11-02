package com.fh.shop.api.product.vo;

import java.math.BigDecimal;

public class ProductVo  {
    private Long id;
    private String productName;//商品名称
    private String price;//价格
    //商品主图
    private String mainImagePath;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMainImagePath() {
        return mainImagePath;
    }

    public void setMainImagePath(String mainImagePath) {
        this.mainImagePath = mainImagePath;
    }

    public String getProducedDate() {
        return producedDate;
    }

    public void setProducedDate(String producedDate) {
        this.producedDate = producedDate;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getShelves() {
        return shelves;
    }

    public void setShelves(Integer shelves) {
        this.shelves = shelves;
    }

    public Integer getHotProduct() {
        return hotProduct;
    }

    public void setHotProduct(Integer hotProduct) {
        this.hotProduct = hotProduct;
    }

    private String producedDate;

    private Integer stock;
    private Integer shelves;
    private Integer hotProduct;



}
