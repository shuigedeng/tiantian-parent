package com.tiantian.order.controller;

import com.tiantian.common.pojo.TaotaoResult;
import com.tiantian.common.utils.CookieUtils;
import com.tiantian.common.utils.JsonUtils;
import com.tiantian.order.pojo.OrderInfo;
import com.tiantian.order.service.OrderService;
import com.tiantian.pojo.TbItem;
import com.tiantian.pojo.TbUser;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 戴礼明
 * @create 2018/4/12 11:52
 */
@Controller
public class OrderController {
    @Value("${COOKIE_CART_KEY}")
    private String COOKIE_CART_KEY;

    @Autowired
    private OrderService orderService;

    @RequestMapping("/order/order-cart")
    public String showOrderCart(HttpServletRequest request) {
        // 取购物车商品列表
        List<TbItem> cartList = getCartList(request);
        // 取用户id
        // TODO
        TbUser user = (TbUser) request.getAttribute("user");
        System.out.println(user.getUsername());
        // 根据用户id来查询收货地址列表，现在数据库里面没有收货地址列表这个表，所以只能是使用静态数据
        // 从数据库中查询支付方式列表
        // 传递给页面
        request.setAttribute("cartList", cartList);
        // 返回一个逻辑视图
        return "order-cart";
    }

    // 从Cookie中取出购物车列表
    private List<TbItem> getCartList(HttpServletRequest request) {
        // 使用CookieUtils取购物车列表
        String json = CookieUtils.getCookieValue(request, COOKIE_CART_KEY, true);
        // 判断Cookie中是否有值
        if (StringUtils.isBlank(json)) {
            // 没有值就返回一个空List
            return new ArrayList();
        }
        // 把json转换成List对象
        List<TbItem> list = JsonUtils.jsonToList(json, TbItem.class);
        return list;
    }

    @RequestMapping(value="/order/create", method= RequestMethod.POST)
    public String createOrder(OrderInfo orderInfo, Model model, HttpServletRequest request) {
        // 取用户id
        TbUser user = (TbUser) request.getAttribute("user");
        orderInfo.setUserId(user.getId());
        orderInfo.setBuyerNick(user.getUsername());
        // 调用服务生成订单
        TaotaoResult result = orderService.createOrder(orderInfo);
        // 取订单号并传递给页面
        String orderId = result.getData().toString();
        model.addAttribute("orderId", orderId);
        model.addAttribute("payment", orderInfo.getPayment());
        // 预计送达时间是三天后
        DateTime dateTime = new DateTime();
        dateTime = dateTime.plusDays(3);
        model.addAttribute("date", dateTime.toString("yyyy-MM-dd"));
        // 返回逻辑视图
        return "success";
    }
}
