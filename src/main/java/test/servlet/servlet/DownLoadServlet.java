package test.servlet.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownLoadServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取用户下载的文件名称（url地址后追加数据，get）
		String fileName = request.getParameter("fileName");
		
		//获取上传目录路径
		String filePath = getServletContext().getRealPath("/upload")+"/"+fileName;
		BufferedInputStream bis = null;
	    BufferedOutputStream bos = null;
	 
	    bis = new BufferedInputStream(new FileInputStream(filePath));
	    bos = new BufferedOutputStream(response.getOutputStream());
	    long fileLength = new File(filePath).length();
	    
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("multipart/form-data");
	    /*
	     * 解决各浏览器的中文乱码问题
	     */
	    
	    String userAgent = request.getHeader("User-Agent");
	    if(userAgent.contains("MSIE") ||userAgent.contains("rv:11")){
	    	fileName = URLEncoder.encode(fileName, "UTF-8");
	    	//fileName = new String(fileName.getBytes(), "ISO-8859-1");
	    }
	    else{
	    	fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
	    }
	   /* byte[] bytes = userAgent.contains("MSIE") ? fileName.getBytes()
	            : fileName.getBytes("UTF-8"); // fileName.getBytes("UTF-8")处理safari的乱码问题
	    fileName = new String(bytes, "ISO-8859-1"); // 各浏览器基本都支持ISO编码
	   */
	    response.setHeader("Content-disposition",
	            String.format("attachment; filename=\"%s\"", fileName));
	 
	    response.setHeader("Content-Length", String.valueOf(fileLength));
	    byte[] buff = new byte[2048];
	    int bytesRead;
	    while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
	        bos.write(buff, 0, bytesRead);
	    }
	    bis.close();
	    bos.close();
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
	
}
