package com.tiantian.order.service;

import com.tiantian.common.pojo.TaotaoResult;
import com.tiantian.order.pojo.OrderInfo;

public interface OrderService {
    TaotaoResult createOrder(OrderInfo orderInfo);
}
