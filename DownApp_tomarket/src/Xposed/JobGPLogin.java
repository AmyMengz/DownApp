package Xposed;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.Key;
import java.util.Map;

import javax.crypto.Cipher;

import u.aly.bt;

import com.google.android.gsf.loginservice.GLSUser.GLSSession;
import com.hdj.downapp_market.activity.SetClickUtil;
import com.mz.utils.EasyOperateClickUtil;
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
import android.text.TextUtils;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class JobGPLogin extends JobAbstract{
	private static JobGPLogin instance;
	long  last = 0 ,now = 0;
	private JobGPLogin() {
		// TODO Auto-generated constructor stub
	}
	public static JobGPLogin getGpLogin(){
		if(instance==null){
			synchronized (JobGPLogin.class) {
				if(instance==null){
					instance=new JobGPLogin();
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
		HookMethod(View.class, "refreshDrawableState", packageName,
				MethodInt.GP_LOGIN_CLICK);
	}
	@Override
	public void handleAftreMethod(String packageName, MethodHookParam param,
			int type) {
		// TODO Auto-generated method stub
		super.handleAftreMethod(packageName, param, type);
		Object obj;
		switch (type) {
		case MethodInt.GP_LOGIN_CLICK:
			obj=param.thisObject;
			if(obj instanceof EditText){
				EditText et = (EditText)obj;
				Map<String, String> map=MyFileUtil.getAccount();
					if(et.getId()==2131427360||et.getId()==2131165223||"电子邮件".equals(et.getText().toString())){
						
						if(map!=null)
						et.setText(map.get(GlobalConstants.MAP_EMAIL));
						Logger.i("===LOGIN===邮箱==="+et.getHint()+"==="+et.getText().toString());
					}
					if(et.getId()==2131427356||et.getId()==2131165219||"密码".equals(et.getHint().toString())){
						
						if(map!=null)
						et.setText(map.get(GlobalConstants.MAP_PASS));
						Logger.i("===LOGIN===密码==="+et.getHint()+"==="+et.getText().toString());
					}
				}
			if(obj instanceof Button){// 和现有 的 id相同
				Button btn= (Button) obj;
				if((btn.getId()==2131427338||btn.getId()==2131165199)&&"".equals(btn.getText().toString())){
					Logger.i("===LOGIN===Button==="+btn.isClickable()+"----:"+btn.getText()+":"+"".equals(btn.getText().toString()));
					if(btn.isClickable()){
							boolean res=SetClickUtil.setClick(btn);
							Logger.i("===LOGIN==Button=next==="+btn.getText().toString()+"===="+res);
					}
				}
//				=====确定
				if(btn.getId()==16908313&&"确定".equals(btn.getText().toString())){
					now = System.currentTimeMillis();
					Logger.i("===LOGIN==确定------==="+btn.getText().toString()+"===last="+last+"  now= "+now);
					if(now-last>1000){
						boolean res=SetClickUtil.setClick(btn);
						Logger.i("===LOGIN===确定==="+btn.getText().toString()+"===="+res);
					}
					last = now;
				}
			}
			//2131427330=====无法登录=
//			-2131821722=====不用了===204
			if(obj instanceof TextView){
				TextView text = (TextView)obj;
				if(!(TextUtils.isEmpty(text.getText()))){
					if("现有".equals(text.getText().toString())){
						Logger.i("===LOGIN===现有==="+text.getText().toString());
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						SetClickUtil.setClick(text);
					}
					if("备份和还原".equals(text.getText().toString())){
						Logger.i("===LOGIN===备份和还原==="+text.getText().toString()+"    "+text.getId());
						EasyOperateClickUtil.setIsotoGoogleStoreFlag(text.getContext(), EasyOperateClickUtil.GOTO_GOOGLE_STORE);
					}
				}
				}
			break;
		default:
			break;
		}
	}
	
}
