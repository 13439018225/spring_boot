package com.huaxin.sboot.util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;


import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
@Configuration
public class ShiroConfig {

   @Bean
   public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
      System.out.println("ShiroConfiguration.shirFilter()");
      ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
      shiroFilterFactoryBean.setSecurityManager(securityManager);
      //拦截器
      Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();
      // 配置不会被拦截的链接 顺序判断，因为前端模板采用了thymeleaf，这里不能直接使用 ("/static/**", "anon")来配置匿名访问，必须配置到每个静态目录
      filterChainDefinitionMap.put("/css/**", "anon");
      filterChainDefinitionMap.put("/fonts/**", "anon");
      filterChainDefinitionMap.put("/images/**", "anon");
      filterChainDefinitionMap.put("/js/**", "anon");
      //filterChainDefinitionMap.put("/html/**", "anon");
      filterChainDefinitionMap.put("/login/**", "anon");
	  //webservice 请求
	  filterChainDefinitionMap.put("/service/**", "anon");
	  filterChainDefinitionMap.put("/services/**", "anon");
      //authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问
      filterChainDefinitionMap.put("/**/*", "authc");
      filterChainDefinitionMap.put("/**/*.do", "authc");
      // 如果不设置默认会自动寻找Web工程根目录下的"/login.html"页面
      shiroFilterFactoryBean.setLoginUrl("/login/tologin");
      // 登录成功后要跳转的链接
      //shiroFilterFactoryBean.setSuccessUrl("/index");

      //未授权界面
      shiroFilterFactoryBean.setUnauthorizedUrl("/403");
      shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
      return shiroFilterFactoryBean;
   }
   
   /*@Bean
    * 不加密码错误次数则用该配置，如果考虑密码错误次数进行账户的锁定，则应该考虑下面的配置（该类的子类）
   public HashedCredentialsMatcher hashedCredentialsMatcher(){
      HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
      hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
      hashedCredentialsMatcher.setHashIterations(2);//散列的次数，比如散列两次，相当于 md5(md5(""));
      return hashedCredentialsMatcher;
   }*/
   
   /**
    * 密码错误次数缓存配置
    */
   @Bean
   public EhCacheManager ehCacheManager(){
	   EhCacheManager ehCacheManager=new EhCacheManager();
	   //ehCacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
	   net.sf.ehcache.CacheManager cacheManager=net.sf.ehcache.CacheManager.getCacheManager("shirocache");
	   if(cacheManager==null){
		   cacheManager= net.sf.ehcache.CacheManager.create();
	   }
	   ehCacheManager.setCacheManager(cacheManager);
	   return ehCacheManager;
   }
   
   @Bean
   public RetryLimitHashedCredentialsMatcher retryLimitHashedCredentialsMatcher(){
	   RetryLimitHashedCredentialsMatcher retryLimitHashedCredentialsMatcher = new RetryLimitHashedCredentialsMatcher(ehCacheManager());
	   retryLimitHashedCredentialsMatcher.setHashAlgorithmName("md5");
	   retryLimitHashedCredentialsMatcher.setHashIterations(2);
	   return retryLimitHashedCredentialsMatcher;
   }
   //登录逻辑类
   @Bean
   public MyShiroRealm myShiroRealm(){
      MyShiroRealm myShiroRealm = new MyShiroRealm();
      myShiroRealm.setCredentialsMatcher(retryLimitHashedCredentialsMatcher());
      return myShiroRealm;
   }
   
   @Bean
   public SecurityManager securityManager(){
      DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
      securityManager.setRealm(myShiroRealm());
      return securityManager;
   }
   
   /**
    * 后台注解权限支持 (方法中的权限注解)
    */
   @Bean
   public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
       DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
       advisorAutoProxyCreator.setProxyTargetClass(true);
       return advisorAutoProxyCreator;
   }
   
   /**
    *  开启shiro aop注解支持.
    *  使用代理方式,所以需要开启代码支持;
    */
   @Bean
   public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
      AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
      authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
      return authorizationAttributeSourceAdvisor;
   }
   /**
    * html 应用shrio 标签
    */
   @Bean
   public ShiroDialect shiroDialect() {
       return new ShiroDialect();
   }

   /**
    * 定义异常信息
    */
  @Bean(name="simpleMappingExceptionResolver")
   public SimpleMappingExceptionResolver createSimpleMappingExceptionResolver() {
      SimpleMappingExceptionResolver r = new SimpleMappingExceptionResolver();
      Properties mappings = new Properties();
      mappings.setProperty("UnauthorizedException","/403");
      mappings.setProperty("AuthorizationException","/403");
      r.setExceptionMappings(mappings);
      // 为所有的异常定义默认的异常处理页面，exceptionMappings未定义的异常使用本默认配置
      //r.setDefaultErrorView("error"); 
      //定义异常处理页面用来获取异常信息的变量名，默认名为exception
      r.setExceptionAttribute("exception");
      return r;
   }
   
}
