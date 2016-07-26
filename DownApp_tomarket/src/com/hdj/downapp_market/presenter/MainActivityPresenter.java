package com.hdj.downapp_market.presenter;

import java.util.List;

import com.hdj.downapp_market.R;
import com.hdj.downapp_market.activity.AlertActivity;
import com.hdj.downapp_market.activity.ChoseDownAppActivity;
import com.hdj.downapp_market.activity.ChoseMarketActivity;
import com.hdj.downapp_market.activity.MainActivity;
import com.hdj.downapp_market.service.PauseService;
import com.hdj.downapp_market.view.IMainActivityView;
import com.mz.bean.AppInfo;
import com.mz.bean.MARKET;
import com.mz.db.DBDao;
import com.mz.utils.ActivityUtils;
import com.mz.utils.CmdUtil;
import com.mz.utils.EasyOperateClickUtil;
import com.mz.utils.MyFileUtil;
import com.mz.utils.GlobalConstants;
import com.mz.utils.Logger;
import com.mz.utils.SprefUtil;
import com.mz.utils.TTSUtil;
import com.mz.utils.VpnUtil;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;

public class MainActivityPresenter {
	private Context context;
	private IMainActivityView mainActivityView;
	private DBDao dbDao = null;
	private boolean gpThread=true;

	public MainActivityPresenter(Context context,
			IMainActivityView mainActivityView) {
		this.context = context;
		this.mainActivityView = mainActivityView;
		dbDao = DBDao.getInstance(context);
	}
	/**
	 * 初始化
	 */
	public void init() {
		context.startService(new Intent(context, PauseService.class));
		getVoicePermissionPrepare();
		getTvChooseMarket();
		getTvApkTips();
		getTvDownPercentTips();
		mainActivityView.setEtPercent(GlobalConstants.FLAG_ET_NORMAL, null);
		mainActivityView.setTvInstallPeriodTips();
		mainActivityView.setEtInstallPeriod(GlobalConstants.FLAG_ET_NORMAL,
				null);
		mainActivityView.setAutoMarketChecked();
		mainActivityView.setAutoHookChecked();
		mainActivityView.setAutoClickChecked();
		mainActivityView.setAutoRebootChecked();
		mainActivityView.setAutoVPNChecked();
		mainActivityView.setTitles(String.valueOf(SprefUtil.getInt(context,SprefUtil.C_COUNT, 0)));
		initRebootTime();
		
	}
	
	public void isGooglePlayAccountFileAccessible(){
		if(SprefUtil.getInt(context, SprefUtil.C_MARKET, MARKET.MARKET_BAIDU.value())==MARKET.MARKET_GP.value()){
			if(MyFileUtil.getGoogleAccountCount()==0||MyFileUtil.getAccountIndex()>=MyFileUtil.getGoogleAccountCount()){
				mainActivityView.toast(context.getString(R.string.google_market_no_more_account));//googel play 没有可用账户
				AlertDialog dialog = new AlertDialog.Builder(context)
				.setTitle(context.getString(R.string.please_add_account_file))
				.setPositiveButton(context.getString(R.string.ok),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								openChoseMarket();
								dialog.dismiss();
							}
						}).create();
		dialog.show();
			}
		}
			
	}

	/**
	 * 获取apk列表
	 * @return
	 */
	public List<AppInfo> loadAllApk() {
		return dbDao.loadAll();
	}

	/**
	 * 初始化提示音，和查看权限
	 */
	private void getVoicePermissionPrepare() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				MyFileUtil.copySoundFile(context,GlobalConstants.SOUND_ROOT_NAME, GlobalConstants.SOUND_ROOT_NAME);
				MyFileUtil.copySoundFile(context,GlobalConstants.SOUND_GOOGLE_NAME, GlobalConstants.SOUND_GOOGLE_NAME);
				ActivityUtils.showPermission(context);
			}
		}).start();
		if (!CmdUtil.isRoot()) {
			AlertDialog dialog = new AlertDialog.Builder(context)
					.setTitle(context.getString(R.string.need_root_permission))
					.setPositiveButton(context.getString(R.string.ok),
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
								}
							}).create();
			dialog.show();
		}
	}
	/*-------------------------------------选择应用市场----------------------------------------------*/
	/**
	 * 跳转到应用市场选择
	 */
	public void openChoseMarket() {
		((MainActivity)context).startActivityForResult(new Intent(context, ChoseMarketActivity.class), GlobalConstants.requestMarket);
	}
	/**
	 * 设置选择的应用市场提示
	 */
	public void getTvChooseMarket() {
		MARKET market;
		int value=SprefUtil.getInt(context, SprefUtil.C_MARKET, MARKET.MARKET_BAIDU.value());
		market=MARKET.valueOf(value);
		String marketTips = "";
		switch (market) {
		case MARKET_BAIDU:
			marketTips=context.getString(R.string.market_baidu);
			break;
		case MARKET_360:
			marketTips=context.getString(R.string.market_360);
			break;
		case MARKET_YYB:
			marketTips=context.getString(R.string.market_yyb);
			break;
		case MARKET_WDJ:
			marketTips=context.getString(R.string.market_wdj);
			break;
		case MARKET_PP:
			marketTips=context.getString(R.string.market_pp_assistant);
			break;
		case MARKET_AZ:
			marketTips=context.getString(R.string.market_anzhi);
			break;
		case MARKET_MU:
			marketTips=context.getString(R.string.market_miu);
			break;
		case MARKET_GP:
			marketTips=context.getString(R.string.market_gp);
			break;
		default:
			break;
		}
		mainActivityView.setTvChooseMarket(String.format(context.getString(R.string.choose_market_tips2), marketTips));
		if(SprefUtil.getBool(context, SprefUtil.C_GOOGLE_VPN, false)){
			if(market.value()==MARKET.MARKET_GP.value()){
				EasyOperateClickUtil.setAutoVpnFlag(context,EasyOperateClickUtil.AUTO_VPN);
			}else {
				EasyOperateClickUtil.setAutoVpnFlag(context,EasyOperateClickUtil.DO_NOT_AUTO_VPN);
			}
		}
		else {
			EasyOperateClickUtil.setAutoVpnFlag(context,EasyOperateClickUtil.DO_NOT_AUTO_VPN);
		}
		mainActivityView.setAutoVPNChecked();
	}
	/*----------------------------------------选择下载应用--------------------------------------------------*/
	/**
	 *  跳转到APK选择
	 */
	public void openChoseAPK() {
		((MainActivity)context).startActivityForResult(new Intent(context, ChoseDownAppActivity.class), GlobalConstants.requestAPK);
	}
	/**
	 * 设置选择的Apk信息提示
	 */
	public void getTvApkTips(){
		int selection=SprefUtil.getInt(context, SprefUtil.C_SELECTION, -1);
		Logger.i("selection============================="+selection);
		List<AppInfo> list=loadAllApk();
		if(list.size()<=0||selection<0){
			mainActivityView.setTvChoseApk(context.getString(R.string.choose_to_add_apk));
		}else {
			mainActivityView.setTvChoseApk(String.format(context.getString(R.string.current_pn_tips), list.get(selection).getPackageName()));
		}
	}
	/*--------------------------------------------下载百分比--------------------------------------------------------------*/
	/**
	 * 设置下载百分比显示提示
	 */
	public void getTvDownPercentTips() {
		int value=SprefUtil.getInt(context, SprefUtil.C_MARKET, MARKET.MARKET_BAIDU.value());
		if(MARKET.valueOf(value)==MARKET.MARKET_WDJ){
			mainActivityView.setTvDownPercentTips(context.getString(R.string.wdj_no_half));
		
		}else {
			int percent = EasyOperateClickUtil.getHalfDownloadValue(context);
			mainActivityView.setTvDownPercentTips(String.format(context.getString(R.string.current_percent_tips), percent));
		}
	}
	/**
	 * 设置下载百分比输入监控
	 * 
	 * @param s
	 */
	public void setEtPercentTextWatcher(Editable s) {
		if (!TextUtils.isEmpty(s.toString())) {
			try {
				int per = Integer.parseInt(s.toString());
				if (per > 100) {
					mainActivityView.setEtPercent(
							GlobalConstants.FLAG_ET_ERROT,
							context.getString(R.string.set_percent_err_tips2));
				}
			} catch (Exception e) {

			}
		}
	}
	/**
	 * 设置下载百分比按钮点击事件
	 */
	public void btnPercentSetClick() {
		if (TextUtils.isEmpty(mainActivityView.getEtPercent())
				|| mainActivityView.getEtPercent().equals("0")
				|| mainActivityView.getEtPercent().equals("00")) {
			mainActivityView.toast(context
					.getString(R.string.set_percent_err_tips));
		} else if (Integer.parseInt(mainActivityView.getEtPercent()) > 100) {
			mainActivityView.setEtPercent(GlobalConstants.FLAG_ET_ERROT,
					context.getString(R.string.set_percent_err_tips2));
			mainActivityView.toast(context
					.getString(R.string.set_percent_err_tips2));
		} else {
			int value = Integer.parseInt(mainActivityView.getEtPercent());
			EasyOperateClickUtil.setHalfDownloadValue(context, value);
			mainActivityView.toast(context.getString(R.string.set_percent_ok));
			getTvDownPercentTips();
		}
	}
	/*------------------------------------------设置等待安装时间-------------------------------------------------------*/
	/**
	 * 设置安装等待时间等待事件
	 */
	public void btnInstallPerioSetClikc() {
		int installTime = 0;
		if (TextUtils.isEmpty(mainActivityView.getEtInstallPeriod())) {
			mainActivityView.setEtInstallPeriod(GlobalConstants.FLAG_ET_ERROT,
					context.getString(R.string.set_install_error_tips));
			return;
		}
		try {
			installTime = Integer.parseInt(mainActivityView
					.getEtInstallPeriod());
		} catch (Exception e) {
			// TODO: handle exception
		}
		SprefUtil.putInt(context, SprefUtil.C_INSTALL_TIME, installTime);
		mainActivityView.setTvInstallPeriodTips();
	}
	
	/**
	 * 下载按钮点击跳转到应用市场
	 */
	public void jumpMarket() {
		int selection = SprefUtil.getInt(context, SprefUtil.C_SELECTION, 0);
		List<AppInfo> list = loadAllApk();
		if(selection<0||list.size()<=0){
			mainActivityView.toast(context.getString(R.string.choose_to_add_apk));
			return;
		}
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
	public  void openMarket(Context context,String packageName,String url){
		Uri localUri = Uri.parse("market://details?id=" + url);
		Intent localIntent = new Intent("android.intent.action.VIEW", localUri);
		localIntent.setPackage(packageName);
		// localIntent.addCategory("android.intent.category.BROWSABLE");//可不用加
		// localIntent.addCategory("android.intent.category.DEFAULT");//可不用加
		localIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		try {
			context.startActivity(localIntent);
		} catch (Exception e) {
			mainActivityView.toast(context.getString(R.string.open_market_err));
			Logger.e(e.toString());
		}
		Logger.i("--------------------------------------------------------------------------");
	}
	/*--------------------------------ToggleButton 设置---------------------------------------------------*/
	/**
	 * 自动跳转到应用市场
	 * 
	 * @param isChecked
	 */
	public void tbTomarketCheck(boolean isChecked) {
		SprefUtil.putBool(context, SprefUtil.C_AUTO_MARKET, isChecked);
	}

	public void tbToHdjHookCheck(boolean isChecked) {
		SprefUtil.putBool(context, SprefUtil.C_AUTO_HOOK, isChecked);
	}

	public void tbAutoClickChecked(boolean isChecked) {
		EasyOperateClickUtil.setAutoClickFlag(context,
				isChecked ? EasyOperateClickUtil.AUTOCLICK
						: EasyOperateClickUtil.DO_NOT_AUTOCLICK);
	}

	public void tbAutoRebootChecked(boolean isChecked) {
		EasyOperateClickUtil.setAutoRebootFlag(context,
				isChecked ? EasyOperateClickUtil.AUTOREBOOT
						: EasyOperateClickUtil.DO_NOT_AUTOREBOOT);
	}
	
	public void tbAutoVpnChecked(boolean isChecked) {
		SprefUtil.putBool(context, SprefUtil.C_GOOGLE_VPN, isChecked);
	}
	
	/**
	 * 设置重启提醒
	 */
	public void initRebootTime(){
		int rebootCount =MyFileUtil.getRebootTimes();
		if(rebootCount>0){
			mainActivityView.setRebootTime(String.format(context.getString(R.string.reboot_recode_tips), rebootCount));
		}else {
			mainActivityView.setRebootTime(context.getString(R.string.reboot_recode_tips_no_reboot));
		}
		
	}
	
	/*---------------------------------------------------------------------------------------------------------*/
	/**
	 * 清除下载记录和重启纪录
	 */
	public void clearCount() {
		SprefUtil.putInt(context, SprefUtil.C_COUNT, 0);
		MyFileUtil.removeRebootRecord();
		initRebootTime();
		mainActivityView.setTitles(SprefUtil.getInt(context, SprefUtil.C_COUNT, 0) + "");
	}
	/**
	 * TTS测试
	 */
	public void ttsRootTest() {
		TTSUtil.getInstance(context).ringReboot();
		Intent in = new Intent(context, AlertActivity.class);
		in.putExtra(GlobalConstants.ALERT_ACTIVITY_FLAG, GlobalConstants.ALERT_ACTIVITY_NO_ROOT);
		in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(in);
	}
	/**
	 * ttsGoogel
	 */
	public void ttsGoogelTest() {
		TTSUtil.getInstance(context).ringGoogle();
		Intent in = new Intent(context, AlertActivity.class);
		in.putExtra(GlobalConstants.ALERT_ACTIVITY_FLAG, GlobalConstants.ALERT_ACTIVITY_NO_ACCOUNT);
		in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(in);
	}

	

	
	

	
}
