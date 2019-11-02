package com.fh.shop.api.brand.biz;

import com.fh.shop.api.brand.param.BrandSearchBrand;
import com.fh.shop.api.brand.po.Brand;
import com.fh.shop.api.conmmons.ServerResponse;

public interface IBrandService {
    ServerResponse list();

    ServerResponse findlist();

    ServerResponse addBrand(Brand brand);

    ServerResponse deleteBrand(Long id);

    ServerResponse deleteBatchBrand(String ids);

    ServerResponse findOne(Long id);

    ServerResponse update(Brand brand);

    ServerResponse searchList(BrandSearchBrand brandSearchBrand);

    ServerResponse add(Brand brand);
}
