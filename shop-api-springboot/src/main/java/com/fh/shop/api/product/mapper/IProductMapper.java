package com.fh.shop.api.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.api.product.po.Product;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface IProductMapper extends BaseMapper<Product> {

    Long updateStock(@Param("id") Long id, @Param("count") Long count);
}
