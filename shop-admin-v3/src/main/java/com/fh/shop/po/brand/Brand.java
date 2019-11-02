package com.fh.shop.po.brand;




import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

public class Brand  implements Serializable {

    private Integer id;

    private String BrandName;

    private Integer sort;

    private Integer hotBrand;

    private String logo;

    @TableField(exist = false)
    private String oldlogo;

    public String getOldlogo() {
        return oldlogo;
    }

    public void setOldlogo(String oldlogo) {
        this.oldlogo = oldlogo;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Integer getHotBrand() {
        return hotBrand;
    }

    public void setHotBrand(Integer hotBrand) {
        this.hotBrand = hotBrand;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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
}
