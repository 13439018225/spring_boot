package com.huaxin.sboot.webservice;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.huaxin.sboot.bean.User;

@WebService(targetNamespace="http://webservice.sboot.huaxin.com")
public interface IWebUserServcie {
	
	public User getObjById(String id);
	
	public int save(String user);
	
}
