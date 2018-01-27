package cn.e3mall.test;

import java.io.IOException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestConsumer {

	@Test
	public void TestQueueConsumer(){
		//初始化一个spring容器
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activeMqSpring.xml");
		
		//等待
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
