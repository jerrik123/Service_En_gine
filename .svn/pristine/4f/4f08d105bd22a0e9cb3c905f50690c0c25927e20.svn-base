package com.mangocity.mbr.controller.mbrmanage;

import org.apache.log4j.Logger;

import com.mangocity.ce.bean.EngineBean;
import com.mangocity.ce.exception.ExceptionAbstract;
import com.mangocity.ce.util.CommonUtils;
import com.mangocity.mbr.controller.mbr.MbrCommService;
import com.mangocity.mbr.util.ServerCall;

public class MbrManageService {
	private static final Logger log = Logger.getLogger(MbrManageService.class);
	public EngineBean queryModuleByUserId(EngineBean pb) throws ExceptionAbstract {
		log.info("ManageService queryModuleByUserId begin()...params: " + pb.getHeadMap());
		Long mbrId = CommonUtils.objectToLong(pb.getHead("userId"), -1L);
		EngineBean resultEngineBean = ServerCall.call(pb);
		log.info("resultEngineBean: " + resultEngineBean);
		return resultEngineBean ;
	}
}
