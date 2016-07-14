/**
 * 
 */
package com.mangocity.mbr.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mangocity.ce.util.CommonUtils;

/**   
 * @Package com.mangocity.mbr.util 
 * @Description :  
 * @author YangJie
 * @email <a href='yangjie_software@163.com'>yangjie</a>
 * @date 2015-9-18
 */
public class RegexUtils {
	 private static Pattern idCardPattern = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");  
	 
	/**
	 * 根据登陆名,获得登陆类型
	 * @param loginName
	 * @return
	 */
	public static String getShortTypeName(String loginName){
		if(CommonUtils.isEmail(loginName)){
			return "E";
		}else if(CommonUtils.isMobilePhone(loginName)){
			return "M";
		}else {
			return "C";
		}
	}
	/**
	 * 是否是合法的身份证
	 * @param loginName
	 * @return
	 */
	public static boolean isValidIDCard(String idCard){
		if(CommonUtils.isBlank(idCard)) return false;
		return idCardPattern.matcher(idCard).matches();
	}
	
}
