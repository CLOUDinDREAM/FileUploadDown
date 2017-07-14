package test.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import test.service.UploadService;

@Controller
public class UploadController {
	@Autowired
	private UploadService uploadService;
	   
	@RequestMapping(value="/file/uplaod",method=RequestMethod.POST)
	public String uploadFile(ModelMap model,MultipartFile file){
		uploadService.uploadFile(file);
		System.out.println("进入"+file.getOriginalFilename());
		model.put("message", "上传成功");
		return "common/message";
	}
	@RequestMapping(value="/filemulti/uplaod")
	public String uploadMultiFile(ModelMap model,@RequestParam("files")MultipartFile[] files){
		uploadService.uploadMultiFile(files);
	
		model.put("message", "上传成功");
		return "common/message";
	}
	
}
