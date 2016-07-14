/**
 * 
 */
package com.mangocity.mbr.controller.sms;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

import com.mangocity.ce.bean.EngineBean;
import com.mangocity.ce.exception.ExceptionAbstract;
import com.mangocity.ce.util.AssertUtils;
import com.mangocity.ce.util.CommonUtils;
import com.mangocity.mbr.book.ErrorCode;
import com.mangocity.mbr.controller.mbr.MbrService;
import com.mangocity.mbr.util.ErrorUtils;
import com.mangocity.mbr.util.ServerCall;
import com.mangocity.mbr.util.StringUtils;

/**
 * @Package com.mangocity.mbr.controller.sms
 * @Description : 短信服务
 * @author YangJie
 * @email <a href='yangjie_software@163.com'>yangjie</a>
 * @date 2015-9-16
 */
public class SmsService {
	private static final Logger log = Logger.getLogger(MbrService.class);

	/**
	 * 发送短信
	 * @param pb {"mobileNo":"13554774675","message":"尊敬的用户","sendType”:"短信发送类型(1.验证码(verify_code)2.普通短信:comm_code)",
	 * "businessType":"业务类型(A:普通发送,不涉及业务B:扣减积分 默认是A)","businessNo":"当非默认值时,businessNo必填","businessSrc":"","ip":"","remark":""}
	 * @return
	 * @throws ExceptionAbstract
	 */
	public EngineBean sendSms(EngineBean pb) throws ExceptionAbstract {
		log.info("SmsService sendSms begin()...");
		AssertUtils.assertNull(pb);
		Map<String,Object> headMap = pb.getHeadMap();
		log.info("appId: " +pb.getAppId()+" ,headMap: " + pb.getHeadMap());
		EngineBean resultEngineBean = new EngineBean();
		String mobileNo = (String)headMap.get("mobileNo");
		String sendType = (String)headMap.get("sendType");
		String message = (String)headMap.get("message");
		String businessType = (String)headMap.get("businessType");//业务类型,针对某个业务发送短信验证码,默认只需校验手机号码和验证码及有效时间。其他业务类型需要校验业务号
		String businessNo = (String)headMap.get("businessNo");//业务号(例如:积分扣减中的订单号)
		String businessSrc = (String)headMap.get("businessSrc");
		String ip = (String)headMap.get("ip");
		String remark = (String)headMap.get("remark");
		String randomCode = null;//随机码
		boolean isSucc = false;//短信是否发送成功
    	if(CommonUtils.isNotBlank(businessType)){
			if("A".equals(businessType)){
				//默认操作
			}else if("B".equals(businessType)){
				if(CommonUtils.isBlank(businessNo)){
					return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID, "businessNo必填");
				}
				if(CommonUtils.isBlank(remark)){
					return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID, "remark必填");
				}
				if(CommonUtils.isBlank(ip)){
					return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID, "ip必填");
				}
			}else{
				return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID, "businessType只能为A或B");
			}
		}else{
			businessType = "A";
			remark = "非积分扣减";
		}
		if("verify_code".equals(sendType)){//如果是短信校验码,则系统生成6为系统随机码
			randomCode = gen6RandomPwd();
			message = "尊敬的芒果网用户:您的手机动态验证码:"+randomCode+",请及时输入";
		}else if(CommonUtils.isBlank(message)){
			return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID, "普通短信必须指定message");
		}
		try {
			log.info("【appId: " +pb.getAppId() + " ,mobileNo: " + mobileNo + "】正在调用发送短信接口sendSms...");
			SmsSender smsSender = new SmsSender();
			smsSender.sendSms(new String[]{mobileNo},message,"TMC",".txt");
			isSucc = true;
		} catch (Exception e) {
			log.error("短信发送失败", e);
			isSucc = false;
		}finally{
			//插入短信记录
			if("verify_code".equals(sendType)){//如果是验证码,则执行入库操作
				pb.setHead("type", businessType);
				pb.setHead("createTime", new Date());
				pb.setHead("updateTime", new Date());
				pb.setHead("createBy", pb.getAppId());
				pb.setHead("status", "VALID");
				pb.setHead("signCode", randomCode);
				pb.setCommand("addSmsRecord");
				//针对积分扣减新增校验字段 2015-11-03
				pb.setHead("businessNo", businessNo);
				pb.setHead("businessSrc", pb.getAppId());//订单来源 输入了应用ID
				pb.setHead("ip", ip);
				pb.setHead("remark", remark);
				EngineBean smsEb = this.addSmsRecord(pb);
				if(!"00000".equals(smsEb.getResultCode())){
					isSucc = false;
				}
			}
		}
		if(!isSucc){//短信如果发送不成功
			pb.setBody("verifyCode", randomCode);//TODO上线之前屏蔽=====================================
			return ErrorUtils.error(pb, ErrorCode.ERROR_SMS_GATEWAY_DISCONNECT, "短信网关不通,发送短信失败");
		}
		resultEngineBean.setBody("verifyCode", randomCode);//TODO上线之前屏蔽=====================================
		resultEngineBean.setResultCode("00000");
		resultEngineBean.setResultMsg("SUCCESS");
		log.info("resultEngineBean: " + resultEngineBean);
		return resultEngineBean;
	}
	
	/**
	 * 校验短信
	 * @param pb {"mobileNo":"","signCode":"","businessType":"A:默认 B:扣减积分","businessNo":""}
	 * @return
	 * @throws ExceptionAbstract
	 */
	public EngineBean validateSms(EngineBean pb) throws ExceptionAbstract {
		log.info("SmsService validateSms begin()...");
		AssertUtils.assertNull(pb);
		log.info("appId: " +pb.getAppId()+" ,headMap: " + pb.getHeadMap());
		String mobileNo = (String)pb.getHead("mobileNo");
		String signCode = (String)pb.getHead("signCode");
		String businessType = (String)pb.getHead("businessType");
		String businessNo = (String)pb.getHead("businessNo");
		String validateType = (String)pb.getHead("validateType");//校验类型 B:普通校验,不修改状态 A:校验后修改状态
		EngineBean resultEngineBean = null;
		if(CommonUtils.isNotBlank(businessType)){
			if("A".equals(businessType)){
				//默认不操作
			}else if("B".equals(businessType)){
				if(CommonUtils.isBlank(businessNo)){
					return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID, "businessNo必填");
				}
			}else{
				return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID, "businessType只能为A或B");
			}
		}else{
			pb.setHead("businessType", "A");//默认校验A类型
		}
		if(CommonUtils.isBlank(validateType)){
			validateType = "A";//默认是正常校验
		}
		if(!"A".equals(validateType) && !"B".equals(validateType)){
			return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID, "validateType只能填A或B");
		}
		resultEngineBean = ServerCall.call(pb);
		if("00000".equals(resultEngineBean.getResultCode())){//如果校验成功,则把状态置为无效
			if("A".equals(validateType)){//如果是正常校验,则修改状态
				log.info("短信验证成功,修改状态为无效");
				pb.setCommand("updateSmsByMobileNoAndCode");
				pb.setHead("status", "INVALID");
				pb.setHead("updateBy", pb.getAppId());
				this.updateSmsByMobileNoAndCode(pb);
			}
		}
		log.info("resultEngineBean: " + resultEngineBean);
		return resultEngineBean;
	}
	
	/**
	 * 把短信置为无效
	 * @param pb {"mobileNo":"13554774675","signCode":"尊敬的用户"}
	 * @return
	 * @throws ExceptionAbstract
	 */
	public EngineBean updateSmsByMobileNoAndCode(EngineBean pb) throws ExceptionAbstract {
		log.info("SmsService updateSmsByMobileNoAndCode begin()...");
		AssertUtils.assertNull(pb);
		Map<String,Object> headMap = pb.getHeadMap();
		log.info("headMap: " + headMap);
		EngineBean resultEngineBean = new EngineBean();
		String mobileNo = (String)headMap.get("mobileNo");
		String signCode = (String)headMap.get("signCode");
		if(CommonUtils.isBlank(mobileNo) || CommonUtils.isBlank(signCode)){
			return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID, "指定手机号码或者短信验证码");
		}
		resultEngineBean = ServerCall.call(pb);
		log.info("resultEngineBean: " + resultEngineBean);
		return resultEngineBean;
	}
	
	/**
	 * 添加短信记录
	 * @param pb
	 * @return
	 * @throws ExceptionAbstract
	 */
	public EngineBean addSmsRecord(EngineBean pb) throws ExceptionAbstract {
		log.info("SmsService addSmsRecord begin()...");
		AssertUtils.assertNull(pb);
		Map<String,Object> headMap = pb.getHeadMap();
		log.info("headMap: " + headMap);
		EngineBean resultEngineBean = new EngineBean();
		String mobileNo = (String)headMap.get("mobileNo");
		String signCode = (String)headMap.get("signCode");
		if(CommonUtils.isBlank(mobileNo) || CommonUtils.isBlank(signCode)){
			return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID, "指定手机号码或者短信验证码");
		}
		resultEngineBean = ServerCall.call(pb);
		log.info("resultEngineBean: " + resultEngineBean);
		return resultEngineBean;
	}
	
	/**
	 * querySmsCount
	 * @param pb
	 * @return
	 * @throws ExceptionAbstract
	 */
	public EngineBean querySmsCount(EngineBean pb) throws ExceptionAbstract {
		log.info("SmsService querySmsCount begin()...");
		AssertUtils.assertNull(pb);
		Map<String,Object> headMap = pb.getHeadMap();
		log.info("headMap: " + headMap);
		EngineBean resultEngineBean = new EngineBean();
		resultEngineBean = ServerCall.call(pb);
		log.info("resultEngineBean: " + resultEngineBean);
		return resultEngineBean;
	}
	
	/**
	 * 添加短信发送记录
	 * @param pb
	 * @return
	 * @throws ExceptionAbstract
	 */
	public EngineBean addSmsCount(EngineBean pb) throws ExceptionAbstract {
		log.info("SmsService addSmsCount begin()...");
		AssertUtils.assertNull(pb);
		Map<String,Object> headMap = pb.getHeadMap();
		log.info("headMap: " + headMap);
		EngineBean resultEngineBean = new EngineBean();
		resultEngineBean = ServerCall.call(pb);
		log.info("resultEngineBean: " + resultEngineBean);
		return resultEngineBean;
	}
	
	/**
	 * 生成六位随机码
	 */
	private String gen6RandomPwd() {
		String password = "";
		for (int mm = 0; mm < 100; mm++) {
			Random rdm = new Random();
			String temp = Integer.toString(Math.abs(rdm.nextInt()));
			if (temp.trim().length() > 6) {
				password = temp.substring(0, 6);
			} else {
				password = temp;
			}
			if (!"111111".equals(password) && !"222222".equals(password)
					&& !"333333".equals(password) && !"444444".equals(password)
					&& !"555555".equals(password) && !"666666".equals(password)
					&& !"777777".equals(password) && !"888888".equals(password)
					&& !"999999".equals(password) && !"000000".equals(password)
					&& !"123456".equals(password) && !"654321".equals(password)) {
				break;
			}
		}
		return password;
	}
}
