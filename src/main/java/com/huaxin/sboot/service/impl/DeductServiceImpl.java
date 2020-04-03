package com.huaxin.sboot.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.huaxin.sboot.bean.Deduct;
import com.huaxin.sboot.bean.LoanApply;
import com.huaxin.sboot.bean.Message;
import com.huaxin.sboot.dao.IDeductMapper;
import com.huaxin.sboot.service.IDeductServcie;
import com.huaxin.sboot.util.DateUtil;
import com.huaxin.sboot.util.DeductUtil;
import com.huaxin.sboot.util.FinalCodeUtil;
import com.huaxin.sboot.util.XmlParseUtil;
@Service
public class DeductServiceImpl implements IDeductServcie {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IDeductMapper deductMapper;
	
	//注入jmstemplate
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

	@Override
	public String query(Map<String, Object> map) throws Exception {
		//当前第几页
		int pageNum=Integer.parseInt((String)map.get("pageNum"));
		//每页记录数
		int pageSize=Integer.parseInt((String)map.get("pageSize"));
		//分页
		Page<Object> page = PageHelper.startPage(pageNum, pageSize);
		
		List<LoanApply> list = deductMapper.query(map);
		//记录日志 
		logger.info("总记录数==="+page.getTotal()+",每页记录数==="+page.getPageSize()+",总页数==="+page.getPages()+","
						+ "当前第几页=="+page.getPageNum());
		
		Map<String,Object> rmap=new HashMap<String,Object>();
		rmap.put("total", page.getTotal());
		rmap.put("rows", list);
		String json=JSONObject.toJSONString(rmap);
		return json;
	}

	@Override
	public String getDeductRecord(String id) throws Exception {
		List<Deduct> list=deductMapper.getDeductRecord(id);
		String json=JSONObject.toJSONString(list);
		return json;
	}

	@Override
	public List<Deduct> getDeductListByIds(Map<String, Object> map)
			throws Exception {
		List<Deduct> list= deductMapper.getDeductListByIds(map);
		return list;
	}

	/**
	 * doDeduct:划扣方法 <br/>
	 * @author fdz
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public String toDeduct(Deduct deduct)throws Exception{
		String mess="";
		String serialNum=UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
		//设置流水号
		deduct.setSerialNum(serialNum);
		deduct.setOrderNum(serialNum);
		//调用统一划扣方法
		String strResp=DeductUtil.sendDeductRequest(deduct);
		
		if(StringUtils.isEmpty(strResp)){
			mess=FinalCodeUtil.DEDUCT_MESSAGE015;
		}else{
			//解析回盘结果
			Map<String,String> map=XmlParseUtil.Dom4jXmlParse(strResp);
			String resCode=map.get("resCode");
			String resMess=map.get("resMess");
			String Amount=map.get("Amount");
			deduct.setLoanAmount(Amount);
			deduct.setDeductRes(resMess);
			//插入划扣记录
			deductMapper.AddDeduct(deduct);
			int state=4;
			if("OK".equals(resCode)){
				mess="OK";
			}else{
				state=5;
				mess=resMess;
			}
			//更新出借申请数据状态
			Map<String,Object> pramap=new HashMap<String,Object>();
			pramap.put("state", state);
			pramap.put("id", deduct.getId());
 			deductMapper.upLoanAppState(pramap);
		}
		//将划扣信息发送到activeMq
		for(int i=0;i<2;i++){
			Message message=new Message();
 			message.setLid(deduct.getId());
 			message.setContext("您好"+deduct.getCusNm()+",您的划扣结果是:"+mess+",请核实!");
 			message.setModel("划扣");
 			message.setEmail(deduct.getEmail());
 			message.setTel(deduct.getMobileNo());
 			message.setType(String.valueOf(i));
 			String messjson=JSONObject.toJSONString(message);
 			jmsMessagingTemplate.convertAndSend("deduct-queue", messjson);
		}
		return mess;
	}
    
	//保存预约时间
	@Override
	public int saveAppiontDate(String ids,String appionDateTime) throws Exception {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("ids", ids);
		map.put("appointdate", appionDateTime);
		return deductMapper.saveAppiontDate(map);
	}
	
	
	/**通过定时任务，扫描预约日期开启自动划扣
     * 定时划扣
     * 1、待划扣 与 划扣失败 状态
       2、预约时间 与 系统当前时间对比  (系统时间>=预约时间)
     * @throws Exception 
     */
    @Scheduled(cron="0 0/30 * * * ? ")
    public void taskDeduct() throws Exception {
    	logger.info("定时划扣开始执行...");
    	//查询满足划扣条件的数据
    	List<Deduct> list = deductMapper.getAppiontRecord();
    	for (Deduct deduct : list) {
    		//划扣方法
    		toDeduct(deduct);
		}
    }
}
