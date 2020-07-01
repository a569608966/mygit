package activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class cosumer {
    private static String ACTIVEMQ_URL="tcp://127.0.0.1:61616";
    private static String QUEUE_NAME="queue01";
    public static void main(String[] args) throws Exception {
        System.out.println("消费者2");
        ActiveMQConnectionFactory activeMqConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMqConnectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
        MessageConsumer consumer = session.createConsumer(queue);
        //第一种：receive方式
        //receive不带参，会一直等待消息，阻塞进程
        //receive带参，时间到了后，会结束进程
        /*while(true){
            TextMessage textMessage = (TextMessage)consumer.receive();
            if(textMessage != null){
                System.out.println("》》》》接收消息："+textMessage.getText());
            }else{
                break;
            }
        }*/

        //第二种：监听方式
        //队列方式：有多个消费者，会类似负载功能，平均分配消息
        //主题方式：所有订阅的消费者都会收到消息，先有消息再有订阅，之前的消息就会成为废消息
        consumer.setMessageListener(new MessageListener(){
            public void onMessage(Message message) {
                if(message != null && message instanceof TextMessage){
                    try {
                        TextMessage textMessage = (TextMessage)message;
                        System.out.println("》》》》接收消息："+textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        System.in.read();
        consumer.close();
        session.close();
        connection.close();
    }
}
