package com.fh.shop.controller.brand;

import com.fh.shop.biz.brand.IBranService;
import com.fh.shop.conmmons.DataTableResult;
import com.fh.shop.conmmons.Log;
import com.fh.shop.conmmons.ServerResponse;
import com.fh.shop.param.brand.BrandSearchBrand;
import com.fh.shop.po.brand.Brand;
import com.fh.shop.biz.brand.IBranService;
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
@RequestMapping("/brand")
public class BrandController {
    @Resource(name="brandService")
    private IBranService branService;



    @RequestMapping("/updateSort")
    @ResponseBody
    public ServerResponse updateSort(Brand brand){

        return branService.updateSort(brand);
    }


    @RequestMapping("/allBrand")
    @ResponseBody
    public ServerResponse allBrand(){
        return branService.allBrand();

    }

    /**
     * 跳转查询角色页面
     * @return
     */
    @RequestMapping("/toList")
    public String toList(){
        return "/brand/BrandList";
    }

    /**
     * 这是查询角色信息的方法
     * @return
     */
    @RequestMapping("/findBrandByList")
    @ResponseBody
    public DataTableResult findBrandByList(BrandSearchBrand brand){
        DataTableResult result = branService.findBrandByList(brand);
        return result;
    }

    /**
     * 添加角色
     * @param
     * @return
     */
    @RequestMapping("/addBrand")
    @ResponseBody
    @Log("添加角色")
    public ServerResponse addBrand(Brand brand){
            branService.addBrand(brand);
            return ServerResponse.success();
    }

    @RequestMapping("/updateByhotBrand")
    @ResponseBody
    @Log("修改热销状态")
    public ServerResponse updateByhotBrand(Long id){
        branService.updateByhotBrand(id);
        return ServerResponse.success();
    }

    /**
     * 回显角色信息
     * @return
     */
    @RequestMapping("/toUpdateBrand")
    @ResponseBody
    public ServerResponse toUpdateBrand(Integer id){
            Brand brand = branService.toUpdateBrand(id);
            return ServerResponse.success(brand);
    }

    /**
     * 修改角色
     * @return
     */
    @RequestMapping("/updateBrand")
    @ResponseBody
    @Log("修改角色")
    public ServerResponse updateBrand(Brand brand){

            branService.updateBrand(brand);
            return ServerResponse.success();

    }

    /**
     * 删除单个角色的方法

     */
    @RequestMapping("/deleteBrand")
    @ResponseBody
    @Log("删除角色")
    public ServerResponse deleteBrand(Integer id,String pathImg){
            branService.deleteBrand(id,pathImg);
            return ServerResponse.success();

    }


}
