package com.jt.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jt.common.vo.PicUploadResult;
import com.jt.manager.service.FileUploadService;

@Controller
public class FileUploadController {
	@Autowired
	private FileUploadService fileUploadService;
	
	/**
	 * 说明： 文件上传时需要通过文件上传解析器才能完成，所以获取文件对象时也需要通过相对于的接口MultipartFile完成。
	 * 
	 * @param uploadFile
	 * @return
	 */
	@RequestMapping("/pic/upload")
	@ResponseBody
	public PicUploadResult uploadFile(MultipartFile uploadFile) {
		return fileUploadService.uploadFile(uploadFile);
	}
}
