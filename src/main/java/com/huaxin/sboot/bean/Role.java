package com.huaxin.sboot.bean;

import java.io.Serializable;

public class Role implements Serializable{

	private static final long serialVersionUID = -1579342056085685764L;

	private String id;
	
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + "]";
	}
	
}
