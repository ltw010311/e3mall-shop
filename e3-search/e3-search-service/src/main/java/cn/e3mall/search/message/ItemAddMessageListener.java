package cn.e3mall.search.message;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import cn.e3mall.common.pojo.SearchItem;
import cn.e3mall.search.mapper.ItemMapper;
/**
 * 监听商品信息的添加，接收到信息后，将对应的商品信息同步到索引库中
 * @author LiuTaiwen
 *
 */
public class ItemAddMessageListener implements MessageListener {

	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private SolrServer solrServer;
	@Override
	public void onMessage(Message message) {
		//从消息中获取商品的id
		TextMessage textMessage = (TextMessage) message;
		try {
			String text = textMessage.getText();
			Long itemId = new Long(text);
			//等待事务提交
			Thread.sleep(1000);
			//根据商品的id查询商品信息
			SearchItem searchItem = itemMapper.getTiemById(itemId);
			//创建一个文档对象
			SolrInputDocument document = new SolrInputDocument();
			//向文档对象中添加域
			document.addField("id",searchItem.getId());
			document.addField("item_title",searchItem.getTitle());
			document.addField("item_sell_point",searchItem.getSell_point() );
			document.addField("item_price",searchItem.getPrice());
			document.addField("item_image",searchItem.getImage());
			document.addField("item_category_name",searchItem.getCategory_name());
			//把文档对象写入索引库中
			solrServer.add(document);
			//提交
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
