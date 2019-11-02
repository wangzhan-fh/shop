package com.fh.shop.biz.brand;

import com.fh.shop.conmmons.DataTableResult;
import com.fh.shop.po.brand.Brand;

import java.util.List;

public interface IBranService {



    DataTableResult findBrandByList(Brand brand);

    void addBrand(Brand brand);

    Brand toUpdateBrand(Integer id);

    void updateBrand(Brand brand);

    void deleteBrand(Integer id);

    List<Brand> findBrandCheckbox();

    List<Brand> allBrand();
}
