package com.fh.shop.mapper.area;

import com.fh.shop.po.area.Area;

import java.util.List;

public interface IAreaMapper {

    List<Area> areaList();

    void addArea(Area area);

    void deleteArea(List<Integer> list);

    void updateArea(Area area);
}
