package com.fh.shop.api.shInfo.biz;

import com.fh.shop.api.conmmons.ServerResponse;
import com.fh.shop.api.shInfo.po.Shinfo;

public interface IShInfoService {
    ServerResponse addInfo(Shinfo shinfo);

    ServerResponse findInfo();

    ServerResponse deleteArea(Integer id);

    ServerResponse findInfobyId(Long id);
}
