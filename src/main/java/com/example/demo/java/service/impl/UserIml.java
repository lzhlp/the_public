package com.example.demo.java.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.java.entity.User;
import com.example.demo.java.jpa.UserRepository;
import com.example.demo.java.service.UserService;

/**  
* 
* @ClassName: DubboIml
* @Description: TODO
* @author LuChunFeng
* @param
* @date 2018年11月29日 下午4:09:35
* @return  
*
*/
@Component
public class UserIml implements UserService{
	protected static final Logger LOG = LoggerFactory.getLogger(UserIml.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User save(User user) {
		LOG.info("生产者l_public_play_producer");
		return userRepository.save(user);
	}


	@Override
	public User findLogin(String username, String password) {
		LOG.info("生产者l_public_play_producer");
		return userRepository.findLogin(username,password);
	}

	@Override
	public void updateInfo(int id, int gender, int course, String name, int hobby, int hobby1, int hobby2, int hobby3) {
		LOG.info("生产者l_public_play_producer");
		userRepository.updateInfo(id, gender, course, name, hobby, hobby1, hobby2, hobby3);
	}





}
