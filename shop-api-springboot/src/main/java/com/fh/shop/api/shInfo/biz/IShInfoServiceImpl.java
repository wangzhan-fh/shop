package com.fh.shop.api.shInfo.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.conmmons.ResponceEnum;
import com.fh.shop.api.conmmons.ServerResponse;
import com.fh.shop.api.shInfo.mapper.IShInfoMapper;
import com.fh.shop.api.shInfo.po.Shinfo;
import com.fh.shop.api.shInfo.vo.ShInfoVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service("shinfoService")
public class IShInfoServiceImpl implements IShInfoService {

    @Autowired
    private IShInfoMapper shInfoMapper;


    @Override
    public ServerResponse addInfo(Shinfo shinfo) {
        Long cityId = shinfo.getCityId();
        Long countyId = shinfo.getCountyId();
        String info = shinfo.getInfo();
        String phone = shinfo.getPhone();
        Long provinceId = shinfo.getProvinceId();
        String name = shinfo.getName();
        //非空判断
        if(cityId==null){
           return ServerResponse.error(ResponceEnum.A2_IS_NULL) ;
        }
        if(StringUtils.isEmpty(info)){
            return ServerResponse.error(ResponceEnum.INFO_IS_NULL);
        }
        if(StringUtils.isEmpty(phone)){
            return ServerResponse.error(ResponceEnum.PHONE_IS_NULL);
        }
        String patter ="^1(3|5|7|8)\\d{9}";
        boolean matches = Pattern.matches(patter, phone);
        if(matches==false){
            return ServerResponse.error(ResponceEnum.PHONE_IS_ERROR);
        }
        if(provinceId==null){
            return ServerResponse.error(ResponceEnum.A1_IS_NULL);
        }
        if(StringUtils.isEmpty(name)){
            return ServerResponse.error(ResponceEnum.NAME_IS_NULL);
        }
        Integer mrdz = shinfo.getMrdz();
        if(mrdz==1){
            QueryWrapper<Shinfo> shinfoQueryWrapper = new QueryWrapper<>();
            QueryWrapper<Shinfo> mrdz1 = shinfoQueryWrapper.eq("mrdz",1);
            Shinfo shinfo1 = shInfoMapper.selectOne(mrdz1);
            if(shinfo1==null){
                //保存数据
                shInfoMapper.insert(shinfo);
                return ServerResponse.success();
            }else{
                shinfo1.setMrdz(0);
                shInfoMapper.updateById(shinfo1);
            }

        }
        //保存数据
        shInfoMapper.insert(shinfo);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse findInfo() {
        List<Shinfo> shinfos = shInfoMapper.findList();
        List<ShInfoVo> voList=new ArrayList<>();
        return ServerResponse.success(shinfos);
    }

    @Override
    public ServerResponse deleteArea(Integer id) {
        shInfoMapper.deleteById(id);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse findInfobyId(Long id) {
        Shinfo shinfo = shInfoMapper.findById(id);
        if(shinfo==null){
            return ServerResponse.error(ResponceEnum.FIND_IS_NULL);
        }
        return ServerResponse.success(shinfo);
    }
}
