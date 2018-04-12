package com.tiantian.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;

/**
 * @author 戴礼明
 * @create 2018/4/12 9:22
 */
public class testTopicProducer {
    @Test
    public void testTopicProducer() throws Exception {
        // 创建一个连接工厂对象
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://114.55.253.31:61617");
        // 使用连接工厂对象来创建一个连接
        Connection connection = connectionFactory.createConnection();
        // 开启连接
        connection.start();
        // 使用连接对象创建一个Session对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 使用Session对象来创建一个Topic
        Topic topic = session.createTopic("test-topic");
        // 使用Session对象来创建一个Producer，指定其目的地是Topic
        MessageProducer producer = session.createProducer(topic);
        // 创建一个TextMessage对象
        TextMessage message = session.createTextMessage("使用topic来发送的消息");
        // 使用Producer对象来发送消息
        producer.send(message);
        // 关闭资源
        producer.close();
        session.close();
        connection.close();
    }
}
