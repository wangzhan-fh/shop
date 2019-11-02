package com.fh.shop.biz.area;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.conmmons.ServerResponse;
import com.fh.shop.mapper.area.IAreaMapper;
import com.fh.shop.po.area.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("areaService")
public class IAreaServiceImpl implements IAreaService {

    @Autowired
    private IAreaMapper areaMapper;


    @Override
    public List<Area> areaList() {
        return areaMapper.areaList();
    }

    @Override
    public void addArea(Area area) {
        areaMapper.insert(area);
    }

    @Override
    public void deleteArea(String str) {
        List<Integer> list =new ArrayList();
        String[] split = str.split(",");
        for (String s : split) {
            list.add(Integer.parseInt(s));
        }
        areaMapper.deleteBatchIds(list);
    }

    @Override
    public void updateArea(Area area) {
        areaMapper.updateById(area);
    }

    @Override
    public ServerResponse findAllById(Integer id) {
        QueryWrapper<Area> areaQueryWrapper = new QueryWrapper<>();
        areaQueryWrapper.eq("fatherId",id);
        List<Area> areas = areaMapper.selectList(areaQueryWrapper);
        return ServerResponse.success(areas);
    }
}
/*package com.fh.shop.biz.brand;

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
}
*/