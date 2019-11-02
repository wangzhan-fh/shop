package com.fh.shop.api.token.biz;

import com.fh.shop.api.conmmons.ServerResponse;
import com.fh.shop.api.util.RedisUtil;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("tokenService")
public class ITokenServiceImpl implements ITokenService {
    @Override
    public ServerResponse getToken() {
        String token = UUID.randomUUID().toString();
        RedisUtil.set(token,token);
        return ServerResponse.success(token);
    }
}
