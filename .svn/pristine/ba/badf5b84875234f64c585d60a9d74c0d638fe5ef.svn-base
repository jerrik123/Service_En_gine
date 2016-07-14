package com.mangocity.mbr.init;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.mangocity.ce.deploy.ConfigManage;


public class MangoServlet extends HttpServlet {
	public void init() throws ServletException {
		//SystemConfigCacgeOperate.instance().readerSysConfig();
		ConfigManage.instance().initSystem();
	}
}
