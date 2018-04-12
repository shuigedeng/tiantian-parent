package com.tiantian.activemq;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;

/**
 * @author 戴礼明
 * @create 2018/4/12 9:27
 */
public class TestSpringActiveMQ {
    @Test
    public void testQueueProducer() {
        // 初始化Spring容器
        ApplicationContext applicationContext = new
                ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
        // 从容器中获得JMSTemplate对象
        JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);
        // 从容器中获得Destination对象
        Queue queue = applicationContext.getBean(Queue.class);
        // 第一个参数：指定发送的目的地
        // 第二个参数：消息的构造器对象，就是创建一个消息
        jmsTemplate.send(queue, new MessageCreator() {

            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage("使用Spring和ActiveMQ整合发送queue消息");
                return textMessage;
            }
        });
    }
}
