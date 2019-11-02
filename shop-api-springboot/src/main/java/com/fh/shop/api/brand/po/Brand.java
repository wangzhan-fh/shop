package com.fh.shop.api.brand.po;

import java.io.Serializable;

public class Brand implements Serializable {


    private Integer id;

    private String BrandName;

    private Integer sort;

    private Integer hotBrand;
    private String logo;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getHotBrand() {
        return hotBrand;
    }

    public void setHotBrand(Integer hotBrand) {
        this.hotBrand = hotBrand;
    }
}
