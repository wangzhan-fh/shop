package com.fh.shop.mapper.log;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.param.log.LogSearchParam;
import com.fh.shop.po.log.LogInfo;

import java.util.List;

public interface ILogMapper extends BaseMapper<LogInfo> {

   /* void addLog(LogInfo logInfo);*/

    Long findCount(LogSearchParam logSearchParam);

    List<LogInfo> findList(LogSearchParam logSearchParam);
}
