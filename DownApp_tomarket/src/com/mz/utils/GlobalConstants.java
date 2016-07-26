package com.mz.utils;

import java.io.File;

import com.mz.bean.MARKET;

import android.net.Uri;
import android.os.Environment;

public class GlobalConstants {

	public final static String CACHE_DIRECTORY = "downAppMarket" + File.separator + "imei";
	public final static String HDJ_GIT_PN="com.hdjad.github";
	public final static String INSTALL_ACTIVITY="ComponentInfo{com.android.packageinstaller/com.android.packageinstaller.PackageInstallerActivity}";
	public final static String INSTALL_MIUI="ComponentInfo{com.miui.home/com.miui.home.launcher.Launcher}";
	public final static String HDJ_GIT_BROADCAST="com.hdjad.openMarket";
	public final static String GOOGLE_DIALOG="ComponentInfo{com.android.vending/com.google.android.finsky.activities.AppsPermissionsActivity}";
	public final static String GOOGLE_DETAIL="ComponentInfo{com.android.vending/com.google.android.finsky.billing.lightpurchase.LightPurchaseFlowActivity}";

	
//	public final static String VPN_DIALOG="com.android.vpndialogs";
//	public final static String SETTINGS="com.android.settings";
	public final static String BAIDU_PN="com.baidu.appsearch";
	public final static String _360_PN="com.qihoo.appstore";
	public final static String YYB_PN = "com.tencent.android.qqdownloader";
	public final static String WDJ_PN = "com.wandoujia.phoenix2";
	public final static String PP_PN = "com.pp.assistant";
	public final static String AZ_PN = "cn.goapk.market";
	public final static String MU_PN = "com.xiaomi.market";
	public final static String MU_DOWN_PN = "com.android.providers.downloads";
	public final static String GP_PN="com.android.vending";
	public final static String GP_PN_Login="com.google.android.gsf.login";
	public final static String PROXY_PN="me.smartproxy";
	public final static String PROXY_CN="me.smartproxy.ui.MainActivity";
	
	public final static String PROXY_PN2="org.duotai";
	public final static String PROXY_CN2="org.duotai.ui.NewMainActivity";
	
	
	public final static String VPN_DIALOG_PN="com.android.vpndialogs";
	
//	/com.android.vpndialogs.ConfirmDialog
	
	public final static String VPN_PN="com.android.settings";
//	{com.android.providers.downloads/com.android.providers.downloads.activity.PrivacyTipActivity}
	
//	ComponentInfo{com.xiaomi.market/com.xiaomi.market.ui.MarketTabActivity
	
	public final static String DOWN_APP_PN = "com.hdj.downapp_market";
	public final static String XPOSED_INSTALLER_PN = "de.robv.android.xposed.installer";
	public final static String XPOSED_INSTALLER_CN = "de.robv.android.xposed.installer.WelcomeActivity";
	public final static String XPOSED_INSTALLER_CN2 = "de.robv.android.xposed.installer.XposedInstallerActivity";
	public final static String EXTRA_SECTION = "section";
	public final static String FINISH_ON_UP_NAVIGATION = "finish_on_up_navigation";
	
	public final static String EXTRA_PATH = Environment.getExternalStorageDirectory()+File.separator+"007"+File.separator+"file"+File.separator+"DownAppFile";
	public final static String SOUND_ROOT_NAME = "speak.wav";
	public final static String SOUND_GOOGLE_NAME = "speak_googel.wav";
	public final static String RECORD_REBOOT = "reboot.txt";
	public final static String FILE_DES_GOOGLE_ACCOUNT = "Google.txt";
	public final static String FILE_GOOGLE_ACCOUNT_COUNT = "googleCount.txt";
	public final static String FILE_GOOGLE_ACCOUNT_USED_RECODE = "usedAccount.txt";
	public final static String FILE_GOOGLE_ACCOUNT_USELESS_RECODE = "uselessAccount.txt";
	
//	ComponentInfo{de.robv.android.xposed.installer/de.robv.android.xposed.installer.XposedInstallerActivity}
	
	public final static String TAG="DOWNAPP";
	public final static String TAGEX="DOWNAPP_EX";
	public final static boolean ISLOG=true;
	
	
	public final static int NO_ROOT_COUNT=35;
	
	
	
	//intent
	public final static String MARKET_TIPS_PERCENT="market_tips_percent";//豌豆荚不能部分下载
	public final static String APK_LIST_SELECTION="apk_list_selection";
	
	public final static String ALERT_ACTIVITY_FLAG="alert_activity_falg";
	public final static int ALERT_ACTIVITY_NO_ROOT=0;
	public final static int ALERT_ACTIVITY_NO_ACCOUNT=1;
	
	
	//request code
	public final static int requestSD = 0;//ChoseDownAppActivity
	public final static int requestMarket = 1;//MainActivity
	public final static int requestAPK = 2;//MainActivity
	public final static int requestSDText = 3;//ChoseMarketActivity
	
	//setTextError
	public final static int FLAG_ET_ERROT=0;
	public final static int FLAG_ET_NORMAL=1;
	
	
	//MAP
	public final static String MAP_EMAIL="email";
	public final static String MAP_PASS="pass";
	
	
//	com.huodongjia.girl2048"
//	
//	"com.yanse.lianzhu"
//	"com.huodongjia.boy2048"
}
