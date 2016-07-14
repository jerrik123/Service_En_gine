/**
 * 
 */
package com.mangocity.mbr.factory;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mangocity.ce.deploy.ConfigManage;
import com.mangocity.ce.exception.SystemException;
import com.mangocity.ce.util.CommonUtils;

/**   
 * @Package com.mangocity.mbr.factory 
 * @Description :  
 * @author YangJie
 * @email <a href='yangjie_software@163.com'>yangjie</a>
 * @date 2015-9-18
 */
public class ApplicationContext implements BeanFactory {
	private static final Logger LOG = Logger.getLogger(ApplicationContext.class);
	/**缓存对象实例*/
	public static final Map<String,Object> serviceMap = new HashMap<String,Object>();

	/**
	 * 获得Bean的实例
	 * @param id  bean id
	 * @return
	 * @throws SystemException
	 */
	public synchronized Object getBean(String id) throws SystemException {
		if(CommonUtils.isBlank(id)){
			throw new SystemException(this, "SYS", "找不到实例化对象");
		}
		if(serviceMap.containsKey(id)){
			return serviceMap.get(id);
		}else{
			try {
				String serviceName = ConfigManage.instance().getSysConfig(id);//获得sys_*.properties中对应的value
				Object instance = Class.forName(serviceName).newInstance();
				serviceMap.put(id, instance);
				return instance;
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				LOG.error(e.getMessage(), e);
				throw new SystemException(this, "SYS", "sys_*.properties对象实例化错误");
			}
		}
	}
	
	/**
	 * 获得Bean的实例
	 * @param id  bean id
	 * @param classType  类型
	 * @return
	 * @throws SystemException
	 */
	public synchronized <T> T getBean(String id,Class<T> classType) throws SystemException{
		if(CommonUtils.isBlank(id) || CommonUtils.isEmpty(classType)){
			throw new SystemException(this, "SYS", "找不到实例化对象");
		}
		return (T)getBean(id);
	}

}
