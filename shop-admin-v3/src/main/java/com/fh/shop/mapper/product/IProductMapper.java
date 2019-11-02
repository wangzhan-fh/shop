package com.fh.shop.mapper.product;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.param.product.ProductSearchParam;
import com.fh.shop.po.product.Product;

import java.util.List;

public interface IProductMapper extends BaseMapper<Product> {
    Long findProductByCount(ProductSearchParam productSearchParam);

    List<Product> productList(ProductSearchParam productSearchParam);

    Product selectByid(Integer id);

    List<Product> productParamList(ProductSearchParam productSearchParam);

   /* void add(Product product);

    void deleteProduct(Integer id);

    Product toUpdateProduct(Integer id);

    void updateProduct(Product product);

    void updateByShelves(Product product);*/
}
