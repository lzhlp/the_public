package com.example.demo.java.jpa;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.java.entity.Contend;
import com.example.demo.java.entity.WeixinUserInfo;

public interface UpdateInfoRepository extends JpaRepository<Contend,String> {

	
	/*@Modifying
	@Transactional(rollbackFor = Exception.class)
	@Query(value="delete from weixinuserinfo  where openid=?1 ", nativeQuery = true)
	int deleteByOpenid(String openId);

	@Query(value = "select * from weixinuserinfo where openid=?1 ", nativeQuery = true)
	WeixinUserInfo findWeixinUser(String fromUserName);

	@Query(value = "select * from weixinuserinfo where userinfoid=?1 ", nativeQuery = true)
	WeixinUserInfo findByIdOne(String id);*/

	@Query(value = "select * from contend where id=(select MAX(id) from contend ) ", nativeQuery = true)
	Contend findUpdateInfoOne();

	@Modifying
	@Transactional(rollbackFor = Exception.class)
	@Query(value="update contend set shelves=1  where id!=?1 ", nativeQuery = true)
	int updateShangjia1(int id);

	@Modifying
	@Transactional(rollbackFor = Exception.class)
	@Query(value="update contend set shelves=1  where id=?1 ", nativeQuery = true)
	int updateShangjia2(int id);
	

}
