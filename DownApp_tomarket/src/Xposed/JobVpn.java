package Xposed;


import com.hdj.downapp_market.activity.SetClickUtil;
import com.mz.utils.EasyOperateClickUtil;
import com.mz.utils.Logger;
import com.umeng.analytics.c;

import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

public class JobVpn extends JobAbstract {
	private static JobVpn instance;
	private boolean isAccount=false;
	private JobVpn(){};
	public static JobVpn getVpn(){
		if(instance==null){
			synchronized (JobVpn.class) {
				if(instance==null){
					instance=new JobVpn();
				}
			}
		}
		return instance;
	}

	@Override
	public void handleMethod(String packageName, ClassLoader classLoader) {
		VPNConnectClicked=false;
		isAccount=false;
		HookMethod(TextView.class, "isSuggestionsEnabled", packageName, MethodInt.VPN_CLICK);
		HookMethod(View.class, "dispatchTouchEvent", packageName,
				MethodInt.DIS, MotionEvent.class);
		HookMethod(AbsListView.class, "isTextFilterEnabled", packageName,
				MethodInt.VPN_LIST);
		
//		HookMethod(TextView.class, "isSuggestionsEnabled", packageName, MethodInt.GOOGLE_ACCOUNT);
//		HookMethod(AbsListView.class, "isTextFilterEnabled", packageName,
//				MethodInt.GOOGLE_ACCOUNT);
		
	}
	@Override
	public void handleAftreMethod(String packageName, MethodHookParam param,
			int type) {
		// TODO Auto-generated method stub
		super.handleAftreMethod(packageName, param, type);
		Object obj;
		switch (type) {
		case MethodInt.VPN_CLICK:
			obj = param.thisObject;
			if(obj instanceof TextView){
				TextView text = (TextView)obj;
				int vpn=EasyOperateClickUtil.getAutoVpnFlag(text.getContext());
				if(vpn==EasyOperateClickUtil.DO_NOT_AUTO_VPN) return;
				int click=EasyOperateClickUtil.getAutoClickFlag(text.getContext());
				if(click==EasyOperateClickUtil.DO_NOT_AUTOCLICK) return;
				if(text.getId()==16908304){  //成功失败标示
					Logger.i("VPN=====16908304========"+text.getId()+"  "+text.getText().toString()+"   "+
							param.method+"  "+VPNConnectClicked+"  "+("失败".equals(text.getText().toString())));
					if(VPNConnectClicked&&"失败".equals(text.getText().toString())){  //如果连接后失败了重新跳回
						VPNConnectClicked=false;
						SetClickUtil.openSetting(text.getContext());
					}
					if("已连接".equals(text.getText().toString())){
						VPNConnectClicked=false;
						SetClickUtil.openHook(text.getContext());
						System.exit(0);
					}
				}
			}
			break;
		case MethodInt.VPN_LIST:
			obj = param.thisObject;
			if(obj instanceof ListView){
				ListView listView = (ListView)obj;
				int vpn=EasyOperateClickUtil.getAutoVpnFlag(listView.getContext());
				if(vpn==EasyOperateClickUtil.DO_NOT_AUTO_VPN) return;
				int click=EasyOperateClickUtil.getAutoClickFlag(listView.getContext());
				if(click==EasyOperateClickUtil.DO_NOT_AUTOCLICK) return;
				if(VPNConnectClicked)return;
				Logger.i("VPN============================="+listView.getCount()+"   "+param.method);
				try{
				View view=listView.getChildAt(1);
				if(view instanceof ViewGroup){
					ViewGroup viewGroup1=(ViewGroup) view;
					ViewGroup view1=(ViewGroup) viewGroup1.getChildAt(2);
					View view11=view1.getChildAt(0);
					if(view11 instanceof CheckBox){
						CheckBox check = (CheckBox)view11;
						if(check.isChecked()){
							boolean res=listView.performItemClick(listView.getChildAt(3), 3,listView.getItemIdAtPosition(3));
							VPNConnectClicked=true;
							Logger.i("VPN 33333333333=================="+res);
						}
						else {
							boolean res=listView.performItemClick(view, 1,listView.getItemIdAtPosition(1));
							VPNConnectClicked=true;
							Logger.i("VPN 1111111111111=================="+res);
						}
						Thread.sleep(3*1000);
					}
				}
				}catch(Exception e){
					Logger.i("VPNEEEEEEE===================="+e.toString());
				}
			}
			break;
		case MethodInt.GOOGLE_ACCOUNT:
			obj = param.thisObject;
			if(obj instanceof TextView){
				TextView text = (TextView)obj;
				if(!(TextUtils.isEmpty(text.getText()))){
					if("添加帐户".equals(text.getText().toString())){
						isAccount=true;
					}
					if("Google".equals(text.getText().toString())){
						isAccount=true;
					}
				}
			}
			if(obj instanceof ListView){
				if(!isAccount) return;
				Logger.i("--------setting-----------"+isAccount);
				ListView listView = (ListView)obj;
				int len=listView.getChildCount();
				for (int i = 0; i < len; i++) {
					ViewGroup viewGroup1=(ViewGroup)listView.getChildAt(i);
					ViewGroup viewGroup2=(ViewGroup)viewGroup1.getChildAt(1);
					View view = viewGroup2.getChildAt(0);
					if(view instanceof TextView){
						TextView text = (TextView) view;
						Logger.i("-------setting------------"+text.getText().toString()+"==="+"Google".equals(text.getText().toString()));
						if("Google".equals(text.getText().toString())){
							
							boolean res=listView.performItemClick(listView.getChildAt(i), i, listView.getItemIdAtPosition(i));
							Logger.i("--------setting-11----------"+isAccount+"   res:"+res);
							if(!res){
								res=listView.performItemClick(listView.getChildAt(i), i, listView.getItemIdAtPosition(i));
								Logger.i("--------setting---22--------"+isAccount+"   res:"+res);
							}
							
							break;
						}
					}
				}
			}
			break;

		default:
			break;
		}
	}

}
