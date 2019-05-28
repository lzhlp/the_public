package com.example.demo.java.controller.updateinfo;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.java.entity.Contend;
import com.example.demo.java.entity.User;
import com.example.demo.java.jpa.UpdateInfoRepository;
import com.example.demo.java.util.UpdateInfoUtil;

@Controller
@RequestMapping("/updateinfo")
public class UpdateInfoController {
	private static Logger LOGGER = LoggerFactory.getLogger(UpdateInfoController.class);
	
	
	 @Autowired
	 private UpdateInfoRepository updateinfoRepository;
	 @Autowired
     ApplicationContext applicationContext;
	    /**
	     *  添加更新信息 鲁春
	     * @param weixinuser
	     * @return
	     */
	    @RequestMapping(value = "/addupdateinfo")
	    @ResponseBody
	    public Map<String, Object> addupdateinfo(Contend updateinfo){
	    Map<String,Object> map=new HashMap<String,Object>();
	    /*updateinfo.setContends("此次版本更新(将个人信息展示页进行优化，使读者更赏心悦目)");
	    updateinfo.setAuthor("鲁春峰");*/
	    updateinfo.setUpdateTime(new Date());
	    updateinfo.setShelves(2);
	    Contend save = updateinfoRepository.save(updateinfo);
	    updateinfoRepository.updateShangjia1(save.getId());
	    //异步启动发布更新通知
	    new Thread(new Runnable() {
			@Override
			public void run() {
				UpdateInfoUtil rcu = applicationContext.getBean(UpdateInfoUtil.class);
				rcu.refresh();
			}
		}).start();
	    map.put("id", save.getId());
	    map.put("result", "1");
	    return map;
	    }
	    
	    
	    /**
	     * 跳转到个人信息首页
	     * @param user
	     * @return
	     */
	    @RequestMapping(value = "/sssss")
	    public String index(Model d){
	    	d.addAttribute("name", "测试");
	    	return "selenium/testselenium";
	    }
	 
}
