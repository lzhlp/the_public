package com.example.demo.java.service.impl;

import com.example.demo.java.service.DubboService;

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
public class DubboIml implements DubboService{
	
	@Override
	public String dubboTest(String src) {
		return "Dubbo+zookeeper1生产者"+src;
	}

}
