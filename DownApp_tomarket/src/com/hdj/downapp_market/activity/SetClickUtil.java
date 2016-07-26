package com.hdj.downapp_market.activity;

import java.io.File;

import com.hdj.downapp_market.R;
import com.mz.utils.ActivityUtils;
import com.mz.utils.CmdUtil;
import com.mz.utils.EasyOperateClickUtil;
import com.mz.utils.MyFileUtil;
import com.mz.utils.GlobalConstants;
import com.mz.utils.Logger;
import com.mz.utils.SprefUtil;
import com.umeng.analytics.c;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Space;

/**
 * 结合XPosed做跳转点击工作
 * 
 * @author mz
 * 
 */
public class SetClickUtil {

	public static boolean setClick(View btn) {
		if (EasyOperateClickUtil.getAutoClickFlag(btn.getContext()) == EasyOperateClickUtil.AUTOCLICK) {
			return btn.performClick();
		} else {
			return false;
		}

	}

	public static boolean setSimulateClick(View view, float x, float y) {
		if (EasyOperateClickUtil.getAutoClickFlag(view.getContext()) == EasyOperateClickUtil.AUTOCLICK) {
			long downTime = SystemClock.uptimeMillis();
			final MotionEvent downEvent = MotionEvent.obtain(downTime,
					downTime, MotionEvent.ACTION_DOWN, x, y, 0);
			downTime += 200;
			final MotionEvent upEvent = MotionEvent.obtain(downTime, downTime,
					MotionEvent.ACTION_UP, x, y, 0);

			boolean onTouchEvent = view.onTouchEvent(downEvent);
			boolean onTouchEvent2 = view.onTouchEvent(upEvent);
			downEvent.recycle();
			upEvent.recycle();
			Logger.i("--------------------onTouchEvent---------" + onTouchEvent
					+ "-----------------" + onTouchEvent2);
			return onTouchEvent && onTouchEvent2;
		} else {
			return false;
		}
	}

	public static void openHook(Context context) {
		try {
			EasyOperateClickUtil.setEasyTag(context,
					EasyOperateClickUtil.UNCLICK);
		} catch (Exception e) {
			Logger.e("OPEN====" + e);
		}
		try {
			ActivityUtils
					.openHOOK(context, GlobalConstants.HDJ_GIT_PN/* "com.mz.webdownload" */);
			System.exit(0);
		} catch (Exception e) {
			Logger.e("OPEN====" + e);
		}
	}

	public static void openSetting(Context context) {
		Intent intent = new Intent();
		intent.setAction("android.net.vpn.SETTINGS");
		context.startActivity(intent);
		Logger.i("====================================vpn.SETTINGS======");
		System.exit(0);
	}

	public static void reBoot(Context context) {
		EasyOperateClickUtil.setNeetRebootFlag(context,
				EasyOperateClickUtil.NEED_REBOOT);
		Logger.i("ReBoot======================="
				+ EasyOperateClickUtil.getNeedRebootFlag(context));
		try {
			MyFileUtil.recordReboot();
			Process p = CmdUtil.run("reboot");
			String s = CmdUtil.readResult(p);
			Logger.i("CMD----------" + s + "==" + CmdUtil.isRoot());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void openMarket(Context context, String packageName,
			String url) {
		Uri localUri = Uri.parse("market://details?id=" + url);
		Intent localIntent = new Intent("android.intent.action.VIEW", localUri);
		localIntent.setPackage(packageName);

		// localIntent.addCategory("android.intent.category.BROWSABLE");//可不用加
		// localIntent.addCategory("android.intent.category.DEFAULT");//可不用加
		localIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		try {
			context.startActivity(localIntent);
		} catch (Exception e) {

			Logger.e(e.toString());
		}
		Logger.i("--------------------------------------------------------------------------");
	}

	public static void clearAccountAndToHook(Context context) {
		AccountManager accountManager = AccountManager.get(context);
		Account[] accounts = accountManager.getAccountsByType("com.google");
		Logger.i("========ACCOUNT======remove==========accounts："
				+ accounts.length);
		if (accounts.length >= 1) {
			boolean rmv = false;
			for (int i = 0; i < accounts.length; i++) {
				Logger.i("========ACCOUNT======remove===iii=======" + i);
				Account account = accounts[i];
				try {
					Logger.i("========ACCOUNT======remove===iaccounts【【i】】===="
							+ account);
					accountManager.removeAccount(account, null, null);
					Logger.i("========ACCOUNT======remove==========account【"
							+ i + "】==" + account.name + "  " + rmv);

				} catch (Exception e) {
					Logger.i("========ACCOUNT=eeeeeeeeeeee===remove==========account【"
							+ i + "】==" + e);
				}
			}
			SetClickUtil.openHook(context);
		} else {
			SetClickUtil.openHook(context);
		}
	}
	
	public static void recodeUselessAccount(Context context){
		MyFileUtil.recodeAccount(GlobalConstants.FILE_GOOGLE_ACCOUNT_USELESS_RECODE);
		clearAccountAndToHook(context);
	}
	
	public static void recodeUsedAccount(Context context){
		MyFileUtil.recodeAccount(GlobalConstants.FILE_GOOGLE_ACCOUNT_USED_RECODE);
		clearAccountAndToHook(context);
	}

}
