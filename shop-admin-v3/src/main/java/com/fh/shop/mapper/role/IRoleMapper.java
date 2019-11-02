package com.fh.shop.mapper.role;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.param.role.RoleSearchParam;
import com.fh.shop.po.role.Role;
import com.fh.shop.po.role.RoleResource;

import java.util.List;

public interface IRoleMapper extends BaseMapper<Role> {
    List<Role> findList();
    Long findRoleByCount();

    List<Role> findRoleByList(RoleSearchParam role);

 /*   void addRole(Role role);

    void updateRole(Role role);

    Role toUpdateRole(Integer id);

    void deleteRole(Integer id);*/

    List<Role> findRoleCheckbox();

    void addRoleResource(List list);

    List<Integer> findResourceIds(Integer id);

    void deleteResource(Integer id);

    void deleteResourceId(Integer id);

    List findUserId(Integer id);
}
