package cn.e3mall.search.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.e3mall.search.pojo.SearchResult;
import cn.e3mall.search.service.SearchService;

/**
 * 商品搜索Controller
 * @author LiuTaiwen
 *
 */
@Controller
public class SearchController {

	@Autowired
	private SearchService searchService;
	@Value("${SEARCH_NUM}")
	private Integer rows;
	
	@RequestMapping("/search")
	public String search(String keyword,@RequestParam(defaultValue="1")int page,Model model) throws Exception{
		//int i= 1/0;
		if(StringUtils.isNotBlank(keyword)){
			keyword = new String(keyword.getBytes("iso-8859-1"), "utf-8");
		}
		//执行查询
		SearchResult result = searchService.search(keyword, page, rows);
		//把查询结果返回给jsp页面
		model.addAttribute("query", keyword);
		model.addAttribute("page", page);
		model.addAttribute("totalPages", result.getTotalPages());
		model.addAttribute("itemList", result.getItemList());
		model.addAttribute("recourdCount", result.getRecourdCount());
		//返回结果视图
		return "search";
	}
}
