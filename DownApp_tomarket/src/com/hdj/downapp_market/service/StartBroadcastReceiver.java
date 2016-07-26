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
 * �����������㲥����
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
			// ��ȡ��Դ����������
			pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);

			// ��ȡPowerManager.WakeLock���󣬺���Ĳ���|��ʾͬʱ��������ֵ�������ǵ����õ�Tag
			wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP
					| PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");

			// ������Ļ
//			wl.acquire();
			wl.acquire(30*24*60*60*1000);

			// �õ�����������������
			km = (KeyguardManager) context
					.getSystemService(Context.KEYGUARD_SERVICE);
			kl = km.newKeyguardLock("unLock");

			// ����
			kl.disableKeyguard();
		} else {
			// ����
			kl.reenableKeyguard();

			// �ͷ�wakeLock���ص�
			wl.release();
		}

	}

	
}