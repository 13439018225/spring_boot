package com.huaxin.sboot.util;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.huaxin.sboot.bean.Permission;
import com.huaxin.sboot.bean.User;
import com.huaxin.sboot.service.IUserInfoServcie;
import com.huaxin.sboot.service.IUserServcie;



public class MyShiroRealm extends AuthorizingRealm{

	@Autowired
	private IUserServcie userService;
	/**
	 * 权限信息，包括角色以及权限
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals){
		String  username = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        try {
        	 // 根据用户名查询当前用户拥有的角色
            Set<String> roles = userService.findRoles(username);
            // 将角色名称提供给info
            authorizationInfo.setRoles(roles);
            
            // 根据用户名查询当前用户权限
            Set<String> permissions = userService.findPermissions(username);
            // 将权限名称提供给info
            authorizationInfo.setStringPermissions(permissions);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return authorizationInfo;
	}

	/**
	 * 主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal();
        User user=null;
		try {
			user = userService.findByUsername(username);
		} catch (Exception e) {
			e.printStackTrace();
		}
        if (user == null) {
            // 用户名不存在抛出异常
            throw new UnknownAccountException();
        }
        if ("1".equals(user.getState())) {
            // 用户被管理员锁定抛出异常
            throw new LockedAccountException();
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getName(),
        		user.getPassword(), ByteSource.Util.bytes(user.getName()+MD5.SECRET_KEY), getName());
        
        return authenticationInfo;
	}

}
