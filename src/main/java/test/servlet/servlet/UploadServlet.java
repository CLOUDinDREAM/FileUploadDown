package test.servlet.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

public class UploadServlet extends HttpServlet{
	Logger logger  = Logger.getLogger(UploadServlet.class);
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		//得到文件保存目录
		String savePath = this.getServletContext().getRealPath("/upload");
		File file = new File(savePath);
		System.out.println("目录："+savePath);
		if(!file.exists() && !file.isDirectory()){
			System.out.println("目录不存在，需创建");
			file.mkdir();
		}
		String message = "";

		try {
			//Apache文件上传组件处理文件上传步骤：
			//创建一个DiskFileItemFactory工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			//创建一个文件上传解析器
			ServletFileUpload upload = new ServletFileUpload(factory);
			//解决上传文件名的中文乱码
			upload.setHeaderEncoding("UTF-8");
			//判断提交的数据是狗是上传表单的数据
			if(! ServletFileUpload.isMultipartContent(request)){
				return;
			}
			//4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
			List<FileItem> list = upload.parseRequest(request); 
			for(FileItem item :list){
				//普通数据项
				if(item.isFormField()){
					String name = item.getFieldName();
					String value = item.getString("utf-8");
					System.out.println(name+"="+value);
				}else {
					//封装的是上传文件
					String filename = item.getName();
					System.out.println(filename);
					if(filename.isEmpty()){
						continue;
					}
					//处理获取到的上传文件的文件名的路径部分，只保留文件名部分
					filename = filename.substring(filename.lastIndexOf("\\")+1);
					//获取item中的文件输入流
					InputStream in = item.getInputStream();
					//创建一个文件输出流
					FileOutputStream out =  new FileOutputStream(savePath +"\\"+filename);
					//创建一个缓冲区
					byte buffer[] = new byte[1024];
					//创建一个输入流结束标识
					int len = 0;
					//将输入流读入缓冲区中
					while((len = in.read(buffer))>0 ){
						out.write(buffer,0,len);
					}
					//关闭输入流
					in.close();
					//关闭输出流
					out.close();
					//删除临时文件
					item.delete();
					message ="文件上传成功";
				}
				
			}
		
		} catch (Exception e) {	
			message="上传失败";
			e.printStackTrace();
		}
		request.setAttribute("message", message);
		request.getRequestDispatcher("/WEB-INF/common/message.jsp").forward(request, response);
		
	}
	
	
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
			doGet(request, response);
	}
}


/*1.创建一个文件项目工厂类DiskFileItemFactory。
	DiskFileItemFactory有俩个构造方法：
	1 DiskFileItemFactory() 其中sizeThreshold是默认值10kB, 文件大小不超过这个值将内容保存在内存，超过这个值会把文件保存到临时目录下，可用System.getProperty("java.io.tmpdir")获取；
	2 DiskFileItemFactory(int sizeThreshold, File repository)  可以指定sizeThreshold, 和文件保存到磁盘的路径。
	DiskFileItemFactory有一个属性FileCleaningTracker，设置这个属性可以用来追踪删除临时文件。
	当这个临时文件不再被使用时将会被立即删除，更精确的说是这个文件对象被垃圾收集器回收时，
	FileCleaningTracker将启动收获者线程(reaper thread)自动删除这个临时文件。FileCleaningTracker是commons-io包的工具类。　
	FileCleaningTracker fileCleaningTracker = FileCleanerCleanup.getFileCleaningTracker(servletcontext);
	DiskFileItemFactory factory = new DiskFileItemFactory();
	factory.setFileCleaningTracker(fileCleaningTracker);　　
	ServletContext获取的几种方法：
	    Javax.servlet.http.HttpSession.getServletContext();
	    Javax.servlet.jsp.PageContext.getServletContext();
	    Javax.servlet.ServletConfig.getServletContext();
　2.创建一个文件处理类ServletFileUpload。
　　　　ServletFileUpload解析上传请求request的信息，封装到FileItem类中，我们通过FileItem可以获取文件的名称、大小、文件流等信息。
		 ServletFileUpload sfu = new ServletFileUpload(factory);
		 　　ServletFileUpload可以设置：
		 　　　　headerEncoding 读取请求头信息时使用的编码
		 　　　　sizeMax 单次请求所能上传的文件总大小的最大size，默认是-1，不限制大小
		 　　　　fileSizeMax 单次请求所能上传的单个文件最大size，默认是-1，不限制大小
		  List<FileItem> items = sfu.parseRequest(req);	
  3.FileCleanerCleanup实现了ServletContextListener监听类，在web启动时调用ServletContext.setAttribute()方法设置了一个全局共享的FileCleaningTracker对象。    
*/


/*
 1、文件上传请求头
		 * Accept	text/html,application/xhtml+xml,application/xml;q=0.9,*;q=0.8
		Accept-Encoding	gzip, deflate
		Accept-Language	zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3
		Connection	keep-alive
		Content-Length	463
		Content-Type	multipart/form-data; boundary=---------------------------99723099725366
		Cookie	JSESSIONID=D4A2424B913862CB2B2BD5F0CBE54AB1; __utma=111872281.2098552496.1495013100.1495013100.1495017526
		.2; __utmz=111872281.1495013100.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none)
		Host	localhost:8090
		Referer	http://localhost:8090/FileUploadDown/
		Upgrade-Insecure-Requests	1
		User-Agent	Mozilla/5.0 (Windows NT 6.3; WOW64; rv:54.0) Gecko/20100101 Firefox/54.0

2、form的post数据提交
		-----------------------------99723099725366
		Content-Disposition: form-data; name="username"
		
		kk
		-----------------------------99723099725366
		Content-Disposition: form-data; name="file1"; filename="pythonå®è£.txt"
		Content-Type: text/plain
		
		ÅäÖÃ»·¾³±äÁ¿£º
		E:\software\python2.7.13;E:\software\python2.7.13\Scripts
		°²×°¿â:
		pip install MySQLdb
		
		pip install bs4
		pip install BeautifulSoup
		pip install lxml
		-----------------------------99723099725366--
*/


