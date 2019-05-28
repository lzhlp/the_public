package com.example.demo.java.util;

import javax.servlet.http.HttpSession;

import com.example.demo.java.entity.User;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class SessionTools {

	
	public static final String USER="USER"; //系统用户
	public static final String IP="IP"; //登录人ip
	public static final String IPAREA="IPAREA"; //所在区域
	public static final String VALIDATECODE="VALIDATECODE";  //登陆验证码
	
	public static final String MEMBER="MEMBER";   //前台普通用户
	public static final String MEMBERTYPE="MEMBERTYPE";  //判断前台用户类型 1：普通   2：机构 3:专家
	public static final String ORGANIZATION="ORGANIZATION";   //前台机构用户

	public static final String EXMEMBER="EXMEMBER";   //前台专家用户
	public static final String SHOPCAR="SHOPCAR";
	public static final String LANGUAGE="LANGUAGE";   //中英文切换
	
	public static final String CODE="CODE";  //随机验证码（用于找回密码问题安全验证）
	
	public static final String NOTOPEN = "NOTOPEN";//站内信未读条数
	
	
	public static HttpSession session;
	
	
	public static void setSessionValue(String key,Object value){
		session=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
		session.setAttribute(key, value);
	}
	
	

	public static Object getSessionValue(String key){
		session=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
		return session.getAttribute(key);
	}
	
	
	
	
	public static User getSessionUser(){
		session=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
		return (User) session.getAttribute(USER);
	}


	public static String getSessionIP(){
		session=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
		if(session.getAttribute(IP)==null || session.getAttribute(IP).equals(""))
			return null;
		return session.getAttribute(IP).toString();
		
	}
	
	
	
	
	public static String getSessionIPArea(){
		session=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
		if(session.getAttribute(IPAREA)==null || session.getAttribute(IPAREA).equals(""))
			return null;
		return session.getAttribute(IPAREA).toString();
		
	}
	
	

	public static void clearSessionUser(){
		session=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
		session.removeAttribute(USER);
	}
	
	

	public static void removeSessionKey(String key){
		session=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
		session.removeAttribute(key);
	}
	
	

	public static String getValidateCode(){
		session=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
		if(session.getAttribute(VALIDATECODE)==null || session.getAttribute(VALIDATECODE).equals(""))
			return null;
		return session.getAttribute(VALIDATECODE).toString();
		
	}
	
	
	
	
	
	
	
	
	///////////////////////////////////前台//////////////////////////////////////
	

	public static String getMemberType(){
		session=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();		
		return (String)session.getAttribute(MEMBERTYPE);
	}
	

	
	
	
	public static String getShopCarId(){
		session=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
		return (String) session.getAttribute(SHOPCAR);
	}

	
	public static String getLanguage() {
		session=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
		return (String) session.getAttribute(SessionTools.LANGUAGE);
	}
	
}
