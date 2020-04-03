package com.huaxin.sboot.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.huaxin.sboot.bean.Deduct;

public class DeductUtil {
	//记录日志
	private static Logger logger = Logger.getLogger(DeductUtil.class);
	
	/**
	 * sendDeductRequest:划扣请求方法 <br/>
	 * @author fdz
	 * @param deduct
	 * @return
	 */
	public static String sendDeductRequest(Deduct deduct){
		String strResp="";
		String CHARSET=FinalCodeUtil.DEDUCT_CHARSET;//请求参数编码
		String TIMEOUT=FinalCodeUtil.DEDUCT_TIMEOUT;//超时时间
		String strURL=FinalCodeUtil.DEDUCT_URL;//请求路径
		String mchntCd=FinalCodeUtil.DEDUCT_MCHNTCD;//商户号
		String mchntPass=FinalCodeUtil.DEDUCT_MCHNTPASS;//商户密码
		deduct.setMchntCd(mchntCd);
		deduct.setMchntPass(mchntPass);
		//组装签名参数
		List<String> parameterlist=getParameterList(deduct);
		//生成签名撮
		String signature=hex(parameterlist,deduct.getMchntPass());
		//请求报文
		String xml ="<?xml version=\"1.0\" encoding=\""+CHARSET+"\"?>"+ 
					"<custmrBusi>" +
					   "<srcChnl>DSF</srcChnl>" +
					   "<serialNum>"+deduct.getSerialNum()+"</serialNum>" +
					   "<bankNo>"+deduct.getBankNo()+"</bankNo>" +
					   "<bankName>"+deduct.getBankName()+"</bankName>" +
					   "<cusNm>"+deduct.getCusNm()+"</cusNm>"+
					   "<mobileNo>"+deduct.getMobileNo()+"</mobileNo>"+
					   "<credtNo>"+deduct.getCredtNo()+"</credtNo>"+
					   "<acntNo>"+deduct.getAcntNo()+"</acntNo>"+
					   "<acntNm>"+deduct.getAcntNm()+"</acntNm>"+
					   "<acntPro>"+deduct.getAcntPro()+"</acntPro>"+
					   "<acntCity>"+deduct.getAcntCity()+"</acntCity>"+
					   "<loanAmount>"+deduct.getLoanAmount()+"</loanAmount>"+
					   "<mchntCd>"+deduct.getMchntCd()+"</mchntCd>"+
					   "<isCallback>0</isCallback>"+
					   "<signature>"+signature+"</signature>" +
					 "</custmrBusi>";
		
		//创建默认客户端对象
		DefaultHttpClient httpclient = new DefaultHttpClient();
		//设置请求参数编码
		httpclient.getParams().setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET,CHARSET);
		//连接超时
		httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,Integer.parseInt(TIMEOUT));
		//读取超时
		httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,Integer.parseInt(TIMEOUT));
		//创建客户端请求方法，传入请求路径 url
		org.apache.http.client.methods.HttpPost PostMethod  =new org.apache.http.client.methods.HttpPost(strURL);
		//创建参数集合对象
		List<NameValuePair> nvps = new ArrayList <NameValuePair>();  
		nvps.add(new BasicNameValuePair("xml",xml));
		logger.info("调用接口发送xml报文===>:"+nvps);
		try {
			//将参数放置到发起请求的方法中
			PostMethod.setEntity(new UrlEncodedFormEntity(nvps,CHARSET));
			long start = System.currentTimeMillis();
			HttpResponse response = httpclient.execute(PostMethod);  
			logger.info("支付请求耗时:"+(System.currentTimeMillis()-start)/1000f+"秒");
			//服务端返回状态
			int statusCode = response.getStatusLine().getStatusCode();
			//服务端返回报文对象
			HttpEntity entity = response.getEntity();
			//读取服务端返回报文内容 
			strResp = EntityUtils.toString(entity,CHARSET);
			if (statusCode != HttpStatus.SC_OK) {
				logger.info("调用接口失败返回报文===>:"+strResp);
			}else {
				logger.info("调用接口成功返回报文===>:"+strResp);
			}
		} catch (Exception e) {
			logger.info("调用接口返回错误信息========>",e);
		}finally{
			httpclient.getConnectionManager().shutdown();
		}
		return strResp;
	}
	/**
     * 支付接口组装参数
     * @param lender
     * @param readXML
     * @return
     */
	public  static List<String>  getParameterList(Deduct deduct){
		List<String> templist=new ArrayList<String>();
		templist.add("DSF");
		templist.add(deduct.getSerialNum());
		templist.add(deduct.getBankNo());
		templist.add(deduct.getBankName());
		templist.add(deduct.getCusNm());
		templist.add(deduct.getMobileNo());
		templist.add(deduct.getCredtNo());
		templist.add(deduct.getAcntNo());
		templist.add(deduct.getAcntNm());
		templist.add(deduct.getAcntPro());
		templist.add(deduct.getAcntCity());
		templist.add(deduct.getLoanAmount());
		templist.add(deduct.getMchntCd());
		templist.add("0");
		return templist;
	}
	/**
	 * 参数生成密钥方法
	 */
	public static String hex(List<String> values,String key){
		String[] strs = new String[values.size()];
		for(int i=0;i<strs.length;i++){
			strs[i] = values.get(i);
		}
		Arrays.sort(strs);
		StringBuffer source = new StringBuffer();
		for(String str:strs){
			source.append(str).append("|");
		}
		String bigstr = source.substring(0,source.length()-1);
		logger.info("支付接口签名明文===>:"+bigstr);
		String result = DigestUtils.shaHex(DigestUtils.shaHex(bigstr)+"|"+key);
		logger.info("支付接口生成的签名信息===>:"+result);
		return result;
	}

}
