package com.fh.shop.api.util;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class IdUtil {

    public static  String getTimeId(){
        DateTimeFormatter yyyyMMddHHmm = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        String id = LocalDateTime.now().format(yyyyMMddHHmm)+IdWorker.getIdStr();
        return id;
    }
}
