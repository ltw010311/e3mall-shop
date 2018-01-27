package cn.e3mall.test;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import cn.e3mall.common.pojo.FastDFSClient;

public class FastDfsTest{

	@Test
	public void TestUpload() throws Exception {
		//1.创建一个配置文件，文件名任意，内容就是tracker服务器地址
		//2.使用全局对象加载配置文件
		ClientGlobal.init("D:/develop/javaworks/e3-manager-web/src/main/resources/conf/client.conf");
		//3.创建一个TrackerClient对象
		TrackerClient trackerClient = new TrackerClient();
		//4.通过TrackerClient获得一个TrackerServer对象、
		TrackerServer trackerServer = trackerClient.getConnection();
		//5.创建一个StorageServer的引用，可以是null
		StorageServer storageServer = null;
		//6.创建一个StorageClient,参数需要TrackerServer和StroageServer
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
		//7.使用StorageClient上传文件
		String[] strings = storageClient.upload_file("F:/image/039.jpg", "jpg",null);
		for (String string : strings) {
			System.out.println(string);
		}
	}
	
	@Test
	public void testUpload2() throws Exception{
		FastDFSClient fastDFSClient = new FastDFSClient("D:/develop/javaworks/e3-manager-web/src/main/resources/conf/client.conf");
		String uploadFile = fastDFSClient.uploadFile("F:/image/040.jpg");
		System.out.println(uploadFile);
	}
}
