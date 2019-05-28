package com.example.demo.java.util;


import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;





/**
 * 项目启动后执行的操作
 * 
 * @author QianTianLun
 */
@Component
@Order(1)
public class StartUpUtil implements CommandLineRunner {

	@Autowired
	ApplicationContext applicationContext;
	@Autowired
	AccessTokenUtil accesstokenutil;
	@Autowired
	UpdateInfoUtil updateinfoutil;
	@Autowired
	QingChenUtil qingchenutil;
	
	@Override
	public void run(String... args) throws Exception {
		// 刷新AccessTokenUtil的配置信息
		accesstokenutil.refresh();
	}

}
