package com.fh.shop.api.cart.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CartVo implements Serializable {

    private Long totalCount;

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public String getToralPrice() {
        return toralPrice;
    }

    public void setToralPrice(String toralPrice) {
        this.toralPrice = toralPrice;
    }

    public List<CartItemVo> getList() {
        return list;
    }

    public void setList(List<CartItemVo> list) {
        this.list = list;
    }

    private String toralPrice;

    private List<CartItemVo> list = new ArrayList<>();


}
