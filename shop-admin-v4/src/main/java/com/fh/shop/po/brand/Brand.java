package com.fh.shop.po.brand;



import com.fh.shop.conmmons.Page;

import java.io.Serializable;

public class Brand extends Page implements Serializable {

    private Integer id;

    private String BrandName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrandName() {
        return BrandName;
    }

    public void setBrandName(String brandName) {
        BrandName = brandName;
    }
}
