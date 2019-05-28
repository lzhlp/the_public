package com.example.demo.java.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.demo.java.entity.Article;
import com.example.demo.java.entity.Articles;
import com.example.demo.java.entity.TextMessage;
import com.example.demo.java.entity.WeixinResponse;
import com.example.demo.java.entity.WeixinUserInfo;
import com.example.demo.java.jpa.WeixinUserRepository;
import com.example.demo.java.service.weixin.RedisService;



/**
 * 
 * @author luchunfeng
 *
 */
@Component
public class WeixinGuanzhuUtil {

	private  static final Logger LOG = LogManager.getLogger(WeixinGuanzhuUtil.class);
	
	@Autowired
	private RedisService redisService;
	@Autowired
	private WeixinUserRepository weixinuserRepository;

	private synchronized  void toSendMessageUser() {
		List<WeixinUserInfo> all=weixinuserRepository.findAll();
		String accessToken=(String) redisService.get("ACCESSTOKEN");
        for(WeixinUserInfo j:all) {
		try {
			//文本信息
			String json = "{\"touser\": \""+j.getOpenId()+"\",\"msgtype\": \"text\", \"text\": {\"content\": \"满满的正能量，早晨起来，拥抱太阳，让身体充满，灿烂的阳光，满满的正能量，嘴角向下，会迷失方向，嘴角向上，蒸蒸日上，满满的正能量，世上没有路，都是人开创，脚底板磨破了，道路就顺畅，满满的正能量。\"}}";
			//图文信息
			String json1 = "{\"touser\": \""+j.getOpenId()+"\",\"msgtype\": \"news\", \"news\": {\"articles\": [ { \"title\":\"满满的正能量\",\"description\":\"满满的正能量，早晨起来，拥抱太阳，让身体充满，灿烂的阳光，满满的正能量，嘴角向下，会迷失方向，嘴角向上，蒸蒸日上，满满的正能量，世上没有路，都是人开创，脚底板磨破了，道路就顺畅，满满的正能量。\",\"url\":\"www.baidu.com\", \"picurl\":\""+j.getHeadImgUrl()+"\"}]}}";

			//发送模版消息给指定用户
			String action = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+accessToken;

			System.out.println("json:"+json); 
			try {
				String result = sendPost(action, json);
				System.out.println(result);
			} catch (Exception e) {
				e.printStackTrace();
			}
        } catch (Exception e) {
            LOG.error("发送客服消息失败,openId="+j.getOpenId(),e);
        }
        }
	}
	
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			//设置通用的请求属性
			conn.setRequestProperty("user-agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:21.0) Gecko/20100101 Firefox/21.0)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			OutputStreamWriter outWriter = new OutputStreamWriter(conn.getOutputStream(), "utf-8");
			out = new PrintWriter(outWriter);
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				in = new BufferedReader(new InputStreamReader(conn.getInputStream()));result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常!"+e);
			e.printStackTrace();
		}
		//使用finally块来关闭输出流、输入流
		finally{
			try{
				if(out!=null){
					out.close();
				}
				if(in!=null){
					in.close();
				}
			}
			catch(IOException ex){
				ex.printStackTrace();
			}
		}
		return result;
	}   
	
	public void refresh() {
		toSendMessageUser();
	}
}
