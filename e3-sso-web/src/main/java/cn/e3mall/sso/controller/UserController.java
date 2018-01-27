package cn.e3mall.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.stylesheets.MediaList;

import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.UserService;

/**
 * 用户注册功能Controller
 * @author LiuTaiwen
 *
 */
@Controller
public class UserController {
	@Autowired
	private UserService userService;

	//展示注册页面
	@RequestMapping("/page/register")
	public String showRegister(){
		return "register";
	}
	//检验用户名和电话是否已经注册
	@RequestMapping("/user/check/{data}/{type}")
	@ResponseBody
	public E3Result checkRegister(@PathVariable String data,@PathVariable int type){
		E3Result e3Result = userService.checkDate(data, type);
		return e3Result;
	}
	//注册用户
	@RequestMapping(value="/user/register",method=RequestMethod.POST)
	@ResponseBody
	public E3Result addUser(TbUser user){
		E3Result result = userService.addUser(user);
		return result;
	}
	//展示登录页面
	@RequestMapping("/page/login")
	public String showLogin(){
		return "login";
	}
	
	//用户登录
	@RequestMapping(value="/user/login",method=RequestMethod.POST)
	@ResponseBody
	public E3Result login(String username, String password,HttpServletRequest request,HttpServletResponse response) {
		E3Result e3Result = userService.login(username, password);
		//判断用户是否成功
		if(e3Result.getStatus()==200){
			String token = e3Result.getData().toString();
			//把token写入session中
			CookieUtils.setCookie(request, response, "token", token);
		}
		return e3Result;
	}
	
	@RequestMapping(value="/user/token/{token}",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String getUserByToken(@PathVariable String token,String callback){
		E3Result e3Result = userService.getUserByToken(token);
		if(StringUtils.isNotBlank(callback)){
			//说明是json请求，返回js
			String str = callback + "(" + JsonUtils.objectToJson(e3Result)+ ");";
			return str;
		}
		return JsonUtils.objectToJson(e3Result);
	}
}
