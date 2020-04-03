package com.huaxin.sboot.controller;

import java.util.HashMap;
import java.util.Map;







import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.huaxin.sboot.bean.User;
import com.huaxin.sboot.service.ILoginServcie;
import com.huaxin.sboot.service.IUserServcie;
import com.huaxin.sboot.util.MD5;
import com.huaxin.sboot.util.RedisUtil;
import com.huaxin.sboot.util.SMS;
@Controller
@RequestMapping("/login")
public class LoginController {
    
	@Autowired
	private ILoginServcie loginServcie;
	
	@Autowired
	private IUserServcie userService;
	
	@Autowired
	private RedisUtil redisUtil;
	
	@RequestMapping("/tologin")
	public String tologin(){
		return "login";
	}
	
	/*@RequestMapping("/login")
	public String login(User user,HttpSession session,Model model){
		try {
			//对密码加密
			String md5pwd=MD5.encryptPassword(user.getName(), user.getPassword(), null);
			user.setPassword(md5pwd);
			User u=loginServcie.login(user);
			if(u!=null && StringUtils.isNotEmpty(u.getId())){
				session.setAttribute("userinfo", u);
				return "redirect:/main/tomain";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("mess","用户名或密码错误");
		return "login";
	}*/
	
	@RequestMapping("/login")
	public String login(User user,ModelMap mv){
		//构建一个登录的令牌对象
		UsernamePasswordToken token = new UsernamePasswordToken(user.getName(), user.getPassword());
		//构建登录对象
		Subject subject=SecurityUtils.getSubject();
		String page="login";
		try {
            subject.login(token);
        } catch (IncorrectCredentialsException ice) {
            // 捕获密码错误异常
        	mv.addAttribute("message", "密码错误!");
            return page;
        } catch (UnknownAccountException uae) {
            // 捕获未知用户名异常
            mv.addAttribute("message", "用户名错误!");
            return page;
        } catch (ExcessiveAttemptsException eae) {
            // 捕获错误登录过多的异常
            mv.addAttribute("message", "错误次数超3次");
            return page;
        }catch (LockedAccountException le) {
            // 捕获账户被锁定
            mv.addAttribute("message", "账户被锁定");
            return page;
        }
		try {
			//将用户信息放到session中
			user = userService.findByUsername(user.getName());
			subject.getSession().setAttribute("userinfo", user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/main/tomain";
	}
	
	//退出
	@RequestMapping("/logout")
	public String logout(HttpSession session){
		
		/*session.removeAttribute("userinfo");
		//销毁session
		session.invalidate();*/
		
		Subject subject=SecurityUtils.getSubject();
		subject.logout();
		return "login";
	}
	
	//发送验证码
	@RequestMapping("/sendCode")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public String sendCode(String tel){
		String res="";
		//短信验证码生命周期(有效期),目前设置了90秒
		long time=1*90;
		if(StringUtils.isNotEmpty(tel)){
			//随机验证码6位
			String valcode=SMS.getRandCode(6);
			Map<String,String> map=new HashMap<String,String>();
			map.put("code", valcode);
			String param=JSONObject.toJSONString(map);
			//发送短信，返回发送结果
			String mess=SMS.sendSms(tel, param);
			//将json格式的字符串转化为对象
			Map<String,String> resmap=JSONObject.parseObject(mess, Map.class);
			//短信发送返回结果的状态码
			String statusCode=resmap.get("Code");
			if("OK".equals(statusCode)){
				//发送成功,记录验证码到redis
				boolean f=redisUtil.set(tel, valcode, time);
				if(f){
					res=time+"_OK";
				}
			}
		}
		return res;
	}
	
	//提交验证码
	@RequestMapping("/subValCocde")
	@ResponseBody
	public String subValCocde(String tel,String valcode){
		String res="0";
		//通过key(手机号),获取验证码,验证码由于有生命周期，所以过期后是取不到的
		String valicode=redisUtil.get(tel)==null?null:(String)redisUtil.get(tel);
		if(valcode.equals(valicode)){
			//如果用户提交过来的验证码与系统发送给用户的一致，则返回1，反之返回0
			res="1";
		}
		return res;
	}
	
	//重置密码
	@RequestMapping("/reUpPwd")
	@ResponseBody
	public String reUpPwd(User user){
		int k=0;
		try {
			k=loginServcie.reUpPwd(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return k+"";
	}
}
