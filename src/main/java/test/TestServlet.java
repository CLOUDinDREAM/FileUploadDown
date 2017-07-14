package test;

import java.io.File;

public class TestServlet {
	public static void main(String[] args) {
		String basePath = "E:\\software\\apache-tomcat-8.5.12\\webapps\\FileUploadDown\\WEB-INF\\upload";
		File file = new File(basePath);
		//目录下所有文件名
		String[] list = file.list();
		if(list != null && list.length > 0 ){
			for(String s:list){
				String fileName = s;
				String shortName = fileName.substring(fileName.lastIndexOf("#")+1);
				System.out.println(fileName+"::"+shortName);
			}
		}
	}
}
