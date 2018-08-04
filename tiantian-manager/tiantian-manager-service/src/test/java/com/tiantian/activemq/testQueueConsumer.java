package com.tiantian.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;

/**
 * @author 戴礼明
 * @create 2018/4/12 9:21
 */
public class testQueueConsumer {
    public void testQueueConsumer() throws Exception {
        // 1.创建一个连接工厂对象
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://114.55.253.31:61617");
        // 2.使用连接工厂对象创建一个连接
        Connection connection = connectionFactory.createConnection();
        // 开启连接
        connection.start();
        // 3.使用连接对象创建一个Session对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 4.使用Session对象创建一个Destination对象，使用Queue
        Queue queue = session.createQueue("test-queue");
        // 5.使用Session对象创建一个消费者
        MessageConsumer consumer = session.createConsumer(queue);
        // 6.使用消费者对象接收消息
        consumer.setMessageListener(new MessageListener() {

            @Override
            public void onMessage(Message message) {
                // 7.打印消息
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
    /*
     * 程序等待接收用户结束操作，等待键盘输入
     * 程序自己并不知道什么时候有消息，也不知道什么时候不再发送消息了，这就需要手动干预了，
     * 当我们想停止接收消息时，可以在控制台输入任意键，然后回车即可结束接收操作（也可以直接按回车）。
     */
        System.in.read();

    /*
    // 下面在实际开发中一般不推荐使用
    while(true) {
        Message message = consumer.receive(3000);
        if (message == null) {
            break;
        }
        // 7.打印消息
        TextMessage textMessage = (TextMessage) message;
        String text = "";
        try {
            text = textMessage.getText();
            System.out.println(text);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
    */
        // 8.关闭资源
        consumer.close();
        session.close();
        connection.close();
    }
}
