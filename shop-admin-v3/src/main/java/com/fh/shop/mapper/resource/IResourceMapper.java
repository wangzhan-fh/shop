package com.fh.shop.mapper.resource;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.po.resource.Resource;

import java.util.List;

public interface IResourceMapper extends BaseMapper<Resource> {
    List<Resource> resourceList();

    List<Resource> menuList(Long id);

    void deleteResourceByid(List<Integer> list);

    List<String> menuUrl(Long id);

    List<Resource> allmenuList(Long id);
}
