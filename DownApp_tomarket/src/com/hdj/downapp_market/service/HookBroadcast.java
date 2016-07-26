package com.hdj.downapp_market.service;

import java.io.BufferedWriter;
import java.io.FileWriter;

import com.hdj.downapp_market.R;
import com.hdj.downapp_market.activity.AlertActivity;
import com.hdj.downapp_market.activity.MainActivity;
import com.mz.bean.MARKET;
import com.mz.utils.CmdUtil;
import com.mz.utils.EasyOperateClickUtil;
import com.mz.utils.ActivityUtils;
import com.mz.utils.MyFileUtil;
import com.mz.utils.GlobalConstants;
import com.mz.utils.Logger;
import com.mz.utils.SprefUtil;
import com.mz.utils.TTSUtil;
import com.mz.utils.VpnUtil;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class HookBroadcast extends BroadcastReceiver {
	boolean market = true;

	@Override
	public void onReceive(final Context context, Intent intent) {
		boolean autoMarket = SprefUtil.getBool(context,
				SprefUtil.C_AUTO_MARKET, true);

		if (intent.getAction().equals(GlobalConstants.HDJ_GIT_BROADCAST)) {
		
			if (!autoMarket)
				return;
Logger.i("----------------------------VPN---------------------------"+VpnUtil.isVpnUsed()+"===="+VpnUtil.isVpnConnect()+"  "+SprefUtil.getBool(context, SprefUtil.C_GOOGLE_VPN, false));
			// checkGoogleVpn(context);
			if (SprefUtil.getBool(context, SprefUtil.C_GOOGLE_VPN, false)) {
				if(!VpnUtil.isVpnUsed()){
					ActivityUtils.openApkByDetail(context, GlobalConstants.PROXY_PN, GlobalConstants.PROXY_CN);
				}
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//				if (EasyOperateClickUtil.getAutoVpnFlag(context) == EasyOperateClickUtil.AUTO_VPN) {
////					if (!VpnUtil.isVpnUsed()) { // ÅÐ¶ÏVPNÊÇ·ñÁ¬½Ó
////						Intent intent2 = new Intent();
////						intent2.setAction("android.net.vpn.SETTINGS");
////						intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////						context.startActivity(intent2);
////						return;
////					}
//					if(!VpnUtil.isVpnUsed()){
//						ActivityUtils.openApkByDetail(context, GlobalConstants.PROXY_PN, GlobalConstants.PROXY_CN);
//					}
//				}
			}
			Logger.i("------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			if (SprefUtil.getInt(context, SprefUtil.C_MARKET,
					MARKET.MARKET_BAIDU.value()) == MARKET.MARKET_GP.value()) {
				MyFileUtil.addAccountIndex();
				
				AccountManager accountManager = AccountManager.get(context);
				Account[] accounts = accountManager.getAccountsByType("com.google");
				Logger.i("ACCOUNT*********************************************************-----------111111111-------"+accounts.length);
				for (int i = 0; i < accounts.length; i++) {
					Account account = accounts[i];
					 accountManager.removeAccount(account, null, null);
					 Logger.i("ACCOUNT*********************************************************-----------22222222222-------"+account);
				}
//					EasyOperateClickUtil.setIsAddAccountFlag(context, EasyOperateClickUtil.ADD_ACCOUNT);
//					ActivityUtils.openOther(context, GlobalConstants.GP_PN);
					
			}
			Logger.i("HookBroadcast===autoMarket----" + autoMarket);
			Logger.i("------------------------------------------------------------------------------------------------------------------------------------------------------------------");

			int count = SprefUtil.getInt(context, SprefUtil.C_COUNT, 0);
			SprefUtil.putInt(context, SprefUtil.C_COUNT, count + 1);

			MainActivity.handleDownload(context);

			new Thread(new Runnable() {

				@Override
				public void run() {
					while (market) {
						String top = ActivityUtils.getLauncherTopApp(context);// getTopActivity(context);
						// Logger.i("HookBroadcast===top----" + top);
						if (top.equals(GlobalConstants.INSTALL_ACTIVITY)) {
							EasyOperateClickUtil.setEasyTag(context,
									EasyOperateClickUtil.UNCLICK);
							int flag = -1;
							while ((flag = EasyOperateClickUtil
									.getEasyTag(context)) != EasyOperateClickUtil.UNCLICK) {
								EasyOperateClickUtil.setEasyTag(context,
										EasyOperateClickUtil.UNCLICK);
							}
							int waitTime = SprefUtil.getInt(context,
									SprefUtil.C_INSTALL_TIME, 0);
							Logger.i("HookBroadcast===InstallPage----market"
									+ market + "  waitTime  " + waitTime);
							try {
								Thread.sleep(waitTime * 1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							if (SprefUtil.getBool(context,
									SprefUtil.C_AUTO_HOOK, true)) {
								ActivityUtils.openHOOK(context,
										GlobalConstants.HDJ_GIT_PN);
								market = false;
							}
						}
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}).start();

		}
	}

	private boolean isNetWorkConneted(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager == null)
			return false;
		NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
		if (networkInfos == null || networkInfos.length <= 0)
			return false;
		for (int i = 0; i < networkInfos.length; i++) {
			if (networkInfos[i].getState() == NetworkInfo.State.CONNECTED) {
				return true;
			}
		}
		return false;
	}

}
