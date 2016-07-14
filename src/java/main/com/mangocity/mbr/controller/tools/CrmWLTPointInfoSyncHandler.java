package com.mangocity.mbr.controller.tools;

import static com.mangocity.ce.util.RedisUtils.exists;
import static com.mangocity.ce.util.RedisUtils.get;
import static com.mangocity.ce.util.RedisUtils.set;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * @ClassName: CrmWLTPointInfoSyncHandler.java
 * @Description: 平安万里通商旅集团会员积分信息同步至芒果网本地(解决积分兑换的耗时问题)
 * @author: jie.yang
 * @email: jie.yang@mangocity.com
 * @date: 2016年5月6日 下午1:52:03
 */
public class CrmWLTPointInfoSyncHandler {
	private static final Logger LOGGER = Logger.getLogger(CrmWLTPointInfoSyncHandler.class);
	
	private static CrmWLTPointInfoSyncHandler uniqueInstance = null;
	
	/**
	 * 作为Redis中的Key 控制该方法是否继续有权限执行.(0:可以往下执行,1:不可以往下执行)
	 */
	private static final String SWITCH_CACHE_KEY = "<tool>crmWLTPointInfoSync_admin</tool>";
	
	private static final String CAN_DO = "0";
	
	private static final String CAN_NOT_DO = "1";
	
	private CrmWLTPointInfoSyncHandler(){}
	
	public static CrmWLTPointInfoSyncHandler getInstance(){
		if(null == uniqueInstance){
			uniqueInstance = new CrmWLTPointInfoSyncHandler();
		}
		return uniqueInstance;
	}
	
	/**
	 * 是否有权限继续往下执行
	 * @return
	 */
	public boolean hasRight(){
		if(!exists(SWITCH_CACHE_KEY)){//如果不存在该key,第一次默认有权限访问
			set(SWITCH_CACHE_KEY, CAN_NOT_DO);
			return true;
		}else{
			final String CACHE_VALUE = get(SWITCH_CACHE_KEY);
			LOGGER.info("CrmWLTPointInfoSyncHandler CACHE_VALUE: " + CACHE_VALUE);
			if(CAN_DO.equalsIgnoreCase(CACHE_VALUE)){
				set(SWITCH_CACHE_KEY, CAN_NOT_DO);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 加载平安万里通商旅集团会员
	 * @return
	 */
	public List<Map<String,Object>> loadCrmWLTMember(){
		return null;
	}
	
	/**
	 * 从集团MQ加载数据
	 */
	public void loadDataFromCrmMQ(){
		
	}
	
	public void 
	
}

