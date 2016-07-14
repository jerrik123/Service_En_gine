package com.mangocity.mbr.factory;

import com.mangocity.ce.exception.SystemException;

/**
 * @Package com.mangocity.mbr.factory
 * @Description :
 * @author YangJie
 * @email <a href='yangjie_software@163.com'>yangjie</a>
 * @date 2015-9-18
 */
public interface BeanFactory {
	/**
	 * 根据bean的id获取bean的实例
	 * @param id
	 * @return
	 */
	public Object getBean(String id) throws SystemException;
	
	public <T> T getBean(String id,Class<T> classType) throws SystemException;
}
