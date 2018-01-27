package cn.e3mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.service.ItemCatService;

/**
 * 商品列表管理
 * @author LiuTaiwen
 *
 */
@Controller
public class ItemCatController {

	@Autowired
	private ItemCatService itemCatService;
	
	@RequestMapping("/item/cat/list")
	@ResponseBody							//easyUI中，需要的是一个id，我们传的不是id，要设置RequestParam转换，第一次加载时没有id，要给默认值：0
	public List<EasyUITreeNode> getItemCatList(@RequestParam(name="id", defaultValue="0")Long parentId){
		List<EasyUITreeNode> list = itemCatService.getItemCatList(parentId);
		return list;
	}
	
}
