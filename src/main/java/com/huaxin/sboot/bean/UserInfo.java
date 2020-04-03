package com.huaxin.sboot.bean;

import java.io.Serializable;

public class UserInfo implements Serializable {
	
	private static final long serialVersionUID = -2113219929469341954L;
	
	private String id;
	private String username;
	private String password;
	
	private int pageNum;//当前第几页
	private int pageSize;//每页记录数
	

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", username=" + username + ", password=" + password + "]";
	}

}
