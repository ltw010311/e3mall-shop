package cn.e3mall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.mapper.TbItemDescMapper;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.ItemDescService;
@Service
public class ItemDescServiceImpl implements ItemDescService {

	@Autowired
	private TbItemDescMapper itemDescMapper; 
	//回显商品描述
	@Override
	public E3Result showItemDescById(Long id) {
		//执行查询
		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(id);
		//返回结果
		return E3Result.ok(itemDesc);
	}

}
