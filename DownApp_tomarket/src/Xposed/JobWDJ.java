package Xposed;

import com.hdj.downapp_market.activity.SetClickUtil;
import com.mz.utils.Logger;

import android.os.Message;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

public class JobWDJ extends JobAbstract {
	private static JobWDJ instatnc;

	private JobWDJ() {
		// TODO Auto-generated constructor stub
	}

	public static JobWDJ getWDJ() {
		if (instatnc == null) {
			instatnc = new JobWDJ();
		}
		return instatnc;
	}

	@Override
	public void handleMethod(String packageName, ClassLoader classLoader) {
		isWdjDown = false;
		wdjLiuliang = false;
		HookMethod(View.class, "refreshDrawableState", packageName,
				MethodInt.WDJ_DOWN);
		HookMethod(View.class, "dispatchTouchEvent", packageName,
				MethodInt.DIS, MotionEvent.class);
	}

	@Override
	public void handleAftreMethod(String packageName,MethodHookParam param, int type) {
		// TODO Auto-generated method stub
		super.handleAftreMethod(packageName,param, type);
		Object obj;
		switch (type) {
		case MethodInt.WDJ_DOWN:
			obj = param.thisObject;
			if (obj instanceof View) {
				View view = (View) obj;
				if (view.getId() == 2131492866) {
					if (isWdjDown)
						return;
					isWdjDown = true;
					Message msg = handler.obtainMessage();
					msg.what = MethodInt.WDJ_DOWN;
					msg.obj = view;
					handler.sendMessageDelayed(msg, 2000);// 这里多等2s才能正常下载
				}
				if (view instanceof TextView) {
					TextView text = (TextView) view;
					if (!TextUtils.isEmpty(text.getText())
							&& "现在下载".equals(text.getText().toString())) {
						Logger.i("WDJ_DOWN--------流量--" + view.getId()
								+ wdjLiuliang);
						if (wdjLiuliang)
							return;
						wdjLiuliang = true;
						boolean res = SetClickUtil.setClick(view);
						// Logger.i("WDJ_DOWN-------流量--"+res);
					}
				}
				// if(view.getId()==2131493270||view.getId()==2131493275){//流量下载
				// 现在下载
				// // Logger.i("WDJ_DOWN--------流量--" + view.getId()+
				// wdjLiuliang);
				// if (wdjLiuliang)
				// return;
				// wdjLiuliang = true;
				// boolean res=SetClickActivity.setClick(view);
				// // Logger.i("WDJ_DOWN-------流量--"+res);
				// }
			}
			break;

		default:
			break;
		}

	}

}
