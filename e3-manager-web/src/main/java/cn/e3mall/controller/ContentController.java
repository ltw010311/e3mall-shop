package cn.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.DataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbContent;
import cn.e3mall.service.content.ContentService;

/**
 * 内容管理ContentController
 * @author LiuTaiwen
 *
 */
@Controller
public class ContentController {

	@Autowired
	private ContentService contentService;
	
	//查询内容列表
	@RequestMapping("/content/query/list")
	@ResponseBody
	public DataGridResult getContentListDataGrid(Long categoryId, int page, int rows){
		DataGridResult result = contentService.getContentListDataGrid(categoryId, page, rows);
		return result;
	}
	//添加内容
	@RequestMapping("/content/save")
	@ResponseBody
	public E3Result addContent(TbContent content){
		E3Result result = contentService.addContent(content);
		return result;
	}
	
	//删除内容
	@RequestMapping("/content/delete")
	@ResponseBody
	E3Result deleteContent(String ids){
		E3Result result = contentService.deleteContent(ids);
		return result;
	}
}
