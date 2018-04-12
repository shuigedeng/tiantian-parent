package com.tiantian.search.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author 戴礼明
 * @create 2018/4/12 9:29
 */
public class MyMessageListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        // 取消息的内容
        try {
            TextMessage textMessage = (TextMessage) message;
            // 取内容
            String text = textMessage.getText();
            System.out.println(text);
            // 其他业务逻辑...
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
