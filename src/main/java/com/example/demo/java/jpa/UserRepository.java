package com.example.demo.java.jpa;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.java.entity.User;

public interface UserRepository extends JpaRepository<User,String> {

	

   	@Query(value = "select * from user where username=?1 and password=?2", nativeQuery = true)
    User findLogin(String username, String password);

   	@Modifying
	@Transactional(rollbackFor = Exception.class)
	@Query(value="update user set gender=?2, course=?3, name=?4, hobby=?5, hobby1=?6, hobby2=?7, hobby3=?8 where id=?1 ", nativeQuery = true)
	int updateInfo(int id, int gender, int course, String name, int hobby, int hobby1, int hobby2, int hobby3);

	
	
/*	@Modifying
	@Transactional(rollbackFor = Exception.class)
	@Query(value="delete from GIRL e where e.id in (:ids) ", nativeQuery = true)
	int deleteByIdsss(@Param("ids")List<String> ids);
	
	@Query(value = "select * from GIRL where ID= ?1", nativeQuery = true) 
	Girl findByIdS(Integer ID);*/
}
