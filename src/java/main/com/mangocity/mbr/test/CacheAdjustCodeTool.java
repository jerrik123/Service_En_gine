/**
 * 
 */
package com.mangocity.mbr.test;

import com.mangocity.ce.util.CommonUtils;
import com.mangocity.ce.util.RedisUtils;
import com.mangocity.mbr.util.SafeUtil;

/**
 * @Package com.mangocity.mbr
 * @Description :
 * @author YangJie
 * @email <a href='yangjie_software@163.com'>yangjie</a>
 * @date 2015-11-18
 */
public class CacheAdjustCodeTool {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(RedisUtils.get("adjustCode_729dd939bbd6becb6935f303c00a8418"));//ac854c5f2e870e7a0a7710dc58caa2b6
		/*addCache("updateUserDevice", "pt38598f0881wq0728", "9aa273cf91a929ee6b59e1c584e88a98");
		addCache("addUserDevice", "pt38598f0881wq0728", "9aa273cf91a929ee6b59e1c584e88a98");*/
		addCache("updateMbrInfo", "imobi8473x9394fs22", "9aa273cf91a929ee6b59e1c584e88a98");
	/*	String key = String.valueOf("addAwardPoints");
		// 处理数据库中因为疏忽存入的空格字符
		String value = SafeUtil.MD5(SafeUtil.MD5("wx17688f0881ac9513" + "addAwardPoints")
				+ "9aa273cf91a929ee6b59e1c584e88a98");
		String newKey = "adjustCode_" + value;
		StringBuilder sb = new StringBuilder();
		sb.append(CommonUtils.trim("wx17688f0881ac9513"));
		sb.append("|");
		sb.append(CommonUtils.trim("addAwardPoints"));
		sb.append("|");
		// RedisUtils.lpush(newKey, String.valueOf(tMap.get("app")));//redis
		// lpush会自动删除key
		if (key.equals("updateMobileNo")) {
			// RedisUtils.lpush(newKey, "1");
			RedisUtils.set(newKey, sb.append("1").toString());
		} else {
			// RedisUtils.lpush(newKey, "0");
			RedisUtils.set(newKey, sb.append("0").toString());
		}*/
	}

	public static void addCache(String methodName, String appId, String appKey) {
		String key = String.valueOf(methodName);
		// 处理数据库中因为疏忽存入的空格字符
		String value = SafeUtil.MD5(SafeUtil.MD5(appId + methodName) + appKey);
		String newKey = "adjustCode_" + value;
		System.out.println("cache key: " + newKey);
		StringBuilder sb = new StringBuilder();
		sb.append(CommonUtils.trim(appId));
		sb.append("|");
		sb.append(CommonUtils.trim(methodName));
		sb.append("|");
		// RedisUtils.lpush(newKey, String.valueOf(tMap.get("app")));//redis
		// lpush会自动删除key
		if (key.equals("updateMobileNo")) {
			// RedisUtils.lpush(newKey, "1");
			RedisUtils.set(newKey, sb.append("1").toString());
		} else {
			// RedisUtils.lpush(newKey, "0");
			RedisUtils.set(newKey, sb.append("0").toString());
		}
	}

	/**
	 * @param key
	 * @param object
	 */
	private static void setProperty(String key, String value) {
		String newKey = "adjustCode_" + value;
		System.out.println(key + "======" + newKey);
	}

}
