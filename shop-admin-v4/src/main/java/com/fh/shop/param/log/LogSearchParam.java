package com.fh.shop.param.log;

import com.fh.shop.conmmons.Page;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class LogSearchParam extends Page {

    private String userName;
    private String realName;
    private String content;
    private Integer status;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date  minTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date  maxTime;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getMinTime() {
        return minTime;
    }

    public void setMinTime(Date minTime) {
        this.minTime = minTime;
    }

    public Date getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(Date maxTime) {
        this.maxTime = maxTime;
    }
}
