package cn.e3mall.freemarker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import cn.e3mall.pojo.Student;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class TestFreeMarker {

	@SuppressWarnings("resource")
	@Test
	public void freemarkerTest() throws Exception{
		//1.创建一个模板对象，向工程中添加jar包
		//2.创建一个Configuration对象，直接new一个对象，构造方法是freemarker对应的版本号
		Configuration configuration = new Configuration(Configuration.getVersion());
		//3.设置模板文件所在的路径关系
		configuration.setDirectoryForTemplateLoading(new File("D:/develop/javaworks/e3-item-web/src/main/webapp/WEB-INF/ftl"));
		//4.指定模板文件所用的字符集编码 utf-8
		configuration.setDefaultEncoding("utf-8");
		//5.加载模板文件，指定模板的文件名，获取模板对象
		Template template = configuration.getTemplate("hello.ftl");
		//6.创建一个使用模板的数据集，可以是pojo也可以是map
		Map model = new HashMap<>();
		//7.向数据集中添加模板需要的数据
		model.put("hello", "hello my is first freemarker");
		//8.指定文件输出的目录和文件名
		FileWriter writer = new FileWriter("D:/develop/javaworks/freemarker/hello.txt");
		//9.输出静态页面
		template.process(model, writer);
		//10.关闭流
		writer.close();
	}
	@Test
	public void freemarkerStudentTest() throws Exception{
		//1.创建一个模板对象，向工程中添加jar包
		//2.创建一个Configuration对象，直接new一个对象，构造方法是freemarker对应的版本号
		Configuration configuration = new Configuration(Configuration.getVersion());
		//3.设置模板文件所在的路径关系
		configuration.setDirectoryForTemplateLoading(new File("D:/develop/javaworks/e3-item-web/src/main/webapp/WEB-INF/ftl"));
		//4.指定模板文件所用的字符集编码 utf-8
		configuration.setDefaultEncoding("utf-8");
		//5.加载模板文件，指定模板的文件名，获取模板对象
		Template template = configuration.getTemplate("student.ftl");
		//6.创建一个使用模板的数据集，可以是pojo也可以是map
		Map model = new HashMap<>();
		//7.向数据集中添加模板需要的数据
		model.put("hello", "hello my is first freemarker");
		List<Student> stuList = new ArrayList<>();
		Student student = new Student(111, "张三", 16, "北京顺义");
		stuList.add(new Student(111, "张三1", 16, "北京顺义"));
		stuList.add(new Student(222, "张三2", 16, "北京顺义"));
		stuList.add(new Student(333, "张三3", 16, "北京顺义"));
		stuList.add(new Student(444, "张三4", 16, "北京顺义"));
		stuList.add(new Student(555, "张三54", 16, "北京顺义"));
		stuList.add(new Student(11661, "张三3", 16, "北京顺义"));
		model.put("stuList", stuList);
		model.put("student", student);
		model.put("data", new Date());
		//8.指定文件输出的目录和文件名
		FileWriter writer = new FileWriter("D:/develop/javaworks/freemarker/student.html");
		//9.输出静态页面
		template.process(model, writer);
		//10.关闭流
		writer.close();
	}
}
