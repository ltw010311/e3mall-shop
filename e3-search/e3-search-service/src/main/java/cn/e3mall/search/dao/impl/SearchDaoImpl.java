package cn.e3mall.search.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.e3mall.common.pojo.SearchItem;
import cn.e3mall.search.dao.SearchDao;
import cn.e3mall.search.pojo.SearchResult;
/**
 * 索引库查询
 * @author LiuTaiwen
 *
 */
@Repository
public class SearchDaoImpl implements SearchDao {

	@Autowired
	private SolrServer solrServer;
	@Override
	public SearchResult search(SolrQuery query) throws Exception {
		//1.根据条件进行索引库查询
		QueryResponse queryResponse = solrServer.query(query);
		//2.取查询结果的总记录数
		SolrDocumentList results = queryResponse.getResults();
		long numFound = results.getNumFound();
		//3.取高亮显示
		Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
		//4.取商品列表
		List<SearchItem> itemList = new ArrayList<>();
		for (SolrDocument solrDocument : results) {
			SearchItem item = new SearchItem();
			item.setCategory_name((String) solrDocument.get("item_category_name"));
			item.setId((String) solrDocument.get("id"));
			item.setImage((String) solrDocument.get("item_image"));
			item.setPrice((long) solrDocument.get("item_price"));
			item.setSell_point((String) solrDocument.get("item_sell_point"));
			//取高亮显示
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			String title="";
			if(list!=null && list.size()>0){
				title = list.get(0);
			}else{
				title = (String) solrDocument.get("item_title");
			}
			item.setTitle(title);
			itemList.add(item);
		}
		//5.创建一个SearchResult对象
		SearchResult result = new SearchResult();
		result.setItemList(itemList);
		result.setRecourdCount(numFound);
		//6.把结果封装在一个SearchResult对象中
		return result;
	}

}
