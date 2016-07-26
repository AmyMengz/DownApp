package Xposed;


import com.mz.utils.GlobalConstants;
import com.mz.utils.Logger;
import android.content.pm.ApplicationInfo;
import android.text.TextUtils;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class HookMain implements IXposedHookLoadPackage {

	static JobInterface job=null;
	static JobInterface jobMI=null;
	@Override
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
		String packageName = lpparam.packageName;
		String className=null;
		if(lpparam.appInfo!=null&&lpparam.appInfo.className!=null){
			className=lpparam.appInfo.className;
		}
		ApplicationInfo appInfo = lpparam.appInfo;
		if (appInfo == null || TextUtils.isEmpty(packageName))
			return;
		if (!(packageName.equals(GlobalConstants.BAIDU_PN)
				|| packageName.equals(GlobalConstants._360_PN)
				|| packageName.equals(GlobalConstants.YYB_PN)
				|| packageName.equals(GlobalConstants.PP_PN)
				|| packageName.equals(GlobalConstants.WDJ_PN) 
				|| packageName.equals(GlobalConstants.HDJ_GIT_PN)
				|| packageName.equals(GlobalConstants.XPOSED_INSTALLER_PN)
				||packageName.equals(GlobalConstants.AZ_PN)
				||packageName.equals(GlobalConstants.MU_PN)
				||packageName.equals(GlobalConstants.MU_DOWN_PN)
				||packageName.equals(GlobalConstants.GP_PN)
				||packageName.equals(GlobalConstants.VPN_PN)
				||packageName.equals(GlobalConstants.GP_PN_Login)
				||packageName.equals(GlobalConstants.PROXY_PN)
				||packageName.equals(GlobalConstants.VPN_DIALOG_PN))) {
			return;
		}
//		android/android.accounts.ChooseTypeAndAccountActivity
		Logger.i("---------£¤£¤£¤£¤£¤£¤£¤£¤£¤£¤£¤---packageName**************" + packageName+"==="+className+"--------------------------");
		job=JobCreator.createJob(lpparam);
		if(job!=null){
			job.handleMethod(lpparam.packageName, lpparam.classLoader);
		}
		
		if(packageName.equals(GlobalConstants.MU_DOWN_PN)){
			jobMI = JobMUDownloader.getMULoader();
			if(jobMI!=null){
				jobMI.handleMethod(lpparam.packageName, lpparam.classLoader);
			}
			
		}
	}
	public static class AppInfos_XC_MethodHook extends XC_MethodHook {
		int type;
		String packageName;

		public AppInfos_XC_MethodHook(int type, String packageName) {
			this.packageName = packageName;
			this.type = type;
		}

		@Override
		protected void afterHookedMethod(MethodHookParam param)
				throws Throwable {
			if(job!=null)
				job.handleAftreMethod(packageName,param, type);
			if(jobMI!=null)
				jobMI.handleAftreMethod(packageName,param, type);
			
		}
	}

}
