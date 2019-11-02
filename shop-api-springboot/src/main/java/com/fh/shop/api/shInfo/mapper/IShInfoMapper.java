package com.fh.shop.api.shInfo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.api.shInfo.po.Shinfo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IShInfoMapper extends BaseMapper<Shinfo> {

    List<Shinfo> findList();

    Shinfo findById(Long id);
}
