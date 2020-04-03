package com.huaxin.sboot.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.huaxin.sboot.bean.Message;
import com.huaxin.sboot.dao.IMessMapper;
import com.huaxin.sboot.service.IMessServcie;
import com.huaxin.sboot.util.MailServiceUtil;
import com.huaxin.sboot.util.SMS;
import com.mongodb.WriteResult;

@Service
public class MessServiceImpl implements IMessServcie {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
    
	@Autowired
	private IMessMapper messMapper;
	
	@Autowired
	private MongoTemplate mongoTemplate;//注入mongodb模板
	
	@Autowired
	private MailServiceUtil mailServiceUtil;
	
	
	//mongo集合名称
	private static final String COLLECTION_NAME="message";
	
	//监听MQ中的数据,将监听到的数据插入mongodb数据库
	@JmsListener(destination = "deduct-queue")
    public void readMsg(String text) {
		logger.info("MessServiceImpl类readMsg方法, 接收到MQ中的消息是===>"+text);
		if(StringUtils.isEmpty(text)){
			return;
		}
		//将json格式的字符串转化为实体类对象
		Message message=JSONObject.parseObject(text, Message.class);
		try {
			//将MQ中的消息插入到mongoBd
			save(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	@Override
	public String query(Map<String, Object> map) throws Exception {
		//当前第几页
		int pageNum=Integer.parseInt((String)map.get("pageNum"));
		//每页记录数
		int pageSize=Integer.parseInt((String)map.get("pageSize"));
		//分页
		Page<Object> page = PageHelper.startPage(pageNum, pageSize);
		
		List<Message> list = messMapper.query(map);
		
		Map<String,Object> rmap=new HashMap<String,Object>();
		rmap.put("total", page.getTotal());
		rmap.put("rows", list);
		String json=JSONObject.toJSONString(rmap);
		return json;
	}

	@Override
	public String mongoQuery(Map<String, Object> map) throws Exception {
		//当前第几页
		int pageNum=Integer.parseInt((String)map.get("pageNum"));
		//每页记录数
		int pageSize=Integer.parseInt((String)map.get("pageSize"));
		//查询条件
		String loancode=map.get("loancode")==null?null:(String)map.get("loancode");
		Query query=new Query();
		//查询条件
		if(StringUtils.isNotEmpty(loancode)){
			//精确匹配
			//query.addCriteria(Criteria.where("lid").is(loancode));
			//模糊匹配
			Pattern pattern=Pattern.compile("^.*"+loancode+".*$", Pattern.CASE_INSENSITIVE);
			query.addCriteria(Criteria.where("lid").regex(pattern));
		}
		//分页
		query.skip((pageNum-1)*pageSize);
		query.limit(pageSize);
		//排序
		query.with(new Sort(Sort.Direction.DESC, "lid"));
        //查询出来结果集
		List<Message> list=mongoTemplate.find(query, Message.class,COLLECTION_NAME);
		//查询总记录数
		int total=(int)mongoTemplate.count(query, COLLECTION_NAME);
		
		Map<String,Object> rmap=new HashMap<String,Object>();
		rmap.put("total",total);
		rmap.put("rows", list);
		String json=JSONObject.toJSONString(rmap);
		return json;
	}

	//将数据保存到mongo中
	@Override
	public int save(Message message) throws Exception {
		int k=0;
		if(StringUtils.isEmpty(message.getStatus())){
			message.setStatus("0");
		}
		if(StringUtils.isEmpty(message.getId())){
			message.setId(null);
		}
		mongoTemplate.save(message, COLLECTION_NAME);
		k=1;
		return k;
	}

	@Override
	public int del(String ids) throws Exception {
		Query query=new Query();
		List<String> list=new ArrayList<String>();
		String[] arryids=ids.split(",");
		for(int i=0;i<arryids.length;i++){
			list.add(arryids[i]);
		}
		query.addCriteria(Criteria.where("_id").in(list));
		WriteResult res = mongoTemplate.remove(query, COLLECTION_NAME);
		int k=res.getN();
		return k;
	}

	@Override
	public String getObjById(String id) throws Exception {
		Message message = mongoTemplate.findById(id, Message.class,COLLECTION_NAME);
		String json=JSONObject.toJSONString(message);
		return json;
	}

	@Override
	public String sendEmail(String ids) throws Exception {
		//发送邮件
		int k=0;
		Query query=new Query();
		List<String> list=new ArrayList<String>();
		String[] arryids=ids.split(",");
		for(int i=0;i<arryids.length;i++){
			list.add(arryids[i]);
		}
		query.addCriteria(Criteria.where("_id").in(list));
		List<Message> emiallist = mongoTemplate.find(query, Message.class,COLLECTION_NAME);
		for(Message message:emiallist){
			mailServiceUtil.sendSimpleMail(message.getEmail(), message.getModel(), message.getContext());
		}
		//发送成功后，修改发送状态为已发送
		Update update=new Update();
		update.set("status", "1");
		WriteResult res=mongoTemplate.updateMulti(query, update, Message.class, COLLECTION_NAME);
		k=res.getN();
		return k+"";
	}

	@Override
	public String sendEms(String ids) throws Exception {
		String[] arryids=ids.split(",");
		Query query=new Query();
		Set<String> set=new HashSet<String>();
		for(int i=0;i<arryids.length;i++){
			set.add(arryids[i]);
		}
		query.addCriteria(Criteria.where("_id").in(set));
		List<Message> emslist = mongoTemplate.find(query, Message.class,COLLECTION_NAME);
		StringBuffer bf=new StringBuffer("");
		for(Message message:emslist){
			String tel=message.getTel();
			bf.append(tel);
		}
		Map<String,String> map=new HashMap<String,String>();
		map.put("code", SMS.getRandCode(6));
		String param=JSONObject.toJSONString(map);
		String res=SMS.sendSms(bf.toString(), param);
		Map<String,String> mmp=JSONObject.parseObject(res, Map.class);
		return mmp.get("Code");
	}
}
