package com.example.demo.java.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.oxm.xstream.XStreamMarshaller;

import com.example.demo.java.entity.AccessToken;
import com.example.demo.java.entity.Article;
import com.example.demo.java.entity.Button;
import com.example.demo.java.entity.ClickButton;
import com.example.demo.java.entity.Menu;
import com.example.demo.java.entity.SendLocalButton;
import com.example.demo.java.entity.SendPicButton;
import com.example.demo.java.entity.TextMessage;
import com.example.demo.java.entity.ViewButton;
import com.example.demo.java.entity.WeixinUserInfo;
import com.example.demo.java.service.weixin.impl.AddMenuIml;
import com.thoughtworks.xstream.XStream;

import net.sf.json.JSONObject;

public class MessageUtil {
	private static Logger LOGGER = LoggerFactory.getLogger(MessageUtil.class);
	//文本消息
	public static final String MESSAGE_TEXT="text";
	//图片消息
	public static final String MESSAGE_IMAGE="image";
	//图文
    public static final String MESSAGE_NEWS = "news";
	//语音消息
	public static final String MESSAGE_VOICE="voice";
	//视频消息
	public static final String MESSAGE_VIDEO="video";
	//链接消息
	public static final String MESSAGE_LINK="link";
	//地理位置消息
	public static final String MESSAGE_LOCATION="LOCATION";
	//事件推送
	public static final String MESSAGE_EVENT="event";
	//关注
	public static final String MESSAGE_SUBSCRIBE="subscribe";
	//取消关注
	public static final String MESSAGE_UNSUBSCRIBE="unsubscribe";
	//文本消息
	public static final String MESSAGE_CLICK="CLICK";
	//文本消息
	public static final String MESSAGE_VIEW="VIEW";
	//添加菜单接口
	public static final String ADD_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	// 获取access_token的接口地址（GET） 限200（次/天）
    public final static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	// 获取用户信息
    public final static String WEIXIN_USER_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";
    //APPID
  	public static final String APPID="wx7876139899174de2";
    //APPSECRET
  	public static final String APPSECRET="0d6a032591dad6ee0fd6b29293a99cee";
  	//推送消息模板
  	public final static String SEND_TEMPLAYE_MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";  
	
	/**
	 * xml转成map集合
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static Map<String,String> xmlToMaphttp(HttpServletRequest request) throws IOException, DocumentException{
		
		Map<String,String> map=new HashMap<String,String>();
		SAXReader reader=new SAXReader();
		InputStream ins=request.getInputStream();
		Document doc =reader.read(ins);
		Element root=doc.getRootElement();
		List<Element> list=root.elements() ;
		
		for(Element a:list) {
			map.put(a.getName(), a.getText());
		}
		ins.close();
		return map;
	}
	
	/**
	 *  将文本转成xml
	 * @param textMessage
	 * @return 
	 */
	public static String textMessageToXml(TextMessage textMessage) {
		XStream xstream=new XStream();
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}
    /**
     * 创建文本信息
     * @param ToUserName
     * @param FromUserName
     * @param Content
     * @return
     */
	public static String initText(String ToUserName,String FromUserName,String Content) {
		TextMessage text=new TextMessage();
		text.setFromUserName(ToUserName);
		text.setToUserName(FromUserName);
		text.setMsgType(MessageUtil.MESSAGE_TEXT);
		text.setCreateTime(new Date().getTime());
		text.setContent(Content);
		return MessageUtil.textMessageToXml(text);
	}
	
	 /**
     * 创建图文信息
     * @param ToUserName
     * @param FromUserName
     * @param Content
     * @return
     */
	public static String initImageMessage(String ToUserName,String FromUserName,int number,List<Article> list) {
		TextMessage text=new TextMessage();
		text.setToUserName(FromUserName);
		text.setFromUserName(ToUserName);
		text.setCreateTime(new Date().getTime());
		text.setMsgType(MessageUtil.MESSAGE_NEWS);
		text.setArticles(list);
		text.setArticleCount(number);
		return MessageUtil.newsimageMessageToXml(text);
	}
	 /**
     * 图文消息对象转换成xml
     * 
     * @param newsMessage 图文消息对象
     * @return xml
     */
    public static String newsimageMessageToXml(TextMessage newsMessage) {
    	XStream xstream=new XStream();
        xstream.alias("xml", newsMessage.getClass());
        xstream.alias("item", new Article().getClass());
        return xstream.toXML(newsMessage);
    }

	
	/**
	 *  回复获取到地理位置的信息
	 * @return
	 */
	public static String locationText(String Latitude,String Longitude,String Precision){
		StringBuffer sb=new StringBuffer();
		sb.append("感谢您的信任，我们获取到您的地理位置是：\n\n");
		sb.append("1 地理位置纬度:"+Latitude+"\n");
		sb.append("2 地理位置经度:"+Longitude+"\n");
		sb.append("3 地理位置精度:"+Precision+"\n\n");
		sb.append("您的地理位置，我们将为您严格保密！");
		return sb.toString();
	}
	
	/**
	 *  菜单
	 * @return
	 */
	public static String menuText(){
		StringBuffer sb=new StringBuffer();
		sb.append("欢迎您的关注，请按照菜单提示进行操作：\n\n");
		sb.append("1 查询天气\n");
		sb.append("2 百度连接\n");
		sb.append("3 获取您的用户信息\n\n");
		sb.append("回复? 调出此菜单。");
		return sb.toString();
	}
	
	/**
	 * 1菜单回复
	 * @return
	 */
	public static String firstMenu(){
		StringBuffer sb=new StringBuffer();
		sb.append("2018年12月28日，星期五\r\n" + 
				"农历十一月二十二\r\n" + 
				"【天气预报】晴\r\n" + 
				"【今天温差】(-13℃~3℃)\r\n" + 
				"【空气质量】良\r\n" + 
				"【PM2.5值】55\r\n" + 
				"【出行限号】1和6");
		return sb.toString();
	}
	

	/**
	 * 2菜单回复
	 * @return
	 */
	public static String secondMenu(){
		StringBuffer sb=new StringBuffer();
		sb.append("www.baidu.com");
		return sb.toString();
	}
	
	/**
	 * 其他菜单回复
	 * @return
	 */
	public static String otherMenu(){
		StringBuffer sb=new StringBuffer();
		sb.append("不好意思我没猜出您要表达的意思，请按照菜单提示进行操作谢谢：\n\n");
		sb.append("1 查询天气\n");
		sb.append("2 百度连接\n");
		sb.append("3 获取您的用户信息\n\n");
		sb.append("回复? 调出此菜单。");
		return sb.toString();
	}
	
	/**
	 * 五天天气查询回复
	 * @return
	 */
	public static String wuTian(String quality,String current,String today,String wind,String weath,String temp){
		StringBuffer sb=new StringBuffer();
		sb.append(quality+"\n\n");
		sb.append(current+"\n");
		sb.append(today+"\n\n");
		sb.append(wind+"\n\n");
		sb.append(weath+"\n\n");
		sb.append(temp+"\n\n");
		sb.append("=============================\n\n");
		return sb.toString();
	}
	
	/**
	 * 组织菜单
	 * @return
	 */
	public static Menu initMenu(){
		Menu menu = new Menu();
		ViewButton button11 = new ViewButton();
		//注意按钮名字不要太长，不然会报40018错误
		button11.setName("百度一下");
		button11.setType("view");
		button11.setUrl("https://www.baidu.com");
		//注意链接不要少了https://  否则会报错40055
		
		SendPicButton button21 = new SendPicButton();
		button21.setName("选择图片");
		button21.setType("pic_photo_or_album");
		button21.setKey("pic");
		
		SendLocalButton button32 = new SendLocalButton();
		button32.setName("发送位置");
		button32.setType("location_select");
		button32.setKey("local");
		
		ClickButton button31 = new ClickButton();
		button31.setName("点赞");
		button31.setType("click");
		button31.setKey("strtest");//事件key
		
		Button button = new Button();
		button.setName("安全系统");
		button.setSub_button(new Button[]{button31,button32});
		button.setSub_button(new Button[]{button31,button32});
		
		menu.setButton(new Button[]{button11,button21,button});
		return menu;
	}
	/**
	 * 获取AccessToken
	 * @param appid
	 * @param appsecret
	 * @return
	 */
	public static AccessToken getAccessToken(String appid,String appsecret){
		//将公众号的appid和appsecret替换进url
		String url = MessageUtil.ACCESS_TOKEN_URL.replace("APPID", appid).replace("APPSECRET", appsecret);
		AccessToken accessToken = new AccessToken();
		//发起get请求获取凭证
		JSONObject jsonObject = HttpRequestUtil.httpsRequest(url,"GET",null);
		LOGGER.info("获取到的json格式的Token为:"+jsonObject);
		if (jsonObject!=null) {
			try {
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
			} catch (Exception e) {
				accessToken = null;
				//获取token失败
				LOGGER.error("获取token失败 errcode:{} errmsg:{}", 
						jsonObject.getInt("errcode"), 
						jsonObject.getString("errmsg"));
			}		
		}
		return accessToken;
	}


	
	

}
