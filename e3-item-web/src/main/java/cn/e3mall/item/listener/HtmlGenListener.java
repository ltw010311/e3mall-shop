package cn.e3mall.item.listener;

import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.e3mall.item.pojo.Item;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.TbItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;
/**
 * 商品详情页面静态化生成
 * @author LiuTaiwen
 *
 */

public class HtmlGenListener implements MessageListener {

	@Value("${html.gen.path}")
	private String HTML_GEN_PATH;
	@Autowired
	private TbItemService itemService;	
	@Autowired
	private FreeMarkerConfigurer  freeMarkerConfigurer;
	
	@Override
	public void onMessage(Message message) {
		try {
			//1.创建一个MessageListener的实现类
			//2.接收商品添加的信息
			TextMessage textMessage = (TextMessage) message;
			//3.从消息中获取商品的id
			String text = textMessage.getText();
			long itemId = Long.parseLong(text);
			Thread.sleep(1000);
			//4.根据商品的id查询商品的信息
			TbItem item = itemService.getItemById(itemId);
			Item item2 = new Item(item);
			//取商品的描述信息
			TbItemDesc itemDesc = itemService.getItemDescById(itemId);
			//创建数据集
			Map data = new HashMap<>();
			data.put("item", item2);
			data.put("itemDesc", itemDesc);
			//5.创建一个商品详情页面的静态化模板
			Configuration configuration = freeMarkerConfigurer.getConfiguration();
			Template template = configuration.getTemplate("item.ftl");
			//6.指定静态页面输出的目录
			FileWriter writer = new FileWriter(HTML_GEN_PATH + itemId +".html");
			//7.生成静态页面
			template.process(data, writer);
			//8.关闭流
			writer.close();
			//9.访问静态页面，使用nginx访问
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
