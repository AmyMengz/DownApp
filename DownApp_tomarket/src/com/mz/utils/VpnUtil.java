package com.mz.utils;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Enumeration;

import android.text.TextUtils;

public class VpnUtil {

	public static boolean isVpnConnect() {
		String string = "netstat -anp | grep :1723";
		try {
			Process p = CmdUtil.run(string);
			p.waitFor();
			String out = CmdUtil.readResult(p).toUpperCase();
			if (!TextUtils.isEmpty(out) && out.contains("ESTABLISHED")) {
				return isVpnConnectFromIpRo();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean isVpnConnectFromIpRo() {
		try {
			Process p = CmdUtil.run("ip ro");
			p.waitFor();
			String out = CmdUtil.readResult(p);
			if (!TextUtils.isEmpty(out) && out.contains("ppp")) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public static boolean isVpnUsed() {  
	    try {  
	        Enumeration<NetworkInterface> niList = NetworkInterface.getNetworkInterfaces();  
	        if(niList != null) {  
	            for (NetworkInterface intf : Collections.list(niList)) {  
	                if(!intf.isUp() || intf.getInterfaceAddresses().size() == 0) {  
	                    continue;  
	                }  
//	                Logger.i( "isVpnUsed() NetworkInterface Name: " + intf.getName());  
	                if ("tun0".equals(intf.getName()) || "ppp0".equals(intf.getName())){                          
	                    return true; // The VPN is up  
	                }  
	            }  
	        }  
	    } catch (Throwable e) {  
	        e.printStackTrace();  
	    }  
	    return false;  
	}  

}
