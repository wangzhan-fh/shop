package com.fh.shop.api.type.biz;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.api.conmmons.ServerResponse;
import com.fh.shop.api.type.mapper.ITypeMapper;
import com.fh.shop.api.type.po.Type;
import com.fh.shop.api.util.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("typeService")
public class ITypeServiceImpl implements ITypeService {

    @Autowired
    private ITypeMapper typeMapper;


    @Override
    public ServerResponse findList() {
        String typeList = RedisUtil.get("typeList");
        if(StringUtils.isNotEmpty(typeList)){
            List<Type> types = JSONObject.parseArray(typeList, Type.class);
            return ServerResponse.success(types);
        }
        List<Type> types = typeMapper.selectList(null);
        String s = JSONObject.toJSONString(types);
        RedisUtil.set("typeList",s);
        return ServerResponse.success(types);
    }
}
