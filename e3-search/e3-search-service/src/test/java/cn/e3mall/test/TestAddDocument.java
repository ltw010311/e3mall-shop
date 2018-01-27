package cn.e3mall.test;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class TestAddDocument {

	@Test
	public void testSolrCloudAddDocument() throws Exception, IOException{
		//1.把solr相关的jar包添加到工程中，
		//2.创建一个SolrServer对象，需要使用CloudSolrServer子类
		CloudSolrServer solrServer = new CloudSolrServer("192.168.25.128:2181,192.168.25.128:2182,192.168.25.128:2183");
		//3.需要设置DefailtCollection属性
		solrServer.setDefaultCollection("collection2");
		//4.创建一个SolrInputDpcument对象
		SolrInputDocument document = new SolrInputDocument();
		//5.向文档中对象中添加域
		document.setField("item_title", "商品测试");
		document.setField("item_price", "100");
		document.setField("id", "test001");
		//6.把文档对象写入索引库中
		solrServer.add(document);
		//7.提交
		solrServer.commit();
	}
	
	@Test
	public void testQueryDocument() throws Exception{
		//1.创建一个CloudSolrServer对象
		CloudSolrServer cloudSolrServer = new CloudSolrServer("192.168.25.128:2181,192.168.25.128:2182,192.168.25.128:2183");
		//2.设置默认的Connection对象
		cloudSolrServer.setDefaultCollection("collection2");
		//3.创建一个查询对象
		SolrQuery query = new SolrQuery();
		//4.设置查询条件
		query.setQuery("*:*");
		//5.执行查询
		QueryResponse queryResponse = cloudSolrServer.query(query);
		SolrDocumentList results = queryResponse.getResults();
		//6.获取查询条件
		long numFound = results.getNumFound();
		System.out.println("总的记录数："+numFound);
		for (SolrDocument solrDocument : results) {
			//7.打印结果
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("item_title"));
			System.out.println(solrDocument.get("title"));
			System.out.println(solrDocument.get("item_price"));
		}
	}
}
