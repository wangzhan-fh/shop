package com.fh.shop.mapper.type;

import com.fh.shop.po.type.Type;

import java.util.List;

public interface ITypeMapper  {

    void addType(Type type);

    List<Type> selectList();

    void deleteBatchIds(List ids);

    void updateById(Type type);

    List<Type> findAllById(Long id);
}
