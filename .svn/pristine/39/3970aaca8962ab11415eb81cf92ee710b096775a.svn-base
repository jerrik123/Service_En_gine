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

public class UserService {

private static final Logger log = Logger.getLogger(UserService.class);
	
	/**
	 * 添加用户
	 * @param pb
	 * @return
	 * @throws IllegalParamException
	 */
	public EngineBean insertUser(EngineBean pb) throws IllegalParamException{
		log.info("UserService insertUser begin()...");
		AssertUtils.assertNull(pb, "EngineBean can't be null.");
		String name=(String) pb.getHead("name");
		String loginName=(String)pb.getHead("loginName");
		String password=(String)pb.getHead("password");
		//int stus=(int)pb.getHead("stus");
		String createBy=(String)pb.getHead("createBy");
		String createTime=(String)pb.getHead("createTime");
		//String structureIds=(String)pb.getHead("structureIds");
		//String menuIds=(String)pb.getHead("menuIds");
		if(pb.getHead("isNosql")!=null && ((String)pb.getHead("isNosql")).equals("o")){
			pb.setIsNosql("o");
			pb.getHeadMap().remove("isNosql");
		}
		if (CommonUtils.isBlank(name)) {
			return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID,
					"name不能为空");
		}
		if (CommonUtils.isBlank(loginName)) {
			return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID,
					"loginName不能为空");
		}
		if (CommonUtils.isBlank(password)) {
			return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID,
					"password不能为空");
		}
		if (CommonUtils.isBlank(createBy)) {
			return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID,
					"createBy不能为空");
		}
		if (CommonUtils.isBlank(createTime)) {
			return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID,
					"createTime不能为空");
		}
		EngineBean result = ServerCall.call(pb);
		return result;
	}
	/**
	 * 通过id删除用户
	 * @param pb
	 * @return
	 * @throws IllegalParamException
	 */
	public EngineBean deleteUserById(EngineBean pb)throws IllegalParamException{
		log.info("UserService deleteUserById begin()...");
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
	 * 删除用户
	 * @param pb
	 * @return
	 * @throws IllegalParamException
	 */
	public EngineBean deleteUser(EngineBean pb)throws IllegalParamException{
		log.info("UserService deleteUser begin()...");
		AssertUtils.assertNull(pb, "EngineBean can't be null.");
		if(pb.getHead("isNosql")!=null && ((String)pb.getHead("isNosql")).equals("o")){
			pb.setIsNosql("o");
			pb.getHeadMap().remove("isNosql");
		}
		EngineBean result = ServerCall.call(pb);
		return result;
	}
	
	/**
	 * 更新用户
	 * @param pb
	 * @return
	 * @throws IllegalParamException
	 */
	public EngineBean updateUser(EngineBean pb)throws IllegalParamException{
		log.info("UserService updateUser begin()...");
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
	 * 查询用户
	 * @param pb
	 * @return
	 * @throws IllegalParamException
	 */
	public EngineBean selectUser(EngineBean pb)throws IllegalParamException{
		log.info("UserService selectUser begin()...");
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
	 * 通过id查询用户
	 * @param pb
	 * @return
	 * @throws IllegalParamException
	 */
	public EngineBean selectUserById(EngineBean pb)throws IllegalParamException{
		log.info("UserService selectUserById begin()...");
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
