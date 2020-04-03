package com.huaxin.sboot.bean;

import java.io.Serializable;

public class Product implements Serializable {

	private static final long serialVersionUID = -4043087862315016347L;

	private String id;

	private String name;

	private String proceeds;// 年化收益

	private String isshelf;// 是否上架

	private String period;// 购买周期

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

	public String getProceeds() {
		return proceeds;
	}

	public void setProceeds(String proceeds) {
		this.proceeds = proceeds;
	}

	public String getIsshelf() {
		return isshelf;
	}

	public void setIsshelf(String isshelf) {
		this.isshelf = isshelf;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

}
