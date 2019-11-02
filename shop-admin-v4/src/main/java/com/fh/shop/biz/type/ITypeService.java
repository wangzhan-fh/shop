package com.fh.shop.biz.type;

import com.fh.shop.conmmons.ServerResponse;
import com.fh.shop.po.type.Type;

import java.util.List;

public interface ITypeService  {
    List<Type> list();

    void addType(Type type);

    void deletetype(List ids);

    void updatetype(Type type);

    ServerResponse findAllById(Long id);
}
