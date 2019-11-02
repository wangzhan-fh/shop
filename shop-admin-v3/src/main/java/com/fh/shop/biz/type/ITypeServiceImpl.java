package com.fh.shop.biz.type;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.conmmons.ServerResponse;
import com.fh.shop.mapper.type.ITypeMapper;
import com.fh.shop.po.type.Type;
import com.fh.shop.util.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("typeService")
public class ITypeServiceImpl implements ITypeService {

    @Autowired
    private ITypeMapper typeMapper;


    @Override
    public ServerResponse list() {
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

    @Override
    public void addType(Type type) {
        typeMapper.addType(type);
    }

    @Override
    public void deletetype(List ids) {
        typeMapper.deleteBatchIds(ids);
    }

    @Override
    public void updatetype(Type type) {
        typeMapper.updateById(type);
    }

    @Override
    public ServerResponse findAllById(Long id) {
        String typeList = RedisUtil.get("typeList");
        if(StringUtils.isNotEmpty(typeList)){
            List<Type> types = JSONObject.parseArray(typeList, Type.class);
            List<Type> types1 = buildTypeListById(id, types);
            return ServerResponse.success(types1);
        }
        List<Type> types = typeMapper.selectList(null);
        String s = JSONObject.toJSONString(types);
        RedisUtil.set("typeList",s);
        List<Type> types1 = buildTypeListById(id, types);
        return ServerResponse.success(types);
    }


    private List<Type> buildTypeListById(Long id, List<Type> list){
        List<Type> typeList = new ArrayList<>();
        for (Type type : list) {
            if(type.getFatherId()==id){
                typeList.add(type);
            }
        }
        return typeList;

    }
}
