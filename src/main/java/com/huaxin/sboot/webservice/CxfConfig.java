package com.huaxin.sboot.webservice;

import javax.xml.ws.Endpoint;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * soap用来描述传递信息的格式，是一种简单的基于 XML 的协议,它使应用程序通过 HTTP 来交换信息
 * WSDL 用来描述如何访问具体的接口， uddi用来管理
 * 
 * @author Administrator
 * 
 */
@Configuration
public class CxfConfig {

	@Autowired
	private Bus bus;//服务总线   
	@Autowired
	IWebUserServcie webUserService;
	
	/**
	 * 声明服务发布的前缀 service
	 * 如果整合 Shiro 框架后，放开此配置则无法登录，需要将该配置注释掉
	 * 如果注释掉该配置，则所有webservice的请求模式前缀是默认的 services
	 * 在 Shiro 过滤器中进行放行配置
	 */
	/*@Bean
	public ServletRegistrationBean dispatcherServlet() {
	   return new ServletRegistrationBean(new CXFServlet(), "/service/*");
	}*/
	
	/**
	 * JAX-WS 站点服务
	 * 如果上面的方法注释了则，服务发布地址: http://127.0.0.1/services/user?wsdl
	 * 如果上面的方法放开了则，服务发布地址: http://127.0.0.1/service/user?wsdl
	 * 客户端的代码在工程 cxfClient 中
	 */
	@Bean
	public Endpoint endpoint() {
		EndpointImpl endpoint = new EndpointImpl(bus, webUserService);
		endpoint.publish("/user");
		return endpoint;
	}
}
