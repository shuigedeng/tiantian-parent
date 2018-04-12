package com.tiantian.sso.service;

import com.tiantian.common.pojo.TaotaoResult;
import com.tiantian.pojo.TbUser;

public interface UserRegisterService {
    TaotaoResult checkUserInfo(String param, int type);
    TaotaoResult createUser(TbUser tbUser);
}
