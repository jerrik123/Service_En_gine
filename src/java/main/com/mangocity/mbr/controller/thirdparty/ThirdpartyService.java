package com.mangocity.mbr.controller.thirdparty;

import java.util.Map;

import org.apache.log4j.Logger;

import com.mangocity.ce.bean.EngineBean;
import com.mangocity.ce.exception.ExceptionAbstract;
import com.mangocity.ce.exception.IllegalParamException;
import com.mangocity.ce.util.AssertUtils;
import com.mangocity.ce.util.CommonUtils;
import com.mangocity.ce.util.MD5Algorithm;
import com.mangocity.mbr.book.ErrorCode;
import com.mangocity.mbr.controller.mbr.MbrService;
import com.mangocity.mbr.factory.ApplicationContext;
import com.mangocity.mbr.factory.BeanFactory;
import com.mangocity.mbr.util.ErrorUtils;
import com.mangocity.mbr.util.MapUtils;
import com.mangocity.mbr.util.RegexUtils;
import com.mangocity.mbr.util.ServerCall;

/**
 * @Package com.mangocity.mbr.controller.thirdparty
 * @Description : 第三方服务接口
 * @author YangJie
 * @email <a href='yangjie_software@163.com'>yangjie</a>
 * @date 2015-8-27
 */
public class ThirdpartyService{
	private static final Logger log = Logger.getLogger(ThirdpartyService.class);
	
	/**工厂实例*/
	private BeanFactory beanFactory = new ApplicationContext();
	
	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	/**
	 * 第三方芒果网账户或者注册并绑定接口
	 * @param pb bindingWay B 单纯绑定 和 绑定并登陆B
	 * @return
	 * @throws ExceptionAbstract
	 */
	@SuppressWarnings("unchecked")
	public EngineBean thirdpartyBinding(EngineBean pb) throws ExceptionAbstract {
		log.info("ThirdpartyService thirdpartyBinding begin()...");
		AssertUtils.assertNull(pb);
		Map<String,Object> headMap = pb.getHeadMap();
		Long mbrId = CommonUtils.objectToLong(headMap.get("mbrId"),-1L);
		String loginName = (String)headMap.get("loginName");
		String loginPwd = (String)headMap.get("loginPwd");
		String bindingWay = (String)headMap.get("bindingWay");//绑定方式
		String openid = (String)headMap.get("openid");
		String type = (String)headMap.get("type");
		EngineBean resultEngineBean = null;
		Map<String,Object> loginMap = null;
		if(CommonUtils.isBlank(bindingWay)){
			return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID, "bindingWay必填");
		}
		if(!"B".equals(bindingWay) && !"A".equals(bindingWay) ){//判断注册方式
			return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID, "bindingWay非法");
		}
		if(CommonUtils.isBlank(openid)){
			return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID, "openid不能为空");
		}
		if(CommonUtils.isBlank(type)){
			return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID, "type不能为空");
		}
		if(CommonUtils.isNotEmpty(mbrId) && mbrId.intValue()>0){//如果mbrId大于0
			resultEngineBean = ServerCall.call(pb);
			if(!"B".equals(bindingWay)){
				return resultEngineBean;
			}
			if("00000".equals(resultEngineBean.getResultCode())){//如果绑定成功,则执行登陆操作
				pb.setHead("opid", openid);
				MbrService mbrService = beanFactory.getBean("MbrService", MbrService.class);//获得MbrService对象
				pb.setCommand("login");
				EngineBean loginEngineBean = mbrService.login(pb);
				MapUtils.unionEngineBean(resultEngineBean, loginEngineBean);
				return resultEngineBean;
			}else{
				return resultEngineBean;
			}
		}else if(CommonUtils.isNotBlank(loginName) && CommonUtils.isNotBlank(loginPwd)){//登录账号和密码不为空
			headMap.put("loginSubType",RegexUtils.getShortTypeName(loginName));
			MbrService mbrService = beanFactory.getBean("MbrService", MbrService.class);//获得MbrService对象
			pb.setCommand("queryRegisterByLoginNameAndPassword");
			pb.setHead("loginPwd", new MD5Algorithm().generateMD5Str(loginPwd));
			EngineBean eb = mbrService.queryRegisterByLoginNameAndPassword(pb);
			if(!eb.getResultCode().equals("00000")){
				return eb;
			}
			Long _mbrId = CommonUtils.objectToLong(eb.getBody("mbrId"), -1L);
			log.info("loginName: " + loginName + ",mbrId: " + _mbrId);
			if(CommonUtils.isEmpty(_mbrId) || _mbrId.intValue()<=0){
				return ErrorUtils.error(pb, ErrorCode.ERROR_MBR_NOT_EXIST, "该会员不存在");
			}
			Object obj = eb.getBody("result");
			loginMap = (Map<String,Object>)obj;
			log.info("验证绑定账号和密码: " + obj);
			headMap.put("mbrId", loginMap.get("mbrId"));
			pb.setCommand("thirdpartyBinding");
			resultEngineBean = ServerCall.call(pb);
			if("00000".equals(resultEngineBean.getResultCode()) && ((Boolean)resultEngineBean.getBody("isBinding"))){//如果绑定成功
				if(!"B".equals(bindingWay)){
					return resultEngineBean;
				}else{
					pb.setCommand("login");
					EngineBean loginEngineBean = mbrService.login(pb);
					MapUtils.unionEngineBean(resultEngineBean, loginEngineBean);
				}
			}else{
				return resultEngineBean;
			}
		}else{
			return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID, "mbrId和loginName,loginPwd二者必选其一");
		}
		return resultEngineBean ;
	}
	
	/**
	 * 微信是否绑定芒果网账户
	 * @param pb openid type checkWay:分为是否绑定,是否绑定并登陆
	 * @return
	 * @throws ExceptionAbstract
	 */
	public EngineBean isBindedMango(EngineBean pb) throws ExceptionAbstract {
		log.info("ThirdpartyService isBindedMango begin()...");
		AssertUtils.assertNull(pb);
		Map<String,Object> headMap = pb.getHeadMap();
		log.info("headMap: " + headMap);
		String openid = (String)headMap.get("openid");
		String type = (String)headMap.get("type");
		String checkWay = (String)headMap.get("checkWay");
		if(CommonUtils.isBlank(openid)){
			return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID, "openid不能为空");
		}else if(CommonUtils.isBlank(type)){
			return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID, "type不能为空");
		}else if(CommonUtils.isBlank(checkWay)){
			return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID, "checkWay不能为空");
		}
		if(!"B".equals(checkWay) && !"A".equals(checkWay)){
			return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID, "checkWay不合法");
		}
		
		EngineBean resultEngineBean = ServerCall.call(pb);//是否绑定的数据
		log.info("resultEngineBean: " + resultEngineBean.getBodyMap());
		if(!(Boolean)resultEngineBean.getBody("isBinding")){//如果没有绑定
			return ErrorUtils.error(pb, ErrorCode.ERROR_NOT_BINDING_MANGO_MBR, "没有绑定芒果网会员");
		}
		if("B".equals(checkWay)){//返回登陆信息
			pb.setCommand("login");
			pb.setHead("opid", openid);
			MbrService mbrService = beanFactory.getBean("MbrService", MbrService.class);
			EngineBean loginEngineBean = mbrService.login(pb);
			log.info("调用登陆接口:" + loginEngineBean.getBodyMap());
			if(CommonUtils.isNotBlank(loginEngineBean.getResultCode()) && !loginEngineBean.getResultCode().equals("00000")){
				return loginEngineBean;
			}
			MapUtils.unionEngineBean(resultEngineBean, loginEngineBean);
			return resultEngineBean;
		}else if("A".equals(checkWay)){//不返回登陆信息
			return resultEngineBean;
		}
		return resultEngineBean ;
	}
}
