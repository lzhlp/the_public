package com.example.demo.java.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "contend")
public class Contend{

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	// 更新内容
	@Column(name = "contends")
    private String contends;
    // 作者
	@Column(name = "author")
    private String author;
    // 更新时间
	@Column(name = "updatetime")
    private Date updateTime;
	
	// 上下架
    @Column(name = "shelves")
	private int shelves;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContends() {
		return contends;
	}
	public void setContends(String contends) {
		this.contends = contends;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getShelves() {
		return shelves;
	}
	public void setShelves(int shelves) {
		this.shelves = shelves;
	}
    
 
}
