package com.fh.shop.mapper.role;

import com.fh.shop.po.role.Role;
import com.fh.shop.po.role.RoleResource;

import java.util.List;

public interface IRoleMapper {
    public List<Role> findList();
    Long findRoleByCount();

    List<Role> findRoleByList(Role role);

    void addRole(Role role);

    void updateRole(Role role);

    Role toUpdateRole(Integer id);

    void deleteRole(Integer id);

    List<Role> findRoleCheckbox();

    void addRoleResource(List list);

    List<Integer> findResourceIds(Integer id);

    void deleteResource(Integer id);
}
