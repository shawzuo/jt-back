package com.jt.manager.service;

import org.springframework.web.multipart.MultipartFile;

import com.jt.common.vo.PicUploadResult;

public interface FileUploadService {
	PicUploadResult uploadFile(MultipartFile uploadFile);
}
