package cn.e3mall.test;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class JedisTest {

	@Test
	public void testJedis(){
		//创建一个连接Jedis对象，参数：host，port
		Jedis jedis = new Jedis("192.168.25.128", 6379);
		//直接使用jedis操作redis，
		jedis.set("test", "my first jedis test");
		String string = jedis.get("test");
		System.out.println(string);
		//关闭连接
		jedis.close();
	}
	/**
	 * 连接单机版使用连接池
	 */
	@Test
	public void testJedisPool(){
		//创建一个连接连接池对象，参数：host，port
		JedisPool jedisPool = new JedisPool("192.168.25.128", 6379);
		//从连接池中获取一个连接
		Jedis jedis = jedisPool.getResource();
		//使用jedis操作redis
		String string = jedis.get("test");
		System.out.println(string);
		//关闭连接
		jedis.close();
		//关闭链接池
		jedisPool.close();
	}
	/**
	 * 连接集群版
	 */
	@Test
	public void testJedisCluster(){
		//1.创建一个JesdisCluster对象，有一个参数是一个set类型，set中包含了若干个HostAndPort对象
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.25.128", 7001));
		nodes.add(new HostAndPort("192.168.25.128", 7002));
		nodes.add(new HostAndPort("192.168.25.128", 7003));
		nodes.add(new HostAndPort("192.168.25.128", 7004));
		nodes.add(new HostAndPort("192.168.25.128", 7005));
		nodes.add(new HostAndPort("192.168.25.128", 7006));
		JedisCluster cluster = new JedisCluster(nodes);
		//2.直接使用JedisCluster对象操作redis
		cluster.set("test0", "123456");
		String str = cluster.get("test0");
		System.out.println(str);
		//关闭JedisCluster对象
		cluster.close();
		
		
	}
}
