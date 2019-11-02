package com.fh.shop.biz.role;

import com.fh.shop.conmmons.DataTableResult;
import com.fh.shop.mapper.role.IRoleMapper;
import com.fh.shop.po.role.Role;
import com.fh.shop.po.role.RoleResource;
import com.fh.shop.vo.role.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("roleService")
public class IRoleServiceImpl implements IRoleService {

    @Autowired
    private IRoleMapper roleMapper;
    @Override
    public List<Role> findList() {
        return roleMapper.findList();
    }

    /**
     * 添加角色
     * @param role
     * @param list
     */
    @Override
    public void addRole(Role role, List<Integer> list) {
        roleMapper.addRole(role);
        addRoleResource(role, list);
    }

    private void addRoleResource(Role role, List<Integer> list) {
        List ids =new ArrayList();
        if(list!=null && list.size()>0){
            for (Integer integer : list) {
                RoleResource rr = new RoleResource();
                rr.setResourceId(integer);
                rr.setRoleId(role.getId());
                ids.add(rr);
            }
            roleMapper.addRoleResource(ids);
        }

    }

    /**
     * 修改角色
     * @param role
     * @param list
     */
    @Override
    public void updateRole(Role role, List<Integer> list) {
        //修改数据
        roleMapper.updateRole(role);
        //删除中间表
        roleMapper.deleteResource(role.getId());
        if(list!=null && list.size()>0){
            //新增校色id
            addRoleResource(role,list);
        }

    }

    /**
     * 回显角色信息
     * @param id
     * @return
     */
    @Override
    public RoleVo toUpdateRole(Integer id) {
        Role role = roleMapper.toUpdateRole(id);
        List<Integer> list=roleMapper.findResourceIds(id);
        RoleVo roleVo = new RoleVo();
        roleVo.setIds(list);
        roleVo.setId(role.getId());
        roleVo.setRoleName(role.getRoleName());
    return roleVo;
    }

    /**
     * 删除角色的方法
     * @param id
     */
    @Override
    public void deleteRole(Integer id) {
        roleMapper.deleteRole(id);
    }

    /**
     * 查询所有
     * @return
     */
    @Override
    public List<Role> findRoleCheckbox() {
        return roleMapper.findRoleCheckbox();
    }

    @Override
    public DataTableResult findRoleByList(Role role) {
        long count = roleMapper.findRoleByCount();
        List<Role> list=roleMapper.findRoleByList(role);
        return new DataTableResult(role.getDraw(),count,count,list);
    }
}
