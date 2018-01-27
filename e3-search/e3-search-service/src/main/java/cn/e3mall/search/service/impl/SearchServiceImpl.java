package cn.e3mall.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.search.dao.SearchDao;
import cn.e3mall.search.pojo.SearchResult;
import cn.e3mall.search.service.SearchService;
@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private SearchDao searchDao;
	@Override
	public SearchResult search(String keyword,int page,int rows) throws Exception {
		//1.创建一个SolrQuery对象
		SolrQuery query = new SolrQuery();
		//2.设置查询条件，参考后台设置
		//3.设置主查询条件
		query.setQuery(keyword);
		//4.设置分页查询条件，start表示起始页索引，rows表示每页显示的数量
		query.setStart((page - 1) * rows);
		query.setRows(rows);
		//5.设置默认搜索域
		query.set("df", "item_title");
		//6.开启高亮显示
		query.setHighlight(true);
		//设置高亮显示的域
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<span style=\"color:red\">");
		query.setHighlightSimplePost("</span>");
		//7.执行查询
		SearchResult searchResult = searchDao.search(query);
		//8.获取结果
		long recourdCount = searchResult.getRecourdCount();
		//9.根据总记录数获取计算总的分页数
		long pageCount = recourdCount / rows;
		if(pageCount % rows >0){
			pageCount ++;
		}
		searchResult.setTotalPages(pageCount);
		//10.返回结果视图
		return searchResult;
	}

}
