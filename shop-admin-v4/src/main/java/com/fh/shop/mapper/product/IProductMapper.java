package com.fh.shop.mapper.product;

import com.fh.shop.param.product.ProductSearchParam;
import com.fh.shop.po.product.Product;

import java.util.List;

public interface IProductMapper {
    Long findProductByCount(ProductSearchParam productSearchParam);

    List<Product> productList(ProductSearchParam productSearchParam);

    void add(Product product);

    void deleteProduct(Integer id);

    Product toUpdateProduct(Integer id);

    void updateProduct(Product product);

    void updateByShelves(Product product);

    List<Product> productParamList(ProductSearchParam productSearchParam);
}
