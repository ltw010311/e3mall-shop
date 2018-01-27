package cn.e3mall.item.controller;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 *生成静态页面的测试Controller 
 * @author LiuTaiwen
 *
 */
@Controller
public class FreemarkerController {

	@Autowired
	private FreeMarkerConfigurer  freeMarkerConfigurer;
	@RequestMapping("html/gen")
	@ResponseBody
	public String genHtml() throws Exception {
		//1.从spring容器中获取FreeMarkerConfiguer对象
		//2.从FreeMarkerConfiguer对象中获取Configuration
		Configuration configuration =freeMarkerConfigurer.getConfiguration();
		//3.使用Configuration对象获取Template对象
		Template template = configuration.getTemplate("hello.ftl");
		//4.创建数据集
		Map data = new HashMap<>();
		data.put("hello", "122336");
		//5.创建输出文件的writer对象
		FileWriter writer = new FileWriter(new File("D:/develop/javaworks/freemarker/hello.txt"));
		//6.调用模板对象的process方法，生成文件
		template.process(data, writer);
		//7.关闭流
		writer.close();
		return "OK";
	}
}
