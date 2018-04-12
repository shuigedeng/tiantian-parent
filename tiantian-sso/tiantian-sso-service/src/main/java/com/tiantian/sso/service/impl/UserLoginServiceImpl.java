package com.tiantian.sso.service.impl;

import com.tiantian.common.pojo.TaotaoResult;
import com.tiantian.common.utils.JsonUtils;
import com.tiantian.jedis.JedisClient;
import com.tiantian.mapper.TbUserMapper;
import com.tiantian.pojo.TbUser;
import com.tiantian.pojo.TbUserExample;
import com.tiantian.sso.service.UserLoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;

/**
 * @author 戴礼明
 * @create 2018/4/12 10:51
 */
@Service
public class UserLoginServiceImpl implements UserLoginService {
    @Autowired
    private TbUserMapper userMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${SESSION_PRE}")
    private String SESSION_PRE;

    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;

    @Override
    public TaotaoResult login(String username, String password) {
        // 判断用户名和密码是否正确
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> list = userMapper.selectByExample(example);
        if (list == null || list.size() == 0) {
            return TaotaoResult.build(400, "用户名或密码错误");
        }
        // 校验密码，密码要进行md5加密后再校验
        TbUser user = list.get(0);
        if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
            return TaotaoResult.build(400, "用户名或密码错误");
        }
        // 生成一个token
        String token = UUID.randomUUID().toString();
        // 把用户信息保存到Redis数据库里面去
        // key就是token，value就是用户对象转换成json
        user.setPassword(null); // 为了安全，就不要把密码保存到Redis数据库里面去，因为这样太危险了，因此我们先把密码置空
        jedisClient.set(SESSION_PRE + ":" + token, JsonUtils.objectToJson(user));
        // 设置key的过期时间
        jedisClient.expire(SESSION_PRE + ":" + token, SESSION_EXPIRE);
        // 返回结果
        return TaotaoResult.ok(token);
    }

    @Override
    public TaotaoResult getUserByToken(String token) {
        String s = jedisClient.get(SESSION_PRE + ":" + token);
        if(StringUtils.isBlank(s)){
            return TaotaoResult.build(400, "此用户已经登录过期");
        }
        jedisClient.expire(SESSION_PRE+ ":" + token, SESSION_EXPIRE);
        TbUser s1 = JsonUtils.jsonToPojo(s, TbUser.class);
        return TaotaoResult.ok(s1);
    }

    @Override
    public TaotaoResult logout(String token) {
        jedisClient.expire(SESSION_PRE+ ":" + token, 0);
        return TaotaoResult.ok();
    }
}
