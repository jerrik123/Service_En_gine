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

/**
 * 权限服务
 * @author longshu.chen
 *
 */
public class AuthorityService {
private static final Logger log = Logger.getLogger(AuthorityService.class);
	
	/**
	 * 添加权限
	 * @param pb
	 * @return
	 * @throws IllegalParamException
	 */
	public EngineBean insertAuthority(EngineBean pb) throws IllegalParamException{
		log.info("AuthorityService insertAuthority begin()...");
		AssertUtils.assertNull(pb, "EngineBean can't be null.");
		String name=(String) pb.getHead("name");
		if(pb.getHead("isNosql")!=null && ((String)pb.getHead("isNosql")).equals("o")){
			pb.setIsNosql("o");
			pb.getHeadMap().remove("isNosql");
		}
		if (CommonUtils.isBlank(name)) {
			return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID,
					"name不能为空");
		}
		EngineBean result = ServerCall.call(pb);
		return result;
	}
	/**
	 * 通过id删除权限
	 * @param pb
	 * @return
	 * @throws IllegalParamException
	 */
	public EngineBean deleteAuthorityById(EngineBean pb)throws IllegalParamException{
		log.info("AuthorityService deleteAuthorityById begin()...");
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
	 * 删除权限
	 * @param pb
	 * @return
	 * @throws IllegalParamException
	 */
	public EngineBean deleteAuthority(EngineBean pb)throws IllegalParamException{
		log.info("AuthorityService deleteAuthority begin()...");
		AssertUtils.assertNull(pb, "EngineBean can't be null.");
		if(pb.getHead("isNosql")!=null && ((String)pb.getHead("isNosql")).equals("o")){
			pb.setIsNosql("o");
			pb.getHeadMap().remove("isNosql");
		}
		EngineBean result = ServerCall.call(pb);
		return result;
	}
	
	/**
	 * 更新权限
	 * @param pb
	 * @return
	 * @throws IllegalParamException
	 */
	public EngineBean updateAuthority(EngineBean pb)throws IllegalParamException{
		log.info("AuthorityService updateAuthority begin()...");
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
	 * 查询权限
	 * @param pb
	 * @return
	 * @throws IllegalParamException
	 */
	public EngineBean selectAuthority(EngineBean pb)throws IllegalParamException{
		log.info("AuthorityService selectAuthority begin()...");
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
	 * 通过id查询权限
	 * @param pb
	 * @return
	 * @throws IllegalParamException
	 */
	public EngineBean selectAuthorityById(EngineBean pb)throws IllegalParamException{
		log.info("AuthorityService selectAuthorityById begin()...");
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
	 * 根据id集合查询权限
	 * @param pb
	 * @return
	 * @throws ExceptionAbstract
	 */
	public EngineBean selectAuthorityByIds(EngineBean pb)throws IllegalParamException{
		log.info("AuthorityService selectAuthorityByIds begin()...");
		AssertUtils.assertNull(pb, "EngineBean can't be null.");
		if(pb.getHead("isNosql")!=null && ((String)pb.getHead("isNosql")).equals("o")){
			pb.setIsNosql("o");
			pb.getHeadMap().remove("isNosql");
		}
		EngineBean result = ServerCall.call(pb);
		return result;
		
	}
}
