package cn.e3mall.controller;

import java.util.HashMap;
import java.util.Map;

import javax.print.attribute.standard.Media;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
/**
 * 图片上传Controller
 * @author LiuTaiwen
 *
 */
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.e3mall.common.pojo.FastDFSClient;
import cn.e3mall.common.utils.JsonUtils;
@Controller
public class PictureController {

	@Value("${image.service.url}")
	private String imageUrl;
	
	//@RequestMapping(value="/pic/upload",produces="text/plain;charset=utf-8")
	@RequestMapping(value="/pic/upload",produces=MediaType.TEXT_HTML_VALUE + ";charset=utf-8")
	@ResponseBody
	public String fileUpload(MultipartFile uploadFile){
		try {
			//1.接收上传的文件
			//2.取文件的原始名称
			String originalFilename = uploadFile.getOriginalFilename();
			//3.取截取文件的扩展名
			String extName = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
			//4.使用FastDRSClient上传到图片服务器
			FastDFSClient fastDFSClient = new FastDFSClient("classpath:conf/client.conf");
			//5.图片服务器返回url
			String path = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
			//6.把图片的ulr变成完整的路径
			String url = imageUrl + path;
			//7.设置返回结果
			Map result = new HashMap<>();
			//error
			result.put("error", 0);
			//url
			result.put("url", url);
			//返回结果
			return JsonUtils.objectToJson(result);
		} catch (Exception e) {
			e.printStackTrace();
			Map result = new HashMap<>();
			//error
			result.put("error", 1);
			//message
			result.put("message", "图片上传失败！");
			//返回结果
			return JsonUtils.objectToJson(result);
		}
	}
}
