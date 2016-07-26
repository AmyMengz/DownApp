package Xposed;

import com.mz.utils.GlobalConstants;

import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class JobCreator {

	public JobCreator() {
		// TODO Auto-generated constructor stub
	}
	public static JobInterface createJob(LoadPackageParam lpparam){
		JobInterface job=null;
		String packageName = lpparam.packageName;
		if (packageName.equals(GlobalConstants.HDJ_GIT_PN)) {
			job = JobHDJHook.getHDJHook();
		}
		if (packageName.equals(GlobalConstants.YYB_PN)) {
			job=JobYYB.getYYB();
		}
		if (packageName.equals(GlobalConstants.WDJ_PN)) {
			job=JobWDJ.getWDJ();
		}
		if (packageName.equals(GlobalConstants.BAIDU_PN)) {
			job=JobBaiDu.getBaiDu();	
		}
		if (packageName.equals(GlobalConstants._360_PN)) {
			job = Job360.get360();	
		}
		if (packageName.equals(GlobalConstants.PP_PN)) {
			job = JobPP.getPP();
		}
		if (packageName.equals(GlobalConstants.AZ_PN)) {
			job = JobAZ.getAZ();
		}
		
		if (packageName.equals(GlobalConstants.MU_PN)) {
			job = JobMU.getMU();
		}
		if(packageName.equals(GlobalConstants.GP_PN)){
			job = JobGP.getGP();
		}
		if(packageName.equals(GlobalConstants.VPN_PN)){
			job = JobVpn.getVpn();
		}
		if(packageName.equals(GlobalConstants.GP_PN_Login)){
			job = JobGPLogin.getGpLogin();
		}
		if(packageName.equals(GlobalConstants.PROXY_PN)){
			job = PoxyJob.getPoxy();
		}
		if(packageName.equals(GlobalConstants.VPN_DIALOG_PN)){
			job = JobVPNConfirm.getVPNConfirm();
		}
		
		return job;
	}
}
