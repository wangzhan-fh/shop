package com.fh.shop.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.biz.resource.IResourceService;
import com.fh.shop.biz.user.IUserService;
import com.fh.shop.conmmons.DataTableResult;
import com.fh.shop.conmmons.Log;
import com.fh.shop.conmmons.ResponceEnum;
import com.fh.shop.conmmons.ServerResponse;
import com.fh.shop.param.user.UserParam;
import com.fh.shop.param.user.UserPasswordParam;
import com.fh.shop.po.user.User;
import com.fh.shop.util.*;
import com.fh.shop.vo.resource.ResourceVo;
import com.fh.shop.vo.user.UserVo;
import com.google.gson.JsonObject;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Date;
import java.util.List;

import static com.fh.shop.util.FileUtil.downloadFile;

@Controller
@RequestMapping("/user")
public class UserController {

    @Resource(name="userService")
    private IUserService userService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;


    @Resource(name="resourceService")
    private IResourceService resourceService;


    @RequestMapping("/tolist")
    public String tolist(){
        return "user/list";
    }


    //导出excel
    @RequestMapping("/downExcel")
    public void downExcel(UserParam user,HttpServletResponse response){
        //通过ids查询
        List<User> list = userService.saeshfindList(user);
        //数据格式装换
        String[] header={"用户名","真实姓名","电话","薪资","入职时间","地区"};
        String[] prpos={"userName","realName","phone","pay","entryTime","areaName"};
        XSSFWorkbook xwb = ExcelUtil.buildWookBook(list, "用户列表", header, prpos, User.class);
        //下载
        FileUtil.excelDownload(xwb,response);
    }




    //导出pdf
    @RequestMapping("/downPdf")
    public void pdfDown(UserParam user,HttpServletResponse response){

        //通过ids查询
        List<User> list = userService.saeshfindList(user);
        //数据格式装换
        ByteArrayOutputStream bos =userService.buildPdf(list);
        //下载
        FileUtil.pdfDownload(response, bos);

    }


    //导出word
    @RequestMapping("/downWord")
   public void wordDownLoad(UserParam user,HttpServletResponse res, HttpServletRequest request) {
        //查询导出数据
        List<User> list = userService.saeshfindList(user);

        File file = userService.buildWord(list);
        //调用下载方法响应
        downloadFile(request, res,file.getPath(),file.getName());
        //删除临时文件
        file.delete();

    }


    //跳转修改密码页面
    @RequestMapping("/topassword")
    public String topassword(){
        return "user/updatePassword";
    }

    //修改密码
    @RequestMapping("/updatePassword")
    @ResponseBody
    @Log("修改密码")
    public ServerResponse updatePassword(UserPasswordParam userPasswordParam){
        return userService.updatePassword(userPasswordParam);
    }


    //通过邮箱找回密码
    @RequestMapping("/sendPasswordByEmail")
    @ResponseBody
    public ServerResponse sendPasswordByEmail(String email){
        return userService.sendPasswordByEmail(email);
    }

    //解除锁定
    @RequestMapping("/updateLock")
    @ResponseBody
    @Log("解除用户锁定")
    public ServerResponse updateLock(long id){
        userService.updateLock(id);
        return ServerResponse.success();
    }


    //重置密码
     @RequestMapping("/resetPassword")
    @ResponseBody
     public ServerResponse resetPassword(Long id){
        return userService.resetPassword(id);
     }

    //登录拦截
    @RequestMapping("/login")
    @ResponseBody
    public ServerResponse login(User user,HttpSession session){

        //判断用户名密码是否为空
        if(StringUtils.isEmpty(user.getPassword()) || StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty(user.getCode()) ){
            return  ServerResponse.error(ResponceEnum.USERNAME_PASSWORD_IS_NULL);
        }
        String sessionId = DistributedSession.getSessionId(request, response);
        String s = RedisUtil.get(KeyUtil.buildCodeKey(sessionId));
        if(!user.getCode().equalsIgnoreCase(s)){
            return  ServerResponse.error(ResponceEnum.IMGCODE_ERROR);
        }

        User userInfo = userService.findByUserName(user);
        if(userInfo==null){
            return  ServerResponse.error(ResponceEnum.USERNAME_IS_ERROR);
        }
        //判断密码是否等于3是否是当天时间
        if(userInfo.getErrorCount()==SystemConst.REEORCOUNT && DateUtil.data2str(userInfo.getErrorTime(),DateUtil.Y_M_D).equals(DateUtil.data2str(new Date(),DateUtil.Y_M_D))){
            return  ServerResponse.error(ResponceEnum.PASSWORD_IS_ERROR);
        }

        //判断密码错误
        if(!Md5Util.md5(Md5Util.md5(user.getPassword())+userInfo.getSalt()).equals(userInfo.getPassword())){
            //密码错误给错误次数赋值
            userService.updateErrorCount(userInfo);
            return  ServerResponse.error(ResponceEnum.PWD_IS_ERROR);
        }
        //用户存方session
  //      session.setAttribute(SystemConst.CURRENT_USER,userInfo);
        
        //获取cookie信息
        String userInfoRedis = JSONObject.toJSONString(userInfo);
        RedisUtil.setEx(KeyUtil.buildUserKey(sessionId),SystemConst.TIME_REDIS,userInfoRedis);


        RedisUtil.del(KeyUtil.buildCodeKey(sessionId));

        //成功
        userService.addLoginTime(userInfo);
        //不同用户查看不同资源
        List<com.fh.shop.po.resource.Resource> list = resourceService.menuList(userInfo.getId());
      //  session.setAttribute(SystemConst.CURRENT_MENU,list);
        String s1 = JSONObject.toJSONString(list);
        RedisUtil.setEx(KeyUtil.buildMenuListKey(sessionId),SystemConst.TIME_REDIS,s1);

        //查看所有权限的路径
        List<com.fh.shop.po.resource.Resource> resourceVos = resourceService.resourceList();
      //  session.setAttribute(SystemConst.MENU_URL,resourceVos);
        String s2 = JSONObject.toJSONString(resourceVos);
        RedisUtil.setEx(KeyUtil.buildMenuUrlKey(sessionId),SystemConst.TIME_REDIS,s2);
        //查看所有权限的路径
        List<com.fh.shop.po.resource.Resource> allResource = resourceService.allmenuList(userInfo.getId());
      //  session.setAttribute(SystemConst.All_MENU_URL,allResource);
        String s3 = JSONObject.toJSONString(allResource);
        RedisUtil.setEx(KeyUtil.buildAllResourceKey(sessionId),SystemConst.TIME_REDIS,s3);

        return  ServerResponse.success();


    }




    //退出
    @RequestMapping("/logout")
    public String logout(){
        String sessionId = DistributedSession.getSessionId(request, response);
        RedisUtil.delbatch(KeyUtil.buildUserKey(sessionId),
                KeyUtil.buildAllResourceKey(sessionId),
                KeyUtil.buildMenuUrlKey(sessionId),
                KeyUtil.buildMenuListKey(sessionId)
        );
        return "redirect:/";
    }




    //新增
        @RequestMapping("/addUser")
        @ResponseBody
        @Log("新增用户")
        public ServerResponse  addUser(User user){
                userService.addUser(user);
                return ServerResponse.success();
        }

    //查询
    @RequestMapping("/findList")
    @ResponseBody
    public DataTableResult findList(UserParam user){
        DataTableResult result=userService.findList(user);
        return  result;
    }


    //删除
    @RequestMapping("/deleteById")
    @ResponseBody
    @Log("删除用户")
    public ServerResponse deleteById(Long id){
            userService.deleteById(id);
            return ServerResponse.success();
    }

    //回显
    @RequestMapping("/queryById")
    @ResponseBody
    public ServerResponse queryById(Long id){
            UserVo user =userService.queryById(id);
            return ServerResponse.success(user);
    }


    //修改
    @RequestMapping("/updateUser")
    @ResponseBody
    @Log("修改用户")
    public  ServerResponse updateUser(User user){
            userService.updateUser(user);

        return ServerResponse.success();

    }

    //批量删除
    @RequestMapping("/deleteUserByIds")
    @ResponseBody
    @Log("批量删除用户")
    public ServerResponse deleteIds(String ids){
            userService.deleteIds(ids);
        return ServerResponse.success();

    }

    //验证用户名
    @RequestMapping("findserName")
    @ResponseBody
    public String findserName(@RequestParam String username){
        User userInfo = userService.findByName(username);
        if(userInfo==null ){
            return "{\"valid\":true}";
        }
        return "{\"valid\":false}";

    }

}
