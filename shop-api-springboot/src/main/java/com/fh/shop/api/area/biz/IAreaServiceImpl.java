package com.fh.shop.api.area.biz;


import com.alibaba.fastjson.JSONObject;
import com.fh.shop.api.area.mapper.IAreaMapper;
import com.fh.shop.api.area.po.Area;
import com.fh.shop.api.conmmons.ServerResponse;
import com.fh.shop.api.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("areaService")
public class IAreaServiceImpl implements IAreaService {

    @Autowired
    private IAreaMapper areaMapper;


    @Override
    public ServerResponse findList(Integer id) {
        String areaList = RedisUtil.get("areaList");
        if(areaList!=null){
            List<Area> areas = JSONObject.parseArray(areaList, Area.class);
            List<Area> areaList1 = bulidList(areas, id);
            return ServerResponse.success(areaList1);
        }
        List<Area> areas = areaMapper.selectList(null);
        String toJSONString = JSONObject.toJSONString(areas);
        RedisUtil.set("areaList",toJSONString);
        List<Area> areaList1 = bulidList(areas, id);
        return ServerResponse.success(areas);
    }

    private  List<Area> bulidList(List<Area> list,Integer id){
        List<Area> areaList = new ArrayList<>();
        for (Area area : list) {
            if(area.getFatherId()!=null){
                if(area.getFatherId().equals(id)){
                    areaList.add(area);
                }
            }

        }
    return areaList;
    }

}