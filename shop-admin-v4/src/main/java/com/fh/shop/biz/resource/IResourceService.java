package com.fh.shop.biz.resource;

import com.fh.shop.po.resource.Resource;
import com.fh.shop.vo.resource.ResourceVo;

import java.util.List;

public interface IResourceService {
    List<ResourceVo> resourceList();

    void addResource(Resource res);

    void updateResource(Resource res);

    void deleteResource(String str);

    List<Resource> findMenuList(Long id);

    List<Resource> findresourceList(Long id);
}
