package com.fh.shop.controller.log;

import com.fh.shop.biz.log.IlogService;
import com.fh.shop.conmmons.DataTableResult;
import com.fh.shop.param.log.LogSearchParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/log")
public class LogController {

    @Resource(name="logService")
    private IlogService logService;


    @RequestMapping("/toList")
    public String  toList(){
        return "log/list";
    }

    @RequestMapping("/findList")
    @ResponseBody
    public DataTableResult findList(LogSearchParam logSearchParam){
        DataTableResult dataTableResult =logService.findList(logSearchParam);
        return dataTableResult;
    }

}
