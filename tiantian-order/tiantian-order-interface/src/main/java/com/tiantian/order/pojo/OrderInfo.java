package com.tiantian.order.pojo;

import com.tiantian.pojo.TbOrder;
import com.tiantian.pojo.TbOrderItem;
import com.tiantian.pojo.TbOrderShipping;

import java.io.Serializable;
import java.util.List;

/**
 * @author 戴礼明
 * @create 2018/4/12 12:58
 */
public class OrderInfo extends TbOrder implements Serializable {

    // 商品列表
    private List<TbOrderItem> orderItems;
    // 收货地址
    private TbOrderShipping orderShipping;

    public List<TbOrderItem> getOrderItems() {
        return orderItems;
    }
    public void setOrderItems(List<TbOrderItem> orderItems) {
        this.orderItems = orderItems;
    }
    public TbOrderShipping getOrderShipping() {
        return orderShipping;
    }
    public void setOrderShipping(TbOrderShipping orderShipping) {
        this.orderShipping = orderShipping;
    }

}
