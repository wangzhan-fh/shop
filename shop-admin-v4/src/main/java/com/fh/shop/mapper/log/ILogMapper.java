package com.fh.shop.mapper.log;

import com.fh.shop.param.log.LogSearchParam;
import com.fh.shop.po.log.LogInfo;

import java.util.List;

public interface ILogMapper {

    void addLog(LogInfo logInfo);

    Long findCount(LogSearchParam logSearchParam);

    List<LogInfo> findList(LogSearchParam logSearchParam);
}
