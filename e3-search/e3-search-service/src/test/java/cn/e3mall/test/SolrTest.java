package cn.e3mall.test;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrTest {

	@Test
	public void solrTest() throws Exception{
		//1.创建一个SolrServer对象，创建一个连接，参数是solr服务的url
		SolrServer server = new HttpSolrServer("http://192.168.25.128:8080/solr/collection1");
		//2.创建一个文档对象，SolrDocument
		SolrInputDocument document = new SolrInputDocument();
		//3.向文档中添加域，文档域必须包含一个id域，所有的域名城必须在scema.xml中定义
		document.addField("id", "document1");
		document.addField("item_title", "测试商品");
		document.addField("item_price", 1000l);
		//4.把文档写入到索引域中
		server.add(document);
		//5.提交
		server.commit();
	}
	/**
	 * 删除
	 * @throws Exception 
	 * @throws SolrServerException 
	 */
	@Test
	public void deleteItem() throws SolrServerException, Exception{
		SolrServer server = new HttpSolrServer("http://192.168.25.128:8080/solr/collection1");
		//删除文档
		server.deleteById("document1");
		//server.deleteByQuery("id:document1");
		//提交
		server.commit();
	}
}
