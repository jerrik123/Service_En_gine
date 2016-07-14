/**
 * 
 */
package com.mangocity.mbr.controller.sms;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Arrays;

import org.apache.log4j.Logger;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;

import com.mangocity.ce.deploy.ConfigManage;
import com.mangocity.mbr.invoker.httpinvoker.HttpInvokerClientConfiguration;
import com.mangocity.mbr.invoker.httpinvoker.HttpInvokerClientConfigurationImpl;
import com.mangocity.mbr.invoker.httpinvoker.SimpleHttpInvokerRequestExecutor;
import com.mangocity.mbr.invoker.support.RemoteInvocation;
import com.mangocity.mbr.invoker.support.RemoteInvocationResult;
import com.mangoctiy.communicateservice.CommunicaterService;
import com.mangoctiy.communicateservice.domain.Sms;

/**
 * @Package com.mangocity.mbr.controller.sms
 * @Description :
 * @author YangJie
 * @email <a href='yangjie_software@163.com'>yangjie</a>
 * @date 2015-9-16
 */
public class SmsSender {
	private static final Logger log = Logger.getLogger(SmsSender.class);
	/**
	 * @param to
	 *            发送号码
	 * @param message
	 *            发送内容
	 * @param applicationName
	 * @param postFix
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void sendSms(String[] to, String message, String applicationName, String postFix)
			throws ClassNotFoundException, IOException {
		log.info("SmsSender sendSms begin");
		log.info("手机号码: " + Arrays.toString(to) + ",短信内容: " + message);
		String smsUrl = ConfigManage.instance().getSysConfig("sms.url");
		log.info("短信网关地址: " + smsUrl);
		HttpInvokerProxyFactoryBean factoryBean = new HttpInvokerProxyFactoryBean();
		factoryBean.setServiceUrl(smsUrl);
		factoryBean.setServiceInterface(CommunicaterService.class);
		factoryBean.afterPropertiesSet();
		CommunicaterService communicaterService = (CommunicaterService)factoryBean.getObject();
		Sms sms = new Sms();
		sms.setTo(to);
		sms.setMessage(message); 
		sms.setApplicationName("TMC");
		communicaterService.sendSms(sms);
		log.info("SmsSender sendSms end");
	}

	private ByteArrayOutputStream getByteArrayOutputStream(RemoteInvocation invocation)
			throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
		writeRemoteInvocation(invocation, baos);
		return baos;
	}

	private void writeRemoteInvocation(RemoteInvocation invocation, OutputStream os)
			throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(os);
		try {
			oos.writeObject(invocation);
			oos.flush();
		} finally {
			oos.close();
		}
	}
	
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		new SmsSender().sendSms(new String[]{"13554774675"}, "I love you.", "TMC", ".txt");
	}
}
