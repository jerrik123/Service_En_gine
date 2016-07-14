/**
 * 
 */
package com.mangocity.mbr.test;

import com.mangocity.ce.util.RedisUtils;

/**   
 * @Package com.mangocity.mbr 
 * @Description :  
 * @author YangJie
 * @email <a href='yangjie_software@163.com'>yangjie</a>
 * @date 2015-11-18
 */
public class CacheTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//清除掉所有已adjustCode_开头的key
		/*Set<String> set = RedisUtils.keys("adjustCode_*");
		for(String key : set){
			RedisUtils.del(key);
		}*/
		/*RedisUtils.lpush("abc", "123123");
		RedisUtils.lpush("abc", "okok");
		
		System.out.println(RedisUtils.lrange("abc", 0,-1 ));*/
	/*	System.out.println(RedisUtils.keys("adjustCode_*"));
		System.out.println(RedisUtils.lrange("adjustCode_3c85049efc6e339d65a52144754d85b7", 0, -1));*/
		
	/*	RedisUtils.set("yj", "123");
		System.out.println(RedisUtils.get("yj"));
		System.out.println(RedisUtils.ttl("yj"));*/
		
	/*	RedisUtils.lpush("yjs", "abc");
		RedisUtils.lpush("yjs", "bcd");
		System.out.println();*/
		
	//	System.out.println(RedisUtils.get("adjustCode_3c85049efc6e339d65a52144754d85b7"));
		
		String str = "abcccc2|3|4";
		String[] strs = str.split("\\|");
		System.out.println(strs[2]);
	}

}
