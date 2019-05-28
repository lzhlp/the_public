package com.example.demo.java.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.java.entity.User;
import com.example.demo.java.jpa.UserRepository;
import com.example.demo.java.service.weixin.AddMenuService;
import com.example.demo.java.service.weixin.WeixinServlet;

@Controller
@RequestMapping("/myweixin")
public class WeiXinController extends WeixinServlet{
	private static Logger LOGGER = LoggerFactory.getLogger(UserConller.class);
	
		@Autowired
		private AddMenuService weiXinService;
		 /**
		  * 微信公众号接口入口
		  * @param req 
		  * @param resp
		  * @throws ServletException
		  * @throws IOException
		  */
	    @RequestMapping(value = "/wx" , method = {RequestMethod.GET,RequestMethod.POST})
	    public void wx(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException{
		resp.setContentType("text/html;charset=utf-8");
	      System.out.println("进入方法");
	      Boolean isGet = req.getMethod().toLowerCase().equals("get");
	      if (isGet){
	    	  super.doGet(req, resp);
	      }else {
	    	  super.doPost(req, resp);
	      }
	    }
	    
	
		/**
		 * 自定义菜单接口
		 * @return 
		 */
	    @RequestMapping(value="/menuAdd",method=RequestMethod.GET)
	    @ResponseBody
	    public Map<String, Object> menuAdd(){
	    	Map<String,Object> map=new HashMap<String,Object>();
	    	boolean b = weiXinService.menuAdd();
			if (b) {
				 map.put("result", "1");
			}else {
				 map.put("result", "2");
			}
			return map;
	    }
	    
}
