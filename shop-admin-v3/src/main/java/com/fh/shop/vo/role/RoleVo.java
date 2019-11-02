package com.fh.shop.vo.role;

import com.fh.shop.conmmons.Page;

import java.io.Serializable;
import java.util.List;

public class RoleVo extends Page implements Serializable {
    private Integer id;

    private String roleName;
    private List<Integer> ids;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }
}
