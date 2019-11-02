package com.fh.shop.mapper.resource;

import com.fh.shop.po.resource.Resource;

import java.util.List;

public interface IResourceMapper {
    List<Resource> resourceList();

    void addResource(Resource res);

    void updateResource(Resource res);

    void deleteResource(List<Integer> list);

    List<Resource> findMenuList(Long id);

    List<Resource> findresourceList(Long id);
}
