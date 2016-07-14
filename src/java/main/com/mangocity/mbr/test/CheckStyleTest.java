package com.mangocity.mbr.test;

import java.util.List;

import com.mangocity.ce.bean.CheckBean;
import com.mangocity.ce.bean.CheckBean.SubParam;
import com.mangocity.ce.deploy.ConfigManage;

public class CheckStyleTest {

	public static void main(String[] args) {
		ConfigManage.instance().initSystem();
		Object obj = ConfigManage.instance().getCheckStyle("addPassengerInfo");
		CheckBean cb = (CheckBean)obj;
		List<SubParam> list = cb.getParamList();
		for (SubParam subParam : list) {
			System.out.println(subParam.getLevel());
		}
	}

}
