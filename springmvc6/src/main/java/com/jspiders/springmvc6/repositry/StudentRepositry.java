package com.jspiders.springmvc6.repositry;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.jspiders.springmvc6.pojo.StudentPojo;

@Repository
public class StudentRepositry {

	private static EntityManagerFactory factory;
	private static EntityManager manager;
	private static EntityTransaction transaction;
	private static Query query;
	private String jpql;

	public void oppenconnection() {
		factory = Persistence.createEntityManagerFactory("SPRINGMVC6");
		manager = factory.createEntityManager();
		transaction = manager.getTransaction();
	}

	public void closeconnection() {
		if (factory != null) {
			factory.close();
		}
		if (manager != null) {
			manager.close();
		}
		if (transaction.isActive()) {
			transaction.rollback();
		}
	}

	public StudentPojo add(String name, String email, long contact, String city, String username,String password) {
		oppenconnection();
		transaction.begin();
		StudentPojo pojo = new StudentPojo();
		pojo.setName(name);
		pojo.setEmail(email);
		pojo.setContact(contact);
		pojo.setCity(city);
		pojo.setUsername(username);
		pojo.setPassword(password);
		manager.persist(pojo);
		transaction.commit();
		closeconnection();
		return pojo;
	}

	public StudentPojo login(String username, String password) {
		oppenconnection();
		transaction.begin();
		jpql= "from StudentPojo where username = '" + username + "' and password = '" + password + "'";
	query=manager.createQuery(jpql);
	List<StudentPojo> resultList = query.getResultList();
	for (StudentPojo student : resultList) {
		transaction.commit();
		closeconnection();
		return student;
	}
	transaction.commit();
	closeconnection();
	return null;
	}

	public StudentPojo search(int id) {
	oppenconnection();
	transaction.begin();
	StudentPojo student = manager.find(StudentPojo.class, id);
	if (student != null) {
		transaction.commit();
		closeconnection();
		return student;
	}
	transaction.commit();
	closeconnection();
		return null;
	}

	public List<StudentPojo> searchAll() {
		oppenconnection();
		transaction.begin();
		jpql="from StudentPojo";
	query = manager.createQuery(jpql);
	  List<StudentPojo> students = query.getResultList();	
		  transaction.commit();
			closeconnection();
			return students;
	}

	public StudentPojo remove(int id) {
		oppenconnection();
		transaction.begin();
		StudentPojo student = manager.find(StudentPojo.class, id);
		if (student != null) {
			manager.remove(student);
		}
		transaction.commit();
		closeconnection();
		return student;
	}

	public StudentPojo update(int id, String name, String email, long contact, String city, String username,
			String password) {
		oppenconnection();
		transaction.begin();
		StudentPojo student =manager.find(StudentPojo.class, id);
		if (student != null) {
			student.setName(name);
			student.setEmail(email);
			student.setContact(contact);
			student.setCity(city);
			student.setUsername(username);
			student.setPassword(password);
			manager.persist(student);
			transaction.commit();
			closeconnection();
			return student;
			
		}
		transaction.commit();
		closeconnection();
		return null;
	}


}


