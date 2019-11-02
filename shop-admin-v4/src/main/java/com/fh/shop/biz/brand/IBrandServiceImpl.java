package com.fh.shop.biz.brand;

import com.fh.shop.conmmons.DataTableResult;
import com.fh.shop.mapper.brand.IBrandMapper;
import com.fh.shop.po.brand.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("brandService")
public class IBrandServiceImpl implements IBranService {
    @Autowired
    private IBrandMapper brandMapper;



    @Override
    public DataTableResult findBrandByList(Brand brand) {
       Long count= brandMapper.findBrandByCount();
        List<Brand> brandByList = brandMapper.findBrandByList(brand);
        return new DataTableResult (brand.getDraw(),count,count,brandByList);
    }

    @Override
    public void addBrand(Brand brand) {
        brandMapper.addBrand(brand);
    }

    @Override
    public Brand toUpdateBrand(Integer id) {
        return brandMapper.toUpdateBrand(id);
    }

    @Override
    public void updateBrand(Brand brand) {
        brandMapper.updateBrand(brand);
    }

    @Override
    public void deleteBrand(Integer id) {
        brandMapper.deleteBrand(id);
    }

    @Override
    public List<Brand> findBrandCheckbox() {
        return brandMapper.findBrandCheckbox();
    }

    @Override
    public List<Brand> allBrand() {
        return brandMapper.allBrand();
    }
}
