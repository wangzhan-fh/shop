package com.fh.shop.api.product.biz;

import com.fh.shop.api.conmmons.ServerResponse;

public interface IProductService {
    ServerResponse productList();

    void findStockLess(int i);
}
