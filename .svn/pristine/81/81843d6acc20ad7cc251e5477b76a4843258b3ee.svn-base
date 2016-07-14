package com.mangocity.mbr.controller.point;


import org.apache.log4j.Logger;

import com.mangocity.ce.bean.EngineBean;
import com.mangocity.ce.exception.ExceptionAbstract;
import com.mangocity.ce.util.AssertUtils;
import com.mangocity.ce.util.CommonUtils;
import com.mangocity.mbr.book.ErrorCode;
import com.mangocity.mbr.util.ErrorUtils;
import com.mangocity.mbr.util.ServerCall;


/**
 * 积分授权
 * @author longshu.chen
 *
 */
public class PointAuthService {
	
	private static final Logger log = Logger.getLogger(PointAuthService.class);
	
	/**
	 * 通过mbrid查询授权
	 * @param map
	 * @return
	 */
	public EngineBean selectPointAuthorizeBymbrId(EngineBean pb)throws ExceptionAbstract{
		log.info("PointAuthFactory selectPointAuthorizeBymbrId begin()...");
		AssertUtils.assertNull(pb, "EngineBean can't be null.");
		Long mbrId = CommonUtils.objectToLong((String) pb.getHead("mbrId"), -1L);
		//String aToken = (String) pb.getHead("aToken");
		if (-1L == mbrId) {
			return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID,"mbrId不能为空或非法数字");
		}
		// 1.判断aToken是否存在 false:返回lToken错误
//		if (!RedisUtils.exists(aToken)) {
//			return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID,
//					"缓存中不存在该aToken");
//		}
		EngineBean pointAuthBean = ServerCall.call(pb);
		return pointAuthBean;
	}
	
	/**
	 * 插入积分授权
	 * @param map
	 * @return
	 */
	public EngineBean insertPointAuthorize(EngineBean pb)throws Exception{
		log.info("PointAuthFactory insertPointAuthorize begin()...");
		AssertUtils.assertNull(pb, "EngineBean can't be null.");
		Long mbrId = CommonUtils.objectToLong((String) pb.getHead("mbrId"), -1L);
		String authorizeId = (String) pb.getHead("authorizeId");
		if (-1L == mbrId) {
			return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID,"mbrId不能为空或非法数字");
		}
		if (CommonUtils.isBlank(authorizeId)) {
			return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID,
					"authorizeId不能为空");
		}
		EngineBean pointAuthBean = ServerCall.call(pb);
		return pointAuthBean;
	}
	
	/**
	 * 根据授权Id查询有效授权信息
	 * @param map
	 * @return
	 */
	public EngineBean queryPointAuthorizeByAuthorizeId(EngineBean pb)throws Exception{
		log.info("PointAuthFactory queryPointAuthorizeByAuthorizeId begin()...");
		EngineBean pointAuthBean = ServerCall.call(pb);
		log.info("queryPointAuthorizeByAuthorizeId resultEngineBean: " + pointAuthBean);
		return pointAuthBean;
	}
}
