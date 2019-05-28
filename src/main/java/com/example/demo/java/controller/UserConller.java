package com.example.demo.java.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.java.entity.User;
import com.example.demo.java.service.UserService;
import com.example.demo.java.util.SessionTools;


@Controller
@RequestMapping("/myuser")
public class UserConller {
	private static Logger LOGGER = LoggerFactory.getLogger(UserConller.class);
	
	
	 
		 @Autowired
		 private UserService userservice;
	    /**
	     *  注册
	     * @param user
	     * @return
	     */
	    @RequestMapping(value = "/registered")
	    @ResponseBody
	    public Map<String, Object> save(User user){
	    Map<String,Object> map=new HashMap<String,Object>();
		User save = userservice.save(user);
	    LOGGER.info("mysql搭建成功"+save.getId());
	    map.put("id", save.getId());
	    map.put("result", "1");
	    return map;
	    }
	    
	    
	    
	    
	    
	    /**
	     *  跳转注册页面
	     * @return
	     */
	    @RequestMapping(value = "/toregistered")
	    public String registered(Model d){
	    	return "wxregistered/registered";
	    }
	    
	    /**
	     *    跳转登录页面
	     * @return
	     */
	    @RequestMapping(value = "/tologin")
	    public String login2(Model d){
	    	return "wxregistered/logins";
	    }
	    /**
	     *  登录
	     * @param user
	     * @return
	     */
	    @RequestMapping(value = "/login")
	    @ResponseBody
	    public Map<String, Object> login(User user){
	    Map<String,Object> map=new HashMap<String,Object>();
		User save = userservice.findLogin(user.getUsername(),user.getPassword());
		if(save!=null) {
			map.put("username", save.getUsername());
			map.put("id", save.getId());
			map.put("result", "1");
		}else {
			map.put("result", "2");
		}
	    return map;
	    }
	    
	    /**
	     * 跳转到个人信息首页
	     * @param user
	     * @return
	     */
	    @RequestMapping(value = "/index")
	    public String index(Model d,User user){
	    	d.addAttribute("name", user.getUsername());
	    	d.addAttribute("id", user.getId());
	    	return "selenium/testselenium";
	    }
	    
	    /**
	     *  修改个人信息
	     * @param user
	     * @return
	     */
	    @RequestMapping(value = "/updauser")
	    @ResponseBody
	    public Map<String, Object> update(User user){
	    Map<String,Object> map=new HashMap<String,Object>();
	    userservice.updateInfo(user.getId(),user.getGender(),user.getCourse(),user.getName(),user.getHobby(),user.getHobby1(),user.getHobby2(),user.getHobby3());
	    LOGGER.info("修改成功");
	    map.put("result", "1");
	    return map;
	    }
	    
	/**
	 * 上传附件.txt文件
	 * 
	 * @param user
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/addloda")
	@ResponseBody
	public Map<String, Object> uplode(User user, @RequestParam(value = "file", required = false) MultipartFile file)
			throws IOException {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1); // 得到前一天
		String yestedayDate = new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
		StringBuilder result = new StringBuilder();
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(file.getOriginalFilename())) {
			InputStream inputStream = file.getInputStream();// 获得字节流
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);// 获得字符流
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);// 缓存数据用于读取
			String s = null;
			int i = 0;
			while ((s = bufferedReader.readLine()) != null) {// 使用readLine方法，一次读一行
				String[] arr = s.trim().split("\\|");
				if (arr != null) {
					if (i == 0) {
						if (arr.length == 2) {
							if (!yestedayDate.equals(arr[0])) {
								System.out.println("头部不符合符合" + arr.length);
								map.put("result", "2");
								map.put("message", "不符合规则!");
								return map;
							} else {
								System.out.println("符合" + arr.length);
							}
						}else {
							map.put("result", "2");
							map.put("message", "不符合规则!");
							return map;
						}
					} else {
						if (arr.length == 7) {
							System.out.println("数组长度符合条件" + arr.length);
						}
					}
					i++;
					arr = null;
					result.append(s);
				}
			}
			bufferedReader.close();
			map.put("result", "1");
		} else {
			map.put("result", "2");
			map.put("message", "请上传文件！");
		}
		return map;
	}
	    
	    
	
	 /**
     *  跳转会员卡页面
     * @return
     */
    @RequestMapping(value = "/tomember1")
    @ResponseBody
    public String tomember1(){
    	SessionTools.setSessionValue(SessionTools.MEMBERTYPE, "2");
    	System.out.println(SessionTools.getSessionValue(SessionTools.MEMBERTYPE));
		return "successful";
    }
	    
	    
    
    /**
     *  跳转会员卡页面
     * @return
     */
    @RequestMapping(value = "/tomember")
    public String tomember(Model d,String status){
    	System.out.println(SessionTools.getSessionValue(SessionTools.MEMBERTYPE));
    	if(SessionTools.getSessionValue(SessionTools.MEMBERTYPE)==null) {
    		return "wxregistered/zc";
    	}else {
    		return "wxregistered/member";
    	}
    }
	 
}
