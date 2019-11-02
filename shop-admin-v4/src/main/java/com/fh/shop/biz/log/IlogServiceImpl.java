package com.fh.shop.biz.log;

import com.fh.shop.conmmons.DataTableResult;
import com.fh.shop.mapper.log.ILogMapper;
import com.fh.shop.param.log.LogSearchParam;
import com.fh.shop.po.log.LogInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("logService")
public class IlogServiceImpl implements IlogService {

    @Autowired
    private ILogMapper logMapper;

    @Override
    public void addLog(LogInfo logInfo) {
        logMapper.addLog(logInfo);
    }

    @Override
    public DataTableResult findList(LogSearchParam logSearchParam) {
       Long count= logMapper.findCount(logSearchParam);

        List<LogInfo> list=logMapper.findList(logSearchParam);

        return new DataTableResult(logSearchParam.getDraw(),count,count,list);
    }
}
