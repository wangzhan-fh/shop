package com.fh.shop.biz.brand;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.fh.shop.conmmons.DataTableResult;
import com.fh.shop.conmmons.ServerResponse;
import com.fh.shop.mapper.brand.IBrandMapper;
import com.fh.shop.param.brand.BrandSearchBrand;
import com.fh.shop.po.brand.Brand;
import com.fh.shop.util.OssUtil;
import com.fh.shop.util.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("brandService")
public class IBrandServiceImpl implements IBranService {
    @Autowired
    private IBrandMapper brandMapper;



    @Override
    public DataTableResult findBrandByList(BrandSearchBrand brand) {
       Long count= brandMapper.findBrandByCount();
        List<Brand> brandByList = brandMapper.findBrandByList(brand);
        return new DataTableResult (brand.getDraw(),count,count,brandByList);
    }

    @Override
    public void addBrand(Brand brand) {
        brandMapper.insert(brand);
        RedisUtil.del("brandList");
    }

    @Override
    public Brand toUpdateBrand(Integer id) {
        return brandMapper.selectById(id);
    }

    @Override
    public void updateBrand(Brand brand) {
        String logo = brand.getLogo();
        if(StringUtils.isEmpty(logo)){
            brand.setLogo(brand.getOldlogo());
        }else{
            OssUtil.deleteFile(brand.getOldlogo());
        }

        brandMapper.updateById(brand);
        RedisUtil.del("brandList");
    }

    @Override
    public void deleteBrand(Integer id,String pathImg) {
        brandMapper.deleteById(id);
        OSSClient ossClient = new OSSClient("https://oss-cn-beijing.aliyuncs.com", "LTAI4Fh2vVXYfa38x6CRRzxy", "CUcWVhTBgyJECuHtfUObHEaMezPx5u");
        ossClient.deleteObject("zhan5897", pathImg);
        RedisUtil.del("brandList");
    }

    @Override
    public List<Brand> findBrandCheckbox() {
        return brandMapper.findBrandCheckbox();
    }

    @Override
    public ServerResponse allBrand() {
        String brandList = RedisUtil.get("brandList");
        if(StringUtils.isEmpty(brandList)){
            List<Brand> list = brandMapper.selectList(null);
            //将list集合转为字符串
            String s = JSONObject.toJSONString(list);
            //将值储存到redis中
            RedisUtil.set("brandList",s);
            return ServerResponse.success(list);
        }else {
            //将字符串装维list
            List<Brand> list = JSONObject.parseArray(brandList, Brand.class);
            return ServerResponse.success(list);

        }



    }

    @Override
    public void updateByhotBrand(Long id) {
        Brand brand = brandMapper.selectById(id);
        if(brand.getHotBrand()==1){
            brand.setHotBrand(2);
        }else{
            brand.setHotBrand(1);
        }
        brandMapper.updateById(brand);
        RedisUtil.del("brandList");
    }

    @Override
    public ServerResponse updateSort(Brand brand) {
        brandMapper.updateById(brand);
        RedisUtil.del("brandList");
        return ServerResponse.success();
    }
}
