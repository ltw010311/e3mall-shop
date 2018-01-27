package cn.e3mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.service.content.ContentCatService;

/**
 * 内容分类管理Controller
 * @author LiuTaiwen
 *
 */
@Controller
public class ContentCatController {

	@Autowired
	private ContentCatService contentCatService;
	//显示内容分类列表
	@RequestMapping(value="/content/category/list")
	@ResponseBody
	public List<EasyUITreeNode> getContentCatList(@RequestParam(name="id",defaultValue="0")Long parentId){
		List<EasyUITreeNode> result = contentCatService.getContentCategoryList(parentId);
		return result;
	}
	
	//添加内容分类
	@RequestMapping("/content/category/create")
	@ResponseBody
	public E3Result addContentCategory(Long parentId, String name){
		E3Result result = contentCatService.addContentCategory(parentId, name);
		return result;
	}
	
	//修改内容分类
	@RequestMapping("/content/category/update")
	@ResponseBody
	public E3Result updateContentCategory(Long id, String name){
		E3Result result = contentCatService.updateContentCategory(id, name);
		return result;
	}
	
	//删除内容分类
	@RequestMapping("/content/category/delete")
	@ResponseBody
	public E3Result deleteContentCategory(Long id){
		E3Result result =contentCatService.deleteContentCategory(id);
		return result;
	}
}
