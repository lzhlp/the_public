package com.example.demo.java.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.java.entity.Contend;
import com.example.demo.java.entity.TemplateData;
import com.example.demo.java.entity.WechatTemplate;
import com.example.demo.java.entity.WeixinUserInfo;
import com.example.demo.java.jpa.UpdateInfoRepository;
import com.example.demo.java.jpa.WeixinUserRepository;
import com.example.demo.java.service.weixin.RedisService;

import net.sf.json.JSONObject;



/**
 * 
 * @author luchunfeng
 *
 */
@Component
public class QingChenUtil {

	private  static final Logger LOG = LogManager.getLogger(QingChenUtil.class);
	private  long refreshTime = 0L;
	@Autowired
	private RedisService redisService;
	@Autowired
	private WeixinUserRepository weixinuserRepository;
	
	
	private synchronized  void toBtainAccessToken() {
		List<WeixinUserInfo> all=weixinuserRepository.findAll();
		
		WechatTemplate wechatTemplate = new WechatTemplate();  
		wechatTemplate.setTemplate_id("GghwH8K7in-jIoh9CRxRij7Eg5JDI2f5WEdMYp-NMY0");
		wechatTemplate.setUrl("https://mp.weixin.qq.com/s/u2_nNS1m0YOg97ydnG4iZg");
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		String accessToken=(String) redisService.get("ACCESSTOKEN");
        for(WeixinUserInfo j:all) {
        	wechatTemplate.setTouser(j.getOpenId());  
    		Map<String,TemplateData> m = new HashMap<String,TemplateData>();  
    		TemplateData first = new TemplateData();  
    		first.setColor("#000000");    
    		first.setValue("满满的正能量，早晨起来，拥抱太阳，让身体充满，灿烂的阳光，满满的正能量，嘴角向下，会迷失方向，嘴角向上，蒸蒸日上，满满的正能量，世上没有路，都是人开创，脚底板磨破了，道路就顺畅，满满的正能量");    
    		m.put("contends", first);  
    		  
    		TemplateData keyword1 = new TemplateData();    
    		keyword1.setColor("#000000");    
    		keyword1.setValue("鲁春峰");    
    		m.put("author", keyword1);  
    		  
    		TemplateData keyword2 = new TemplateData();    
    		keyword2.setColor("#000000");    
    		keyword2.setValue(simpleDateFormat.format(new Date()));    
    		m.put("updateTime", keyword2);  
    		  
    		wechatTemplate.setData(m); 
    		
    		String jsonString = jsonstring(wechatTemplate).toString();  
        	    String requestUrl = MessageUtil.SEND_TEMPLAYE_MESSAGE_URL.replace("ACCESS_TOKEN", accessToken);   
        	    JSONObject jsonObject = HttpRequestUtil.httpsRequest(requestUrl, "POST", jsonString);   
        	    LOG.info("jsonObject="+jsonObject);    
        	    if (null != jsonObject) {  
        	        int errorCode = jsonObject.getInt("errcode");         
        	        if (0 == errorCode) {  
        	        	LOG.info("模板消息发送成功");  
        	        } else {  
        	            String errorMsg = jsonObject.getString("errmsg");  
        	            LOG.info("模板消息发送失败,错误是 "+errorCode+",错误信息是"+ errorMsg);  
        	        }  
        	    }  
        }
	}


	public void refresh() {
		toBtainAccessToken();
	}

	
	
	
	public static JSONObject jsonstring(WechatTemplate wechatTemplate ) {
		 JSONObject json = new JSONObject();
		 json.put("touser", wechatTemplate.getTouser());
		 json.put("template_id", wechatTemplate.getTemplate_id());
		 json.put("url", wechatTemplate.getUrl());
		 json.put("data", wechatTemplate.getData());
		return json;
	}
}
