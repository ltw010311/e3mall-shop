package cn.e3mall.sso.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.mapper.TbUserMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.pojo.TbUserExample;
import cn.e3mall.pojo.TbUserExample.Criteria;
import cn.e3mall.sso.service.UserService;
/**
 * 用户管理Service
 * @author LiuTaiwen
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Value("${SESSION_TIME}")
	private Integer SESSION_TIME;
	@Autowired
	private TbUserMapper userMapper;
	@Autowired
	private JedisClient jedisClient;
	
	@Override
	public E3Result checkDate(String data, int type) {
		//1.判断type的值，1：用户名；2：手机；3：邮箱
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		//2.根据不同的查询条件进行查询
		if(type ==1){
			criteria.andUsernameEqualTo(data);
		}else if(type ==2){
			criteria.andPhoneEqualTo(data);
		}else if(type ==3){
			criteria.andEmailEqualTo(data);
		}else{
			return E3Result.build(400, "数据类型错误");
		}
		//3.执行查询
		List<TbUser> list = userMapper.selectByExample(example);
		//4.判断查询的结果中是否有数据
		if(list ==null || list.size()==0){
			//5.如果没有数据，返回true
			return E3Result.ok(true);
		}
		//6.如果有数据，返回false
		return E3Result.ok(false);
	}
	
	@Override
	public E3Result addUser(TbUser user) {
		//1.密码需要m5进行加密
		String password = user.getPassword();
		String md5Pass = DigestUtils.md5DigestAsHex(password.getBytes());
		//2.补全属性数据
		user.setPassword(md5Pass);
		user.setCreated(new Date());
		user.setUpdated(new Date());
		//3.向数据库中插入数据
		userMapper.insert(user);
		//4.返回成功
		return E3Result.ok();
	}

	//用户登录
	@Override
	public E3Result login(String username, String password) {
		//1.接收到用户名和密码
		//2.判断用户名是否存在
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<TbUser> list = userMapper.selectByExample(example);
		//3.如果没有查询到结果，用户名不存在
		if(list == null || list.size()==0){
			return E3Result.build(400, "用户名或密码有误");
		}
		//4.如果用户名存在，判断密码是否存在
		TbUser user = list.get(0);
		String md5Pass = user.getPassword();
		if(!md5Pass.equals(DigestUtils.md5DigestAsHex(password.getBytes()))){
			return E3Result.build(400, "用户名或密码有误");
		}
		//5.如果密码正确，登录成功
		//6.生成token，使用uuid
		String token = UUID.randomUUID().toString();
		//7.把用户的信息保存在redis中，使用hash数据类型
		user.setPassword("");
		jedisClient.hset("session:"+token, "user", JsonUtils.objectToJson(user));
		//8.设置key的过期时间
		jedisClient.expire("session:"+token, SESSION_TIME);
		//9.返回e3Result
		return E3Result.ok(token);
	}

	//获取token
	@Override
	public E3Result getUserByToken(String token) {
		//1.根据token查询redis中的信息
		String json = jedisClient.hget("session:"+token, "user");
		//2.如果没有取到用户信息，说明用户信息已经过期
		if(StringUtils.isBlank(json)){
			return E3Result.build(400, "用户登录已经过期!");
		}
		//3.如果取到。重置session的过期时间
		jedisClient.expire("session:"+token, SESSION_TIME);
		//4.返回用户信息
		TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
		return E3Result.ok(user);
	}

}
