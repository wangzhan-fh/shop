package com.fh.shop.api.area.po;

import java.io.Serializable;

public class Area implements Serializable {
    private Integer id;
    private String name;
    private Integer fatherId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFatherId() {
        return fatherId;
    }

    public void setFatherId(Integer fatherId) {
        this.fatherId = fatherId;
    }
}
