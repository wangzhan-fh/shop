package com.fh.shop.api.pay.vo;

import java.io.Serializable;

public class PayVo implements Serializable {

    private String memony;

    private String outId;

    private String codeUrl;

    public String getMemony() {
        return memony;
    }

    public void setMemony(String memony) {
        this.memony = memony;
    }

    public String getOutId() {
        return outId;
    }

    public void setOutId(String outId) {
        this.outId = outId;
    }

    public String getCodeUrl() {
        return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }
}
