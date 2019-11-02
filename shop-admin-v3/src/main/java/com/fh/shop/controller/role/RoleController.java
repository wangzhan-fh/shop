package com.fh.shop.controller.role;

import com.fh.shop.biz.role.IRoleService;
import com.fh.shop.conmmons.DataTableResult;
import com.fh.shop.conmmons.Log;
import com.fh.shop.conmmons.ServerResponse;
import com.fh.shop.controller.resource.ResourceController;
import com.fh.shop.param.role.RoleSearchParam;
import com.fh.shop.po.role.Role;
import com.fh.shop.vo.role.RoleVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Resource(name="roleService")
    private IRoleService roleService;

    @RequestMapping("/findList")
    @ResponseBody
    public ServerResponse findList(){
            List<Role> list =roleService.findList();
            return ServerResponse.success(list);
    }
    /**
     * 跳转查询角色页面
     * @return
     */
    @RequestMapping("/toList")
    public String toList(){
        return "/role/roleList";
    }

    /**
     * 这是查询角色信息的方法
     * @return
     */
    @RequestMapping("/findRoleByList")
    @ResponseBody
    public DataTableResult findRoleByList(RoleSearchParam rol){
        DataTableResult resilt =  roleService.findRoleByList(rol);
        return resilt;
    }

    /**
     * 添加角色
     * @param role
     * @return
     */
    @RequestMapping("/addRole")
    @ResponseBody
    @Log("新增角色")
    public ServerResponse addRole(Role role, @RequestParam(value = "ids[]",required=false) List<Integer> list){
            roleService.addRole(role,list);
           return ServerResponse.success();
    }

    /**
     * 回显角色信息
     * @return
     */
    @RequestMapping("/toUpdateRole")
    @ResponseBody
    public ServerResponse toUpdateRole(Integer id){
            RoleVo roleVo = roleService.toUpdateRole(id);
           return ServerResponse.success(roleVo);
    }

    /**
     * 修改角色
     * @return
     */
    @RequestMapping("/updateRole")
    @ResponseBody
    @Log("修改角色")
    public ServerResponse updateRole(Role role,@RequestParam(value="ids[]",required=false) List<Integer> list){
            roleService.updateRole(role,list);
            return ServerResponse.success();
    }

    /**
     * 删除单个角色的方法
     * @param id
     * @return
     */
    @RequestMapping("/deleteRole")
    @ResponseBody
    @Log("删除角色")
    public ServerResponse deleteRole(Integer id){
           roleService.deleteRole(id);
            return ServerResponse.success();
    }

    /**
     * 查询所有
     * @return
     */
    @RequestMapping("/findRoleCheckbox")
    @ResponseBody
    public List<Role> findRoleCheckbox(){
        List<Role> list = roleService.findRoleCheckbox();
        return list;
    }

}
