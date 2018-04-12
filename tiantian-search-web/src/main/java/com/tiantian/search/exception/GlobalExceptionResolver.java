package com.tiantian.search.exception;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 戴礼明
 * @create 2018/4/12 9:03
 */
public class GlobalExceptionResolver implements HandlerExceptionResolver {
    private static Logger logger = Logger.getLogger(GlobalExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        // 写日志文件
        logger.error("运行时异常", e);
        // 发短信、发邮件
        // 发短信：调用第三方运营商服务；发邮件使用jmail包
        // 跳转到友好地错误页面
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/exception");
        modelAndView.addObject("message", "您的网络异常，请稍后重试。。。。。");
        return modelAndView;
    }
}
