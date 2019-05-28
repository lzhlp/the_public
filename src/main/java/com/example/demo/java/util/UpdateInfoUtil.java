package com.example.demo.java.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
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
public class UpdateInfoUtil {

	private  static final Logger LOG = LogManager.getLogger(UpdateInfoUtil.class);
	private  long refreshTime = 0L;
	@Autowired
	private RedisService redisService;
	@Autowired
	private WeixinUserRepository weixinuserRepository;
	 @Autowired
	 private UpdateInfoRepository updateinfoRepository;

	private synchronized  void toBtainAccessToken() {
		List<WeixinUserInfo> all=weixinuserRepository.findAll();
		
		Contend d=updateinfoRepository.findUpdateInfoOne();
		
		WechatTemplate wechatTemplate = new WechatTemplate();  
		wechatTemplate.setTemplate_id("TYhyAVpllsyPDTwysllWAU80YWUFUX8ozOKo0UJ-Tgg");
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if(d.getShelves()==2) {
			String accessToken=(String) redisService.get("ACCESSTOKEN");
	        for(WeixinUserInfo j:all) {
	        	wechatTemplate.setTouser(j.getOpenId());  
	    		wechatTemplate.setUrl("http://www.lzhlp.com/weixinuser/weixiniinfo?id="+j.getId()+"");
	    		Map<String,TemplateData> m = new HashMap<String,TemplateData>();  
	    		TemplateData first = new TemplateData();  
	    		first.setColor("#000000");    
	    		first.setValue(d.getContends());    
	    		m.put("contends", first);  
	    		  
	    		TemplateData keyword1 = new TemplateData();    
	    		keyword1.setColor("#000000");    
	    		keyword1.setValue(d.getAuthor());    
	    		m.put("author", keyword1);  
	    		  
	    		TemplateData keyword2 = new TemplateData();    
	    		keyword2.setColor("#000000");    
	    		keyword2.setValue(simpleDateFormat.format(d.getUpdateTime()));    
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
	        updateinfoRepository.updateShangjia2(d.getId());
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
