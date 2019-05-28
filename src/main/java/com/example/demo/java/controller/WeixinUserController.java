package com.example.demo.java.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.java.entity.User;
import com.example.demo.java.entity.WeixinUserInfo;
import com.example.demo.java.jpa.UserRepository;
import com.example.demo.java.jpa.WeixinUserRepository;
import com.example.demo.java.service.weixin.RedisService;

@Controller
@RequestMapping("/weixinuser")
public class WeixinUserController {
	private static Logger LOGGER = LoggerFactory.getLogger(WeixinUserController.class);
	
	
	 @Autowired
	 private WeixinUserRepository weixinuserRepository;
	 
	 @Autowired
	 private RedisService redisService;
	    /**
	     *  添加微信关注者的信息
	     * @param weixinuser
	     * @return
	     */
	    @RequestMapping(value = "/addweixinuser")
	    @ResponseBody
	    public Map<String, Object> addweixinuser(WeixinUserInfo weixinuser){
	    Map<String,Object> map=new HashMap<String,Object>();
	    WeixinUserInfo save = weixinuserRepository.save(weixinuser);
	    LOGGER.info("mysql搭建成功"+save.getId());
	    map.put("id", save.getId());
	    map.put("result", "1");
	    return map;
	    }
	    
	    
	    /**
	     *  添加微信关注者的信息
	     * @param weixinuser
	     * @return
	     */
	    @RequestMapping(value = "/deleteweixinuser")
	    @ResponseBody
	    public Map<String, Object> deleteweixinuser(String openId){
	    Map<String,Object> map=new HashMap<String,Object>();
	    weixinuserRepository.deleteByOpenid(openId);
	    map.put("result", "1");
	    return map;
	    }
	    
	    
	    @RequestMapping(value = "/get")
	    @ResponseBody
	    public String getKey(){
	    	String value=(String) redisService.get("ACCESSTOKEN");
	    	LOGGER.info("取值redis成功:"+value);
	    	return value;
	    }
	    
	    /**
	     * 个人微信信息展示
	     * @param user
	     * @return
	     */
	    @RequestMapping(value = "/weixiniinfo")
	    public String weixiniinfo(Model d,String id){
	    	WeixinUserInfo user=weixinuserRepository.findByIdOne(id);
	    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        long lt = new Long(user.getSubscribeTime());
	        
	        long time1 = 1527767665;
	        String result1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(lt * 1000));
	        System.out.println("10位数的时间戳（秒）--->Date:" + result1);
	    /*    Date date1 = new Date(time1*1000);
	        
	        Date date = new Date(lt);
	        String res = simpleDateFormat.format(date);*/
	        user.setSubscribeTime(result1);
	    	d.addAttribute("user", user);
	    	return "weixininfo/index";
	    }
	 
}
