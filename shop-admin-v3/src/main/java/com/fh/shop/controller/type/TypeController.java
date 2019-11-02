package com.fh.shop.controller.type;

import com.fh.shop.biz.type.ITypeService;
import com.fh.shop.conmmons.ServerResponse;
import com.fh.shop.po.type.Type;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/types")
public class TypeController {

    @Resource(name="typeService")
    private ITypeService typeService;

    @RequestMapping("/findAllById")
    @ResponseBody
    public ServerResponse findAllById(Long id){
        return typeService.findAllById(id);

    }

    @RequestMapping("/toList")
    public String  toList (){
        return "type/index";
    }



    @RequestMapping("list")
    @ResponseBody
    public ServerResponse list(){
        return  typeService.list();

    }

    @RequestMapping("addtype")
    @ResponseBody
    public ServerResponse addtype(Type type){
        typeService.addType(type);
        return  ServerResponse.success(type.getId());
    }



    @RequestMapping("deletetype")
    @ResponseBody
    public ServerResponse deletetype(@RequestParam(value="str[]",required=false) List ids){
        typeService.deletetype(ids);
        return  ServerResponse.success();
    }


    @RequestMapping("updatetype")
    @ResponseBody
    public ServerResponse updatetype(Type type){
        typeService.updatetype(type);
        return  ServerResponse.success();
    }

}
