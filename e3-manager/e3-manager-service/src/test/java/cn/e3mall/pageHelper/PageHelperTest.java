package cn.e3mall.pageHelper;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemExample;

public class PageHelperTest {

	@Test
	public void pageHelper(){
		//设置分页参数、
		PageHelper.startPage(1, 10);
		
		//执行查询
		//初始化spring容器
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		TbItemMapper itemMapper = applicationContext.getBean(TbItemMapper.class);
		TbItemExample example = new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(example);
		//取分页信息
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		
		//返回结果
		System.out.println(pageInfo.getTotal());//总的条数
		System.out.println(pageInfo.getPages());//总的页数
		System.out.println(pageInfo.getFirstPage());
		System.out.println(pageInfo.getLastPage());
		System.out.println(pageInfo.getPageSize());
		System.out.println(pageInfo.getPageNum());
		System.out.println(list.size());
	}
}
