package Xposed;

import com.hdj.downapp_market.activity.SetClickUtil;
import com.mz.utils.EasyOperateClickUtil;
import com.mz.utils.Logger;

import android.os.Message;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class JobYYB extends JobAbstract {
	private static JobYYB instance;
	private JobYYB() {
	}
	public static  JobYYB getYYB(){
		if(instance==null){
			instance=new JobYYB();
		}
		return instance;
	}

	@Override
	public void handleMethod(String packageName,ClassLoader classLoader) {
		isYybDown = false;
		yybGo = false;
		yybProgress=false;
		HookMethod(View.class, "refreshDrawableState", packageName,
				MethodInt.VIEW_TOSTRING_YYB_DOWN);
		HookMethod(TextView.class, "isSuggestionsEnabled", packageName,
				MethodInt.VIEW_TOSTRING_YYB_TV_PROGRESS);
		HookMethod(View.class, "dispatchTouchEvent", packageName,
				MethodInt.DIS, MotionEvent.class);// 后面参数是方法的参数类型  
		HookMethod(View.class, "setVisibility", packageName,
				MethodInt.VIEW_TOSTRING_YYB_TV_LOAD,int.class );
		HookMethod(View.class, "getMeasuredWidth", packageName,
				MethodInt.VIEW_TOSTRING_YYB_TV_LOAD);
	}

	@Override
	public void handleAftreMethod(String packageName,MethodHookParam param, int type) {
		// TODO Auto-generated method stub
		super.handleAftreMethod(packageName,param, type);
		Object obj;
		switch (type) {
		case MethodInt.VIEW_TOSTRING_YYB_TV_LOAD:
			obj = param.thisObject;
			if (obj instanceof TextView) {//2131231373
				TextView t = (TextView) obj;
				if (((t.getId() == 2131559032|| (t.getId() ==2131231373)))&& t.getWidth() > 0 && !yybGo) {
					Logger.i("YYB_LOAD-------yyb -------------"+t.getId()+"===="+t.getVisibility());
					yybGo = true;
				}
			}// 没有break
		case MethodInt.VIEW_TOSTRING_YYB_DOWN:
			obj = param.thisObject;

			if (obj instanceof View) {
				View view = (View) obj;//2131231366
				if (((view.getId() == 2131559032)||(view.getId()==2131231366)) && view.getWidth() > 0) {
					Logger.i("View-------------yyb -------------"+view.getId()+"===="+view.getVisibility()+isYybDown+yybGo);
					if (isYybDown || !yybGo)
						return;
					isYybDown = true;
					Message msg = handler.obtainMessage();
					msg.what = MethodInt.YYB_DOWN;
					msg.obj = view;
					handler.sendMessageDelayed(msg, 2000);// 这里多等2s才能正常下载
				}
				// 快速安装2131231818
				if (((view.getId() == 2131559413)||(view.getId()==2131231818)) && view.getWidth() > 0) {
					if (yybLiuliang)
						return;
					yybLiuliang = false;
					Message msg = handler.obtainMessage();
					msg.what = MethodInt.YYB_DOWN;
					msg.obj = view;
					handler.sendMessageDelayed(msg, 200);// 这里多等2s才能正常下载
				}

			}
			break;
		// 2131231373 下载 (11.4MB)***2131231370  2131231373
		case MethodInt.VIEW_TOSTRING_YYB_TV_PROGRESS:
			obj = param.thisObject;
			if (obj instanceof TextView) {//
				TextView t = (TextView) obj;
				if(!TextUtils.isEmpty(t.getText().toString()))
//					Logger.i("YYB==="+t.getText()+"===="+t.getId());
				if (((t.getId() == 2131559039)|| (t.getId()==2131231373))&& t.getWidth() > 0) {
//					int halfFlag = EasyOperateClickUtil.getBaiduHalfFlag(t.getContext());
//					if (halfFlag == EasyOperateClickUtil.NO_HALF) {
//						return;
//					}
					int halfValue = EasyOperateClickUtil.getHalfDownloadValue(t.getContext());
					if (halfValue == 100)
						return;
					String text = t.getText().toString().trim();
					int index = text.indexOf("%");
					int start = text.indexOf(" ");
					if (index > 0) {
						float f = Float.parseFloat(text.substring(
								start + 1, index));
						if (f > halfValue && !yybProgress) {
							final TextView tv = t;
							yybProgress = true;
							SetClickUtil.openHook(tv.getContext());
//							handler.sendEmptyMessageDelayed(
//									MethodInt.YYB_PAUSE, 8000);

						}
					}
				}

			}
			break;
		}
	}


}
