package com.mangocity.mbr.util;

import java.io.IOException;
import java.net.UnknownHostException;

import com.mangocity.ce.bean.EngineBean;
import com.mangocity.ce.deploy.ConfigManage;
import com.mangocity.ce.remote.ClientCall;

public class ServerCall {
	private static  String serverIp = "127.0.1.1";
	private static  int serverPort = 1124;
	
	public ServerCall(){
		serverIp = ConfigManage.instance().getSysConfig("remoteip");
		serverPort = Integer.parseInt(ConfigManage.instance().getSysConfig("remoteport"));
	}
	public static EngineBean call(EngineBean eb){
		try {
			eb = ClientCall.instance().call(ConfigManage.instance().getSysConfig("remoteip"),  Integer.parseInt(ConfigManage.instance().getSysConfig("remoteport")), eb);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return eb;
	}
}
