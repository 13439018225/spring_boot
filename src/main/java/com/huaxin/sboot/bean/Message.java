package com.huaxin.sboot.bean;

import java.io.Serializable;

public class Message implements Serializable{

	private static final long serialVersionUID = 6812833970946260304L;
   
	private String id;
	
	private String type;//0 短信  1邮件
	
	private String model;
	
	private String context;
	
	private String lid;//出借申请ID
	
	private String tel;
	
	private String email;
	
	private String status;//发送主题  0  未发送  1 已发送
	

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLid() {
		return lid;
	}

	public void setLid(String lid) {
		this.lid = lid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", type=" + type + ", model=" + model
				+ ", context=" + context + ", lid=" + lid + "]";
	}

}
