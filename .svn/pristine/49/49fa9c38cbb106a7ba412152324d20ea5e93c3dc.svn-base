package com.mangocity.mbr.controller.crm;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mangocity.ce.bean.EngineBean;
import com.mangocity.ce.exception.IllegalParamException;
import com.mangocity.ce.util.AssertUtils;
import com.mangocity.ce.util.CommonUtils;
import com.mangocity.mbr.book.ErrorCode;
import com.mangocity.mbr.util.ErrorUtils;
import com.mangocity.mbr.util.ServerCall;

public class OrganizationStructureService {
	private static final Logger log = Logger.getLogger(OrganizationStructureService.class);
	
	/**
	 * 添加组织结构
	 * @param pb
	 * @return
	 * @throws IllegalParamException
	 */
	public EngineBean insertStructure(EngineBean pb) throws IllegalParamException{
		log.info("OrganizationStructureService insertStructure begin()...");
		AssertUtils.assertNull(pb, "EngineBean can't be null.");
		String name=(String) pb.getHead("text");
		String parentId=(String)pb.getHead("parentId");
		if(pb.getHead("isNosql")!=null && ((String)pb.getHead("isNosql")).equals("o")){
			pb.setIsNosql("o");
			pb.getHeadMap().remove("isNosql");
		}
		if (CommonUtils.isBlank(name)) {
			return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID,
					"name不能为空");
		}
		if (CommonUtils.isBlank(parentId)) {
			return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID,
					"parentId不能为空");
		}
		EngineBean result = ServerCall.call(pb);
		return result;
	}
	/**
	 * 通过id删除组织结构
	 * @param pb
	 * @return
	 * @throws IllegalParamException
	 */
	public EngineBean deleteStructureById(EngineBean pb)throws IllegalParamException{
		log.info("OrganizationStructureService deleteStructureById begin()...");
		AssertUtils.assertNull(pb, "EngineBean can't be null.");
		String id=(String) pb.getHead("id");
		if(pb.getHead("isNosql")!=null && ((String)pb.getHead("isNosql")).equals("o")){
			pb.setIsNosql("o");
			pb.getHeadMap().remove("isNosql");
		}
		if (CommonUtils.isBlank(id)) {
			return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID,
					"id不能为空");
		}
		EngineBean result = ServerCall.call(pb);
		return result;
	}
	
	/**
	 * 删除组织结构
	 * @param pb
	 * @return
	 * @throws IllegalParamException
	 */
	public EngineBean deleteStructure(EngineBean pb)throws IllegalParamException{
		log.info("OrganizationStructureService deleteStructure begin()...");
		AssertUtils.assertNull(pb, "EngineBean can't be null.");
		if(pb.getHead("isNosql")!=null && ((String)pb.getHead("isNosql")).equals("o")){
			pb.setIsNosql("o");
			pb.getHeadMap().remove("isNosql");
		}
		EngineBean result = ServerCall.call(pb);
		return result;
	}
	
	/**
	 * 更新组织结构
	 * @param pb
	 * @return
	 * @throws IllegalParamException
	 */
	public EngineBean updateStructure(EngineBean pb)throws IllegalParamException{
		log.info("OrganizationStructureService updateStructure begin()...");
		AssertUtils.assertNull(pb, "EngineBean can't be null.");
		String id=(String) pb.getHead("id");
		if(pb.getHead("isNosql")!=null && ((String)pb.getHead("isNosql")).equals("o")){
			pb.setIsNosql("o");
			pb.getHeadMap().remove("isNosql");
		}
		if (CommonUtils.isBlank(id)) {
			return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID,
					"id不能为空");
		}
		EngineBean result = ServerCall.call(pb);
		return result;
	}
	
	/**
	 * 查询组织结构
	 * @param pb
	 * @return
	 * @throws IllegalParamException
	 */
	public EngineBean selectStructure(EngineBean pb)throws IllegalParamException{
		log.info("OrganizationStructureService selectStructure begin()...");
		AssertUtils.assertNull(pb, "EngineBean can't be null.");
		if(pb.getHead("isNosql")!=null && ((String)pb.getHead("isNosql")).equals("o")){
			pb.setIsNosql("o");
			pb.getHeadMap().remove("isNosql");
		}
		EngineBean result = ServerCall.call(pb);
		JSONArray jsonObj=(JSONArray) JSONObject.parse((String) result.getBody("result"));
		result.setBody("result", jsonObj);
		return result;
	}
	
	/**
	 * 通过id查询组织结构
	 * @param pb
	 * @return
	 * @throws IllegalParamException
	 */
	public EngineBean selectStructureById(EngineBean pb)throws IllegalParamException{
		log.info("OrganizationStructureService selectStructureById begin()...");
		AssertUtils.assertNull(pb, "EngineBean can't be null.");
		String id=(String) pb.getHead("id");
		if(pb.getHead("isNosql")!=null && ((String)pb.getHead("isNosql")).equals("o")){
			pb.setIsNosql("o");
			pb.getHeadMap().remove("isNosql");
		}
		if (CommonUtils.isBlank(id)) {
			return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID,
					"id不能为空");
		}
		EngineBean result = ServerCall.call(pb);
		return result;
	}
}
