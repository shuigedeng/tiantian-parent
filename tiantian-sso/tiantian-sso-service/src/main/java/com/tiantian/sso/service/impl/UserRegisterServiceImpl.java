package com.tiantian.sso.service.impl;

import com.tiantian.common.pojo.TaotaoResult;
import com.tiantian.mapper.TbUserMapper;
import com.tiantian.pojo.TbUser;
import com.tiantian.pojo.TbUserExample;
import com.tiantian.sso.service.UserRegisterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * @author 戴礼明
 * @create 2018/4/12 10:42
 */
@Service
public class UserRegisterServiceImpl implements UserRegisterService {
    @Autowired
    private TbUserMapper userMapper;

    @Override
    public TaotaoResult checkUserInfo(String param, int type) {
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        // 判断要校验的数据类型，来设置不同的查询条件
        // 1、2、3分别代表username、phone、email
        if (type == 1) {
            criteria.andUsernameEqualTo(param);
        } else if (type == 2) {
            criteria.andPhoneEqualTo(param);
        } else if (type == 3) {
            criteria.andEmailEqualTo(param);
        }
        // 执行查询
        List<TbUser> list = userMapper.selectByExample(example);
        if (list == null || list.size() == 0) {
            return TaotaoResult.ok(true);
        }
        return TaotaoResult.ok(false);
    }

    @Override
    public TaotaoResult createUser(TbUser user) {
        // 校验数据的合法性
        if (StringUtils.isBlank(user.getUsername())
                || StringUtils.isBlank(user.getPassword())) {
            return TaotaoResult.build(400, "用户名和密码不能为空");
        }
        // 校验用户名是否重复
        TaotaoResult taotaoResult = checkUserInfo(user.getUsername(), 1);
        boolean flag = (boolean) taotaoResult.getData();
        if (!flag) {
            return TaotaoResult.build(400, "用户名重复");
        }
        // 校验手机号是否重复
        if (user.getPhone() != null) { // 注意：空串也算有值
            taotaoResult = checkUserInfo(user.getPhone(), 2);
            if (!(boolean) taotaoResult.getData()) {
                return TaotaoResult.build(400, "手机号重复");
            }
        }
        // 校验邮箱是否重复
        if (user.getEmail() != null) { // 注意：空串也算有值
            taotaoResult = checkUserInfo(user.getEmail(), 3);
            if (!(boolean) taotaoResult.getData()) {
                return TaotaoResult.build(400, "邮箱重复");
            }
        }
        // 补全TbUser对象的属性
        user.setCreated(new Date());
        user.setUpdated(new Date());
        // 把密码进行MD5加密
        String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5Pass);
        // 插入到数据库
        userMapper.insert(user);
        // 返回结果
        return TaotaoResult.ok();
    }
}
