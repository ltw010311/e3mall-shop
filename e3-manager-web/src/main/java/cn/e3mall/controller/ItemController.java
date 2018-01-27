package cn.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.DataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.ItemDescService;
import cn.e3mall.service.TbItemService;

/**
 * 商品管理 Cotroller
 * @author LiuTaiwen
 *
 */
@Controller
public class ItemController {

	@Autowired
	private TbItemService itemService;
	@Autowired
	private ItemDescService itemDescService;
	
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable Long itemId){
		TbItem tbItem = itemService.getItemById(itemId);
		return tbItem;
	}
	//展示商品列表
	@RequestMapping("/item/list")
	@ResponseBody
	public DataGridResult getItemList(int page,int rows){
		DataGridResult dataGrid = itemService.getItemByDataGrid(page, rows);
		return dataGrid;
	}
	
	//添加商品信息
	@RequestMapping("/item/save")
	@ResponseBody
	public E3Result addItem(TbItem item, String desc){
		E3Result result = itemService.addItem(item, desc);
		return result;
	}
	
	//修改商品信息
	@RequestMapping("/rest/item/update")
	@ResponseBody
	public E3Result updateItem(TbItem item, String desc){
		E3Result result = itemService.updateItem(item, desc);
		return result;
	}
	
	
	//回显商品描述
	@RequestMapping("/rest/item/query/item/desc/{id}")
	@ResponseBody
	public E3Result showItemDescById(@PathVariable Long id){
		System.out.println(id);
		E3Result result = itemDescService.showItemDescById(id);
		return result;
	}
	
	//下架商品
	@RequestMapping("/rest/item/instock")
	@ResponseBody
	public E3Result dropItemByIds(String ids){
		System.out.println(ids);
		E3Result result = itemService.dropItemByIds(ids);
		return result;
	}
	//商品上架
	@RequestMapping("/rest/item/reshelf")
	@ResponseBody
	public E3Result groundItemByIds(String ids){
		System.out.println(ids);
		E3Result result = itemService.groundItemByIds(ids);
		return result;
	}

	//删除商品
	@RequestMapping("/rest/item/delete")
	@ResponseBody
	public E3Result deleteItem(String ids){
		E3Result result = itemService.deleteItemById(ids);
		return result;
	}

}
