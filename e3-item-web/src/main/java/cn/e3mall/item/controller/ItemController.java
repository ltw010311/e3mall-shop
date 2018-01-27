package cn.e3mall.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.e3mall.item.pojo.Item;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.TbItemService;

/**
 * 商品详情页展示Controller
 * @author LiuTaiwen
 *
 */
@Controller
public class ItemController {

	@Autowired
	private TbItemService itemService;
	
	@RequestMapping("/item/{itemId}")
	public String showItemInfo(@PathVariable Long itemId,Model model){
		//System.out.println(itemId);
		//根据商品的itemid查询商品信息
		TbItem tbItem = itemService.getItemById(itemId);
		Item item = new Item(tbItem);
		//根据商品的itemId查询商品描述
		TbItemDesc itemDesc = itemService.getItemDescById(itemId);
		//把查询到的数据传递给jsp页面中
		model.addAttribute("item", item);
		model.addAttribute("itemDesc", itemDesc);
		//返回结果视图
		return "item";
	}
}
