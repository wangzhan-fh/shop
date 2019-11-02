package com.fh.shop.controller.area;

import com.fh.shop.biz.area.IAreaService;
import com.fh.shop.conmmons.Log;
import com.fh.shop.conmmons.ServerResponse;
import com.fh.shop.po.area.Area;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/area")
public class AreaController {
    @Resource(name = "areaService")
    private IAreaService areaService;


    @RequestMapping("/tolist")
    public String  toList(){
        return  "area/areaList";
    }


    //查询
    @RequestMapping("/areaList")
    @ResponseBody
    public ServerResponse areaList(){
            List<Area> areaList = areaService.areaList();
           return ServerResponse.success(areaList);
    }

    //新增
    @RequestMapping("/addArea")
    @ResponseBody
    @Log("新增地区")
    public ServerResponse addArea(Area area){
            areaService.addArea(area);
            return ServerResponse.success(area.getId());
    }

    //删除
    @RequestMapping("/deleteArea")
    @ResponseBody
    @Log("删除地区")
    public ServerResponse deleteArea(String str){
            areaService.deleteArea(str);
            return ServerResponse.success();
    }

    //修改
    @RequestMapping("/updateArea")
    @ResponseBody
    @Log("修改地区")
    public ServerResponse updateArea(Area area){
        areaService.updateArea(area);
        return ServerResponse.success();
    }
}
