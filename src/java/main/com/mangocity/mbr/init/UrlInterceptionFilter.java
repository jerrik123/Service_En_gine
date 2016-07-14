package com.mangocity.mbr.init;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.mangocity.ce.bean.EngineBean;
import com.mangocity.ce.deploy.ConfigManage;
import com.mangocity.ce.exception.ExceptionAbstract;
import com.mangocity.ce.util.CommonUtils;
import com.mangocity.ce.util.RedisUtils;
import com.mangocity.ce.util.ServletUtil;
import com.mangocity.ce.web.prosess.ServerRequestProcess;
import com.mangocity.mbr.book.ErrorCode;
import com.mangocity.mbr.util.ErrorUtils;
import com.mangocity.mbr.util.SafeUtil;

/**
 * @Package com.mangocity.mbr.init
 * @Description : Url核心拦截过滤器
 * @author YangJie
 * @email <a href='yangjie_software@163.com'>yangjie</a>
 * @date 2015-8-12
 */
public class UrlInterceptionFilter extends FrameworkFilter {
	private static final Logger log = Logger
			.getLogger(UrlInterceptionFilter.class);

	protected void doDispatcher(HttpServletRequest request,
			HttpServletResponse response) {
		ServerRequestProcess reqProcess = ServerRequestProcess.getInstance();
		EngineBean eb = new EngineBean();
		Object obj = null;
		boolean isNeededValidate = isNeededValidate();
		try {
			eb.setRequest(request);
			eb.setResponse(response);
			// 针对入参非正确json格式的处理方式
			try {
				reqProcess.setCharacterEncoding(eb);
				reqProcess.transformRequestParam(eb);
			} catch (ExceptionAbstract e) {
				eb.setResultCode(e.getErrorCode());
				eb.setResultMsg(e.getErrorMsg());
				reqProcess.responseProcess(eb, response);
				return;
			}
			// 应用权限校验
			if (CommonUtils.isBlank(eb.getAdjustCode())) {
				// adjustcode必填
				obj = ErrorUtils
						.error(eb, ErrorCode.ERROR_REQUEST_PARAM_INVALID,
								"adjustCode不能为空");
				System.out.println("adjustCode不能为空");
			} else if (CommonUtils.isBlank(eb.getAppId())) {
				// appid必填
				obj = ErrorUtils.error(eb,
						ErrorCode.ERROR_REQUEST_PARAM_INVALID, "appId不能为空");
				System.out.println("appId不能为空");
			} else if (isNeededValidate && !isValidateSuccess(eb)) {//如果在配置文件配置request.validate.switch=off,则不需要校验
				obj = ErrorUtils.error(eb,
						ErrorCode.ERROR_REQUEST_PARAM_INVALID,
						"校验失败,请输入正确的appId和adjustCode");
				log.info("校验adjustCode和appId不通过...");
			} else {
				log.info("appId: " + eb.getAppId() + " ,adjustCode: "
						+ eb.getAdjustCode() + " ,请求服务: " + eb.getCommand());
				obj = reqProcess.distribution(eb);
			}
			reqProcess.responseProcess(obj, response);
		} catch (ExceptionAbstract e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	//配置是否需要开启校验(方便本地测试)
	private boolean isNeededValidate() {
		String requestSwitch = ConfigManage.instance().getSysConfig(
				"request.validate.switch");
		if (CommonUtils.isBlank(requestSwitch)){
			return true;
		}
		if ("off".equalsIgnoreCase(requestSwitch)) {
			return false;
		}else if("on".equalsIgnoreCase(requestSwitch)){
			return true;
		}
		return true;
	}

	/**
	 * 校验接口的合法性
	 * 
	 * @param eb
	 *            redis key:adjustCode_XXX value: appId|method|level
	 * @return
	 */
	private boolean isValidateSuccess(EngineBean eb) {
		StringBuffer sb = new StringBuffer("adjustCode_");
		Collection<InetAddress> colInetAddress = getAllHostAddress();
		for (InetAddress address : colInetAddress) {
			if (!address.isLoopbackAddress()) {
				// 生产
				if (ConfigManage.instance().getSysConfig("proip")
						.contains(address.getHostAddress())) {
					sb.append("P");
					log.info("生产ip对应服务器ip-->" + address.getHostAddress());
					break;
				} else if (ConfigManage.instance().getSysConfig("testip")
						.contains(address.getHostAddress())) {
					sb.append("T");
					log.info("测试ip对应服务器ip-->" + address.getHostAddress());
					break;
				}
			}
		}
		String adjustCode = sb.append(eb.getAdjustCode()).toString();
		String appId = eb.getAppId();
		String methodAuthLimit = null;

		String cacheValue = RedisUtils.get(adjustCode);// 取出appid和等级

		// 1.判断缓存是否存在该key
		if (!RedisUtils.exists(adjustCode)) {
			log.info("缓存中不存在该adjustCode: " + adjustCode);
			return false;
		}

		if (CommonUtils.isBlank(cacheValue)) {
			log.info("redis中key=" + adjustCode + " ,对应的value为空");
			return false;
		}
		String[] splitCacheStr = cacheValue.split("\\|");

		if (splitCacheStr.length != 4) {
			log.info("redis中的adjustCode_*对应的value不是按XX|XX|的规则来存储: "
					+ cacheValue);
			return false;
		}
		// 2.判断缓存中的value和appId是否相等
		if (!appId.equals(splitCacheStr[0])) {
			log.info("缓存中appId和传入appId不相同");
			return false;
		}
		// 3.判断调用的接口是否和redis中adjustCode_*对应的method相同
		if (null != eb.getCommand()
				&& !eb.getCommand().equals(splitCacheStr[1])) {
			log.info("缓存中method和command不相同");
			return false;
		}

		if (!splitCacheStr[3].contains(ServletUtil.getRemoteAddress(eb
				.getRequest()))) {
			log.info("调用服务器认证失败"
					+ ServletUtil.getRemoteAddress(eb.getRequest()) + "-->"
					+ splitCacheStr[3]);
			return false;
		}
		methodAuthLimit = splitCacheStr[2];
		log.info("接口认证权限: " + methodAuthLimit);
		// 将接口调用的等级传入
		eb.setHead("methodAuthLimit", methodAuthLimit);
		return true;
	}

	public static Collection<InetAddress> getAllHostAddress() {
		try {
			Enumeration<NetworkInterface> networkInterfaces = NetworkInterface
					.getNetworkInterfaces();
			Collection<InetAddress> addresses = new ArrayList<InetAddress>();

			while (networkInterfaces.hasMoreElements()) {
				NetworkInterface networkInterface = networkInterfaces
						.nextElement();
				Enumeration<InetAddress> inetAddresses = networkInterface
						.getInetAddresses();
				while (inetAddresses.hasMoreElements()) {
					InetAddress inetAddress = inetAddresses.nextElement();
					addresses.add(inetAddress);
				}
			}
			return addresses;
		} catch (SocketException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}
