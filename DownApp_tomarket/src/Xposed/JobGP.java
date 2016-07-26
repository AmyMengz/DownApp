package Xposed;

import java.util.Map;

import com.hdj.downapp_market.activity.SetClickUtil;
import com.mz.utils.EasyOperateClickUtil;
import com.mz.utils.MyFileUtil;
import com.mz.utils.GlobalConstants;
import com.mz.utils.Logger;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import Xposed.JobAbstract;
import Xposed.MethodInt;

public class JobGP extends JobAbstract {
	boolean removed=false;
	boolean nocrarChoose=false;
	private View continueBtn = null;
	private static JobGP instance;
	private View installBtn = null;
	private View acceptBtn = null;
	private int cannotInstallTimes = 0;
	private JobGP() {
		// TODO Auto-generated constructor stub
	}
	public static JobGP getGP(){
		if(instance==null){
			synchronized (JobGP.class) {
				if(instance==null){
					instance=new JobGP();
				}
			}
		}
		return instance;
	}
	@Override
	public void handleMethod(String packageName, ClassLoader classLoader) {
		gpInstall_click=false;
		gpAccountAdd=false;
		nocrarChoose=false;
		cannotInstallTimes=0;
		removed=false;
		continueBtn =null;
		acceptBtn =null;
		//AppsPermissionsActivity
		HookMethod(View.class, "refreshDrawableState", packageName,
				MethodInt.GP_DOWN);
		HookMethod(View.class, "getMeasuredWidth", packageName,
				MethodInt.GP_TV_PROGRESS);
		HookMethod(TextView.class, "setText", packageName,
				MethodInt.GP_TV_PROGRESS,CharSequence.class);
		
		HookMethod(View.class, "refreshDrawableState", packageName,
				MethodInt.GP_TV_INSTALL);
		HookMethod(TextView.class, "isSuggestionsEnabled", packageName,
				MethodInt.GP_TV_INSTALL);

		HookMethod(View.class, "dispatchTouchEvent", packageName,
				MethodInt.DIS, MotionEvent.class);
		HookMethod("com.google.android.finsky.activities.AppsPermissionsActivity", "onCreate", classLoader,packageName, MethodInt.GP_ACCOUNT_DIALOG,Bundle.class);
	}
	//�����Ϣ
	/**
	 * ���ܣ�2131820862  ;��װ2131820959  ;����2��2131821105   ;12%   2131820974   ;ж��2131820953
	 */
	@Override
	public void handleAftreMethod(String packageName,MethodHookParam param, int type) {
		// TODO Auto-generated method stub
		super.handleAftreMethod(packageName,param, type);
		Object obj;
		switch (type) {
		case MethodInt.GP_DOWN://-16908313=====ȷ��
			obj=param.thisObject;
			if( obj instanceof TextView){//-2131820959=====��װ
				TextView text = (TextView) obj;
				if((text.getId()==2131820950||text.getId()==2131820959)&&!TextUtils.isEmpty(text.getText())&&"��װ".equals(text.getText().toString())){
					Logger.i("GOOGLE��װ================"+text.getText()+text.getId()+"::visibility::"+text.getVisibility());
					//if(gpInstall_click) return;
//					gpInstall_click=true;
					if(text.getVisibility()==View.VISIBLE){
						installBtn=text;
						boolean res=SetClickUtil.setClick(text);
						boolean res2=SetClickUtil.setSimulateClick(text, 0, 0);
						Logger.i("GOOGLE��װ================"+text.getText()+text.getId()+"  "+text.getWidth()+" "+res+"  res2:"+res2+param.method);
				
					}
				}//2131820862=====����=  
				if((text.getId()==2131820858||text.getId()==2131820862)&&!TextUtils.isEmpty(text.getText())&&"����".equals(text.getText().toString())&&text.getWidth()>0){
					boolean res=SetClickUtil.setClick(text);
					Logger.i("GOOGLE����================"+text.getText()+text.getId()+"  "+text.getWidth()+" "+res+"  "+param.method);
				}
				
				if(text.getId()==16908313&&!TextUtils.isEmpty(text.getText())&&"����".equals(text.getText().toString())&&text.getWidth()>0){
					boolean res=SetClickUtil.setClick(text);
					Logger.i("GOOGLE����================"+text.getText()+text.getId()+"  "+text.getWidth()+" "+res+"  "+param.method);
				}
				if(text.getId()==16908313&&!TextUtils.isEmpty(text.getText())&&"ȷ��".equals(text.getText().toString())&&text.getWidth()>0){
					boolean res=SetClickUtil.setClick(text);
					boolean res2=SetClickUtil.setClick(installBtn);
					cannotInstallTimes++;
					Logger.i("GOOGLE=�޷�����==ȷ��============="+text.getText()+text.getId()+"  "+" "+res+" res2: "+res2+"  cannotInstallTimes: "+cannotInstallTimes+"  "+param.method);
					if(cannotInstallTimes>=10){
						SetClickUtil.recodeUselessAccount(text.getContext());
//						SetClickUtil.openHook(text.getContext());
					}
				}
				//-2131821105=====����
				if((text.getId()==2131821093||text.getId()==2131821105)&&!TextUtils.isEmpty(text.getText())&&"����".equals(text.getText().toString())/*&&text.getWidth()>0*/){
					boolean res=SetClickUtil.setClick(text);
					acceptBtn=text;
					Logger.i("GOOGLE����222222================"+text.getText()+text.getId()+"  "+"  "+res+"  "+param.method);
				}
				//����˻�ʱ
				if(text.getId()==2131820728&&"������".equals(text.getText().toString())){
					ViewGroup parent=(ViewGroup) text.getParent();
					View view1=parent.getChildAt(0);
					if( view1 instanceof RadioButton){
						RadioButton checkBox = (RadioButton) view1;
						Logger.i("�����˲����˲����˲�checkBox========"+checkBox.getText()+"   "+checkBox.isChecked());
						if(checkBox.isChecked()){
							nocrarChoose=true;
							if(continueBtn!=null){
								boolean res=SetClickUtil.setClick(continueBtn);
								Logger.i("�����˲����˲�����=====continueBtn========"+continueBtn+"   "+res);
							}
						}
					}
					if(!nocrarChoose){
						boolean res=SetClickUtil.setSimulateClick(parent, 0, 0);
						Logger.i("�����˲����˲����˲����˲����˲�����========="+text.getText()+res);
					}
				}
				if(text.getId()==2131820862&&"����".equals(text.getText().toString())){
					continueBtn = text;
					Logger.i("����������������=============="+text.getText()+text.getId()+nocrarChoose+"   continuebtn---"+continueBtn);
					
					if(nocrarChoose){
						boolean res=SetClickUtil.setClick(text);
						Logger.i("����������������=============="+text.getText()+text.getId()+"  "+text.getWidth()+"  "+res+"  "+param.method);
						if(res) nocrarChoose=false;
					}
				}
			}
			break;
		case MethodInt.GP_TV_PROGRESS:
			obj=param.thisObject;
		
			if( obj instanceof TextView){
				TextView text = (TextView) obj; //���� 
				int halfValue = EasyOperateClickUtil.getHalfDownloadValue(text.getContext());
				if (halfValue == 100)
					return;
				if((text.getId()==2131820960||text.getId()==2131820974)&&!TextUtils.isEmpty(text.getText())){
					Logger.i("======"+text.getText()+"  "+param.method);
					String proStr=text.getText().toString();
					if(proStr.contains("%")){
						int index = proStr.indexOf("%");
						float progress =Float.parseFloat(proStr.substring(0, index)) ;
						Logger.i("======progress======="+progress);
						if(progress>=halfValue){
							SetClickUtil.recodeUsedAccount(text.getContext());
//							SetClickUtil.clearAccountAndToHook(text.getContext());
						}
					}
				}
			}
			break;
		case MethodInt.GP_TV_INSTALL:
			obj=param.thisObject;
			
			if( obj instanceof TextView){
				TextView text = (TextView) obj; //2131821757
				if(text.getId()==2131821722||text.getId()==2131821757/*&&!TextUtils.isEmpty(text.getText())&&"����".equals(text.getText().toString())*/){
					boolean res=SetClickUtil.setClick(text);
					Logger.i("GOOGLE����================"+text.getText()+text.getId()+"  "+text.getWidth()+"  "+res+"  "+param.method);
				}
				if((text.getId()==2131820944||text.getId()==2131820953)&&!TextUtils.isEmpty(text.getText())&&"ж��".equals(text.getText().toString())&&text.getVisibility()==View.VISIBLE){
					Logger.i("GOOGLE ж��============="+text.getText()+text.getId()+text.getWidth()+"  "+text.getVisibility()+"  "+param.method);
					SetClickUtil.recodeUsedAccount(text.getContext());
//					SetClickUtil.clearAccountAndToHook(text.getContext());
				}
				if(!TextUtils.isEmpty(text.getText())&&"�޷�����Ӧ��".equals(text.getText().toString())){
					Logger.i("GOOGLE�޷�����Ӧ��============="+text.getText()+text.getId()+text.getWidth()+"  "+text.getVisibility()+"  "+param.method);
//					if(EasyOperateClickUtil.getAutoClickFlag(text.getContext())==EasyOperateClickUtil.AUTOCLICK){
//						SetClickUtil.clearAccountAndToHook(text.getContext());
//					}
				}
				if("���ӳ�ʱ".equals(text.getText().toString())){
					Logger.i("GOOGLE���ӳ�ʱ============="+text.getText());
					if(EasyOperateClickUtil.getAutoClickFlag(text.getContext())==EasyOperateClickUtil.AUTOCLICK){
						SetClickUtil.recodeUselessAccount(text.getContext());
//						SetClickUtil.clearAccountAndToHook(text.getContext());
					}
				}
			}
			if(obj instanceof CheckBox){
				CheckBox checkBox = (CheckBox)obj;
				if("��ʹ��WLAN����".equals(checkBox.getText().toString())){
					if(checkBox.isChecked()){
						checkBox.performClick();
					}
				}
			}
			break;
		case MethodInt.GP_ACCOUNT_DIALOG:
			obj=param.thisObject;
			Logger.i("---------GP_ACCOUNT_DIALOG------------"+obj.getClass().toString()+"--------"+param.method);
			if(obj!=null&&obj.getClass().toString().equals("class com.google.android.finsky.activities.AppsPermissionsActivity")){
				Activity a=(Activity)obj;
				
				Button v=(Button) a.findViewById(2131821093);
				if(v==null){
					v=(Button) a.findViewById(2131821105);
				}if(v!=null){
					v.refreshDrawableState();
					boolean res=SetClickUtil.setSimulateClick(v, 0, 0);
					v.refreshDrawableState();
					Logger.i("---------GP_ACCOUNT_DIALOG-----------v-"+v.getClass()+"-------"+"   "+v.getId()+"  ��"+v.getText().toString()+"��  "+v.isClickable()+" res: "+res);
				}
				if(EasyOperateClickUtil.getAutoClickFlag(v.getContext())==EasyOperateClickUtil.AUTOCLICK){
						Message msg = handler.obtainMessage();
						msg.obj=a;
						msg.what=MethodInt.GP_ACCOUNT_DIALOG;
						handler.sendMessageDelayed(msg, 10*1000);
				}
			}
			break;
		default:
			break;
		}
	}

}
