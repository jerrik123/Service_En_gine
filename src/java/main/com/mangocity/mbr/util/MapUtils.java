/**
 * 
 */
package com.mangocity.mbr.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mangocity.ce.bean.EngineBean;
import com.mangocity.ce.exception.IllegalParamException;
import com.mangocity.ce.util.AssertUtils;
import com.mangocity.mbr.controller.mbr.MbrService;

/**
 * @Package com.mangocity.mbr.util
 * @Description : 合并Map
 * @author YangJie
 * @email <a href='yangjie_software@163.com'>yangjie</a>
 * @date 2015-9-23
 */
public class MapUtils {
	private static final Logger log = Logger.getLogger(MapUtils.class);
	public static void unionEngineBean(EngineBean targetPb, EngineBean sourcePb)
			throws IllegalParamException {
		AssertUtils.assertEmpty(new Object[] { targetPb, sourcePb });
		unionMap((Map<String,Object>)targetPb.getBody("result"), (Map<String,Object>)sourcePb.getBody("result"));
	}

	public static void unionMap(EngineBean pb, Map<String, Object> map)
			throws IllegalParamException {
		AssertUtils.assertEmpty(new Object[] { pb, map });
		unionMap(pb.getBodyMap(), map);
	}

	public static <T, V> void unionMap(Map<T, V> targetMap, Map<T, V> sourceMap)
			throws IllegalParamException {
		AssertUtils.assertNull(targetMap, "targetMap不能为空");
		AssertUtils.assertNull(sourceMap, "sourceMap不能为空");
		log.info("targetMap: " + targetMap);
		log.info("sourceMap: " + sourceMap);
		if (targetMap.isEmpty()) {
			targetMap = sourceMap;
		}
		targetMap.putAll(sourceMap);
	}

	public static void main(String[] args) throws IllegalParamException {
		Map<String, Object> targetMap = new HashMap<String, Object>();
		targetMap.put("key1", "ok");

		Map<String, Object> sourceMap = new HashMap<String, Object>();
		targetMap.put("key1", "bad");

		unionMap(targetMap, sourceMap);
		System.out.println(targetMap);
	}
}
