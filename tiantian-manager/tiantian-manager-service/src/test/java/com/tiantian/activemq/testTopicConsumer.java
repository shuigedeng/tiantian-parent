package com.tiantian.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;

/**
 * @author 戴礼明
 * @create 2018/4/12 9:23
 */
public class testTopicConsumer {
    public void testTopicConsumer() throws Exception {
        // 创建一个连接工厂对象
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://114.55.253.31:61617");
        // 使用工厂对象创建一个连接
        Connection connection = connectionFactory.createConnection();
        // 开启连接
        connection.start();
        // 使用连接对象创建一个Session对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 创建一个Destination对象，使用topic
        Topic topic = session.createTopic("test-topic");
        // 使用Session对象创建一个消费者
        MessageConsumer consumer = session.createConsumer(topic);
        System.out.println("topic消费者1。。。。");
        // 使用消费者对象接收消息
        consumer.setMessageListener(new MessageListener() {

            @Override
            public void onMessage(Message message) {
                // 打印消息
                TextMessage textMessage = (TextMessage) message;
                String text = "";
                try {
                    text = textMessage.getText();
                    System.out.println(text);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        // 程序等待
        System.in.read();

        // 关闭资源
        consumer.close();
        session.close();
        connection.close();

    }
}
