package com.mangocity.mbr.test;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.internal.StringMap;
import com.mangocity.ce.exception.IllegalParamException;
import com.mangocity.ce.util.JsonUtil;

public class Test1 {
public static void main(String[] args) throws IllegalParamException {
	Map map1 = new HashMap();
	Map map2 = new HashMap();
	map1.put("command", "getUserInfo");
	map1.put("adjustCode", "asdfx1");
	map2.put("userid", "xo0000000003");
	map2.put("username", "刘春元");
	map1.put("headMap", map2);
	System.out.println(JsonUtil.encodeCmd(map1));
	String s = JsonUtil.encodeCmd(map1);
	Map map3 = JsonUtil.decodeCmd(s);
	//System.out.println(map3.get("headMap").getClass().getTypeName());
	Map m =  (Map) map3.get("headMap");
	System.out.println(m.get("userid"));
}
}
