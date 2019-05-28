package com.example.demo.java.service.weixin.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.java.controller.UserConller;
import com.example.demo.java.service.weixin.AddMenuService;
import com.example.demo.java.service.weixin.RedisService;
import com.example.demo.java.util.HttpRequestUtil;
import com.example.demo.java.util.MessageUtil;

import net.sf.json.JSONObject;

@Service
public class AddMenuIml implements AddMenuService {
	
	private static Logger LOGGER = LoggerFactory.getLogger(AddMenuIml.class);
	
	@Autowired
	private RedisService redisService;
	
	@Override
	public boolean menuAdd() {
		//获取access_token
		String accessToken = (String) redisService.get("ACCESSTOKEN");
		LOGGER.info("取成功,ACCESSTOKEN"+accessToken);
		String url = MessageUtil.ADD_MENU_URL.replace("ACCESS_TOKEN", accessToken);
		String menu = JSONObject.fromObject(MessageUtil.initMenu()).toString();
		System.out.println(url);
		JSONObject result = HttpRequestUtil.httpsRequest(url,"POST",menu);
		if("ok".equals(result.getString("errmsg"))){
			LOGGER.info("添加菜单结果：{}", result);
        	return true;
        }
		LOGGER.info("添加菜单结果：{}", result);
		return false;

	}

}
