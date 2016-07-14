/**
 * 
 */
package com.mangocity.mbr.controller.mg;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mangocity.ce.bean.EngineBean;
import com.mangocity.ce.book.SysBook;
import com.mangocity.ce.exception.ExceptionAbstract;
import com.mangocity.ce.util.AssertUtils;
import com.mangocity.ce.util.CommonUtils;
import com.mangocity.ce.util.MD5Algorithm;
import com.mangocity.ce.util.RedisUtils;
import com.mangocity.mbr.book.ErrorCode;
import com.mangocity.mbr.factory.ApplicationContext;
import com.mangocity.mbr.factory.BeanFactory;
import com.mangocity.mbr.util.ErrorUtils;
import com.mangocity.mbr.util.SafeUtil;
import com.mangocity.mbr.util.ServerCall;

/**   
 * @Package com.mangocity.mbr.controller.mg 
 * @Description :  mbrmg管理员服务
 * @author YangJie
 * @email <a href='yangjie_software@163.com'>yangjie</a>
 * @date 2015-11-18
 */
public class MbrManageService {

	private static final Logger log = Logger.getLogger(MbrManageService.class);
	
	/**工厂实例*/
	private BeanFactory beanFactory = new ApplicationContext();
	
	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}
	/**
	 * mbrmg 管理员登陆接口
	 * @param pb {"loginName":"","loginPassword":"","deviceInfo":"","IP":""}
	 * @return
	 * @throws ExceptionAbstract
	 */
	public EngineBean adminLogin(EngineBean pb) throws ExceptionAbstract {
		log.info("MbrService adminLogin begin()...headMap: " + pb.getHeadMap());
		AssertUtils.assertNull(pb);
		EngineBean resultEngineBean = null;
		String loginPassword = (String) pb.getHead("loginPassword");
		String aToken = null;//管理员登陆缓存
		String userId = null;
		String deviceInfo = (String) pb.getBase("deviceInfo");
		String IP = (String) pb.getBase("IP");
		String systemVersion = null;
		String networking = null;
		StringBuffer sb = new StringBuffer();
		Object obj = null;
		if(CommonUtils.isNotBlank(loginPassword) && loginPassword.length()!=32){
			pb.setHead("loginPassword", new MD5Algorithm().generateMD5Str(loginPassword));
		}
		resultEngineBean = ServerCall.call(pb);
		EngineBean authBean =  new EngineBean();
		if(null != resultEngineBean && "00000".equals(resultEngineBean.getResultCode())){
			userId = String.valueOf(resultEngineBean.getBody("userId"));
			//设备认证
			pb.setCommand("queryUserDeviceByUserId");
			pb.setHead("userId", userId);
			authBean = ServerCall.call(pb);
			if(null != authBean && authBean.getResultCode().equals(SysBook.SUCCESS)){
				obj = authBean.getBody("result");
				if(obj instanceof List){//一个用户多个设备的情况
					boolean flag = false;
					for(Object tObj : (List)obj){
						if(deviceInfo.equals((String)((Map)tObj).get("systemVersion")) &&
								IP.equals((String)((Map)tObj).get("networking"))){
							flag = true;
							systemVersion = (String)((Map)tObj).get("systemVersion");
							networking = (String)((Map)tObj).get("networking");
							break;
						}else{
							flag = false;
						}
					}
					if(!flag){
						return ErrorUtils.error(pb, ErrorCode.ERROR_CLIENT_IS_LIMITED, "该客户端无权限访问");
					}
				}else if(deviceInfo.equals((String)authBean.getBody("systemVersion")) &&
						IP.equals((String)authBean.getBody("networking"))){
					systemVersion = (String)authBean.getBody("systemVersion");
					networking = (String)authBean.getBody("networking");
				}else{
					return ErrorUtils.error(pb, ErrorCode.ERROR_CLIENT_IS_LIMITED, "该客户端无权限访问");
				}
			}else{
				return ErrorUtils.error(pb, ErrorCode.ERROR_CLIENT_IS_LIMITED, "该客户端无权限访问");
			}
			//userDeviceId = String.valueOf(authBean.getBody("id"));
			sb.append(userId).append("|").append(systemVersion).append(networking);//用户登陆并且设备认证成功 返回aToken(md5(userId+|+deviceId))
			aToken = SafeUtil.MD5(sb.toString());
			//以aToken为键,userId为value存入缓存
			RedisUtils.set(aToken, sb.toString());
			
			log.info("存入redis key: " + aToken + " ,value: " + sb.toString());
			((Map) resultEngineBean.getBody("result")).put("aToken",aToken);
			log.info("adminLogin resultEngineBean: " + resultEngineBean.getBodyMap());
		}
		return resultEngineBean;
	}
	public EngineBean queryModuleByUserId(EngineBean pb) throws ExceptionAbstract {
		log.info("ManageService queryModuleByUserId begin()...params: " + pb.getHeadMap());
		Long mbrId = CommonUtils.objectToLong(pb.getHead("userId"), -1L);
		EngineBean resultEngineBean = ServerCall.call(pb);
		log.info("resultEngineBean: " + resultEngineBean);
		return resultEngineBean ;
	}
}
