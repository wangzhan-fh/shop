package com.fh.shop.biz.product;


import com.fh.shop.param.product.ProductSearchParam;
import com.fh.shop.po.product.Product;
import com.fh.shop.conmmons.DataTableResult;
import com.fh.shop.param.product.ProductSearchParam;
import com.fh.shop.po.product.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ProductService {

    DataTableResult productList(ProductSearchParam productSearchParam);

    void add(Product product);


    void deleteProduct(Integer id);

    Product toUpdateProduct(Integer id);

    void updateProduct(Product product);

    void updateByShelves(Integer id);

    List<Product> productParamList(ProductSearchParam productSearchParam);
}
