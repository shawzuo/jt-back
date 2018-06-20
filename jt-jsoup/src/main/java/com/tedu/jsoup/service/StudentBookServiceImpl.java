package com.tedu.jsoup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tedu.jsoup.mapper.StudentBookMapper;

@Service
public class StudentBookServiceImpl implements StudentBookService{
	@Autowired
	private StudentBookMapper studentBookMapper;
	
	@Override
	public void insertBook(String url) {
		
	}

}
