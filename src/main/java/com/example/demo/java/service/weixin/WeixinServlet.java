package com.example.demo.java.service.weixin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.java.entity.Article;
import com.example.demo.java.entity.TextMessage;
import com.example.demo.java.entity.User;
import com.example.demo.java.entity.WeixinUserInfo;
import com.example.demo.java.jpa.WeixinUserRepository;
import com.example.demo.java.util.CheckUtil;
import com.example.demo.java.util.MessageUtil;
import com.example.demo.java.util.WeatherUtil;

@Component
public class WeixinServlet extends HttpServlet{
	
	@Autowired
	private AddWeixinService addweiXinService;
	
	@Autowired
	private RedisService redisService;
	@Autowired
	private WeixinUserRepository weixinuserRepository;
	
	@Override
	protected void doGet(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException{
		
	String signature=req.getParameter("signature");	
	String timestamp=req.getParameter("timestamp");	
	String nonce=req.getParameter("nonce");	
    String echostr=req.getParameter("echostr");	
    
    PrintWriter out =resp.getWriter();
    if(CheckUtil.checkSignature(signature, timestamp, nonce)) {
    	out.print(echostr);
    }
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException{
		  PrintWriter out =resp.getWriter();
		  req.setCharacterEncoding("UTF-8");
		  resp.setCharacterEncoding("UTF-8");
		  
		  String code="";
		  String openid="";
		try {
			
			Map<String,String> map=MessageUtil.xmlToMaphttp(req);
			String FromUserName=map.get("FromUserName");
			String ToUserName=map.get("ToUserName");
			String MsgType=map.get("MsgType");
			String Content=map.get("Content");
			List<Article> articleList = new ArrayList<Article>();
			String message=null;
			//文本消息
			if(MessageUtil.MESSAGE_TEXT.equals(MsgType)) {
				if("1".equals(Content)) {
					message=MessageUtil.initText(ToUserName, FromUserName, WeatherUtil.getWeather());
				}else if("2".equals(Content)){
					message=MessageUtil.initText(ToUserName, FromUserName, MessageUtil.secondMenu());
				}else if("3".equals(Content)){
					WeixinUserInfo user = weixinuserRepository.findWeixinUser(FromUserName);
					Article article = new Article();
					if(user.getSex()==1) {
						article.setTitle("帅锅你好："+user.getNickname());					
					}if(user.getSex()==2) {
						article.setTitle("小仙女你好："+user.getNickname());					
					}if(user.getSex()==0){
						article.setTitle("未知性别你好："+user.getNickname());					
					}
                    article.setDescription("点击进入详情");
                    article.setPicUrl(user.getHeadImgUrl());
                 /*   article.setUrl("https://music.163.com/m/song?id=1331364073&userid=469432388&from=groupmessage&isappinstalled=0");*/
                    article.setUrl("http://23g1708k68.iok.la/weixinuser/weixiniinfo?id="+user.getId()+"");
                    articleList.add(article);
                    message=MessageUtil.initImageMessage(ToUserName, FromUserName,articleList.size(),articleList);
				}else if("?".equals(Content) || "？".equals(Content)){
					message=MessageUtil.initText(ToUserName, FromUserName, MessageUtil.menuText());
				}else {
					message=MessageUtil.initText(ToUserName, FromUserName, MessageUtil.otherMenu());
				}
			}else if(MessageUtil.MESSAGE_EVENT.equals(MsgType)) {
				String eventType=map.get("Event");	
				if(MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)) {
					code="1";
					openid=FromUserName;
					message=MessageUtil.initText(ToUserName, FromUserName, MessageUtil.menuText());
				}if(MessageUtil.MESSAGE_UNSUBSCRIBE.equals(eventType)) {
					code="2";
					openid=FromUserName;
					message=MessageUtil.initText(ToUserName, FromUserName, MessageUtil.menuText());
				}if(MessageUtil.MESSAGE_LOCATION.equals(eventType)) {
					String Latitude=map.get("Latitude");
					String Longitude=map.get("Longitude");
					String Precision=map.get("Precision");
					message=MessageUtil.initText(ToUserName, FromUserName, MessageUtil.locationText(Latitude,Longitude,Precision));
				}
				}
			System.out.println(message);
			out.print(message);
		} catch (DocumentException e) {
			e.printStackTrace();
		}finally{
			out.close();
			//关注逻辑处理
			if(code.equals("1")) {
				String accessToken=(String) redisService.get("ACCESSTOKEN");
				addweiXinService.getUserInfo(accessToken, openid);		
			}
			//取消关注逻辑处理
			if(code.equals("2")) {
				 weixinuserRepository.deleteByOpenid(openid);
			}
		}
		
	}


}
