package com.hdj.downapp_market.service;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.hdj.downapp_market.R;
import com.hdj.downapp_market.activity.AlertActivity;
import com.hdj.downapp_market.activity.MainActivity;
import com.hdj.downapp_market.activity.SetClickUtil;
import com.mz.bean.MARKET;
import com.mz.utils.CmdUtil;
import com.mz.utils.EasyOperateClickUtil;
import com.mz.utils.ActivityUtils;
import com.mz.utils.GlobalConstants;
import com.mz.utils.Logger;
import com.mz.utils.MyFileUtil;
import com.mz.utils.SprefUtil;
import com.mz.utils.TTSUtil;
import com.umeng.analytics.c;

import u.aly.co;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.widget.Toast;

public class PauseService extends Service {
	int count = 0;
	int noRootCount = 0;
	String top = "", last = "";
	boolean lastRoot = true, nowRoot = true;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
			
		checkTopActivity();
		showNotif();
		return START_STICKY;
	}
	private static final int NOTIFY_FAKEPLAYER_ID=1339;  
	void showNotif() {
		
		Intent i = new Intent(this,MainActivity.class);
        //ע��Intent��flag���ã�FLAG_ACTIVITY_CLEAR_TOP: ���activity���ڵ�ǰ���������У�����ǰ�˵�activity���ᱻ�رգ����ͳ�����ǰ�˵�activity��FLAG_ACTIVITY_SINGLE_TOP: ���activity�Ѿ�����ǰ�����У�����Ҫ�ټ��ء�����������flag��������һ����Ψһ��һ��activity��������棩��������ǰ�ˡ�
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);           
        Notification myNotify = new Notification.Builder(this) 
                                .setSmallIcon(R.drawable.ic_launcher) 
                                .setTicker(getString(R.string.background_service)) 
                                .setContentTitle(getString(R.string.background_service)) 
                                .setContentText(getString(R.string.background_service_content))
                                .setContentIntent(pi) 
                                .build();             
        //����notification��flag�������ڵ��֪ͨ��֪ͨ��������ʧ��Ҳ������ͼ������֪ͨ����ʾͼ�ꡣ����ȷ����activity���˳���״̬������ͼ�����������������ٴν���activity��
        myNotify.flags |= Notification.FLAG_NO_CLEAR;
        
       // ���� 2��startForeground( int, Notification)����������Ϊforeground״̬��ʹϵͳ֪���÷������û���ע�����ڴ�����²���killed�����ṩ֪ͨ���û���������foreground״̬��
        startForeground(NOTIFY_FAKEPLAYER_ID,myNotify);
	}

	private void checkTopActivity() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					checkRebootFlag();
					checkGoogleAccount();
					String top = ActivityUtils.getLauncherTopApp(getApplicationContext());
					
//					if(top.equals(GlobalConstants.GOOGLE_DIALOG))
//					top=GlobalConstants.GOOGLE_DETAIL;
					Logger.i("PauseService===top "+top+"==count=="+count+" easy  "+EasyOperateClickUtil.getEasyTag(getApplicationContext()));
					if (!SprefUtil.getBool(getApplicationContext(),
							SprefUtil.C_AUTO_HOOK, true)) {
						try {
							Thread.sleep(5 * 1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						count = 0;
						continue;
					}
					top = ActivityUtils
							.getLauncherTopApp(getApplicationContext());
					if (!top.equals(last)) {
						last = top;
						count = 0;
					} else {
						last = top;
						count++;
						if (count >= 30) {
							while ((EasyOperateClickUtil
									.getEasyTag(getApplicationContext())) != EasyOperateClickUtil.UNCLICK) {
							boolean bool=	EasyOperateClickUtil.setEasyTag(
										getApplicationContext(),
										EasyOperateClickUtil.UNCLICK);
							if(bool!=true) continue;
							}
							count = 0;
							if(SprefUtil.getInt(PauseService.this, SprefUtil.C_MARKET, MARKET.MARKET_BAIDU.value())==MARKET.MARKET_GP.value()){
//								SetClickUtil.clearAccountAndToHook(PauseService.this);
								SetClickUtil.recodeUselessAccount(PauseService.this);
							}else {
								ActivityUtils.openHOOK(PauseService.this,
										GlobalConstants.HDJ_GIT_PN);
							}
						}
						try {
							Thread.sleep(5* 1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}
			}
		}).start();

	}
	protected void checkGoogleAccount() {
		if(!(SprefUtil.getInt(this, SprefUtil.C_MARKET, MARKET.MARKET_BAIDU.value())==MARKET.MARKET_GP.value())){
			return;
		}
		if(MyFileUtil.getGoogleAccountCount()-MyFileUtil.getAccountIndex()<0||MyFileUtil.getGoogleAccountCount()==0){
//			Logger.i("ACCOUNT*******MyFileUtil.getGoogleAccountCount()-MyFileUtil.getAccountIndex()222-=="+(MyFileUtil.getGoogleAccountCount()-MyFileUtil.getAccountIndex()));
//			Toast.makeText(this, getString(R.string.google_market_no_more_account), Toast.LENGTH_LONG).show();
			TTSUtil.getInstance(this).ringGoogle();
//			Intent in = new Intent(this, AlertActivity.class);
//			in.putExtra(GlobalConstants.ALERT_ACTIVITY_FLAG, GlobalConstants.ALERT_ACTIVITY_NO_ACCOUNT);
//			in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			startActivity(in);
		}
	}

	@Override
	public void onDestroy() {
		Intent localIntent = new Intent();
		localIntent.setClass(this, PauseService.class); //����ʱ��������Service
		this.startService(localIntent);
		super.onDestroy();
	}
	public void checkRebootFlag(){
		if(EasyOperateClickUtil.getNeedRebootFlag(getApplicationContext())==EasyOperateClickUtil.NEED_REBOOT){
			ActivityUtils.reBoot(getApplicationContext());
		}
		if(EasyOperateClickUtil.getAutoRebootFlag(this) == EasyOperateClickUtil.AUTOREBOOT){
			Logger.i("noRootCount--------------------------=="+noRootCount);
			if(!CmdUtil.isRoot()){
				lastRoot=nowRoot;
				nowRoot=false;
				if(lastRoot==nowRoot){
					noRootCount++;
				}
				Logger.i("noRootCount--------------------------=="+noRootCount+"  "+lastRoot+"  "+nowRoot);
				if(noRootCount>=GlobalConstants.NO_ROOT_COUNT){
					
					TTSUtil.getInstance(this).ringReboot();
					Intent in=new Intent(getApplicationContext(), AlertActivity.class);
					in.putExtra(GlobalConstants.ALERT_ACTIVITY_FLAG, GlobalConstants.ALERT_ACTIVITY_NO_ROOT);
					in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(in);
				}
			}
			else {
				lastRoot=nowRoot;
				nowRoot=true;
				noRootCount=0;
			}
		}
				
		
		
	}
	
	
}
