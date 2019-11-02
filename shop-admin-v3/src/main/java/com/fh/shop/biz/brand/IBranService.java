package com.fh.shop.biz.brand;

import com.fh.shop.conmmons.DataTableResult;
import com.fh.shop.conmmons.ServerResponse;
import com.fh.shop.param.brand.BrandSearchBrand;
import com.fh.shop.po.brand.Brand;

import java.util.List;

public interface IBranService {



    DataTableResult findBrandByList(BrandSearchBrand brand);

    void addBrand(Brand brand);

    Brand toUpdateBrand(Integer id);

    void updateBrand(Brand brand);

    void deleteBrand(Integer id,String pathImg);

    List<Brand> findBrandCheckbox();

    ServerResponse allBrand();

    void updateByhotBrand(Long id);

    ServerResponse updateSort(Brand brand);
}
