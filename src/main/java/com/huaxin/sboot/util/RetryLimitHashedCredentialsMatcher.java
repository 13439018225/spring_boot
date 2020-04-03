package com.huaxin.sboot.util;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;

import com.huaxin.sboot.bean.User;
import com.huaxin.sboot.service.IUserServcie;

public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher{
	
	@Autowired
	private IUserServcie userService;

	public RetryLimitHashedCredentialsMatcher() {
	}
	
	// 声明一个缓存接口，这个接口是Shiro缓存管理的一部分，它的具体实现可以通过外部容器注入
    private Cache<String, AtomicInteger> passwordRetryCache;

    public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager) {
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String username = (String) token.getPrincipal();
        UsernamePasswordToken usertoken = (UsernamePasswordToken) token;
        String password=new String(usertoken.getPassword());
        AtomicInteger retryCount = passwordRetryCache.get(username);
        if (retryCount == null) {
            retryCount = new AtomicInteger(0);
            passwordRetryCache.put(username, retryCount);
        }
        // 自定义一个验证过程：当用户连续输入密码错误3次以上禁止用户登录一段时间
        if (retryCount.incrementAndGet() > 3) {
        	//通过用户名修改用户是否锁定的状态  "1" 锁定  "0" 正常
        	try {
				userService.updateStateByUserName("1", username);
			} catch (Exception e) {
				e.printStackTrace();
			}
            throw new ExcessiveAttemptsException();
        }
        boolean match = super.doCredentialsMatch(token, info);
        if (match) {
            passwordRetryCache.remove(username);
        }
        return match;
    }
	
}
