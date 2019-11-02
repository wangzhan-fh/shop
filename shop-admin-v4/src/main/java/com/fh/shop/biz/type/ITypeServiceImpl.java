package com.fh.shop.biz.type;

import com.fh.shop.conmmons.ServerResponse;
import com.fh.shop.mapper.type.ITypeMapper;
import com.fh.shop.po.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("typeService")
public class ITypeServiceImpl implements ITypeService {

    @Autowired
    private ITypeMapper typeMapper;


    @Override
    public List<Type> list() {
        return typeMapper.selectList();
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
        List<Type> types = typeMapper.findAllById(id);
        return ServerResponse.success(types);
    }
}
