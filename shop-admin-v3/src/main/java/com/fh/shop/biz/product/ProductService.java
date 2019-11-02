package com.fh.shop.biz.product;


import com.fh.shop.param.product.ProductSearchParam;
import com.fh.shop.po.product.Product;
import com.fh.shop.conmmons.DataTableResult;
import com.fh.shop.param.product.ProductSearchParam;
import com.fh.shop.po.product.Product;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;
import java.util.Map;

public interface ProductService {

    DataTableResult productList(ProductSearchParam productSearchParam);

    void add(Product product);


    void deleteProduct(Integer id);

    Product toUpdateProduct(Integer id);

    void updateByShelves(Integer id);

    void updateProduct(Product product);

    void updateByhotProduct(Integer id);

    List<Product> productParamList(ProductSearchParam productSearchParam);

    ByteArrayOutputStream buildPdf(List<Product> productList);

    File buildWord(List<Product> productList);


    /*    void updateProduct(Product product);*/
}
