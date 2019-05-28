package com.example.demo.java.service.weixin;

import com.example.demo.java.entity.WeixinUserInfo;

public interface AddWeixinService {

	
	WeixinUserInfo getUserInfo(String accessToken, String openId);
}
