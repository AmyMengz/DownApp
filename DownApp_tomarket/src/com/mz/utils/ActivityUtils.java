package com.mz.utils;

import java.util.ArrayList;
import java.util.List;

import com.hdj.downapp_market.R;
import com.mz.bean.AppInfo;
import com.mz.bean.MARKET;
import com.mz.db.DBDao;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.KeyguardManager.KeyguardLock;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.widget.Toast;

public class ActivityUtils {
	
	public static void jumpMarket(Context context) {
		Logger.i("STATIC-----------------------------------------------------------JUMP");
		int selection = SprefUtil.getInt(context, SprefUtil.C_SELECTION, 0);
		DBDao dbDao = DBDao.getInstance(context);
		List<AppInfo> list = dbDao.loadAll();
		final String url=list.get(selection).getPackageName();
		String packageName = "";
		int value=SprefUtil.getInt(context, SprefUtil.C_MARKET, MARKET.MARKET_BAIDU.value());
		MARKET market=MARKET.valueOf(value);
		ActivityUtils.checkApkInstalled(context, url);
		switch (market) {
		case MARKET_BAIDU:
			packageName = GlobalConstants.BAIDU_PN;
			break;
		case MARKET_360:
			packageName = GlobalConstants._360_PN;
			break;
		case MARKET_YYB:
			packageName = GlobalConstants.YYB_PN;
			if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
				ActivityUtils.openYYB(context);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			break;
		case MARKET_WDJ:
			packageName = GlobalConstants.WDJ_PN;
			break;
		case MARKET_PP:
			packageName = GlobalConstants.PP_PN;
			break;
		case MARKET_AZ:
			packageName = GlobalConstants.AZ_PN;
			break;
		case MARKET_MU:
			packageName = GlobalConstants.MU_PN;
			break;
		case MARKET_GP:
			packageName = GlobalConstants.GP_PN;
			openMarket(context, GlobalConstants.GP_PN, url);
//			ActivityUtils.openAddAccount(context);
//			new Thread(new Runnable() {
//				
//				@Override
//				public void run() {
//					while (EasyOperateClickUtil.getIsGotoGoogleStoreFlag(context)!=EasyOperateClickUtil.GOTO_GOOGLE_STORE) {
//						try {
//							Thread.sleep(1000);
//						} catch (InterruptedException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//						Logger.e("---￥￥￥￥￥￥￥￥￥￥￥￥4-----MARKET_GP---------MARKET_GP----------MARKET_GP----￥￥￥￥￥￥￥￥￥￥￥￥￥￥----"+EasyOperateClickUtil.getIsGotoGoogleStoreFlag(context));
//					}
//					Logger.e("---￥￥￥￥￥￥￥￥￥￥￥￥4-----MARKET_GP---------MARKET_GP----------MARKET_GP----￥￥￥￥￥￥￥￥￥￥￥￥￥￥----"+EasyOperateClickUtil.getIsGotoGoogleStoreFlag(context));
//					EasyOperateClickUtil.setIsotoGoogleStoreFlag(context, EasyOperateClickUtil.DO_NOT_GOTO_GOOGLE_STORE);
////					gpThread=false;
//					Thread.interrupted();
//					openMarket(context, GlobalConstants.GP_PN, url);
//				}
//			}).start();
			return;
		default:
			break;
		}
		
		Logger.i("--------------------------------------------------------------------------");
		openMarket(context, packageName, url);
		Logger.i("--------------------------------------------------------------------------");
	}
	public static  void openMarket(Context context,String packageName,String url){
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
	
	public static void openHOOK(Context context, String appPackageName) {
		try {
			Intent intent = new Intent();
			intent.setAction("android.intent.action.MAIN");
			intent.setClassName("com.hdjad.github",
					"com.hdjad.github.activity.LoginActivity");
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
			Logger.i("START HDJ");
			// Intent
			// intent=context.getPackageManager().getLaunchIntentForPackage(appPackageName);
			// context.startActivity(intent);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void openAddAccount(Context context){
		Logger.i("OPEN  ACCOUNT");
		Intent intent2 = new Intent();
		intent2.setAction("android.settings.ADD_ACCOUNT_SETTINGS");
		intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent2);
		Logger.i("OPEN  ACCOUNT  END");
		return;
	}
	public static void openGoogleLogin(Context context, String appPackageName) {
		try {
			Logger.i("START GoogleLogin");
			Intent intent = new Intent();
			intent.setAction("android.intent.action.MAIN");
			intent.setClassName(appPackageName,
					"com.google.android.gsf.login.AccountIntroUIActivity");
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);

		} catch (Exception e) {
			Logger.i("STARTGoogleLogineeeeeeeeeeeeee==="+e);
			e.printStackTrace();
		}
	}
	public static void openApkByDetail(Context context, String appPackageName,String className) {
		try {
			Logger.i("START openApkByDetail");
			Intent intent = new Intent();
			intent.setAction("android.intent.action.MAIN");
			intent.setClassName(appPackageName,
					className);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);

		} catch (Exception e) {
			Logger.i("openApkByDetail   eeee==="+e);
			e.printStackTrace();
		}
	}

	public static void openOther(Context context, String appPackageName) {
		try {
			Logger.i("START DOWN");
			Intent intent = context.getPackageManager()
					.getLaunchIntentForPackage(appPackageName);
			context.startActivity(intent);

		} catch (Exception e) {
			Logger.i("START DOWNeeeeeeeeeeeeee==="+e);
			e.printStackTrace();
		}
	}

	public static void openYYB(Context context) {
		Intent intent = new Intent();
		intent.setComponent(new ComponentName(
				"com.tencent.android.qqdownloader",
				"com.tencent.midas.wx.APMidasWXPayActivity"));
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	public static void openYYB2(Context context) {

		Intent intent2 = new Intent();
		intent2.setComponent(new ComponentName(
				"com.tencent.android.qqdownloader",
				"com.connector.tencent.connector.ConnectionActivity"));
		intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent2);

	}

	public static void openAPK2(Context context, String packageName) {
		List<ResolveInfo> matches = findActivitiesForPackage(context,
				packageName);
		if ((matches != null) && matches.size() > 0) {
			ResolveInfo resolveInfo = matches.get(0);
			ActivityInfo activityInfo = resolveInfo.activityInfo;
			startApk(activityInfo.packageName, activityInfo.name);
		}
	}

	private static void startApk(String packageName, String name) {
		String cmd = "am start -n" + packageName + "/" + name + "\n";
		try {
			CmdUtil.run(cmd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static List<ResolveInfo> findActivitiesForPackage(Context context,
			String packageName) {
		final PackageManager pm = context.getPackageManager();
		final Intent intent = new Intent(Intent.ACTION_MAIN, null);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		intent.setPackage(packageName);
		final List<ResolveInfo> apps = pm.queryIntentActivities(intent, 0);
		return apps != null ? apps : new ArrayList<ResolveInfo>();
	}

	/**
	 * 获取栈顶activity
	 * 
	 * @param context
	 * @return
	 */
	/*
	 * public static String getTopActivity(Context context) { ActivityManager
	 * manager = (ActivityManager) context
	 * .getSystemService(Context.ACTIVITY_SERVICE); List<RunningTaskInfo>
	 * runningTaskInfos = manager.getRunningTasks(1);
	 * 
	 * if (runningTaskInfos != null) return
	 * (runningTaskInfos.get(0).topActivity).toString(); else return null; }
	 */
	public static String getPackageName(Context context, String path) {
		String packageName = null;
		PackageManager pm = context.getPackageManager();
		PackageInfo info = pm.getPackageArchiveInfo(path,
				PackageManager.GET_ACTIVITIES);
		if (info != null) {
			packageName = info.packageName;

		}
		return packageName;
	}

	public static boolean isNoSwitch(Context context) {
		long ts = System.currentTimeMillis();
		UsageStatsManager usageStatsManager = (UsageStatsManager) context
				.getSystemService("usagestats");
		List queryUsageStats = usageStatsManager.queryUsageStats(
				UsageStatsManager.INTERVAL_BEST, 0, ts);
		if (queryUsageStats == null || queryUsageStats.isEmpty()) {
			return false;
		}
		return true;
	}

	public static String getLauncherTopApp(Context context) {

		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
			ActivityManager activityManager = (ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE);
			List<ActivityManager.RunningTaskInfo> appTasks = activityManager
					.getRunningTasks(1);
			if (null != appTasks && !appTasks.isEmpty()) {
				return appTasks.get(0).topActivity.toString();
			}
		} else {
			UsageStatsManager sUsageStatsManager = (UsageStatsManager) context
					.getSystemService("usagestats");
			long endTime = System.currentTimeMillis();
			long beginTime = endTime - 10000;
			if (sUsageStatsManager == null) {
				sUsageStatsManager = (UsageStatsManager) context
						.getSystemService(Context.USAGE_STATS_SERVICE);
			}
			String result = "";
			UsageEvents.Event event = new UsageEvents.Event();
			UsageEvents usageEvents = sUsageStatsManager.queryEvents(beginTime,
					endTime);
			while (usageEvents.hasNextEvent()) {
				usageEvents.getNextEvent(event);
				if (event.getEventType() == UsageEvents.Event.MOVE_TO_FOREGROUND) {
					ComponentName com = new ComponentName(
							event.getPackageName(), event.getClassName());
					result = com.toString();
				}
			}
			if (!android.text.TextUtils.isEmpty(result)) {
				return result;
			}
		}
		return "";
	}

	public static void showPermission(Context context) {
		if (!(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
				&& !ActivityUtils.isNoSwitch(context)) {
			Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
			Toast.makeText(context,
					context.getString(R.string.need_permission),
					Toast.LENGTH_SHORT).show();
		}
	}

	private static KeyguardManager km;
	private static KeyguardLock kl;
	private static PowerManager pm;
	private static PowerManager.WakeLock wl;

	public static void wakeAndUnlock(Context context, boolean b) {
		if (b) {
			Logger.i("LOCKKKKK");
			// 获取电源管理器对象
			pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);

			// 获取PowerManager.WakeLock对象，后面的参数|表示同时传入两个值，最后的是调试用的Tag
			wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP
					| PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");

			// 点亮屏幕
			// wl.acquire();
			wl.acquire(30 * 24 * 60 * 60 * 1000);

			// 得到键盘锁管理器对象
			km = (KeyguardManager) context
					.getSystemService(Context.KEYGUARD_SERVICE);
			kl = km.newKeyguardLock("unLock");

			// 解锁
			kl.disableKeyguard();
		} else {
			// 锁屏
			kl.reenableKeyguard();

			// 释放wakeLock，关灯
			wl.release();
		}

	}

	public static void openXposed(Context context) {
		Intent intent = new Intent();
		intent.setComponent(new ComponentName(
				GlobalConstants.XPOSED_INSTALLER_PN,
				GlobalConstants.XPOSED_INSTALLER_CN2));
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra(GlobalConstants.EXTRA_SECTION, 0);
		intent.putExtra(GlobalConstants.FINISH_ON_UP_NAVIGATION, true);
		context.startActivity(intent);
	}

	public static void reBoot(Context context2) {
		try{
			Process p = CmdUtil.run("reboot");
			String s = CmdUtil.readResult(p);
			Logger.i("CMD----------" + s + "==" + CmdUtil.isRoot());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public static boolean isApkInstalled(Context context,String packageName){
		PackageManager pm= context.getPackageManager();
		try {
			 pm.getPackageInfo(packageName,PackageManager.GET_ACTIVITIES);  
			 return true;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	public static void checkApkInstalled(Context context,String packageName){
		Logger.i("isApkInstalled(context, packageName)=========="+isApkInstalled(context, packageName)+"==="+packageName);
		if(isApkInstalled(context, packageName)){
			Process p;
			try {
				p = CmdUtil.run("pm uninstall "+packageName);
				String result=CmdUtil.readResult(p);
				Logger.i("isApkInstalled(context, packageName)==========result:::::"+result);
				Thread.sleep(2*1000);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	

}
