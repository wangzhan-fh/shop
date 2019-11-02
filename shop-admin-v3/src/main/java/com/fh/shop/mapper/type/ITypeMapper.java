package com.fh.shop.mapper.type;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.po.type.Type;

public interface ITypeMapper extends BaseMapper<Type> {

    void addType(Type type);
}
