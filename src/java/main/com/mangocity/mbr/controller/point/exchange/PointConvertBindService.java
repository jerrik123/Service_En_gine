package com.mangocity.mbr.controller.point.exchange;

import org.apache.log4j.Logger;

import com.mangocity.ce.bean.EngineBean;
import com.mangocity.ce.exception.ExceptionAbstract;
import com.mangocity.mbr.util.ServerCall;

/**
 * 积分互换绑定服务
 * @author mbr.yangjie
 */
public class PointConvertBindService {
	private static final Logger LOGGER = Logger.getLogger(PointConvertBindService.class);
	
	/**
	 * 根据万里通用户名查询绑定信息
	 * @param pb
	 * @return
	 * @throws ExceptionAbstract
	 */
	public EngineBean queryWltAccoutByBindUserName(EngineBean pb) throws ExceptionAbstract {
		LOGGER.info("PointConvertBindService queryWltAccoutByBindUserName begin()...appId: " + pb.getAppId()
				+ " ,params: " + pb.getHeadMap());
		EngineBean resultEngineBean = ServerCall.call(pb);
		LOGGER.info("resultEngineBean: " + resultEngineBean);
		return resultEngineBean;
	}
	
	/**
	 * 根据mbrId查询万里通绑定信息
	 * @param pb
	 * @return
	 * @throws ExceptionAbstract
	 */
	public EngineBean queryWltAccoutByMbrId(EngineBean pb) throws ExceptionAbstract {
		LOGGER.info("PointConvertBindService queryWltAccoutByMbrId begin()...appId: " + pb.getAppId()
				+ " ,params: " + pb.getHeadMap());
		EngineBean resultEngineBean = ServerCall.call(pb);
		LOGGER.info("resultEngineBean: " + resultEngineBean);
		return resultEngineBean;
	}
	
	/**
	 * 根据mbrId查询万里通绑定信息
	 * @param pb
	 * @return
	 * @throws ExceptionAbstract
	 */
	public EngineBean pointConvertBindCreate(EngineBean pb) throws ExceptionAbstract {
		LOGGER.info("PointConvertBindService pointConvertBindCreate begin()...appId: " + pb.getAppId()
				+ " ,params: " + pb.getHeadMap());
		EngineBean resultEngineBean = ServerCall.call(pb);
		LOGGER.info("resultEngineBean: " + resultEngineBean);
		return resultEngineBean;
	}
	
	/**
	 * 检查平安万里通的充值流水是否存在
	 * @param pb
	 * @return
	 * @throws ExceptionAbstract
	 */
	public EngineBean isExistWltOrder(EngineBean pb) throws ExceptionAbstract {
		LOGGER.info("PointConvertBindService isExistWltOrder begin()...appId: " + pb.getAppId()
				+ " ,params: " + pb.getHeadMap());
		EngineBean resultEngineBean = ServerCall.call(pb);
		LOGGER.info("resultEngineBean: " + resultEngineBean);
		return resultEngineBean;
	}
	
	/**
	 * 根据万里通订单号查询芒果订单流水情况
	 * @param pb
	 * @return
	 * @throws ExceptionAbstract
	 */
	public EngineBean queryPointConvertOrderByWltOrder(EngineBean pb) throws ExceptionAbstract {
		LOGGER.info("PointConvertBindService queryPointConvertOrderByWltOrder begin()...appId: " + pb.getAppId()
				+ " ,params: " + pb.getHeadMap());
		EngineBean resultEngineBean = ServerCall.call(pb);
		LOGGER.info("resultEngineBean: " + resultEngineBean);
		return resultEngineBean;
	}
	
	/**
	 * 根据万里通交易号查询总积分
	 * @param pb
	 * @return
	 * @throws ExceptionAbstract
	 */
	public EngineBean querySumMangoPointByOrder(EngineBean pb) throws ExceptionAbstract {
		LOGGER.info("PointConvertBindService querySumMangoPointByOrder begin()...appId: " + pb.getAppId()
				+ " ,params: " + pb.getHeadMap());
		EngineBean resultEngineBean = ServerCall.call(pb);
		LOGGER.info("resultEngineBean: " + resultEngineBean);
		return resultEngineBean;
	}
}
