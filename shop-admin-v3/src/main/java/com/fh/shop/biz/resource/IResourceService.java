package com.fh.shop.biz.resource;

import com.fh.shop.po.resource.Resource;
import com.fh.shop.vo.resource.ResourceVo;

import java.util.List;

public interface IResourceService {
    List<Resource> resourceList();

    void addResource(Resource res);

    void updateResource(Resource res);

    void deleteResource(String str);

    List<Resource> menuList(Long id);

    List<String> menuUrl(Long id);

    List<Resource> allmenuList(Long id);
}
