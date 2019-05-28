package com.example.demo.java.jpa;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.java.entity.User;
import com.example.demo.java.entity.WeixinUserInfo;

public interface WeixinUserRepository extends JpaRepository<WeixinUserInfo,String> {

	
	@Modifying
	@Transactional(rollbackFor = Exception.class)
	@Query(value="delete from weixinuserinfo  where openid=?1 ", nativeQuery = true)
	int deleteByOpenid(String openId);

	@Query(value = "select * from weixinuserinfo where openid=?1 ", nativeQuery = true)
	WeixinUserInfo findWeixinUser(String fromUserName);

	@Query(value = "select * from weixinuserinfo where userinfoid=?1 ", nativeQuery = true)
	WeixinUserInfo findByIdOne(String id);
	
/*
   	@Query(value = "select * from user where username=?1 and password=?2", nativeQuery = true)
    User findLogin(String username, String password);*/
	
	
/*	@Modifying
	@Transactional(rollbackFor = Exception.class)
	@Query(value="delete from GIRL e where e.id in (:ids) ", nativeQuery = true)
	int deleteByIdsss(@Param("ids")List<String> ids);
	
	@Query(value = "select * from GIRL where ID= ?1", nativeQuery = true) 
	Girl findByIdS(Integer ID);*/
}
