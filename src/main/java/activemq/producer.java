package activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.UUID;

public class producer {
    private static String ACTIVEMQ_URL="tcp://127.0.0.1:61616";
    private static String QUEUE_NAME="myQueue";
    public static void main(String[] args) throws Exception {
        ActiveMQConnectionFactory activeMqConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMqConnectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
        MessageProducer producer = session.createProducer(queue);
        for(int i = 1;i <2; i++ ){
            TextMessage message = session.createTextMessage();
            message.setJMSDeliveryMode(DeliveryMode.PERSISTENT);//持久化
            message.setJMSPriority(9);//优先级，默认为4,1-4为低优先，5-9高优先
            message.setJMSMessageID(UUID.randomUUID().toString());//消息编号
            message.setText("queue消息内容"+i);
            producer.send(message);
            System.out.println("发送消息："+message.getText());
        }
        producer.close();
        session.close();
        connection.close();
    }
}
