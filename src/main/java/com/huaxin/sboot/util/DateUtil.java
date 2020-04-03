package com.huaxin.sboot.util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtil {

	public static void main(String[] args) {
		/*Date d=getCurrentDate();
		date2String(d,"yyyy-MM-dd");
		System.out.println(getReqCode("1","C"));
		string2Date("2010-09-09","yyyy-MM-dd");
		*/
	}
	
	/**
	 * 获取当前日期 
	 */
	public static Date getCurrentDate(){
		java.util.Date udate=new java.util.Date();
		Date date=new Date(udate.getTime());
		//System.out.println(date);
		return date;
	}
	
	/**
	 * date 转 字符串
	 * @param n
	 * @return
	 */
	public static String date2String(Date date,String pattn){
		SimpleDateFormat df=new SimpleDateFormat(pattn);
		String str=df.format(date);
		//System.out.println(str);
		return str;
	}
	
	/**
	 * 字符串 转 date
	 * @param n
	 * @return
	 */
	public static Date string2Date(String str,String pattn){
		SimpleDateFormat df=new SimpleDateFormat(pattn);
		Date sqldate=null;
		try {
			java.util.Date udate=df.parse(str);
			sqldate=new Date(udate.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//System.out.println(sqldate);
		return sqldate;
	}
	/**
	 * 生成编号规则
	 * @param code
	 * @param preffix 前缀
	 * @return
	 */
	public static String getReqCode(String code,String preffix){
		//日期+000+code
		String sysdate=date2String(getCurrentDate(),"yyyyMMdd");
		StringBuffer bf=new StringBuffer(preffix+sysdate);
		for(int k=0;k<4-code.length();k++){
			bf.append("0");
		}
		bf.append(code);
		return bf.toString();
	}
}
