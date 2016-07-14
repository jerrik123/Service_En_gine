package com.mangocity.mbr.controller.cashaccount;

import java.text.ParseException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mangocity.ce.bean.EngineBean;
import com.mangocity.ce.exception.ExceptionAbstract;
import com.mangocity.ce.util.AssertUtils;
import com.mangocity.ce.util.CommonUtils;
import com.mangocity.ce.util.StringUtil;
import com.mangocity.mbr.book.ErrorCode;
import com.mangocity.mbr.controller.point.PointService;
import com.mangocity.mbr.util.ErrorUtils;
import com.mangocity.mbr.util.ServerCall;
import com.mangocity.mbr.util.StringUtils;

/**
 * @Package com.mangocity.mbr.controller.cashaccount
 * @Description : 现金账户服务接口
 * @author YangJie
 * @email <a href='yangjie_software@163.com'>yangjie</a>
 * @date 2015-8-27
 */
public class CashAccountService {
	private static final Logger log = Logger.getLogger(CashAccountService.class);
	
	/**
	 * 查询现金账户余额
	 * @param pb
	 * @return
	 * @throws ExceptionAbstract
	 */
	public EngineBean queryCashAccountBalance(EngineBean pb) throws ExceptionAbstract {
		log.info("CashAccountService queryCashAccountBalance begin()...");
		AssertUtils.assertNull(pb);
		Long mbrId = CommonUtils.objectToLong(pb.getHead("mbrId"), -1L);
		if(CommonUtils.isEmpty(pb.getHead("mbrId"))){
			return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID, "mbrId不能为空");
		}
		if(-1L == mbrId){
			return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID, "mbrId必须为数字");
		}
		EngineBean resultEngineBean = ServerCall.call(pb);
		log.info("resultEngineBean: " + resultEngineBean);
		return resultEngineBean ;
	}
	
	/**
	 * 查询现金账户进账
	 * @param pb
	 * @return
	 * @throws ExceptionAbstract
	 */
	public EngineBean queryCashAccountInCome(EngineBean pb) throws ExceptionAbstract {
		log.info("CashAccountService queryCashAccountInCome begin()...");
		AssertUtils.assertNull(pb);
		EngineBean resultEngineBean = ServerCall.call(pb);
		log.info("resultEngineBean: " + resultEngineBean);
		return resultEngineBean ;
	}
	
	/**
	 * 查询现金账户出账
	 * @param pb
	 * @return
	 * @throws ExceptionAbstract
	 */
	public EngineBean queryCashAccountOutCome(EngineBean pb) throws ExceptionAbstract {
		log.info("CashAccountService queryCashAccountOutCome begin()...");
		AssertUtils.assertNull(pb);
		EngineBean resultEngineBean = ServerCall.call(pb);
		log.info("resultEngineBean: " + resultEngineBean);
		return resultEngineBean ;
	}
	
	/**
	 * 查询现金账户待出账(申请记录)
	 * @param pb
	 * @return
	 * @throws ExceptionAbstract
	 */
	public EngineBean queryCashAccountApplyRecord(EngineBean pb) throws ExceptionAbstract {
		log.info("CashAccountService queryCashAccountApplyRecord begin()...");
		AssertUtils.assertNull(pb);
		EngineBean resultEngineBean = ServerCall.call(pb);
		log.info("resultEngineBean: " + resultEngineBean);
		return resultEngineBean ;
	}
	
	/**
	 * 查询全部现金账户待
	 * @param pb
	 * @return
	 * @throws ExceptionAbstract
	 */
	public EngineBean queryAllCashAccountTrans(EngineBean pb) throws ExceptionAbstract {
		log.info("CashAccountService queryAllCashAccountTrans begin()...");
		AssertUtils.assertNull(pb);
		Long mbrId = CommonUtils.objectToLong((String)pb.getHead("mbrId"), -1L);
		Long pageNo = CommonUtils.objectToLong((String)pb.getHead("pageNo"), -1L);
		Long pageSize = CommonUtils.objectToLong((String)pb.getHead("pageSize"), -1L);
		String beginDate = (String)pb.getHead("beginDate");
		String endDate = (String)pb.getHead("endDate");
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
		/*if(CommonUtils.isBlank(beginDate)){
			return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID, "beginDate不能为空");
		}
		if(CommonUtils.isBlank(endDate)){
			return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID, "endDate不能为空");
		}*/
		 int type = -1;
			try {
				if(CommonUtils.isNotBlank(beginDate)){
					type = 1;
					CommonUtils.parseDate(beginDate, "yyyy-MM-dd");
				}
				if(CommonUtils.isNotBlank(endDate)){
					type = 2;
					CommonUtils.parseDate(endDate, "yyyy-MM-dd");
				}
				if(CommonUtils.isNotBlank(beginDate) && CommonUtils.isNotBlank(endDate)){
					if(CommonUtils.parseDate(beginDate, "yyyy-MM-dd").after(CommonUtils.parseDate(endDate, "yyyy-MM-dd"))){
						return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID, "beginDate不能比endDate大");
					}
				}
			} catch (ParseException e) {
				return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID, (type>0?(type==1?"beginDate":"endDate"):"")+"必须为yyyy-MM-dd格式");
			}
		EngineBean resultEngineBean = ServerCall.call(pb);
		log.info("resultEngineBean: " + resultEngineBean);
		return resultEngineBean ;
	}
}
