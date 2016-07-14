package com.mangocity.mbr.controller.mbrship;

import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

import com.mangocity.ce.bean.EngineBean;
import com.mangocity.ce.book.ErrorConstant;
import com.mangocity.ce.exception.ExceptionAbstract;
import com.mangocity.ce.util.AssertUtils;
import com.mangocity.ce.util.CommonUtils;
import com.mangocity.ce.util.MD5Algorithm;
import com.mangocity.mbr.book.ErrorCode;
import com.mangocity.mbr.controller.sms.SmsService;
import com.mangocity.mbr.factory.ApplicationContext;
import com.mangocity.mbr.factory.BeanFactory;
import com.mangocity.mbr.util.ErrorUtils;
import com.mangocity.mbr.util.RegexUtils;
import com.mangocity.mbr.util.ServerCall;
import com.mangocity.mbr.util.StringUtils;

public class MbrShipService {
	private static final Logger log = Logger.getLogger(MbrShipService.class);

	/**
	 * 会员 {"adjustCode":"register","headMap":
	 * {"loginName":"","loginPwd":"","setPasswordType":"auto manual自动和手动",}
	 * 
	 * @param pb
	 * @return
	 * @throws ExceptionAbstract
	 */
	public EngineBean queryMbrShipListByMbrid(EngineBean pb) throws ExceptionAbstract {
		log.info("MbrShipService queryMbrShipListByMbrid begin()...");
		try {
			String mbrIdStr = (String) pb.getHead("mbrId");
			if (CommonUtils.isBlank(mbrIdStr)) {
				return ErrorUtils.error(pb, ErrorCode.ERROR_MBR_NOT_EXIST, "mbrId不能为空");
			}
		} catch (Exception e) {
			log.error("mbrId转换类型失败", e);
			return ErrorUtils.error(pb, ErrorCode.ERROR_MBR_NOT_EXIST, "mbrId传入数据必须是数字字符串");
		}
		Long mbrId = CommonUtils.objectToLong(pb.getHead("mbrId"), -1L);
		if (-1L == mbrId) {
			return ErrorUtils.error(pb, ErrorCode.ERROR_MBR_NOT_EXIST, "mbrId必须是数字");
		}
		EngineBean resultEngineBean = ServerCall.call(pb);
		return resultEngineBean;
	}
	
	/**
	 * 根据mbrshipCd查询会籍信息
	 * @param pb
	 * @return
	 * @throws ExceptionAbstract
	 */
	public EngineBean queryMbrShipByMbrshipCd(EngineBean pb) throws ExceptionAbstract {
		log.info("MbrShipService queryMbrShipByMbrshipCd begin()...params: " + pb.getHeadMap());
		if(CommonUtils.isBlankIncludeNull(String.valueOf(pb.getHead("mbrshipCd")))){
			return ErrorUtils.error(pb, ErrorConstant.ERROR_PARAM_NULL_10000, "mbrshipCd必填");
		}
		EngineBean resultEngineBean = ServerCall.call(pb);
		log.info("resultEngineBean: " + resultEngineBean);
		return resultEngineBean ;
	}
	
	/**
	 * 通过会籍编码和联名卡号查询
	 * @param pb
	 * @return
	 * @throws ExceptionAbstract
	 */
	public EngineBean queryMbrshipBymbrshipCategoryIdAndAliasNo(EngineBean pb) throws ExceptionAbstract {
		log.info("MbrShipService queryMbrshipBymbrshipCategoryIdAndAliasNo begin()...params: " + pb.getHeadMap());
		String categoryId=(String) pb.getHead("categoryId");
		String aliasNo=(String) pb.getHead("aliasNo");
		if(CommonUtils.isBlank(categoryId)){
			return ErrorUtils.error(pb, ErrorCode.ERROR_MBR_NOT_EXIST, "categoryId不能为空");
		}
		if(CommonUtils.isBlank(aliasNo)){
			return ErrorUtils.error(pb, ErrorCode.ERROR_MBR_NOT_EXIST, "aliasNo不能为空");
		}
		EngineBean resultEngineBean = ServerCall.call(pb);
		log.info("resultEngineBean: " + resultEngineBean);
		return resultEngineBean ;
	}
	
	/**
	 * 通过shipCd查询信息
	 * @param headMap
	 * @return
	 */
	public EngineBean queryMbrShipByShipCd(EngineBean pb) throws ExceptionAbstract{
		log.info("MbrShipService queryMbrShipByShipCd begin()...params: " + pb.getHeadMap());
		String mbrShipCd=(String) pb.getHead("mbrShipCd");
		if(CommonUtils.isBlank(mbrShipCd)){
			return ErrorUtils.error(pb, ErrorCode.ERROR_MBR_NOT_EXIST, "mbrShipCd不能为空");
		}
		EngineBean resultEngineBean = ServerCall.call(pb);
		log.info("resultEngineBean: " + resultEngineBean);
		return resultEngineBean ;
	}
}
