import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author 戴礼明
 * @create 2018/4/12 9:31
 */
public class ActiveMQTest {
    @Test
    public void testQueueConsumer() throws Exception {
        // 初始化Spring容器
        ApplicationContext applicationContext = new
                ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
        // 一旦Spring容器初始化成功之后，程序就结束了，但我们不能让其结束，所以得让它在这等着。
        // 你什么时候发消息，我就什么时候接收。
        System.in.read();
    }
}
