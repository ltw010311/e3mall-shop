package cn.e3mall.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.junit.Test;
/**
 * 点到点的形式方消息
 * @author LiuTaiwen
 *
 */
public class ActiveMqTest {

	@Test
	public void testActiveMq() throws Exception{
		//1.创建一个ConnectionFactory对象，指定ActiveMq的地址和端口号
		ConnectionFactory connectionFactory = (ConnectionFactory) new ActiveMQConnectionFactory("tcp://192.168.25.128:61616");
		//2.使用ConnectionFactory对象创建一个Connection对象
		Connection connection = connectionFactory.createConnection();
		//3.开启连接，start方法
		connection.start();
		//4.使用Connection对象创建一个Session对象
		//第一个参数：是否开启事务，如果为true，开启事务，第二个参数无意义，一般不开启事务
		//第二个参数：应答模式，一般分为自动应答模式和手动应答模式
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//5.使用session创建一个Destnation对象，queue。topic，当前应该创建queue
		Queue queue = session.createQueue("test-query");
		//6.使用session创建一个消息的生产者
		MessageProducer producer = session.createProducer(queue);
		//7.创建一个Message对象
		TextMessage message = session.createTextMessage("hello queue");
		//8.发送消息
		producer.send(message);
		//9.关闭资源
		producer.close();
		session.close();
		connection.close();
	}
	
	@Test
	public void testQueueConsumer() throws Exception{
		//1.创建一个ConnectionFactory对象
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.128:61616");
		//2.使用ConnectionFactory对象创建一个Connection对象
		Connection connection = connectionFactory.createConnection();
		//3.开启连接，start方法
		connection.start();
		//4.使用Connection对象创建一个Session对象
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//5.使用Session创建一个Destnation对象，这里创建的是topic对象
		 Queue queue = session.createQueue("spring-queue");
		//6.使用session创建一个消费者
		MessageConsumer consumer = session.createConsumer(queue);
		//7.接收消息
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				//8.打印结果
				TextMessage textMessage = (TextMessage) message;
				String text;
				try {
					 text 	= textMessage.getText();
					 System.out.println(text);
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		//设置等待接受消息
		System.in.read();
		//9.关闭资源
		connection.close();
		session.close();
		connection.close();
	}
	/**
	 * 发送消息
	 * @throws Exception
	 */
	@Test
	public void testQueueTopic() throws Exception{
		//1.创建一个ConnectionFactory对象，指定ActiveMq的地址和端口号
		ConnectionFactory connectionFactory = (ConnectionFactory) new ActiveMQConnectionFactory("tcp://192.168.25.128:61616");
		//2.使用ConnectionFactory对象创建一个Connection对象
		Connection connection = connectionFactory.createConnection();
		//3.开启连接，start方法
		connection.start();
		//4.使用Connection对象创建一个Session对象
		//第一个参数：是否开启事务，如果为true，开启事务，第二个参数无意义，一般不开启事务
		//第二个参数：应答模式，一般分为自动应答模式和手动应答模式
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//5.使用session创建一个Destnation对象，queue。topic，当前应该创建topic
		Topic topic = session.createTopic("test-query");
		//6.使用session创建一个消息的生产者
		MessageProducer producer = session.createProducer(topic);
		//7.创建一个Message对象
		TextMessage message = session.createTextMessage("hello queue");
		//8.发送消息
		producer.send(message);
		//9.关闭资源
		producer.close();
		session.close();
		connection.close();
	}
	/**
	 *消费者消费 
	 */
	@Test
	public void testTopicConsumer() throws Exception{
		//1.创建一个ConnectionFactory对象
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.128:61616");
		//2.使用ConnectionFactory对象创建一个Connection对象
		Connection connection = connectionFactory.createConnection();
		//3.开启连接，start方法
		connection.start();
		//4.使用Connection对象创建一个Session对象
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//5.使用Session创建一个Destnation对象，这里创建的是topic对象
		 Topic topic = session.createTopic("spring-queue");
		//6.使用session创建一个消费者
		MessageConsumer consumer = session.createConsumer(topic);
		//7.接收消息
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				//8.打印结果
				TextMessage textMessage = (TextMessage) message;
				String text;
				try {
					 text 	= textMessage.getText();
					 System.out.println(text);
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		//设置等待接受消息
		System.out.println("topic消费者三启动。。");
		System.in.read();
		//9.关闭资源
		connection.close();
		session.close();
		connection.close();
	}
}
