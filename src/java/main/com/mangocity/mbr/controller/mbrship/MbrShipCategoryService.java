package com.mangocity.mbr.controller.mbrship;

import org.apache.log4j.Logger;

import com.mangocity.ce.bean.EngineBean;
import com.mangocity.ce.exception.ExceptionAbstract;
import com.mangocity.ce.util.CommonUtils;
import com.mangocity.mbr.book.ErrorCode;
import com.mangocity.mbr.util.ErrorUtils;
import com.mangocity.mbr.util.ServerCall;

/**
 * MbrShipCategoryService
 * @author longshu.chen
 *
 */
public class MbrShipCategoryService {
	private static final Logger log = Logger.getLogger(MbrShipCategoryService.class);
	/**
	 * 根据会籍类型编码查询
	 * @param pb
	 * @return
	 * @throws ExceptionAbstract
	 */
	public EngineBean mbrshipCategoryByCategoryCd(EngineBean pb) throws ExceptionAbstract {
		log.info("MbrShipCategoryFactory mbrshipCategoryByCategoryCd begin()...param: " + pb.getHeadMap());
		String categoryCd = (String) pb.getHead("categoryCd");
		if (CommonUtils.isBlank(categoryCd)) {
			return ErrorUtils.error(pb, ErrorCode.ERROR_MBR_NOT_EXIST, "categoryCd不能为空");
		}
		EngineBean resultEngineBean = ServerCall.call(pb);
		return resultEngineBean;
	}
}
