package Xposed;

import java.util.Map;

import com.hdj.downapp_market.activity.SetClickUtil;
import com.mz.utils.EasyOperateClickUtil;
import com.mz.utils.MyFileUtil;
import com.mz.utils.GlobalConstants;
import com.mz.utils.Logger;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import Xposed.JobAbstract;
import Xposed.MethodInt;

public class JobGP2 extends JobAbstract {
	int count=0;
	boolean removed=false;
	boolean nocrarChoose=false;
	private static JobGP2 instance;
	private JobGP2() {
		// TODO Auto-generated constructor stub
	}
	public static JobGP2 getGP(){
		if(instance==null){
			synchronized (JobGP2.class) {
				if(instance==null){
					instance=new JobGP2();
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
		count=0;
		removed=false;
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
//		HookMethod(View.class, "getMeasuredWidth", packageName,
//				MethodInt.GP_TV_INSTALL);

		HookMethod(View.class, "dispatchTouchEvent", packageName,
				MethodInt.DIS, MotionEvent.class);
//		HookMethod(AccountManager.class, "getAccountsByType", packageName,
//				MethodInt.GP_ACCOUNT, String.class);

		
		//		HookMethod(AccountManager.class, "getAccounts", packageName,
//				MethodInt.GP_ACCOUNT);
		
//		HookMethod(AccountManager.class, "getPassword", packageName,
//				MethodInt.GP_ACCOUNT_LOGIN2, Account.class);

		

	}
	@Override
	public void handleAftreMethod(String packageName,MethodHookParam param, int type) {
		// TODO Auto-generated method stub
		super.handleAftreMethod(packageName,param, type);
		Object obj;
		switch (type) {
		case MethodInt.GP_DOWN:
			obj=param.thisObject;
			if( obj instanceof TextView){//-2131820959=====��װ
				TextView text = (TextView) obj;
				if((text.getId()==2131820950||text.getId()==2131820959)&&!TextUtils.isEmpty(text.getText())&&"��װ".equals(text.getText().toString())){
					if(gpInstall_click) return;
					gpInstall_click=true;
					boolean res=SetClickUtil.setClick(text);
					Logger.i("GOOGLE��װ================"+text.getText()+text.getId()+"  "+text.getWidth()+" "+res+"  "+param.method);
				}//2131820862=====����=
				if((text.getId()==2131820858||text.getId()==2131820862)&&!TextUtils.isEmpty(text.getText())&&"����".equals(text.getText().toString())&&text.getWidth()>0){
					boolean res=SetClickUtil.setClick(text);
					Logger.i("GOOGLE����================"+text.getText()+text.getId()+"  "+text.getWidth()+" "+res+"  "+param.method);
				}
				if(text.getId()==16908313&&!TextUtils.isEmpty(text.getText())&&"����".equals(text.getText().toString())&&text.getWidth()>0){
					boolean res=SetClickUtil.setClick(text);
					Logger.i("GOOGLE����================"+text.getText()+text.getId()+"  "+text.getWidth()+" "+res+"  "+param.method);
				}
				//-2131821105=====����
				if((text.getId()==2131821093||text.getId()==2131821105)&&!TextUtils.isEmpty(text.getText())&&"����".equals(text.getText().toString())&&text.getWidth()>0){
					boolean res=SetClickUtil.setClick(text);
					Logger.i("GOOGLE����222222================"+text.getText()+text.getId()+"  "+text.getWidth()+"  "+res+"  "+param.method);
				}
//����˻�ʱ
				if(text.getId()==2131820728&&"������".equals(text.getText().toString())){
					ViewGroup parent=(ViewGroup) text.getParent();
					View view1=parent.getChildAt(0);
					Logger.i("�����˲����˲����˲����˲����˲�����========="+text.getText()+parent+"  "+ (view1 instanceof CheckBox));
					if( view1 instanceof CheckBox){
						CheckBox checkBox = (CheckBox) view1;
						Logger.i("�����˲����˲����˲�checkBox========"+checkBox.getText()+"   "+checkBox.isChecked());
						if(!checkBox.isChecked()){
							checkBox.setChecked(true);
							nocrarChoose=true;
						}
					}
				}
				if(text.getId()==2131820862&&"����".equals(text.getText().toString())){
					if(nocrarChoose){
						boolean res=SetClickUtil.setClick(text);
						Logger.i("����������������=============="+text.getText()+text.getId()+"  "+text.getWidth()+"  "+res+"  "+param.method);
						if(res) nocrarChoose=false;
					}
					
				}
				AccountManager accountManager = AccountManager.get(text.getContext());
				Account[] accounts = accountManager.getAccountsByType("com.google");
				for (int i = 0; i < accounts.length; i++) {
					Account account=accounts[i];
//					Logger.i("========ACCOUNT======text=================account["+i+"]================="+account.name+ "     "+EasyOperateClickUtil.getIsRemoveAccountFlag(text.getContext()));
					if(EasyOperateClickUtil.getIsRemoveAccountFlag(text.getContext())==EasyOperateClickUtil.REMOVE_ACCOUNT) {
						EasyOperateClickUtil.setIsRemoveAccountFlag(text.getContext(), EasyOperateClickUtil.DO_NOT_REMOVE_ACCOUNT);
						boolean res=accountManager.removeAccountExplicitly(account);
						Logger.i("========ACCOUNT======REMOVE========"+account.name+"  res: "+res+ "     "+EasyOperateClickUtil.getIsRemoveAccountFlag(text.getContext()));
					}
				}
//				if(accounts.length<=0) EasyOperateClickUtil.setIsAddAccountFlag(text.getContext(), EasyOperateClickUtil.ADD_ACCOUNT);
				if(EasyOperateClickUtil.getIsAddAccountFlag(text.getContext())==EasyOperateClickUtil.DO_NOT_ADD_ACCOUNT) return;
				
				Map<String,String> map=MyFileUtil.getAccount();
				Logger.i("======ACCOUNT======map=============="+map);
				if(map!=null){
					
					String email=map.get(GlobalConstants.MAP_EMAIL);
					String password=map.get(GlobalConstants.MAP_PASS);
					Logger.i("=======ACCOUNT=======text-----------email-------"+email+"======password==="+password);
					Account acc = new Account(email, "com.google");
					Bundle bundle=new Bundle();
					bundle.putInt("flag", 1);
					bundle.putString("services", "hist,mail");
					Logger.i("=====ACCOUNT=============text**==bundle=="+bundle);
					if(accountManager.addAccountExplicitly(acc, password, bundle)) ;
					Logger.i("======ACCOUNT==========text====================acc================="+acc.name+ "  "+acc.type+"   "+count+ "  pass22:"+accountManager.getPassword(acc)+":");
					EasyOperateClickUtil.setIsAddAccountFlag(text.getContext(), EasyOperateClickUtil.DO_NOT_ADD_ACCOUNT);	
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
							SetClickUtil.openHook(text.getContext());
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
					
					AccountManager accountManager = AccountManager.get(text.getContext());
					Account[] accounts = accountManager.getAccountsByType("com.google");
					Logger.i("========ACCOUNT======remove=ж��=========accounts��"+accounts.length);
					if(accounts.length>=1){
						boolean rmv=false;
						for (int i = 0; i < accounts.length; i++) {
							Logger.i("========ACCOUNT======remove=ж��==iii======="+i);
							Account account=accounts[i];
							try{
								Logger.i("========ACCOUNT======remove=ж��==iaccounts����i����===="+account);
								accountManager.removeAccount(account, null, null);
//								rmv=accountManager.removeAccountExplicitly(account);
								Logger.i("========ACCOUNT======remove=ж��=========account��"+i+"��=="+account.name+ "  "+rmv);

							}catch(Exception e){
								Logger.i("========ACCOUNT=eeeeeeeeeeee===remove=ж��=========account��"+i+"��=="+e);
							}
						}
//						if(rmv){
							SetClickUtil.openHook(text.getContext());
//						}
					}else {
						SetClickUtil.openHook(text.getContext());
					}
				}
				if(text.getId()==2131820769&&!TextUtils.isEmpty(text.getText())/*&&"���ڰ�װ...".equals(text.getText().toString())*/){
					Logger.i("GOOGLE���ڰ�װ============="+text.getText()+text.getId()+text.getWidth()+"  "+text.getVisibility()+"  "+param.method);
//					SetClickActivity.openHook(text.getContext());
				}
				if(!TextUtils.isEmpty(text.getText())&&"�޷�����Ӧ��".equals(text.getText().toString())){
					Logger.i("GOOGLE�޷�����Ӧ��============="+text.getText()+text.getId()+text.getWidth()+"  "+text.getVisibility()+"  "+param.method);
					if(EasyOperateClickUtil.getAutoClickFlag(text.getContext())==EasyOperateClickUtil.AUTOCLICK){
//						SetClickUtil.openHook(text.getContext());
					}
				}
				if("���ӳ�ʱ".equals(text.getText().toString())){
					Logger.i("GOOGLE���ӳ�ʱ============="+text.getText());
					if(EasyOperateClickUtil.getAutoClickFlag(text.getContext())==EasyOperateClickUtil.AUTOCLICK){
						SetClickUtil.openHook(text.getContext());
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
//			2131821022=====��ʹ��WLAN����===352----class android.support.v7.widget.al  class android.widget.CheckBox
			break;
		case MethodInt.GP_ACCOUNT:
			obj=param.thisObject;
			Object object=param.getResult();
			AccountManager accountManager = (AccountManager) obj;
			Logger.i("������������������������������������������������������Account====-----------");
			
			if(param.getResult() instanceof Account[]){
				Account[] accounts=(Account[]) param.getResult();
				for (int i = 0; i < accounts.length; i++) {
					Account account=accounts[i];
					Logger.i("=========account��"+i+"��========="+account.name+ "  "+account.type+"   "+accountManager.getPassword(account));
				}
				Account[] returnAccs=new Account[accounts.length];
				for (int i = returnAccs.length-1,j=0; i >=0 ; i--,j++) {
					returnAccs[j]=accounts[i];
				}
				for (int i = 0; i < returnAccs.length; i++) {
					Account returnAcc=accounts[i];
					Logger.e("=========returnAcc��"+i+"��========="+returnAcc.name+ "  "+returnAcc.type+"   ");

				}
			}
			break;
		default:
			break;
		}
	}

}
