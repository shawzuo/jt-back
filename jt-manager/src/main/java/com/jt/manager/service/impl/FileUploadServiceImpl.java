package com.jt.manager.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jt.common.vo.PicUploadResult;
import com.jt.manager.service.FileUploadService;

@Service
public class FileUploadServiceImpl implements FileUploadService {
	private String dirPath = "E:/jt-upload/"; // 定义本地的磁盘路径
	private String url = "http://image.jt.com/"; // 定义url访问路径

	/**
	 * 编程思路: 
	 * 1.获取图片的类型(获取图片的名称) 
	 * 2.截取图片的类型 
	 * 3.判断是否为图片格式（.jpg .png .gif）
	 * 4.判断是否为恶意程序 
	 * 5.定义磁盘路径和url访问路径
	 * 6.准备以时间为界限的文件夹
	 * 7.让图片不重名，时间毫秒数+三位随机数
	 * 8.创建问价
	 * 9.开始写盘操作
	 * 10. 将数据准备好返回给客户端
	 */
	@Override
	public PicUploadResult uploadFile(MultipartFile uploadFile) {
		PicUploadResult result = new PicUploadResult();
		// 获取图片的名称
		String fileName = uploadFile.getOriginalFilename();
		// 获取图片的类型
		String fileType = fileName.substring(fileName.lastIndexOf("."));
		// 判断是否为图片格式 采用正则表达式
		if (!fileType.matches("^.*(jpg|png|gif)$")) {
			// 表示不是图片类型
			result.setError(1);
			return result;
		}
		/*
		 * 判断是否为恶意程序 通过工具类判断是否为图片 判断一个文件是否为图片，一般要获取图片的宽度和高度，两者都不为0则为图片
		 * 
		 */
		try {
			BufferedImage bufferedImage = ImageIO.read(uploadFile.getInputStream());
			// 获取图片的高度和宽度
			int width = bufferedImage.getWidth();
			int height = bufferedImage.getHeight();
			if (width == 0 || height == 0) {
				result.setError(1); // 表示非法的程序
				return result;
			}
			// 准备时间文件夹 时间格式yyyy/MM/dd/HH
			String dataPathDir = new SimpleDateFormat("yyyy/MM/dd/HH").format(new Date());
			// 准备时间
			String millis = System.currentTimeMillis() + "";
			Random random = new Random();
			int randomNum = random.nextInt(999);
			String randomPath = millis + randomNum;
			// 8.创建文件夹
			String localPath = dirPath + dataPathDir;
			File file = new File(localPath);
			if (!file.exists()) {
				file.mkdirs(); // 如果文件夹不存在 则创建文件夹
			}
			// 通过工具类实现写盘操作
			String localPathFile = localPath + "/" + randomPath + fileName;
			// 文件写盘
			uploadFile.transferTo(new File(localPathFile));
			// 准备数据返回给客户端
			result.setHeight(height+"");
			result.setWidth(width+"");
			String urlPath = url + dataPathDir + "/" + randomPath + fileName;
			result.setUrl(urlPath);
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
		}
		return null;
	}

	
}
