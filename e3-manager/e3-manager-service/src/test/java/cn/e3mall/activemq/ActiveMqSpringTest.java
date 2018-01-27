package cn.e3mall.activemq;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class ActiveMqSpringTest {

	@Test
	public void testSpringActiveMq(){
		//1.初始化spring容器
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activeMqSpring.xml");
		//2.从spring容器中获取JMSTemplate对象
		JmsTemplate jmsTemplatebean = applicationContext.getBean(JmsTemplate.class);
		//3.从spring容器中获取destination对象
		Destination destination= (Destination) applicationContext.getBean("queueDestination");
		//4.使用jmsTemplate对象发送消息
		jmsTemplatebean.send(destination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				// 发送消息
				return session.createTextMessage("send activemq message");
			}
		});
	}
}
