package com.jspiders.springmvc6.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jspiders.springmvc6.pojo.StudentPojo;
import com.jspiders.springmvc6.repositry.StudentRepositry;

@Service
public class StudentService {

	@Autowired
	private StudentRepositry repositry;

	

	public StudentPojo add(String name, String email, long contact, String city, String username, String password) {
		StudentPojo student= repositry.add(name,email,contact,city,username,password);
		 return student;
	}



	public StudentPojo login(String username, String password) {
		StudentPojo student= repositry.login(username, password);
		return student;
	}



	public StudentPojo search(int id) {
	  StudentPojo student=repositry.search(id);
		return student;
	}


	public List<StudentPojo> searchAll() {
	     List<StudentPojo> students= repositry.searchAll();
		return students;
	}



	public StudentPojo remove(int id) {
		StudentPojo student=repositry.remove(id);
		return student;
	}



	public StudentPojo update(int id, String name, String email, long contact, String city, String username,
			String password) {
StudentPojo student= repositry.update(id, name, email, contact, city, username, password);
		return student;
	}




}


