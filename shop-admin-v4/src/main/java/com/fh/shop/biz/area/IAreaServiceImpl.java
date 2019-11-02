package com.fh.shop.biz.area;

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
        areaMapper.addArea(area);
    }

    @Override
    public void deleteArea(String str) {
        List<Integer> list =new ArrayList();
        String[] split = str.split(",");
        for (String s : split) {
            list.add(Integer.parseInt(s));
        }
        areaMapper.deleteArea(list);
    }

    @Override
    public void updateArea(Area area) {
        areaMapper.updateArea(area);
    }
}
