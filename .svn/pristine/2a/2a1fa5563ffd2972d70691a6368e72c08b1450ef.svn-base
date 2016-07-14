package com.mangocity.mbr.controller.voucher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

import org.apache.log4j.Logger;

import com.mangocity.ce.bean.EngineBean;
import com.mangocity.ce.exception.ExceptionAbstract;
import com.mangocity.ce.util.AssertUtils;
import com.mangocity.ce.util.CommonUtils;
import com.mangocity.mbr.book.ErrorCode;
import com.mangocity.mbr.controller.mbrship.MbrShipService;
import com.mangocity.mbr.factory.ApplicationContext;
import com.mangocity.mbr.factory.BeanFactory;
import com.mangocity.mbr.util.ErrorUtils;
import com.mangocity.mbr.util.ServerCall;
import com.mangocity.mbr.util.StringUtils;

/**
 * @Package com.mangocity.mbr.controller.voucher
 * @Description : 代金券服务接口
 * @author YangJie
 * @email <a href='yangjie_software@163.com'>yangjie</a>
 * @date 2015-8-27
 */
public class VoucherService {
	private static final Logger log = Logger.getLogger(VoucherService.class);
	
	/**工厂实例*/
	private BeanFactory beanFactory = new ApplicationContext();
	
	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}
	/**
	 * 查询未被使用的代金券
	 * 
	 * @param pb
	 * @return
	 * @throws ExceptionAbstract
	 */
	public EngineBean queryNotUsedVoucher(EngineBean pb)
			throws ExceptionAbstract {
		log.info("VoucherService queryNotUsedVoucher begin()...");
		AssertUtils.assertNull(pb);
		Long mbrId = CommonUtils.objectToLong(pb.getHead("mbrId"), -1L);
		Long pageNo = CommonUtils.objectToLong((String)pb.getHead("pageNo"), -1L);
		Long pageSize = CommonUtils.objectToLong((String)pb.getHead("pageSize"), -1L);
		if(-1L == mbrId){
			return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID, "mbrId不能为空或非法数字");
		}
		if(-1L == pageNo){
			return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID, "pageNo不能为空或非法数字");
		}
		if(-1L == pageSize){
			return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID, "pageSize不能为空或非法数字");
		}
		if(pageNo<=0){
			return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID, "pageNo必须是正整数");
		}
		if(pageSize<=0){
			return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID, "pageSize必须是正整数");
		}
		MbrShipService mbrShipService = beanFactory.getBean("MbrShipService", MbrShipService.class);
		pb.setCommand("queryMbrShipListByMbrid");
		EngineBean eb = mbrShipService.queryMbrShipListByMbrid(pb);//会籍列表
		if(!"00000".equals(eb.getResultCode())){
			return eb;
		}
		pb.setHead("status", 0);
		List<Map<String,Object>> resultList = (List<Map<String, Object>>) eb.getBody("result");
		List list = new ArrayList();
		for(Map<String,Object> map : resultList){
			list.add(map.get("MBRSHIP_CD"));
			list.add(map.get("OLD_MBRSHIP_CD"));
		}
		pb.setHead("memberCdList",list);
		pb.setCommand("queryNotUsedVoucher");
		EngineBean resultEngineBean = ServerCall.call(pb);
		log.info("resultEngineBean: " + resultEngineBean);
		return resultEngineBean;
	}

	/**
	 * 查询已经被使用的代金券
	 * 
	 * @param pb
	 * @return
	 * @throws ExceptionAbstract
	 */
	public EngineBean queryUsedVoucher(EngineBean pb) throws ExceptionAbstract {
		log.info("VoucherService queryUsedVoucher begin()...");
		AssertUtils.assertNull(pb);
		Long mbrId = CommonUtils.objectToLong(pb.getHead("mbrId"), -1L);
		Long pageNo = CommonUtils.objectToLong((String)pb.getHead("pageNo"), -1L);
		Long pageSize = CommonUtils.objectToLong((String)pb.getHead("pageSize"), -1L);
		if(-1L == mbrId){
			return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID, "mbrId不能为空或非法数字");
		}
		if(-1L == pageNo){
			return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID, "pageNo不能为空或非法数字");
		}
		if(-1L == pageSize){
			return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID, "pageSize不能为空或非法数字");
		}
		if(pageNo<=0){
			return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID, "pageNo必须是正整数");
		}
		if(pageSize<=0){
			return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID, "pageSize必须是正整数");
		}
		MbrShipService mbrShipService = beanFactory.getBean("MbrShipService", MbrShipService.class);
		pb.setCommand("queryMbrShipListByMbrid");
		EngineBean eb = mbrShipService.queryMbrShipListByMbrid(pb);//会籍列表
		if(!"00000".equals(eb.getResultCode())){
			return eb;
		}
		List<Map<String,Object>> resultList = (List<Map<String, Object>>) eb.getBody("result");
		List list = new ArrayList();
		for(Map<String,Object> map : resultList){
			list.add(map.get("MBRSHIP_CD"));
			list.add(map.get("OLD_MBRSHIP_CD"));
		}
		pb.setHead("memberCdList",list);
		pb.setCommand("queryUsedVoucher");
		EngineBean resultEngineBean = ServerCall.call(pb);
		log.info("resultEngineBean: " + resultEngineBean);
		return resultEngineBean;
	}

	public EngineBean queryVoucherBalanceByCd(EngineBean pb)
			throws ExceptionAbstract {
		log.info("VoucherService queryVoucherBalanceByCd begin()...");
		AssertUtils.assertNull(pb);
		Long mbrId = CommonUtils.objectToLong(pb.getHead("mbrId"), -1L);
		if(-1L == mbrId){
			return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID, "mbrId不能为空或非法数字");
		}
		MbrShipService mbrShipService = beanFactory.getBean("MbrShipService", MbrShipService.class);
		pb.setCommand("queryMbrShipListByMbrid");
		EngineBean eb = mbrShipService.queryMbrShipListByMbrid(pb);//会籍列表
		if(!"00000".equals(eb.getResultCode())){
			return eb;
		}
		List<Map<String,Object>> resultList = (List<Map<String, Object>>) eb.getBody("result");
		List list = new ArrayList();
		for(Map<String,Object> map : resultList){
			list.add("'"+map.get("MBRSHIP_CD")+"'");
			list.add("'"+map.get("OLD_MBRSHIP_CD")+"'");
		}
		pb.setHead("memberCds",StringUtils.listToStr(list, ","));
		pb.setCommand("queryVoucherBalanceByCd");
		EngineBean resultEngineBean = ServerCall.call(pb);
		log.info("resultEngineBean: " + resultEngineBean);
		return resultEngineBean;
	}
}
