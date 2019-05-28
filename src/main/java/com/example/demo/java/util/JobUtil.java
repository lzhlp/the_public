package com.example.demo.java.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 
 * @author luchunfeng
 *
 */
@Component
@EnableScheduling
public class JobUtil {

	@Autowired
	ApplicationContext applicationContext;

	/**
	 * 每90分钟刷新一次RocketmqConf
	 */
	@Scheduled(cron = "0 0/90 * * * ?")
	public void refreshAccessToken() {
		AccessTokenUtil rcu = applicationContext.getBean(AccessTokenUtil.class);
		rcu.refresh();
	}
	
	/**
	 * 每天早上8点定时发送给关注用户天气预报
	 */
	@Scheduled(cron = "0 0 08 * * ?")
	public void refreshGyanzhu() {
		QingChenUtil rcu = applicationContext.getBean(QingChenUtil.class);
		rcu.refresh();
	}
	
	/**
	 * 预留接口后期可以帮助定时设置更新内容
	 */
/*	@Scheduled(cron = "0 0/1 * * * ?")
	public void refreshGengxin() {
		UpdateInfoUtil rcu = applicationContext.getBean(UpdateInfoUtil.class);
		rcu.refresh();
	}*/
}
