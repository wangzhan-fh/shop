package com.fh.shop.biz.product;




import com.fh.shop.conmmons.DataTableResult;
import com.fh.shop.mapper.product.IProductMapper;
import com.fh.shop.param.product.ProductSearchParam;
import com.fh.shop.po.product.Product;
import com.fh.shop.util.DateUtil;
import com.fh.shop.util.FileUtil;
import com.fh.shop.vo.product.ProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service("productService")
public class ProductServiceImpl implements ProductService {
    @Autowired
    private IProductMapper productMapper;

    /**
     * 查询商品
     * @param productSearchParam
     * @return
     */
    @Override
    public DataTableResult productList(ProductSearchParam productSearchParam) {
        //查询总条数
        Long count = productMapper.findProductByCount(productSearchParam);
        //查询本页数据
        List<Product> list = productMapper.productList(productSearchParam);

        List<ProductVo> productVos = buildProductVo(list);
        
        DataTableResult dataTableResult = new DataTableResult(productSearchParam.getDraw(), count, count, productVos);
        return dataTableResult;
    }
    private List<ProductVo> buildProductVo(List<Product> products) {
        List<ProductVo> productVos=new ArrayList<>();
        if(products!=null && products.size()>0 ){
            for (Product produc : products) {
                ProductVo productVo1 = new ProductVo();
                productVo1.setId(produc.getId());
                productVo1.setPrice(produc.getPrice()+"");
                productVo1.setProducedDate(DateUtil.data2str(produc.getProducedDate(),DateUtil.Y_M_D));
                productVo1.setProductName(produc.getProductName());
                productVo1.setHotProduct(produc.getHotProduct());
                productVo1.setMainImagePath(produc.getMainImagePath());
                productVo1.setShelves(produc.getShelves());
                productVo1.setStock(produc.getStock());
                productVo1.setBrandName(produc.getBrandName());
                productVos.add(productVo1);
            }
        }
        return productVos;
    }

    /**
     * 添加商品
     * @param product
     */
    @Override
    public void add(Product product) {
        productMapper.add(product);
    }



    /**
     * 删除商品
     * @param id
     */
    @Override
    public void deleteProduct(Integer id) {
        productMapper.deleteProduct(id);

    }

    /**
     * 回显
     * @param id
     * @return
     */
    @Override
    public Product toUpdateProduct(Integer id) {
        Product product = productMapper.toUpdateProduct(id);
        return product;
    }

    /**
     * 修改
     * @param product
     */
    @Override
    public void updateProduct(Product product) {
        productMapper.updateProduct(product);
    }

    @Override
    public void updateByShelves(Integer id) {
        Product product = productMapper.toUpdateProduct(id);
        if(product.getShelves()==1){
            product.setShelves(2);
        }else {
            product.setShelves(1);
        }
        productMapper.updateByShelves(product);
    }

    @Override
    public List<Product> productParamList(ProductSearchParam productSearchParam) {
        return productMapper.productParamList(productSearchParam);
    }
}
