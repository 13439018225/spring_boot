package com.huaxin.sboot.util;

import java.security.MessageDigest;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;


/**
 * 
 * ClassName: MD5 <br/>
 * MD5加密工具类
 * date: 2017年3月18日 下午1:20:07 <br/>
 * * @author fdz
 */
public class MD5 {

	//定义一个密钥
	public  static final  String SECRET_KEY="HUAXINZHIYUANSECRET_KEY";
	/**
	 * stringMD5:加密方法 <br/>
	 * @author fdz
	 * @param username 用户名
	 * @param pwd  密码
	 * @param encrypt 加密类型 MD5 ,SHA1
	 * @return
	 */
	public static String encodeMD5(String username,String pwd,String encrypt) {
		String secretstr=username+SECRET_KEY+pwd;
		String encryptpass="";
		try {
			// 拿到一个MD5转换器（如果想要SHA1参数换成”SHA1”）
			MessageDigest messageDigest = MessageDigest.getInstance(encrypt);
			// 输入的字符串转换成字节数组
			byte[] inputByteArray = secretstr.getBytes();
			// inputByteArray是输入字符串转换得到的字节数组
			messageDigest.update(inputByteArray);
			// 转换并返回结果，也是字节数组，包含16个元素
			byte[] resultByteArray = messageDigest.digest();
			// 字符数组转换成字符串返回
			encryptpass=byteArrayToHex(resultByteArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptpass;
	}
	public static String encodeMD5(String username,String pwd) {
		return encodeMD5(username,pwd,"MD5");
	}
	public static String byteArrayToHex(byte[] byteArray) {
		// 首先初始化一个字符数组，用来存放每个16进制字符
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		// new一个字符数组，这个就是用来组成结果字符串的
		char[] resultCharArray = new char[byteArray.length * 2];
		// 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
		int index = 0;
		for (byte b : byteArray) {
			resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
			resultCharArray[index++] = hexDigits[b & 0xf];
		}
		// 字符数组组合成字符串返回
		return new String(resultCharArray);
	}

    public static String encryptPassword(String username,String pwd,String salt) {
    	String secretstr=username+SECRET_KEY;
    	if(salt==null){
    		salt=secretstr;
    	}
    	String ALGORITHM_NAME = "md5"; // 基础散列算法
        int HASH_ITERATIONS = 2; // 自定义散列次数
        String newPassword = new SimpleHash(ALGORITHM_NAME,pwd,ByteSource.Util.bytes(secretstr), HASH_ITERATIONS).toHex();
        return newPassword;
    }
    
	public static void main(String[] args) {
		System.out.println("MD5加密== "+MD5.encodeMD5("admin", "1", "MD5"));
		System.out.println("SHA1加密== "+MD5.encodeMD5("admin", "1", "SHA1"));
		System.out.println("hash加密=="+MD5.encryptPassword("admin", "2", null));
	}
}
