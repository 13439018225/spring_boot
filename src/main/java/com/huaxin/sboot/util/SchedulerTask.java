package com.huaxin.sboot.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.huaxin.sboot.dao.ICodeMapper;
import com.huaxin.sboot.dao.IUserMapper;

/**
 * 
 * 定时任务
 * 1、@Scheduled 参数可以接受两种定时的设置，一种是我们常用的cron="*\/6 * * * * ?","
 * 一种是 fixedRate = 6000，两种都表示每隔六秒打印一下
 * 
 * 2、@Scheduled(fixedRate = 6000) ：上一次开始执行时间点之后6秒再执行
   @Scheduled(fixedDelay = 6000) ：上一次执行完毕时间点之后6秒再执行
   @Scheduled(initialDelay=1000, fixedRate=6000) ：第一次延迟1秒后执行，之后按fixedRate的规则每6秒执行一次
   
   3、在启动类上面加上@EnableScheduling
 */
@Component
public class SchedulerTask {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @Autowired
	private ICodeMapper codeMapper;
    
    @Autowired
    private IUserMapper userMapper;
	
	private int count=0;
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	//@Scheduled(cron="*/6 * * * * ?")
    private void taskMethod01(){
        System.out.println("打印结果  "+(count++));
    }
	

    //@Scheduled(fixedRate = 6000)
    public void taskMethod02() {
        System.out.println("现在时间：" + dateFormat.format(new Date()));
    }
    
    /**
     * 将数据插入 编号表 b_code
     * 每天执行一次 
     * @throws Exception 
     */
    @Scheduled(cron="0 17 9 0/1 * ? ")
    public void inserCode() throws Exception {
    	String[] types={"0","1"};
    	Map<String,Object> map=new HashMap<String,Object>();
		map.put("reqdate", DateUtil.getCurrentDate());
		map.put("code", 0);
		for(String s:types){
			map.put("type", s);
			codeMapper.insertCode(map);
		}
    }
    
    /**
     * 用户锁定状态解锁
     */
    @Scheduled(cron="0 0/3 * * * ? ")
    public void updateUserState() throws Exception {
    	Map<String,Object> map=new HashMap<String,Object>();
    	map.put("newstate", "0");
    	map.put("oldstate", "1");
    	int k=userMapper.updateState(map);
    	logger.info("用户锁定状态解锁==>"+k+"条");
    }
}
