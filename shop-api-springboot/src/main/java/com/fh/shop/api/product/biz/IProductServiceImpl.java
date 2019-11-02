package com.fh.shop.api.product.biz;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.conmmons.ServerResponse;
import com.fh.shop.api.product.mapper.IProductMapper;
import com.fh.shop.api.product.po.Product;
import com.fh.shop.api.product.vo.ProductVo;
import com.fh.shop.api.util.DateUtil;
import com.fh.shop.api.util.MailUtil;
import com.fh.shop.api.util.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("productService")
public class IProductServiceImpl implements IProductService {

    @Autowired
    private IProductMapper productMapper;

    @Override
    public ServerResponse productList() {
        String showProductList = RedisUtil.get("showProductList");
        if(StringUtils.isNotEmpty(showProductList)){
            List<ProductVo> productVos = JSONObject.parseArray(showProductList, ProductVo.class);
            return ServerResponse.success(productVos);
        }
        QueryWrapper<Product> productQueryWrapper = new QueryWrapper<>();
        productQueryWrapper.orderByDesc("id");
        productQueryWrapper.eq("shelves",1);
        List<Product> products = productMapper.selectList(productQueryWrapper);
        List<ProductVo> productVos1 = buildProductVo(products);
        String s = JSONObject.toJSONString(productVos1);
        RedisUtil.setEx("showProductList",20,s);
        return ServerResponse.success(productVos1);
    }

    @Override
    public void findStockLess(int i) {
        QueryWrapper<Product> productQueryWrapper = new QueryWrapper<>();
        productQueryWrapper.le("stock",i);
        List<Product> products = productMapper.selectList(productQueryWrapper);

    }


    private List<ProductVo> buildProductVo(List<Product> products) {
        List<ProductVo> productVos=new ArrayList<>();
        if(products!=null && products.size()>0 ){
            for (Product produc : products) {
                ProductVo productVo1 = new ProductVo();
                productVo1.setMainImagePath(produc.getMainImagePath());
                productVo1.setId(produc.getId());
                productVo1.setPrice(produc.getPrice().toString());
                productVo1.setProducedDate(DateUtil.data2str(produc.getProducedDate(),DateUtil.Y_M_D));
                productVo1.setProductName(produc.getProductName());
                productVos.add(productVo1);
            }
        }
        return productVos;
    }
}
