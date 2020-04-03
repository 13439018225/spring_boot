package com.huaxin.sboot.util;

public class FinalCodeUtil {
	//定义全局常量
	public static final String DEDUCT_CHARSET="UTF-8";//划扣编码
	
	public static final String DEDUCT_TIMEOUT="120000";//请求超时设置 单位毫秒
	
	public static final String DEDUCT_REQ_MAPING_NAME="/deductHttpServer";//请求映射路径
	
	public static final String DEDUCT_REQ_METHOD_NAME="/getHttpSerRes.do";//请求方法名称
	
	public static final String DEDUCT_PTAH="http://127.0.0.1:8080";//请求路径
	
	public static final String DEDUCT_URL=DEDUCT_PTAH+DEDUCT_REQ_MAPING_NAME+DEDUCT_REQ_METHOD_NAME;//请求完整路径
	
	public static final String DEDUCT_MCHNTCD="DDD2900F045338";//商户号
	
	public static final String DEDUCT_MCHNTPASS="123456";//商户密码
	
	//定义接口返回状态信息
	
	public static final String DEDUCT_CODE001="001";
	
	public static final String DEDUCT_MESSAGE001="银行编号为空";
	
    public static final String DEDUCT_CODE002="002";
	
	public static final String DEDUCT_MESSAGE002="客户名称为空";
	
    public static final String DEDUCT_CODE003="003";
	
	public static final String DEDUCT_MESSAGE003="手机号为空";
	
    public static final String DEDUCT_CODE004="004";
	
	public static final String DEDUCT_MESSAGE004="身份证号为空";
	
    public static final String DEDUCT_CODE005="005";
	
	public static final String DEDUCT_MESSAGE005="账号为空";
	
    public static final String DEDUCT_CODE006="006";
	
	public static final String DEDUCT_MESSAGE006="开户名为空";
	
    public static final String DEDUCT_CODE007="007";
	
	public static final String DEDUCT_MESSAGE007="省份为空";
	
    public static final String DEDUCT_CODE008="008";
	
	public static final String DEDUCT_MESSAGE008="金额为空";
	
    public static final String DEDUCT_CODE009="009";
	
	public static final String DEDUCT_MESSAGE009="商户号为空";
	
    public static final String DEDUCT_CODE010="010";
	
	public static final String DEDUCT_MESSAGE010="签名不一致";
	
    public static final String DEDUCT_CODE011="011";
	
	public static final String DEDUCT_MESSAGE011="客户姓名与户名不一致";
	
    public static final String DEDUCT_CODE012="012";
	
	public static final String DEDUCT_MESSAGE012="单笔交易金额超渠道单笔限额";
	
    public static final String DEDUCT_CODE013="013";
	
	public static final String DEDUCT_MESSAGE013="日交易金额超渠道日限额";
	
    public static final String DEDUCT_CODE014="014";
	
	public static final String DEDUCT_MESSAGE014="账号余额不足";
	
    public static final String DEDUCT_CODE015="015";
	
	public static final String DEDUCT_MESSAGE015="未返回回盘信息";
	
	public static final String FILE_UPLOAD_PATH="E:\\upload";

}
