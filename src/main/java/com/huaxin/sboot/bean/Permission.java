package com.huaxin.sboot.bean;

import java.io.Serializable;

public class Permission implements Serializable{

	private static final long serialVersionUID = -4416503942294431144L;

	//权限id
	private String perid;
	
	//权限名称
	private String pername;
	
	//权限字符串 例子：role:create,role:update,role:delete,role:view
	private String permision;

	public String getPerid() {
		return perid;
	}

	public void setPerid(String perid) {
		this.perid = perid;
	}

	public String getPername() {
		return pername;
	}

	public void setPername(String pername) {
		this.pername = pername;
	}

	public String getPermision() {
		return permision;
	}

	public void setPermision(String permision) {
		this.permision = permision;
	}
	
}
