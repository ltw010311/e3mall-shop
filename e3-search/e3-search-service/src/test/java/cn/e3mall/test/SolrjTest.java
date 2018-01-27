package cn.e3mall.test;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

public class SolrjTest {

	@Test
	public void SolrQueryTest() throws Exception{
		//1.创建一个SolrServer对象，使用httpSolrServer类
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8080/solr/collection1");
		//2.创建SolrQuery对象
		SolrQuery query = new SolrQuery();
		//3.向SolrQuery对象中添加查询条件，过滤条件
		query.setQuery("*:*");
		query.setStart(0);
		query.setRows(10);
		//4.执行查询，
		QueryResponse queryResponse = solrServer.query(query);
		//5.取查询结果
		SolrDocumentList results = queryResponse.getResults();
		//获取总的记录数
		System.out.println(results.getNumFound());
		//6.遍历得到结果
		for (SolrDocument solrDocument : results) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("item_title"));
			System.out.println(solrDocument.get("item_price"));
		}
	}
	@Test
	public void SolrQueryTestWithHighLighting() throws Exception{
		//1.创建一个SolrServer对象，使用httpSolrServer类
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8080/solr/collection1");
		//2.创建SolrQuery对象
		SolrQuery query = new SolrQuery();
		//3.向SolrQuery对象中添加查询条件，过滤条件
		query.setQuery("手机");
		//指定默认索引
		query.set("df", "item_keywords");
		query.setStart(0);
		query.setRows(10);
		//开启高亮显示
		query.setHighlight(true);
		//指定高亮显示的区域
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<em>");
		query.setHighlightSimplePost("</em>");
		//4.执行查询，
		QueryResponse queryResponse = solrServer.query(query);
		//5.取查询结果
		SolrDocumentList results = queryResponse.getResults();
		//获取总的记录数
		System.out.println("查询结果的总记录数："+results.getNumFound());
		//取高亮显示
		Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
		//6.遍历得到结果
		for (SolrDocument solrDocument : results) {
			System.out.println(solrDocument.get("id"));
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			String title ="";
			if(list!=null &&list.size()>0){
				title = list.get(0);
			}else{
				title = (String) solrDocument.get("item_title");
			}
			System.out.println(title);
			System.out.println(solrDocument.get("item_price"));
		}
	}
}
