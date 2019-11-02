package com.fh.shop.biz.area;

import com.fh.shop.conmmons.ServerResponse;
import com.fh.shop.po.area.Area;

import java.util.List;

public interface IAreaService {

    List<Area> areaList();

    void addArea(Area area);

    void deleteArea(String str);

    void updateArea(Area area);

    ServerResponse findAllById(Integer id);
}
