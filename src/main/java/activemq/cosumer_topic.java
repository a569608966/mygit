package activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class cosumer_topic {
    private static String ACTIVEMQ_URL="tcp://127.0.0.1:61616";
    private static String TOPIC_NAME="myTopic";
    public static void main(String[] args) throws Exception {
        ActiveMQConnectionFactory activeMqConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //关闭那一堆的Advisorie
        activeMqConnectionFactory.setWatchTopicAdvisories(false);
        Connection connection = activeMqConnectionFactory.createConnection();
        //持久化订阅，即使不在线，下次上线也能收到消息
        connection.setClientID("durableSubscriber2");
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);
        //创建消费者-持久订阅的消费者
        TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic,"hoho");
        //MessageConsumer consumer = session.createConsumer(topic);
        //第二种：监听方式
        //队列方式：有多个消费者，会类似负载功能，平均分配消息
        //主题方式：所有订阅的消费者都会收到消息，先有消息再有订阅，之前的消息就会成为废消息
        topicSubscriber.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                if (message != null && message instanceof TextMessage) {
                    try {
                        TextMessage textMessage = (TextMessage) message;
                        System.out.println("》》》》接收消息：" + textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        System.in.read();
        topicSubscriber.close();
        session.close();
        connection.close();
    }
}
