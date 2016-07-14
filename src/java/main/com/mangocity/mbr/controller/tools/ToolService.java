package com.mangocity.mbr.controller.tools;

import org.apache.log4j.Logger;

import com.mangocity.ce.bean.EngineBean;
import com.mangocity.ce.book.ErrorConstant;
import com.mangocity.ce.deploy.ConfigManage;
import com.mangocity.ce.exception.ExceptionAbstract;
import com.mangocity.mbr.controller.address.DeliverAddressService;

/**
 * @ClassName: ToolService.java
 * @Description: 工具服务
 * @author: jie.yang
 * @email: jie.yang@mangocity.com
 * @date: 2016年6月4日 下午2:52:03
 */
public class ToolService {
	private static final Logger LOGGER = Logger
			.getLogger(ToolService.class);
	
	/**
	 * 平安万里通商旅集团会员积分信息同步至芒果网本地(解决积分兑换的耗时问题)
	 */
	public EngineBean crmWLTPointInfoSyncMango(EngineBean pb)
			throws ExceptionAbstract {
		LOGGER.info("ToolService crmWLTPointInfoSyncMango begin()");
		String crmWLTPointInfoSyncMangoSwitch = ConfigManage.instance().getSysConfig("crmWLTPointInfoSyncMango.switch");
		LOGGER.info("crmWLTPointInfoSyncMango.switch: " + crmWLTPointInfoSyncMangoSwitch);
		if("on".equalsIgnoreCase(crmWLTPointInfoSyncMangoSwitch)){//表示可以调用该方法
			
		}else{
			pb.setResultCode(ErrorConstant.ERROR_UNSUPPORTED_OPERATION_10007);
			pb.setResultMsg("The key 【crmWLTPointInfoSyncMango.switch】is off.you can't call it again.");
			return pb;
		}
		return null;
	}
}
