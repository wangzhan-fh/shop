package com.fh.shop.mapper.brand;


import com.fh.shop.po.brand.Brand;

import java.util.List;

public interface IBrandMapper {

    Long findBrandByCount();

    List<Brand> findBrandByList(Brand brand);

    void addBrand(Brand brand);

    Brand toUpdateBrand(Integer id);

    void updateBrand(Brand brand);

    void deleteBrand(Integer id);

    List<Brand> findBrandCheckbox();

    List<Brand> allBrand();
}
