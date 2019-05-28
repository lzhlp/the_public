package com.example.demo.java.service;

import com.example.demo.java.entity.User;

/**  
* 
* @ClassName: UserService
* @Description: TODO
* @author LuChunFeng
* @param
* @date 2018年11月29日 下午4:10:35
* @return  
*
*/
public interface UserService {


	User save(User user);

	User findLogin(String username, String password);

	void updateInfo(int id, int gender, int course, String name, int hobby, int hobby1, int hobby2, int hobby3);

}
