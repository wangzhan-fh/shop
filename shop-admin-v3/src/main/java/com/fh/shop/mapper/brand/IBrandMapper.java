package com.fh.shop.mapper.brand;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.param.brand.BrandSearchBrand;
import com.fh.shop.po.brand.Brand;

import java.util.List;

public interface IBrandMapper extends BaseMapper<Brand> {

    Long findBrandByCount();

    List<Brand> findBrandByList(BrandSearchBrand brand);

   /* void addBrand(Brand brand);

    Brand toUpdateBrand(Integer id);

    void updateBrand(Brand brand);

    void deleteBrand(Integer id);*/

    List<Brand> findBrandCheckbox();

    void addBrand(Brand brand);


}
