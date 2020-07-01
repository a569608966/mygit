package activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.UUID;

public class producer_topic {
    private static String ACTIVEMQ_URL="tcp://127.0.0.1:61616";
    private static String TOPIC_NAME="myTopic";
    public static void main(String[] args) throws Exception {
        ActiveMQConnectionFactory activeMqConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        activeMqConnectionFactory.setWatchTopicAdvisories(false);
        Connection connection = activeMqConnectionFactory.createConnection();
        connection.start();

        //是否开启事务，签收方式
        //开启事务后，必须手动commit提交,commit前程序出错，消息不会进队列
        Session session = connection.createSession(true,Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);
        MessageProducer producer = session.createProducer(topic);

        for(int i = 1;i <2; i++ ){
            //System.out.println(6/(6-i));
            TextMessage message = session.createTextMessage();
            message.setJMSDeliveryMode(DeliveryMode.PERSISTENT);//持久化
            message.setJMSPriority(9);//优先级，默认为4,1-4为低优先，5-9高优先
            message.setJMSMessageID(UUID.randomUUID().toString());//消息编号
            message.setText("topic消息内容"+i);
            producer.send(message);
            System.out.println("发送消息："+message.getText());
        }
        producer.close();
        session.commit();
        session.close();
        connection.close();
    }
}
