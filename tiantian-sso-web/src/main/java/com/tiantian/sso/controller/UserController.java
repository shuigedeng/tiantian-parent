package com.tiantian.sso.controller;

import com.tiantian.common.pojo.TaotaoResult;
import com.tiantian.common.utils.CookieUtils;
import com.tiantian.common.utils.JsonUtils;
import com.tiantian.pojo.TbUser;
import com.tiantian.sso.service.UserLoginService;
import com.tiantian.sso.service.UserRegisterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 戴礼明
 * @create 2018/4/12 10:45
 */
@Controller
public class UserController {
    @Autowired
    private UserRegisterService userRegisterService;

    @Autowired
    private UserLoginService userLoginService;

    @Value("${COOKIE_TOKEN_KEY}")
    private String COOKIE_TOKEN_KEY;

    @RequestMapping(value="/user/check/{param}/{type}", method= RequestMethod.GET)
    @ResponseBody
    public TaotaoResult checkUserInfo(@PathVariable String param, @PathVariable Integer type) {
        TaotaoResult result = userRegisterService.checkUserInfo(param, type);
        return result;
    }

    @RequestMapping(value="/user/register", method= RequestMethod.POST)
    @ResponseBody
    public TaotaoResult registerUser(TbUser tbUser){
        TaotaoResult user = userRegisterService.createUser(tbUser);
        return user;
    }

    @RequestMapping(value="/user/login", method=RequestMethod.POST)
    @ResponseBody
    public TaotaoResult userLogin(String username, String password,
                                  HttpServletRequest request, HttpServletResponse response) {
        TaotaoResult taotaoResult = userLoginService.login(username, password);
        // 取出token
        if(taotaoResult.getStatus() == 200){
            String token = taotaoResult.getData().toString();
            // 在返回结果之前，设置cookie(即将token写入cookie)
            // 1.cookie怎么跨域？
            // 2.如何设置cookie的有效期？
            CookieUtils.setCookie(request, response, COOKIE_TOKEN_KEY, token);
        }
        // 返回结果
        return taotaoResult;
    }

    @RequestMapping(value="/user/token/{token}")
    @ResponseBody
    public Object getUserByToken(@PathVariable String token, String callback){
        TaotaoResult userByToken = userLoginService.getUserByToken(token);
        if (StringUtils.isNotBlank(callback)) {
            // 设置要包装的数据
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(userByToken);
            // 设置回调方法
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        }
        return userByToken;
    }

    @RequestMapping(value="/user/logout/{token}")
    @ResponseBody
    public TaotaoResult logout(@PathVariable String token){
        TaotaoResult logout = userLoginService.logout(token);
        return logout;
    }
}
