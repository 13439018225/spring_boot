package com.huaxin.sboot.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.alibaba.fastjson.JSONObject;


/**
 * ClassName: XmlParseUtil <br/>
 * 解析xml通用工具类 <br/>
 * date: 2017年3月25日 下午1:32:29 <br/>
 * 
 * @author fdz
 */
public class XmlParseUtil {

	private static Logger logger =Logger.getLogger(XmlParseUtil.class);
	
	public static Map<String, String> Dom4jXmlParse(String xml) {
		Document doc = null;
		Map<String,String> map=new HashMap<String,String>();
		try {
			// 读取并解析XML文档
			// SAXReader就是一个管道，用一个流的方式，把xml文件读出来
			// SAXReader reader = new SAXReader(); 
			// Document document = reader.read(new File("User.hbm.xml"));
			// 下面的是通过解析xml字符串的
			doc = DocumentHelper.parseText(xml); // 将字符串转为XML
			Element root = doc.getRootElement(); // 获取根节点
			Iterator<Element> iter = root.elementIterator();
			while (iter.hasNext()) {
				Element element = (Element) iter.next();
				String name=element.getName();//节点名称
				String text=element.getText();//节点值
				map.put(name, text);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return map;
	}
	
	 static String  xml ="<?xml version=\"1.0\" encoding=\""+FinalCodeUtil.DEDUCT_CHARSET+"\"?>"+ 
				"<custmrBusi>" +
				   "<srcChnl>DSF</srcChnl>" +
				   "<bankNo>0010</bankNo>" +
				   "<bankName>建设银行</bankName>" +
				   "<cusNm>0000000001</cusNm>"+
				   "<mobileNo>13439018225</mobileNo>"+
				   "<credtNo>152631187365235112</credtNo>"+
				   "<acntNo>16352229997555</acntNo>"+
				   "<acntNm>冯德智</acntNm>"+
				   "<acntPro>北京</acntPro>"+
				   "<acntCity>北京</acntCity>"+
				   "<loanAmount>20000</loanAmount>"+
				   "<mchntCd>30000FFFF099999</mchntCd>"+
				   "<isCallback>0</isCallback>"+
				   "<signature>0000000000000000002</signature>" +
				 "</custmrBusi>";
	 
	 
	public static void main(String[] args) {
		Map<String,String> map=XmlParseUtil.Dom4jXmlParse(xml);
		System.out.println(map);
	}

}
