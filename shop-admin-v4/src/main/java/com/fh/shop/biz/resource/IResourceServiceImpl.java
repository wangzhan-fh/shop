package com.fh.shop.biz.resource;

import com.fh.shop.mapper.resource.IResourceMapper;
import com.fh.shop.mapper.role.IRoleMapper;
import com.fh.shop.po.resource.Resource;
import com.fh.shop.vo.resource.ResourceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("resourceService")
public class IResourceServiceImpl implements IResourceService {
    @Autowired
    private IResourceMapper resourceMapper;


    @Override
    public List<ResourceVo> resourceList() {
       List<Resource> list= resourceMapper.resourceList();
        List<ResourceVo> l =new ArrayList<>();
        for (Resource resource : list) {
            ResourceVo resourceVo = new ResourceVo();
            resourceVo.setId(resource.getId());
            resourceVo.setName(resource.getMenuName());
            resourceVo.setpId(resource.getPid());
            resourceVo.setMenuType(resource.getMenuType());
            resourceVo.setMenuUrl(resource.getMenuUrl());
            l.add(resourceVo);
        }
        return l;
    }

    @Override
    public void addResource(Resource res) {
        resourceMapper.addResource(res);
    }

    @Override
    public void updateResource(Resource res) {
        resourceMapper.updateResource(res);
    }

    @Override
    public void deleteResource(String str) {
        List<Integer> list =new ArrayList();
        String[] split = str.split(",");
        for (String s : split) {
            list.add(Integer.parseInt(s));
        }
        resourceMapper.deleteResource(list);
    }

    @Override
    public List<Resource> findMenuList(Long id) {

        return resourceMapper.findMenuList(id);
    }

    @Override
    public List<Resource> findresourceList(Long id) {
        return resourceMapper.findresourceList(id);
    }
}
