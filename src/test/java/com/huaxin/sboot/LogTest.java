package com.huaxin.sboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 日志级别 从高到底 OFF、FATAL、ERROR、WARN、INFO、DEBUG、TRACE、 ALL
 * 建议使用4个  ERROR、WARN、INFO、DEBUG
 * 
 * 注意：测试类所在的包结构一定要与 App启动类在同一包名称下
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LogTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Test
	public void logDebug(){
		logger.debug("debug 日志级别的测试");
	}
	
	@Test
	public void logInfo(){
		logger.info("info 日志级别的测试");
	}
	
	@Test
	public void logWarn(){
		logger.warn("warn 日志级别的测试");
	}
	
	@Test
	public void logError(){
		logger.error("error 日志级别的测试");
	}
	
}
