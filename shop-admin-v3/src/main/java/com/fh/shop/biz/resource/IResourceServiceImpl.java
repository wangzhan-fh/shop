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
    public List<Resource> resourceList() {
       List<Resource> list= resourceMapper.selectList(null);

        return list;
    }

    @Override
    public void addResource(Resource res) {
        resourceMapper.insert(res);
    }

    @Override
    public void updateResource(Resource res) {
        resourceMapper.updateById(res);
    }

    @Override
    public void deleteResource(String str) {
        List<Integer> list =new ArrayList();
        String[] split = str.split(",");
        for (String s : split) {
            list.add(Integer.parseInt(s));
        }
        resourceMapper.deleteResourceByid(list);
        resourceMapper.deleteBatchIds(list);
    }

    @Override
    public List<Resource> menuList(Long id) {
        List<Resource> menulist= resourceMapper.menuList(id);

        return menulist;
    }

    @Override
    public List<String> menuUrl(Long id) {
        return resourceMapper.menuUrl(id);
    }

    @Override
    public List<Resource> allmenuList(Long id) {
        return resourceMapper.allmenuList(id);
    }
}
