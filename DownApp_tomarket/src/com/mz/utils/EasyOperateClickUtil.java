package com.mz.utils;

import android.content.Context;
import android.provider.Settings.System;

public class EasyOperateClickUtil {
	public static final int UNCLICK = 1;//一键操作按钮是否点击了
	public static final int CILCKED = 2;
	public static final String EASY_OPERATION_CLICK_DTATUS = "easy_operate_state_tag";
//	public static final int HALF = 1; 
//	public static final int NO_HALF = 2;
//	public static final String HALF_DOWNLOAD_FLAG = "hanlf_download_flag";//是否要部分下载
	public static final int HALF_VALUE = 100; //默认部分下载值为100
	public static final String HALF_DOWNLOAD_VALUE = "hanlf_download_value";//部分下载的值
	
	public static final String IS_AUTOCLICK_FLAT ="market_auto_click_download_flag";
	public static final int  AUTOCLICK=1;
	public static final int  DO_NOT_AUTOCLICK=2;
	
	public static final String IS_AUTO_REBOOT_FLAT ="market_auto_reboot_flag";
	public static final int  AUTOREBOOT=1;
	public static final int  DO_NOT_AUTOREBOOT=2;
	
	public static final String IS_NEED_REBOOT_FLAT ="is_need_reboot_flag";
	public static final int  NEED_REBOOT=1;
	public static final int  DO_NOT_NEED_EBOOT=2;
	
	public static final String IS_AUTO_VPN_FLAT ="auto_vpn_connect_flag";
	public static final int  AUTO_VPN=1;
	public static final int  DO_NOT_AUTO_VPN=2;
	
	public static final String IS_ADD_ACCOUNT ="add_account_flag";
	public static final int  ADD_ACCOUNT=1;
	public static final int  DO_NOT_ADD_ACCOUNT=2;
	
	public static final String IS_REMOVE_ACCOUNT ="remove_account_flag";
	public static final int  REMOVE_ACCOUNT=1;
	public static final int  DO_NOT_REMOVE_ACCOUNT=2;
	
	public static final String IS_GOTO_GOOGLE_STORE ="goto_google_store_flag";
	public static final int  GOTO_GOOGLE_STORE=1;
	public static final int  DO_NOT_GOTO_GOOGLE_STORE=2;
	
	public static boolean  setEasyTag(Context context, int value) {
		return	System.putInt(context.getContentResolver(),EASY_OPERATION_CLICK_DTATUS, value);
	}
	public static int getEasyTag(Context context) {
		synchronized (EASY_OPERATION_CLICK_DTATUS) {
			return System.getInt(context.getContentResolver(),EASY_OPERATION_CLICK_DTATUS, UNCLICK);
		}
	}
//	public static void setBaiduHalfFlag(Context context, int value) {
//		synchronized (HALF_DOWNLOAD_FLAG) {
//			System.putInt(context.getContentResolver(),HALF_DOWNLOAD_FLAG, value);
//		}
//	}
//	public static int getBaiduHalfFlag(Context context) {
//		synchronized (HALF_DOWNLOAD_FLAG) {
//			return System.getInt(context.getContentResolver(),HALF_DOWNLOAD_FLAG, NO_HALF);
//		}
//	}
	public static void setHalfDownloadValue(Context context, int value) {
		synchronized (HALF_DOWNLOAD_VALUE) {
			System.putInt(context.getContentResolver(),HALF_DOWNLOAD_VALUE, value);
		}
	}
	public static int getHalfDownloadValue(Context context) {
		synchronized (HALF_DOWNLOAD_VALUE) {
			return System.getInt(context.getContentResolver(),HALF_DOWNLOAD_VALUE, HALF_VALUE);
		}
	}
	public static int getAutoClickFlag(Context context) {
		return System.getInt(context.getContentResolver(),IS_AUTOCLICK_FLAT, DO_NOT_AUTOCLICK);
	}
	public static void setAutoClickFlag(Context context,int value) {
		 System.putInt(context.getContentResolver(), IS_AUTOCLICK_FLAT, value);
	}
	
	
	public static int getAutoRebootFlag(Context context) {
		return System.getInt(context.getContentResolver(),IS_AUTO_REBOOT_FLAT, DO_NOT_AUTOREBOOT);
	}
	public static void setAutoRebootFlag(Context context,int value) {
		 System.putInt(context.getContentResolver(), IS_AUTO_REBOOT_FLAT, value);
	}
	
	
	public static int getNeedRebootFlag(Context context) {
		return System.getInt(context.getContentResolver(),IS_NEED_REBOOT_FLAT, DO_NOT_NEED_EBOOT);
	}
	public static void setNeetRebootFlag(Context context,int value) {
		 System.putInt(context.getContentResolver(), IS_NEED_REBOOT_FLAT, value);
	}
	
	public static int getAutoVpnFlag(Context context) {
		return System.getInt(context.getContentResolver(),IS_AUTO_VPN_FLAT, DO_NOT_AUTO_VPN);
	}
	public static void setAutoVpnFlag(Context context,int value) {
		 System.putInt(context.getContentResolver(), IS_AUTO_VPN_FLAT, value);
	}
	
	public static int getIsAddAccountFlag(Context context) {
		return System.getInt(context.getContentResolver(),IS_ADD_ACCOUNT, DO_NOT_ADD_ACCOUNT);
	}
	public static void setIsAddAccountFlag(Context context,int value) {
		 System.putInt(context.getContentResolver(), IS_ADD_ACCOUNT, value);
	}

	
	public static int getIsRemoveAccountFlag(Context context) {
		return System.getInt(context.getContentResolver(),IS_REMOVE_ACCOUNT, DO_NOT_REMOVE_ACCOUNT);
	}
	public static void setIsRemoveAccountFlag(Context context,int value) {
		 System.putInt(context.getContentResolver(), IS_REMOVE_ACCOUNT, value);
	}
	
	/**
	 * 
	 * @param context
	 * @return
	 */
	public static int getIsGotoGoogleStoreFlag(Context context) {
		return System.getInt(context.getContentResolver(),IS_GOTO_GOOGLE_STORE, DO_NOT_GOTO_GOOGLE_STORE);
	}
	public static void setIsotoGoogleStoreFlag(Context context,int value) {
		 System.putInt(context.getContentResolver(), IS_GOTO_GOOGLE_STORE, value);
	}



}
