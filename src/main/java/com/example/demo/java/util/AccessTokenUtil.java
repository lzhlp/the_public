package com.example.demo.java.util;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.demo.java.service.weixin.RedisService;



/**
 * 
 * @author luchunfeng
 *
 */
@Component
public class AccessTokenUtil {

	private  static final Logger LOG = LogManager.getLogger(AccessTokenUtil.class);
	private  long refreshTime = 0L;
	@Autowired
	private RedisService redisService;

	private synchronized  void toBtainAccessToken() {
		// 一分钟内只能更新缓存一次
		if (System.currentTimeMillis() - refreshTime < 60 * 1000) {
			LOG.info("AccessToken more one times in one minute.");
			return;
		}
		//获取access_token
		String accessToken = MessageUtil.getAccessToken(MessageUtil.APPID, MessageUtil.APPSECRET).getToken();
		if (accessToken != null && !accessToken.isEmpty()) {
	    	redisService.set("ACCESSTOKEN",accessToken);
	    	LOG.info("存成功,ACCESSTOKEN"+accessToken);
	    	String value=(String) redisService.get("ACCESSTOKEN");
	    	LOG.info("取成功,ACCESSTOKEN"+value);
		}
		refreshTime = System.currentTimeMillis();
	}


	public void refresh() {
		toBtainAccessToken();
	}

}
