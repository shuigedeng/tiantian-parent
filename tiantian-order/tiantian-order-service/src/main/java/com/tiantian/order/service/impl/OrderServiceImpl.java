package com.tiantian.order.service.impl;

import com.tiantian.common.pojo.TaotaoResult;
import com.tiantian.content.jedis.JedisClient;
import com.tiantian.mapper.TbOrderItemMapper;
import com.tiantian.mapper.TbOrderMapper;
import com.tiantian.mapper.TbOrderShippingMapper;
import com.tiantian.order.pojo.OrderInfo;
import com.tiantian.order.service.OrderService;
import com.tiantian.pojo.TbOrderItem;
import com.tiantian.pojo.TbOrderShipping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author 戴礼明
 * @create 2018/4/12 12:59
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private TbOrderMapper orderMapper;
    @Autowired
    private TbOrderItemMapper orderItemMapper;
    @Autowired
    private TbOrderShippingMapper orderShippingMapper;
    @Autowired
    private JedisClient jedisClient;

    @Value("${ORDER_GEN_KEY}")
    private String ORDER_GEN_KEY;
    @Value("${ORDER_ID_INIT}")
    private String ORDER_ID_INIT;
    @Value("${ORDER_DETAIL_GEN_KEY}")
    private String ORDER_DETAIL_GEN_KEY;

    @Override
    public TaotaoResult createOrder(OrderInfo orderInfo) {
        // 生成一个订单号，使用Redis的incr命令来生成
        // ORDER_GEN_KEY就是对应订单号生成的key
        // 判断订单号生成的key是否存在
        if (!jedisClient.exists(ORDER_GEN_KEY)) {
            // 如果不存在，要设置初始值
            jedisClient.set(ORDER_GEN_KEY, ORDER_ID_INIT);
        }
        String orderId = jedisClient.incr(ORDER_GEN_KEY).toString();
        // 向订单表插入数据
        orderInfo.setOrderId(orderId);
        // 免邮费
        orderInfo.setPostFee("0");
        // 订单状态
        // 状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
        orderInfo.setStatus(1);
        Date date = new Date();
        orderInfo.setCreateTime(date);
        orderInfo.setUpdateTime(date);
        // 插入数据
        orderMapper.insert(orderInfo);
        // 向订单明细表插入数据
        List<TbOrderItem> orderItems = orderInfo.getOrderItems();
        for (TbOrderItem tbOrderItem : orderItems) {
            // 生成一个订单明细表的主键
            Long orderDetailId = jedisClient.incr(ORDER_DETAIL_GEN_KEY);
            tbOrderItem.setId(orderDetailId.toString());
            // 设置订单id
            tbOrderItem.setOrderId(orderId);
            // 插入数据
            orderItemMapper.insert(tbOrderItem);
        }
        // 向订单物流信息表插入数据
        TbOrderShipping orderShipping = orderInfo.getOrderShipping();
        orderShipping.setOrderId(orderId);
        orderShipping.setCreated(date);
        orderShipping.setUpdated(date);
        orderShippingMapper.insert(orderShipping);
        // 返回订单号
        return TaotaoResult.ok(orderId);
    }
}
