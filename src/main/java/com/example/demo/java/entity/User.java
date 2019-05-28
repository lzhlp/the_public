package com.example.demo.java.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {

	 @Id
	 @Column(name = "id")
	 @GeneratedValue(strategy = GenerationType.IDENTITY) 
	 private int id;
	 
	 @Column(name = "username")
	 private String username;
	 
	 @Column(name = "password")
	 private String password;
	 
	 @Column(name = "age")
	 private int age;
	 
	 @Column(name = "gender")
	 private int gender;
	 
	 @Column(name = "course")
	 private int course;
	 
	 @Column(name = "name")
	 private String name;
	 
	 
	 @Column(name = "hobby")
	 private int hobby;
	 
	 @Column(name = "hobby1")
	 private int hobby1;
	 
	 @Column(name = "hobby2")
	 private int hobby2;
	 
	 @Column(name = "hobby3")
	 private int hobby3;
	public User(){}
	 
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public int getCourse() {
		return course;
	}

	public void setCourse(int course) {
		this.course = course;
	}


	public int getHobby() {
		return hobby;
	}

	public void setHobby(int hobby) {
		this.hobby = hobby;
	}

	public int getHobby1() {
		return hobby1;
	}

	public void setHobby1(int hobby1) {
		this.hobby1 = hobby1;
	}

	public int getHobby2() {
		return hobby2;
	}

	public void setHobby2(int hobby2) {
		this.hobby2 = hobby2;
	}

	public int getHobby3() {
		return hobby3;
	}

	public void setHobby3(int hobby3) {
		this.hobby3 = hobby3;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	 
	 
}
