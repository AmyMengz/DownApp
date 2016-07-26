package com.hdj.downapp_market.service;

import com.mz.utils.ActivityUtils;
import com.mz.utils.EasyOperateClickUtil;
import com.mz.utils.GlobalConstants;
import com.mz.utils.Logger;

import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
/**
 * 开机自启动广播处理
 * @author mz
 *
 */
public class StartBroadcastReceiver extends BroadcastReceiver {
	private static final String ACTION = "android.intent.action.BOOT_COMPLETED";
	private static final String ACTION2 = "android.intent.action.MEDIA_MOUNTED";
	private static final String ACTION3 = "android.intent.action.MEDIA_UNMOUNTED";
	private static final String ACTION4 = "android.net.conn.CONNECTIVITY_CHANGE";
	
	public void onReceive(Context context, Intent intent) {
		Logger.i(intent.getAction());
		if (intent.getAction().equals(ACTION)||intent.getAction().equals(ACTION2)
				||intent.getAction().equals(ACTION3)
				) {
			EasyOperateClickUtil.setNeetRebootFlag(context, EasyOperateClickUtil.DO_NOT_NEED_EBOOT);
			wakeAndUnlock(context, true);
			ActivityUtils.openOther(context, GlobalConstants.DOWN_APP_PN);
			Intent i = new Intent(Intent.ACTION_RUN);
			i.setClass(context, PauseService.class);
			context.startService(i);
			EasyOperateClickUtil.setEasyTag(context,EasyOperateClickUtil.UNCLICK);
			ActivityUtils.openHOOK(context, GlobalConstants.HDJ_GIT_PN);

		}
		

	}
	private  KeyguardManager km;
	private  KeyguardLock kl;
	private  PowerManager pm;
	private  PowerManager.WakeLock wl;
	public  void wakeAndUnlock(Context context, boolean b) {
		if (b) {
			Logger.i("LOCKKKKK");
			// 获取电源管理器对象
			pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);

			// 获取PowerManager.WakeLock对象，后面的参数|表示同时传入两个值，最后的是调试用的Tag
			wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP
					| PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");

			// 点亮屏幕
//			wl.acquire();
			wl.acquire(30*24*60*60*1000);

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

	
}