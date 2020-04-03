package com.huaxin.sboot.bean;

import java.io.Serializable;

/**
 * 
 * ClassName: Deduct <br/>
 * 划扣参数封装类 <br/>
 * date: 2017年3月24日 下午4:30:23 <br/>
 * * @author fdz
 */
public class Deduct implements Serializable{

	private static final long serialVersionUID = 7305699427003288730L;

	private String id;
	
	private String mchntCd;//商户编号
	
	private String mchntPass;//商户密码
	
    private String bankNo;//银行编号
    
    private String bankName;//银行名称
    
    private String cusNm;//客户姓名
    
    private String mobileNo;//手机号
    
    private String email;//邮箱
    
    private String credtNo;//身份证
    
    private String acntNo;//银行账号
    
    private String acntNm;//账户名称
    
    private String acntPro;//省份
    
    private String acntCity;//城市
    
    private String loanAmount;//支付金额
    
    private String orderNum;//订单号
    
    private String serialNum;//流水号
    
    private String platForm;//划扣平台
    
    private String deductRes;//划扣结果 结果描述
    
    private String deductTime;//划扣时间
    
    private Integer state;//出借状态
    
    private String remark;//备注
    
    
    
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMchntPass() {
		return mchntPass;
	}

	public void setMchntPass(String mchntPass) {
		this.mchntPass = mchntPass;
	}

	public String getMchntCd() {
		return mchntCd;
	}

	public void setMchntCd(String mchntCd) {
		this.mchntCd = mchntCd;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCusNm() {
		return cusNm;
	}

	public void setCusNm(String cusNm) {
		this.cusNm = cusNm;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getCredtNo() {
		return credtNo;
	}

	public void setCredtNo(String credtNo) {
		this.credtNo = credtNo;
	}

	public String getAcntNo() {
		return acntNo;
	}

	public void setAcntNo(String acntNo) {
		this.acntNo = acntNo;
	}

	public String getAcntNm() {
		return acntNm;
	}

	public void setAcntNm(String acntNm) {
		this.acntNm = acntNm;
	}

	public String getAcntPro() {
		return acntPro;
	}

	public void setAcntPro(String acntPro) {
		this.acntPro = acntPro;
	}

	public String getAcntCity() {
		return acntCity;
	}

	public void setAcntCity(String acntCity) {
		this.acntCity = acntCity;
	}

	public String getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	public String getPlatForm() {
		return platForm;
	}

	public void setPlatForm(String platForm) {
		this.platForm = platForm;
	}

	public String getDeductRes() {
		return deductRes;
	}

	public void setDeductRes(String deductRes) {
		this.deductRes = deductRes;
	}

	public String getDeductTime() {
		return deductTime;
	}

	public void setDeductTime(String deductTime) {
		this.deductTime = deductTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
    
    
}
