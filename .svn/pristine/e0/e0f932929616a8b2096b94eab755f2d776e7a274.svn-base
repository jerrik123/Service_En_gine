/**
 * 
 */
package com.mangocity.mbr.init;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**   
 * @Package com.mangocity.mbr.init 
 * @Description :  filter基类
 * @author YangJie
 * @email <a href='yangjie_software@163.com'>yangjie</a>
 * @date 2015-8-12
 */
public class FrameworkFilter implements Filter {
	private static final Logger log = Logger.getLogger(FrameworkFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log.debug("FrameworkFilter init begin...");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request.getParameterNames();
		request.getParameterMap();
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		doDispatcher(httpRequest,httpResponse);//execute doDispatcher
		//chain.doFilter(httpRequest, httpResponse);
	}
	
	/**
	 * 供子类覆盖
	 * @param request
	 * @param response
	 */
	protected void doDispatcher(HttpServletRequest request,HttpServletResponse response) {}

	@Override
	public void destroy() {
		log.debug("FrameworkFilter init destroy...");
	}

}
