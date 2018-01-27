package cn.e3mall.content.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jboss.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.pojo.DataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.mapper.TbContentMapper;
import cn.e3mall.pojo.TbContent;
import cn.e3mall.pojo.TbContentExample;
import cn.e3mall.pojo.TbContentExample.Criteria;
import cn.e3mall.service.content.ContentService;
/**
 * 内容服务
 * @author LiuTaiwen
 *
 */
@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${CONTENT_LIST}")
	private String CONTENT_LIST;
	//查询内容
	@Override
	public DataGridResult getContentListDataGrid(Long categoryId, int page, int rows) {
		//1.设置分页参数,使用PageHelper的startPage()方法
		PageHelper.startPage(page, rows);
		//2.设置查询条件，根据分类的id进行查询
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		//3.执行查询
		List<TbContent> list = contentMapper.selectByExample(example);
		//4.取分页的结果
		PageInfo<TbContent> pageInfo = new PageInfo<>(list);
		long total = pageInfo.getTotal();
		//5.创建一个DataGridResult对象
		DataGridResult result = new DataGridResult();
		//6.设置对象属性
		result.setTotal(total);
		result.setRows(list);
		//7.返回结果
		return result;
	}

	//添加内容
	@Override
	public E3Result addContent(TbContent content) {
		//1.设置补全对象的属性
		content.setCreated(new Date());
		content.setUpdated(new Date());
		//2.执行添加操作
		contentMapper.insert(content);
		//缓存同步，删除缓存中的数据
		jedisClient.hdel(CONTENT_LIST, content.getCategoryId().toString());
		//3.返回结果
		return E3Result.ok();
	}

	//删除内容
	@Override
	public E3Result deleteContent(String ids) {
		// 1.截取获得id
		String[] strs = ids.split(",");
		//2.根据id进行删除
		for (String id : strs) {
			TbContentExample example = new TbContentExample();
			Criteria criteria = example.createCriteria();
			criteria.andIdEqualTo(Long.valueOf(id));
			contentMapper.deleteByExample(example);
		}
		return E3Result.ok();
	}

	//查询轮播图内容列表
	@Override
	public List<TbContent> getContentListByCid(Long cid) {
		//查询缓存
		try {
			//如果缓存中有直接响应结果
			String json = jedisClient.hget(CONTENT_LIST, cid +"");
			if(StringUtils.isNotBlank(json)){
				List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 1.设置查询条件
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(cid);
		//2.执行查询
		List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);
		try {
			//如果没有缓存，把结果添加到缓存中
			jedisClient.hset(CONTENT_LIST, cid + "", JsonUtils.objectToJson(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		//3.返回结果
		return list;
	}

	
	
}
