package com.mangocity.mbr.init;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ControllerDistribution extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		//System.out.println(req.getHeader("Referer"));
		// TODO Auto-generated method stub
		
		BufferedReader bf = req.getReader();
		StringBuffer str = new StringBuffer();
		String readLine = "";
		while(null != (readLine = bf.readLine())){
			str.append(readLine);
		}
		System.out.println(str);
		Object a = req.getParameterMap();
		Object b = req.getParameterNames();

	}

}
