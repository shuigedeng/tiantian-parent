package com.tiantian.order.interceptor;

import com.tiantian.common.pojo.TaotaoResult;
import com.tiantian.common.utils.CookieUtils;
import com.tiantian.pojo.TbUser;
import com.tiantian.sso.service.UserLoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 戴礼明
 * @create 2018/4/12 11:46
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Value("${COOKIE_TOKEN_KEY}")
    private String COOKIE_TOKEN_KEY;

    @Value("${SSO_URL}")
    private String SSO_URL;

    @Autowired
    private UserLoginService userLoginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 先从cookie中取token
        String token = CookieUtils.getCookieValue(request, COOKIE_TOKEN_KEY);
        /*
         * 如果没有token，直接跳转到sso系统的登录页面，而且还需要把当前请求的url
         * 做为参数传递给sso系统，用户登录成功之后还要跳转回当前请求的页面，
         * 因此要在请求url中添加一个回调地址。
         */
        if (StringUtils.isBlank(token)) {
            // 跳转到http://localhost:8088/page/login
            String url = SSO_URL + "/page/login?redirect=" + request.getRequestURL().toString();
            // 如何进行跳转呢？
            response.sendRedirect(url);
            // 拦截
            return false;

        }
        // 如果有token，需要调用sso系统的服务，根据token查询用户信息
        TaotaoResult result = userLoginService.getUserByToken(token);
        TbUser user = null;
        if (result != null && result.getStatus() == 200) {
            user = (TbUser) result.getData();
        }
        // 如果查询不到用户，跳转到sso系统的登录页面
        else {
            // 跳转到http://localhost:8088/page/login
            String url = SSO_URL + "/page/login?redirect=" + request.getRequestURL().toString();
            // 如何进行跳转呢？
            response.sendRedirect(url);
            // 拦截
            return false;
        }
        /*
         * 现在拦截器里面能够查询出用户，已经查了一次，如果我们在OrderController里面再取
         * 一遍，那么就重复了，所以我们在拦截器里面取出用户之后，直接将其传递给OrderController。
         */
        // 把user对象放到request中，拦截器里面的request与OrderController里面的request是同一个
        request.setAttribute("user", user);
        // 如果查询到用户，则放行
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }
}
