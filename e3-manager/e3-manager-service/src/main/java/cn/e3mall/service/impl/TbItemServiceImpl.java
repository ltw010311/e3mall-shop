package cn.e3mall.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.pojo.DataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.IDUtils;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.mapper.TbItemDescMapper;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.pojo.TbItemDescExample;
import cn.e3mall.pojo.TbItemExample;
import cn.e3mall.pojo.TbItemExample.Criteria;
import cn.e3mall.service.TbItemService;
/**
 * 商品管理Service
 * @author LiuTaiwen
 *
 */
@Service
public class TbItemServiceImpl implements TbItemService {

	@Autowired
	private TbItemMapper tbItemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private JmsTemplate jmsTemplate;
	@Resource
	private Destination topicDestination;
	@Autowired
	private JedisClient jedisClient;
	
	private cn.e3mall.pojo.TbItemDescExample.Criteria createCriteria;
	
	@Value("${EXPIRE_TIME}")
	private Integer EXPIRE_TIME;
	@Override
	public TbItem getItemById(long itemId) {
		//查询缓存
		try {
			//当查询商品数据的时候，先从缓存中进行查找，如果缓存中没有数据，在从数据库中进行查找
			String json = jedisClient.get("item_info:"+ itemId + ":base");
			//判断查找中的数据是否为空
			if(json !=null && ! "".equals(json)){
				return JsonUtils.jsonToPojo(json, TbItem.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//根据主键查询
		TbItem item = tbItemMapper.selectByPrimaryKey(itemId);
		//当缓存中没有商品数据，从数据库中查找到数据信息，放入到缓存中
		try {
			//把结果放入缓存中
			jedisClient.set("item_info:"+ itemId + ":base", JsonUtils.objectToJson(item));
			//设置缓存的有效时间
			jedisClient.expire("item_info:"+ itemId + ":base", EXPIRE_TIME);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return item;
	/*	TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		//设置查询条件
		criteria.andIdEqualTo(itemId);
		//执行查询
		List<TbItem> list = tbItemMapper.selectByExample(example);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;*/
	}

	//查询商品列表信息
	@Override
	public DataGridResult getItemByDataGrid(int page, int rows) {
		//1.设置分页信息
		PageHelper.startPage(page, rows);
		//2.执行查询
		TbItemExample example = new TbItemExample();
		List<TbItem> list = tbItemMapper.selectByExample(example);
		//3.获取分页结果
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		//4.获取总的条数
		long total = pageInfo.getTotal();
		//5.创建DataGridResult对象
		DataGridResult result = new DataGridResult();
		//6.设置DataGridResult参数
		result.setTotal(total);
		result.setRows(list);
		//7.返回result
		return result;
	}

	@Override
	public E3Result addItem(TbItem item, String desc) {
		//1.生成商品的id
		final long id = IDUtils.genItemId();
		//2.把商品Ttem对象的的属性补充完整
		item.setId(id);
		item.setStatus((byte) 1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		//3.向商品表中插入数据
		tbItemMapper.insert(item);
		//4.创建TbItemDesc对象
		TbItemDesc tbItemDesc = new TbItemDesc();
		//5.把TbItemDesc对象的属性补充完整
		tbItemDesc.setItemId(id);
		tbItemDesc.setItemDesc(desc);
		tbItemDesc.setCreated(new Date());
		tbItemDesc.setUpdated(new Date());
		//6.向TbItemDesc表中插入数据
		itemDescMapper.insert(tbItemDesc);
		//发送商品的添加信息
		jmsTemplate.send(topicDestination, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				// 发送信息
				return session.createTextMessage(id +"");
			}
		});
		//7.返回结果
		return E3Result.ok();
	}

	@Override
	public E3Result deleteItemById(String ids) {
		//1.截取数组
		String[] strIds = ids.split(",");
		//2.便利得到各个商品的id
		for (String id : strIds) {
			//3.创建TbItemExample对象
			TbItemExample example = new TbItemExample();
			//4.设置删除挑条件
			Criteria criteria = example.createCriteria();
			criteria.andIdEqualTo(Long.valueOf(id));
			//5.执行删除
			tbItemMapper.deleteByExample(example);
			
			TbItemDescExample descExample = new TbItemDescExample();
			createCriteria = descExample.createCriteria();
			createCriteria.andItemIdEqualTo(Long.valueOf(id));
			itemDescMapper.deleteByExample(descExample);
		}
		//6.返回结果
		return E3Result.ok();
	}

	//商品下架
	@Override
	public E3Result dropItemByIds(String ids) {
		//1.截取id获得各个商品的id
		String[] strIds = ids.split(",");
		//2.获取id
		for (String id : strIds) {
			//2.创建Item对象
			TbItem item = new TbItem();
			TbItemExample example = new TbItemExample();
			Criteria criteria = example.createCriteria();
			//3.设置商品为下架状态
			item.setStatus((byte) 2);
			criteria.andIdEqualTo(Long.valueOf(id));
			tbItemMapper.updateByExampleSelective(item, example);
		}
		return E3Result.ok();
	}

	@Override
	public E3Result groundItemByIds(String ids) {
		//1.截取id获得各个商品的id
		String[] strIds = ids.split(",");
		for (String id : strIds) {
			TbItem item = new TbItem();
			TbItemExample example = new TbItemExample();
			Criteria criteria = example.createCriteria();
			criteria.andIdEqualTo(Long.valueOf(id));
			item.setStatus((byte) 1);
			tbItemMapper.updateByExampleSelective(item, example);
		}
		return E3Result.ok();
	}

	//更新商品信息
	@Override
	public E3Result updateItem(TbItem item, String desc) {
		item.setUpdated(new Date());
		tbItemMapper.updateByPrimaryKeySelective(item);
		TbItemDesc tbItemDesc = new TbItemDesc();
		//5.把TbItemDesc对象的属性补充完整
		tbItemDesc.setItemId(item.getId());
		tbItemDesc.setItemDesc(desc);
		tbItemDesc.setUpdated(new Date());
		itemDescMapper.updateByPrimaryKeySelective(tbItemDesc);
		return E3Result.ok();
	}

	//根据商品的id查询商品的描述
	@Override
	public TbItemDesc getItemDescById(long itemId) {
		//查询缓存
		try {
			//当查询商品数据的时候，先从缓存中进行查找，如果缓存中没有数据，在从数据库中进行查找
			String json = jedisClient.get("item_info:"+ itemId + ":desc");
			//判断查找中的数据是否为空
			if(json !=null && ! "".equals(json)){
				return JsonUtils.jsonToPojo(json, TbItemDesc.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
		//当缓存中没有商品数据，从数据库中查找到数据信息，放入到缓存中
		try {
			//把结果放入缓存中
			jedisClient.set("item_info:"+ itemId + ":desc", JsonUtils.objectToJson(itemDesc));
			//设置缓存的有效时间
			jedisClient.expire("item_info:"+ itemId + ":desc", EXPIRE_TIME);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemDesc;
	}
	
	
}
