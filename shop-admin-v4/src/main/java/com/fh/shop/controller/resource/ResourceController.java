package com.fh.shop.controller.resource;

import com.fh.shop.biz.resource.IResourceService;
import com.fh.shop.conmmons.Log;
import com.fh.shop.conmmons.ServerResponse;
import com.fh.shop.util.SystemConst;
import com.fh.shop.vo.resource.ResourceVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/resource")
public class ResourceController {

    @Resource(name="resourceService")
    private IResourceService resourceService;
    @Autowired
    private HttpServletRequest request;

    @RequestMapping("/toList")
    public String tolist(){
        return "resource/resourceList";
    }

    @RequestMapping("/menu")
    @ResponseBody
    public ServerResponse menu(){
            List<Resource> list = (List<Resource>) request.getSession().getAttribute(SystemConst.CURRENT_MENU);
            return ServerResponse.success(list);
    }

    //查询
    @RequestMapping("/resourceList")
    @ResponseBody
    public ServerResponse resourceList(){
            List<ResourceVo> list = resourceService.resourceList();
            return ServerResponse.success(list);
    }
    @RequestMapping("/addResource")
    @ResponseBody
    @Log("添加菜单")
    public ServerResponse addResource(com.fh.shop.po.resource.Resource res){
           resourceService.addResource(res);
            return ServerResponse.success(res.getId());
    }

    //修改

    @RequestMapping("/updateResource")
    @ResponseBody
    @Log("修改菜单")
    public ServerResponse updateResource(com.fh.shop.po.resource.Resource res){
            resourceService.updateResource(res);
            return ServerResponse.success();
    }


    //删除
    @RequestMapping("/deleteResource")
    @ResponseBody
    @Log("删除菜单")
    public ServerResponse deleteResource(String str){
            resourceService.deleteResource(str);
            return ServerResponse.success();
    }
}
