package com.fh.shop.api.brand.biz;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.shop.api.brand.mapper.IBrandMapper;
import com.fh.shop.api.brand.param.BrandSearchBrand;
import com.fh.shop.api.brand.po.Brand;
import com.fh.shop.api.conmmons.DataTableResult;
import com.fh.shop.api.conmmons.ResponceEnum;
import com.fh.shop.api.conmmons.ServerResponse;
import com.fh.shop.api.util.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("brandService")
public class IBrandServiceImpl implements IBrandService {

    @Autowired
    private IBrandMapper brandMapper;


    @Override
    public ServerResponse list() {
        String hotBrandList = RedisUtil.get("hotBrandList");
        if(StringUtils.isNotEmpty(hotBrandList)){
            List<Brand> brands = JSONObject.parseArray(hotBrandList, Brand.class);
            return ServerResponse.success(brands);
        }
        QueryWrapper<Brand> brandQueryWrapper = new QueryWrapper<>();
        brandQueryWrapper.orderByDesc("id");
        brandQueryWrapper.eq("hotBrand",1);
        List<Brand> brands = brandMapper.selectList(brandQueryWrapper);
        String s = JSONObject.toJSONString(brands);
        RedisUtil.setEx("hotBrandList",20,s);
        return ServerResponse.success(brands);
    }

    @Override
    public ServerResponse findlist() {
        List<Brand> brands = brandMapper.selectList(null);
        return ServerResponse.success(brands);
    }

    @Override
    public ServerResponse addBrand(Brand brand) {
        brandMapper.insert(brand);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse deleteBrand(Long id) {
        brandMapper.deleteById(id);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse deleteBatchBrand(String ids) {
        if(StringUtils.isEmpty(ids)){
            ServerResponse.error(ResponceEnum.IDS_IS_NULL);
        }
        String[] split = ids.split(",");
        List list = new ArrayList();
        for (String s : split) {
            list.add(s);
        }
        brandMapper.deleteBatchIds(list);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse findOne(Long id) {
        Brand brand = brandMapper.selectById(id);
        if(brand==null){
            return ServerResponse.error(ResponceEnum.FIND_IS_NULL);
        }
        return ServerResponse.success(brand);
    }

    @Override
    public ServerResponse update(Brand brand) {
        brandMapper.updateById(brand);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse searchList(BrandSearchBrand brandSearchBrand) {
        Long count= brandMapper.findBrandByCount();
        List<Brand> brandByList = brandMapper.findBrandByList(brandSearchBrand);
        DataTableResult dataTableResult = new DataTableResult(brandSearchBrand.getDraw(), count, count, brandByList);
        return  ServerResponse.success(dataTableResult);
    }

    @Override
    public ServerResponse add(Brand brand) {
        brandMapper.insert(brand);
        return ServerResponse.success();
    }
}
