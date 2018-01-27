package cn.e3mall.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.e3mall.common.jedis.JedisClient;

public class JedisTestClient {

	@Test
	public void testJedis(){
		//初始化spring容器
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
		JedisClient jedisClient = applicationContext.getBean(JedisClient.class);
		//从容器中获取对象
		jedisClient.set("myjedis", "搭得我好费劲");
		String str = jedisClient.get("myjedis");
		System.out.println(str);
	}
	
}
