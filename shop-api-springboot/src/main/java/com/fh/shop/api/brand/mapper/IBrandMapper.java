package com.fh.shop.api.brand.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.api.brand.param.BrandSearchBrand;
import com.fh.shop.api.brand.po.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IBrandMapper extends BaseMapper<Brand>{

    Long findBrandByCount();

    List<Brand> findBrandByList(BrandSearchBrand brandSearchBrand);
}
