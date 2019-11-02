package com.fh.shop.controller.user;

import com.fh.shop.biz.resource.IResourceService;
import com.fh.shop.biz.user.IUserService;
import com.fh.shop.conmmons.*;
import com.fh.shop.param.user.UserParam;
import com.fh.shop.param.user.UserPasswordParam;
import com.fh.shop.po.user.User;
import com.fh.shop.util.DateUtil;
import com.fh.shop.util.MailUtil;
import com.fh.shop.util.Md5Util;
import com.fh.shop.util.SystemConst;
import com.fh.shop.vo.resource.ResourceVo;
import com.fh.shop.vo.user.UserVo;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {

    @Resource(name="userService")
    private IUserService userService;
    @Resource(name="resourceService")
    private IResourceService resourceService;

    @Autowired
    private HttpServletRequest request;


    @RequestMapping("/tolist")
    public String tolist(){
        return "user/list";
    }

    //跳转修改密码页面
    @RequestMapping("toUpdatePassword")
    public String toUpdatePassword(){
        return "user/updatePassword";
    }

    //修改密码
    @RequestMapping("/updatePassword")
    @ResponseBody
    public ServerResponse updatePassword(UserPasswordParam userPasswordParam){
        return userService.updatePassword(userPasswordParam);
    }

    //解锁用户
    @RequestMapping("/updateLock")
    @ResponseBody
    public ServerResponse updateLock(Long id){
        userService.updateLock(id);
        return ServerResponse.success();
    }


    //管理员重置密码
    @RequestMapping("/updatePasswordByGlY")
    @ResponseBody
    public ServerResponse updatePassword(Long id){
        return  userService.updatePasswordById(id);
    }


    //通过邮箱找回密码
    @RequestMapping("/findEmail")
    @ResponseBody
    public ServerResponse findEmail(String email){
        if(StringUtils.isEmpty(email) ){
            return  ServerResponse.error(ResponceEnum.EMAIL_IS_NULL);
        }
         User userDB=userService.updateEmail(email);
        if(userDB==null){
            return  ServerResponse.error(ResponceEnum.EMAIL_IS_ERROR);
        }
        String salt = UUID.randomUUID().toString();
        String s = RandomStringUtils.randomAlphanumeric(6);
        String password = Md5Util.enCodeString(s,salt);
        userDB.setSalt(salt);
        userDB.setPassword(password);
        userService.updatePasswordByEmail(userDB);
        MailUtil.sendMail("找回密码","您的新密码是"+s,email);
        return ServerResponse.success();
    }




    //登录
    @RequestMapping("/login")
    @ResponseBody
    public ServerResponse login(User user, HttpSession session){
        String password = user.getPassword();
        String userName = user.getUserName();
        if(StringUtils.isEmpty(password) || StringUtils.isEmpty(userName)){
            return  ServerResponse.error(ResponceEnum.USERNAME_PASSWORD_IS_NULL);
        }
        User userInfo = userService.findByName(userName);

        //判断用户是否存在
        if(userInfo==null){
            return  ServerResponse.error(ResponceEnum.USERNAME_IS_ERROR);
        }

        //判断当天登录密码连续错误三次被锁定
        if(userInfo.getErrorCount()==SystemConst.ERRORCOUNT && DateUtil.data2str(userInfo.getErrorTime(),DateUtil.Y_M_D).equals(DateUtil.data2str(new Date(),DateUtil.Y_M_D))){
            return  ServerResponse.error(ResponceEnum.IS_ERROR);
        }

        //判断密码是否正确
        if(!Md5Util.enCodeString(password, userInfo.getSalt()).equals(userInfo.getPassword())){
            //错误给登录次数以及登录时间重新赋值
            userService.updateError(userInfo);
            return  ServerResponse.error(ResponceEnum.PASSWORD_IS_ERROR);
        }
        //登陆成功方便登录册数获取。
        session.setAttribute(SystemConst.CURRENT_USER,userInfo);
        //登陆成功
        userService.addLoginTime(userInfo);
        //不同用户对应不同的菜单
        List<com.fh.shop.po.resource.Resource> menuList=resourceService.findMenuList(userInfo.getId());
        session.setAttribute(SystemConst.CURRENT_MENU,menuList);

        //不同用户对应不同的权限
        List<com.fh.shop.po.resource.Resource> resourceList=resourceService.findresourceList(userInfo.getId());
        session.setAttribute(SystemConst.USER_CURRENT_MENU,resourceList);

        //所有的权限
        List<ResourceVo> resourceVos = resourceService.resourceList();
        session.setAttribute(SystemConst.ALL_MENUURL,resourceVos);

        return  ServerResponse.success();
        
    }



    //注销登陆
    @RequestMapping("/logout")
    public String logout(){
        //清除session信息
        request.getSession().invalidate();
        //跳装到登陆页面
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
