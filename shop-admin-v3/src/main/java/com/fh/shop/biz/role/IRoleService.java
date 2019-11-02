package com.fh.shop.biz.role;

import com.fh.shop.conmmons.DataTableResult;
import com.fh.shop.param.role.RoleSearchParam;
import com.fh.shop.po.role.Role;
import com.fh.shop.vo.role.RoleVo;

import java.util.List;

public interface IRoleService {
    List<Role> findList();

    void addRole(Role role, List<Integer> list);

    void updateRole(Role role, List<Integer> list);

    RoleVo toUpdateRole(Integer id);

    void deleteRole(Integer id);

    List<Role> findRoleCheckbox();

    DataTableResult findRoleByList(RoleSearchParam rol);
}
