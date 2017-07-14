package test.service;

import java.io.File;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadService {
	public static void main(String[] args) {
		String url = "/doctor/a";
		String[] strs = url.split("/");
		System.out.println(strs.length);
		for(String s: strs){
			System.out.println(s.length());
		}
		/*int len = strs[1].length()+2;
		System.out.println(len);
		System.out.println(url.substring(len));	*/
		
	

	}
	public void uploadFile(MultipartFile file){
		//this.getClass().getClassLoader().getResource("/").getPath(); 
			String basePath = "D:/upload/";
			File pathFile = new File(basePath);
			if(!pathFile.exists()){
				pathFile.mkdir();
			}
		 // 判断文件是否为空  
        if (!file.isEmpty()) {  
            try {  
                // 文件保存路径  
            	String filePath =  basePath+file.getOriginalFilename();  
            	
                // 转存文件  
                file.transferTo(new File(filePath));  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
	}
	public void uploadMultiFile(MultipartFile[] files){
		 //判断file数组不能为空并且长度大于0  
        if(files!=null&&files.length>0){  
            //循环获取file数组中得文件  
            for(int i = 0;i<files.length;i++){  
                MultipartFile file = files[i];  
                //保存文件  
                uploadFile(file);  
            }  
        }  
	}
}
