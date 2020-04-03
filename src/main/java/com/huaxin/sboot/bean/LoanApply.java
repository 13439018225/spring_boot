package com.huaxin.sboot.bean;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * 
 * ClassName: LoanApply <br/>
 * 出借申请实体类. <br/>
 * date: 2018年3月23日 上午10:02:39 <br/>
 * @author fdz
 */
public class LoanApply implements Serializable{

	private static final long serialVersionUID = 283759243309352049L;

	private String id;
	
	private String cid;//客户id
	
	private String pid;//产品id
	
	private String cusname;//客户名称
	
	private String proname;//产品名称
	
	private String loancode;//出借编号
	
	private String loanamount;//出借金额
	
	private String loandate;//出借日期
	
    private String coverageDate;//计息日期
    
    private String auditor;//审核人
    
    private String auditordate;//业务部审核日期
    
    private String status;//单据状态  0-未提交 1-待审核 2-待划扣  3-审核退回 4-划扣成功 5-划扣失败 6-回款中 7-出借完结
    
    private String appointdate;//预约日期 yyyy-mm-dd hh24:mm
    
    

	public String getAppointdate() {
		return appointdate;
	}

	public void setAppointdate(String appointdate) {
		this.appointdate = appointdate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getCusname() {
		return cusname;
	}

	public void setCusname(String cusname) {
		this.cusname = cusname;
	}

	public String getProname() {
		return proname;
	}

	public void setProname(String proname) {
		this.proname = proname;
	}

	public String getLoancode() {
		return loancode;
	}

	public void setLoancode(String loancode) {
		this.loancode = loancode;
	}

	

	public String getLoanamount() {
		return loanamount;
	}

	public void setLoanamount(String loanamount) {
		this.loanamount = loanamount;
	}

	public String getLoandate() {
		return loandate;
	}

	public void setLoandate(String loandate) {
		this.loandate = loandate;
	}

	public String getCoverageDate() {
		return coverageDate;
	}

	public void setCoverageDate(String coverageDate) {
		this.coverageDate = coverageDate;
	}

	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	public String getAuditordate() {
		return auditordate;
	}

	public void setAuditordate(String auditordate) {
		this.auditordate = auditordate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    

}
