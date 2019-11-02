package com.fh.shop.biz.log;

import com.fh.shop.conmmons.DataTableResult;
import com.fh.shop.param.log.LogSearchParam;
import com.fh.shop.po.log.LogInfo;

public interface IlogService {
    public void addLog(LogInfo logInfo);

    DataTableResult findList(LogSearchParam logSearchParam);
}
