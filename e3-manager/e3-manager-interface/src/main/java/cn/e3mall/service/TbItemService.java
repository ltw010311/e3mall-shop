package cn.e3mall.service;

import cn.e3mall.common.pojo.DataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;

public interface TbItemService {

	TbItem getItemById(long itemId);
	
	DataGridResult getItemByDataGrid( int page, int rows);
	
	E3Result addItem(TbItem item, String desc);
	
	E3Result updateItem(TbItem item, String desc);
	
	E3Result deleteItemById(String ids);

	E3Result dropItemByIds(String ids);
	
	E3Result  groundItemByIds(String ids);
	//从商品描述表中取商品的描述信息
	TbItemDesc getItemDescById(long itemId);
}
