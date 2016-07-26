package Xposed;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.Key;
import java.util.Map;

import javax.crypto.Cipher;

import com.google.android.gsf.loginservice.GLSUser.GLSSession;
import com.mz.utils.MyFileUtil;
import com.mz.utils.GlobalConstants;
import com.mz.utils.Logger;

import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.OnAccountsUpdateListener;
import android.app.Activity;
import android.content.ContentResolver;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;

public class JobGPLogin2 extends JobAbstract{
	private static JobGPLogin2 instance;
	private JobGPLogin2() {
		// TODO Auto-generated constructor stub
	}
	public static JobGPLogin2 getGpLogin(){
		if(instance==null){
			synchronized (JobGPLogin2.class) {
				if(instance==null){
					instance=new JobGPLogin2();
				}
			}
		}
		return instance;
	}
int count=0;
	@Override
	public void handleMethod(String packageName, ClassLoader classLoader) {
		count=0;
		HookMethod(View.class, "dispatchTouchEvent", packageName,
				MethodInt.DIS, MotionEvent.class);
		
//		HookMethod(AccountManager.class, "getAccountsByType", packageName,
//				MethodInt.GP_ACCOUNT_LOGIN, String.class);

		//		HookMethod(AccountManager.class, "addAccountExplicitly", packageName,
//				MethodInt.GP_ACCOUNT_LOGIN3, Account.class,String.class,Bundle.class);
//		
//		
//		HookMethod("com.google.android.gsf.loginservice.PasswordEncrypter", "encryptPassword", classLoader, packageName, MethodInt.GP_ACCOUNT_LOGIN4, ContentResolver.class,String.class,String.class);
////		HookMethod(AccountManager.class, "setAuthToken",  packageName, MethodInt.GP_ACCOUNT_LOGIN6,Account.class,  String.class, String.class);
////		HookMethod("com.google.android.gsf.Gservices", "getString", classLoader, packageName, MethodInt.GP_ACCOUNT_LOGIN6, ContentResolver.class,String.class);
////		HookMethod(Base64.class, "encodeToString", packageName, MethodInt.GP_ACCOUNT_LOGIN6,byte[].class,int.class);
//		HookMethod("com.google.android.gsf.loginservice.GLSUser","addAccount", classLoader , packageName,
//				MethodInt.GP_ACCOUNT_LOGIN5,boolean.class, String.class);
//		
//		HookMethod("com.google.android.gsf.loginservice.GLSUser","firstSave", classLoader , packageName,
//				MethodInt.GP_ACCOUNT_LOGIN7, boolean.class, String.class, String.class, String.class, boolean.class);
//		HookMethod("com.google.android.gsf.loginservice.GLSUser","addAccount", classLoader , packageName,
//				MethodInt.GP_ACCOUNT_LOGIN6, GLSSession.class,boolean.class,boolean.class,boolean.class,String.class);
//		
//		HookMethod("com.google.android.gsf.loginservice.GLSUser","processTokenResponse", classLoader , packageName,
//				MethodInt.GP_ACCOUNT_LOGIN6, Map.class, boolean.class);
////		HookMethod("com.google.android.gsf.login.LoginActivityTask","start", classLoader , packageName,
////				MethodInt.GP_ACCOUNT_LOGIN8);
//		HookMethod("com.google.android.gsf.loginservice.GLSUser","getAuthtokenRaw", classLoader , packageName,
//				MethodInt.GP_ACCOUNT_LOGIN8,String.class, int.class, GLSSession.class, Bundle.class, boolean.class, String.class, boolean.class);
		
		
		
		
	}
	@Override
	public void handleAftreMethod(String packageName, MethodHookParam param,
			int type) {
		// TODO Auto-generated method stub
		super.handleAftreMethod(packageName, param, type);
		Object obj;
		switch (type) {
//		case MethodInt.GP_ACCOUNT_LOGIN:
//			Logger.i("======Login==================GP_ACCOUNT_LOGIN");
//			obj=param.thisObject;
//			Object object=param.getResult();
//			AccountManager accountManager = (AccountManager) obj;
//			if(param.getResult() instanceof Account[]){
//				Account[] accounts=(Account[]) param.getResult();
//				for (int i = 0; i < accounts.length; i++) {
//					Account account=accounts[i];
////					if(removed) removed=true;
////					removed=true;
////					accountManager.removeAccount(account, null, null);
//					Logger.i("======Login==================account["+i+"]================="+account.name+ "  "+account.type+"   "+accountManager.getPassword(account));
//				}
//			}
//			if(obj instanceof AccountManager){
//				Logger.i("ACCOUNT****Login**************************************-count-----"+count);
//				if(count>100) return;
//				count++;
//				Map<String,String> map=MyFileUtil.getAccount();
//				if(map!=null){
//					String email=map.get(GlobalConstants.MAP_EMAIL);
//					String password=map.get(GlobalConstants.MAP_PASS);
//					Logger.i("ACCOUNT****Login*****************************************************-----------email-------"+email+"======password==="+password);
//
//					Account acc = new Account(email, "com.google");
//					Bundle bundle=new Bundle();
//					bundle.putInt("flag", 1);
//					bundle.putString("services", "hist,mail");
//					Logger.i("ACCOUNT****Login*==bundle=="+bundle);
//					if(accountManager.addAccountExplicitly(acc, password, bundle)) ;
//						Logger.i("ACCOUNT****Login*=========acc================="+acc.name+ "  "+acc.type+"   "+count+ "  pass22:"+accountManager.getPassword(acc)+":");
//				}
//			}
//			break;
//		case MethodInt.GP_ACCOUNT_LOGIN6:
//			obj=param.thisObject;
//			Object[] args3=param.args;
//			for (int i = 0; i < args3.length; i++) {
//				Logger.e("-666666666666--------------args6["+i+"]-------"+obj+"        "+args3[i]+"    "+param.method+"    "+param.getResult()+"  ");
////			if(args3[i].getClass().toString().equals("class com.google.android.gsf.loginservice.GLSUser$GLSSession")){
////				try{
////				Class<?> ClassSession=args3[i].getClass();
////				Field[] fields=ClassSession.getDeclaredFields();
////				for (int j = 0; j < fields.length; j++) {
////					Field field= fields[j];
////					field.setAccessible(true);
////					Logger.e("--------66666666666=========fields["+j+"]==="+field+"======="+field.get(args3[i]));
////				}
////				}catch(Exception e){
////					Logger.e("--------66666666666=EEEEEEEEEEee========"+e);
////				}
////			}
//			}
////			try {
////				Class<?> clazz=Class.forName("com.google.android.gsf.loginservice.BaseActivity");
////				Field[] fields =clazz.getDeclaredFields();
////				for (int i = 0; i < fields.length; i++) {
////					Field field=fields[i];
////					field.setAccessible(true);
////					Object o=clazz.newInstance();
////					Logger.e("-66666666666666--------------fields["+i+"]-------"+field+"    "+field.get(o));
////				}
////			} catch (Exception e) {
////				Logger.e("-66666666EEEEEEEEEEEEEEEEEEEEEEEEE"+e);
////				e.printStackTrace();
////			}
//			break;
//		case MethodInt.GP_ACCOUNT_LOGIN:
//			obj=param.thisObject;
//			AccountManager accountManager = (AccountManager) obj;
//			Object object=param.getResult();
//			if(param.getResult() instanceof Account[]){
//				Account[] accounts=(Account[]) param.getResult();
//				for (int i = 0; i < accounts.length; i++) {
//					Account account =accounts[i];
//					Logger.i("-----------账户信息列表Account=================="+account.name+ "  "+account.type+ " :: "+accountManager.getPassword(account)
//							+"   ");
//				}	
//			}
//			break;
//		case MethodInt.GP_ACCOUNT_LOGIN3:
//			obj=param.thisObject;
//Logger.i("DOWNAPP23","^^^^^^^^^^^添加账户^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");			
//			Object[] args=param.args;
//			for (int i = 0; i < args.length; i++) {
//				Logger.i("DOWNAPP23","---添加账户--------args["+i+"]--------------"+args[i]+"    "+param.method);
//				Logger.e("----添加账户-------args["+i+"]--------------"+args[i]+"    "+param.method);
//			}
//Logger.i("DOWNAPP23","^^^^^^^^^^添加账户^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
//			break;
//		case MethodInt.GP_ACCOUNT_LOGIN4:
//			obj=param.thisObject;
//			Logger.i("DOWNAPP24","^^^^^^^密码加密^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");			
//			Object[] args2=param.args;
//			for (int i = 0; i < args2.length; i++) {
//				Logger.i("DOWNAPP24","-------密码加密----args2["+i+"]--------------"+args2[i]+"    "+param.method+"    "+param.getResult());
//				Logger.e("encryptPassword----密码加密-------args2["+i+"]--------------"+args2[i]+"    "+param.method+"    "+param.getResult());
//			}
//			Logger.i("DOWNAPP24","^^^^^^^^密码加密^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
//			break;
//		
//		case MethodInt.GP_ACCOUNT_LOGIN5:
//			obj=param.thisObject;
//			Object[] args5=param.args;
//			Logger.e("==================GLSUser---addAccount===========================================================================================");
//			Logger.e("===================="+obj.getClass());
//			for (int i = 0; i < args5.length; i++) {
//				Logger.e("-5555555--------------args3["+i+"]-------"+obj.getClass()+"        "+args5[i]+"    "+param.method+"    "+param.getResult());
//			}
//			try{
//			Class<?> class1=obj.getClass();
//			Field[] fields=class1.getDeclaredFields();
//			for (int i = 0; i < fields.length; i++) {
//				Field field=fields[i];
//				field.setAccessible(true);
//				Logger.e("-5555555================field["+i+"]"+field+"======"+field.get(obj));
//			};
//			/*Method[] methods=class1.getDeclaredMethods();
//			for (int i = 0; i < methods.length; i++) {
//				Logger.e("-5555555================methods["+i+"]"+methods[i]+"======");
//			}*/
//			}catch (Exception e) {
//				Logger.e("EEEEEEEEEEEEEE================"+e);
//			}
//			break;
//		case MethodInt.GP_ACCOUNT_LOGIN7:
//			obj=param.thisObject;
//			Object[] args7=param.args;
//			Logger.e("==================GLSUser---addAccount===========================================================================================");
//			Logger.e("===================="+obj.getClass());
//			for (int i = 0; i < args7.length; i++) {
//				Logger.e("-777777777777--------------args7["+i+"]-------"+obj.getClass()+"        "+args7[i]+"    "+param.method+"    "+param.getResult());
//			}
//			try {
//				Class<?> clazz=Class.forName("com.google.android.gsf.loginservice.BaseActivity");
//				Field[] fields =clazz.getDeclaredFields();
//				for (int i = 0; i < fields.length; i++) {
//					Field field=fields[i];
//					field.setAccessible(true);
//					Object o=clazz.newInstance();
//					Logger.e("-777777777777--------------fields["+i+"]-------"+field+"    ");
//					Logger.e("-777777777777--------------fields["+i+"]-------"+field+"    "+field.get(o));
//					
//				}
//			} catch (Exception e) {
//				Logger.e("-777777777777EEEEEEEEEEEEEEEEEEEEEEEEE"+e);
//				e.printStackTrace();
//			}
//			
//			break;
//		case MethodInt.GP_ACCOUNT_LOGIN8:
//
//			obj=param.thisObject;
//			Object[] args8=param.args;
//			for (int i = 0; i < args8.length; i++) {
//				Logger.e("-8888888888--------------args8["+i+"]-------"+obj+"        "+args8[i]+"    "+param.method+"    "+param.getResult()+"  ");
//			}
//			break;
		default:
			break;
		}
	}
	
}
