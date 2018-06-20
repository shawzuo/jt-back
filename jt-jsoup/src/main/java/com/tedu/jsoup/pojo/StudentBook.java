package com.tedu.jsoup.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//忽略未知字段，保证数据获取正常
@JsonIgnoreProperties(ignoreUnknown=true)
public class StudentBook {
	// 
	private List<StudentSection> sections;
	
    private Integer bookId;

    private Integer id;

    private Integer time;

    private String teacher;

    private Integer price;

    private String url;

    private Integer studentNum;

    private String bookImg;

    private String direction;

    private Integer knowledgePointNum;

    private String name;

    private String level;

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(Integer studentNum) {
        this.studentNum = studentNum;
    }

    public String getBookImg() {
        return bookImg;
    }

    public void setBookImg(String bookImg) {
        this.bookImg = bookImg;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Integer getKnowledgePointNum() {
        return knowledgePointNum;
    }

    public void setKnowledgePointNum(Integer knowledgePointNum) {
        this.knowledgePointNum = knowledgePointNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

	public List<StudentSection> getSections() {
		return sections;
	}

	public void setSections(List<StudentSection> sections) {
		this.sections = sections;
	}
    
    
}