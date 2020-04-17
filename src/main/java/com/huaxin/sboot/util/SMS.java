package com.huaxin.sboot.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

/**
 * http://www.alidayu.com/
 * 短息发送工具类
 * 服务提供商阿里云
 */
public class SMS {
	
	//AccessKeyId
	private static final String ACCESSKEYID="LTAI4FgwrVhviSSfPZjv";
	//AccessKeySecret
	private static final String ACCESSKEYSECRET="9aWiS2Z8rP2iZlWgYuRc8giEZ";
	//模板类型 短息验证码类型
	private static final String TEMPLATECODE="SMS_184631141"; 
	//private static final String TEMPLATECODE="SMS_184631147";
	//签名名称
	private static final String SIGNNAME="XS系统";
	
	public static void main(String[] args) {
		//单条参数
		Map<String,String> map1=new HashMap<String,String>();
		map1.put("name", getRandCode(6));
		String param=JSONObject.toJSONString(map1);
		//System.out.println(param);
		sendSms("17331659394,13351033175,15732216337,15774632347",param);
		
		//批量测试
		List<Map<String,String>> list=new ArrayList<Map<String,String>>();
		Map<String,String> map2=new HashMap<String,String>();
		map2.put("code", getRandCode(6));
		list.add(map1);
		list.add(map2);
		String[] phones={"11111111111","12222222222"};
		//发送参数
		String jsonparam=JSONObject.toJSONString(list);
		//发送手机号
		String phoneNumberJson=JSONObject.toJSONString(phones);
		
		System.out.println(jsonparam+",=="+phoneNumberJson);
		//sendBatchSms(phoneNumberJson,jsonparam);
		
		//验证码测试
		//getRandCode(6);
    }

	/**
	 * 单条发送（推荐）
	 * phoneNumbers 手机号码
	 * 多个号码用",号分隔",最多1000个号码
	 * 例如 13439018224,13823234387
	 * param 模板参数 例如： "{'code':'001'}"
	 */
	public static String sendSms(String phoneNumbers,String param){
		DefaultProfile profile = DefaultProfile.getProfile("cn-bj",ACCESSKEYID, ACCESSKEYSECRET);
        IAcsClient client = new DefaultAcsClient(profile);
        //通用请求对象
        CommonRequest request = new CommonRequest();
        //版本日期
        request.setVersion("2017-05-25");
        //使用post提交
        request.setMethod(MethodType.POST);
        //短信API产品域名（接口地址固定，无需修改）
        request.setDomain("dysmsapi.aliyuncs.com");
        //系统规定参数,取值：SendSms 不是必填项
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-bj");
        request.putQueryParameter("PhoneNumbers", phoneNumbers);
        request.putQueryParameter("SignName", SIGNNAME);
        request.putQueryParameter("TemplateCode", TEMPLATECODE);
        request.putQueryParameter("TemplateParam", param);
        String res="";
        try {
            CommonResponse response = client.getCommonResponse(request);
            res=response.getData();
            System.out.println(res);
            Map<String,String> mmp=JSONObject.parseObject(res, Map.class);
            System.out.println("状态"+mmp.get("Code"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
	}
	/**
	 * 批量发送（推荐）
	 * PhoneNumberJson 手机号码
	 * 例如 ["15900000000","13500000000"],最多100个电话号
	 * SignNameJson 签名数组  [\"XS系统\"]
	 * TemplateParamJson 参数 "[{\"code\":\"234455\"}]"
	 * 注意：批量发送时（ 模板变量值的个数必须与手机号码、签名的个数相同）
	 */
	public static void sendBatchSms(String PhoneNumberJson,String jsonParam) {
		String[] signnames={SIGNNAME,SIGNNAME};
		//发送签名
		String signnameJson=JSONObject.toJSONString(signnames);
				
		DefaultProfile profile = DefaultProfile.getProfile("cn-bj",ACCESSKEYID, ACCESSKEYSECRET);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendBatchSms");
        request.putQueryParameter("RegionId", "cn-bj");
        request.putQueryParameter("PhoneNumberJson",PhoneNumberJson);
        request.putQueryParameter("SignNameJson", signnameJson);
        request.putQueryParameter("TemplateCode", TEMPLATECODE);
        request.putQueryParameter("TemplateParamJson",jsonParam);
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	/**
     * 生成随机验证码
     * @param digits 验证码生成位数
     * @return
     */
    public static String getRandCode(int digits) {
        StringBuilder sBuilder = new StringBuilder();
        Random rd = new Random((new Date()).getTime());
        for(int i = 0; i < digits; ++i) {
            sBuilder.append(String.valueOf(rd.nextInt(9)));
        }
        String code=sBuilder.toString();
        System.out.println(code);
        return code;
    }
}
