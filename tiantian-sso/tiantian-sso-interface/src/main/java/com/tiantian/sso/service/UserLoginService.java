package com.tiantian.sso.service;

import com.tiantian.common.pojo.TaotaoResult;

public interface UserLoginService {
    TaotaoResult login(String usernaem, String password);

    TaotaoResult getUserByToken(String token);

    TaotaoResult logout(String token);
}
