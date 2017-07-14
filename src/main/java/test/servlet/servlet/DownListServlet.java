package test.servlet.servlet;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.serializer.MapSerializer;

public class DownListServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 实现思路：先获取upload目录下所有文件的文件名，再保存；跳转到down.jsp列表展示
		Map<String, String> fileNames = new HashMap<>();
		//获取上传目录，
		//"E:\\software\\apache-tomcat-8.5.12\\webapps\\FileUploadDown\\WEB-INF\\upload"
		
		String basePath = getServletContext().getRealPath("/upload");
		//目录
		File file = new File(basePath);
		//目录下所有文件名
		String[] list = file.list();
		if(list != null && list.length > 0 ){
			for(String s:list){
				String fileName = s;
				String shortName = fileName.substring(fileName.lastIndexOf("#")+1);
				fileNames.put(fileName, shortName);
			}
		}
		req.setAttribute("fileNames", fileNames);
		req.getRequestDispatcher("/WEB-INF/list.jsp").forward(req, resp);
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
}
