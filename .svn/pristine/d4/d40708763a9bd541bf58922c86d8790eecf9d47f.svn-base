package com.mangocity.mbr.controller.point;

import org.apache.log4j.Logger;

import com.mangocity.ce.bean.EngineBean;
import com.mangocity.mbr.util.ServerCall;


public class PointSchemeService {
	private static final Logger log = Logger.getLogger(PointSchemeService.class);
	
	public EngineBean queryAllPointSchemes(EngineBean eb){
		log.info("PointSchemeService queryAllPointSchemes begin()...params: " + eb.getHeadMap());
		EngineBean resultEngineBean = ServerCall.call(eb);
		log.info("resultEngineBean: " + resultEngineBean);
		return resultEngineBean;
	}
	
	public EngineBean saveOrUpdateScheme(EngineBean eb){
		log.info("PointSchemeService saveOrUpdateScheme begin()...params: " + eb.getHeadMap());
		EngineBean resultEngineBean = ServerCall.call(eb);
		log.info("resultEngineBean: " + resultEngineBean);
		return resultEngineBean;
	}
	
}
