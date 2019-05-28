package com.example.demo.java.service.impl;

import com.example.demo.java.service.DubboService;
import com.example.demo.java.service.DubboService2;

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
public class DubboIml2 implements DubboService2{
	
	@Override
	public String dubboTest2(String src) {
		return "Dubbo2+zookeeper1生产者"+src;
	}

}
