package Xposed;

import com.hdj.downapp_market.activity.SetClickUtil;
import com.mz.utils.EasyOperateClickUtil;
import com.mz.utils.Logger;

import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

public class JobBaiDu extends JobAbstract {
	private static JobBaiDu instance;

	private JobBaiDu() {
		// TODO Auto-generated constructor stub
	}

	public static JobBaiDu getBaiDu() {
		if (instance == null) {
			synchronized (JobBaiDu.class) {
				if (instance == null) {
					instance = new JobBaiDu();
				}
			}
		}
		return instance;
	}

	@Override
	public void handleMethod(String packageName, ClassLoader classLoader) {
		isBaiduDown = false;
		isBaiduDownc = false;// 3G情况下继续下载用的
		baiduProgress = false;
		HookMethod(View.class, "refreshDrawableState", packageName,
				MethodInt.VIEW_TOSTRING_BAIDU_DOWN);
		HookMethod(TextView.class, "isSuggestionsEnabled", packageName,
				MethodInt.VIEW_TOSTRING_BAIDU_TV_PROGRESS);
		HookMethod(View.class, "dispatchTouchEvent", packageName,
				MethodInt.DIS, MotionEvent.class);
	}

	@Override
	public void handleAftreMethod(String packageName,MethodHookParam param, int type) {
		// TODO Auto-generated method stub
		super.handleAftreMethod(packageName,param, type);
		Object obj;
		switch (type) {
		case MethodInt.VIEW_TOSTRING_BAIDU_DOWN:
			obj = param.thisObject;
			if (obj instanceof TextView) {
				TextView btn = (TextView) obj;
				String s = btn.getText().toString();
				if ("继续下载".equals(s)) {
					Logger.i("VIEW_TOSTRING_BAIDU_DOWN======" + s + btn.getId());
					if (isBaiduDownc)
						return;
					isBaiduDownc = true;
					Message msg = handler.obtainMessage();
					msg.what = MethodInt.BAIDU_DOWN;
					msg.obj = btn;
					handler.sendMessageDelayed(msg, 2000);// 这里多等2s才能正常下载
				}
				if("取消".equals(s)){
					SetClickUtil.setClick(btn);
					Logger.i("VIEW_TOSTRING_BAIDU_取消===========" + s + btn.getId());
				}
			}
			if (obj instanceof View) {
				View v = (View) obj;
				int id = v.getId();
				if (id == 2131558608 && (v.getWidth() > 0)) {
					Logger.i("BAIDU_DOWN=========" + v.getId());
					if (isBaiduDown)
						return;
					isBaiduDown = true;
					Message msg = handler.obtainMessage();
					msg.what = MethodInt.BAIDU_DOWN;
					msg.obj = v;
					handler.sendMessageDelayed(msg, 2000);// 这里多等2s才能正常下载
				}
			}

			break;
		case MethodInt.VIEW_TOSTRING_BAIDU_TV_PROGRESS:// 百度监控下载进度
			obj = param.thisObject;
			if (obj instanceof TextView) {// 2131559283
				TextView t = (TextView) obj;
				if (t.getId() == 2131559283 && t.getWidth() > 0) {
//					int halfFlag = EasyOperateClickUtil.getBaiduHalfFlag(t
//							.getContext());
//					if (halfFlag == EasyOperateClickUtil.NO_HALF) {
//						return;
//					}
					int halfValue = EasyOperateClickUtil
							.getHalfDownloadValue(t.getContext());
					if (halfValue == 100)
						return;
					String text = t.getText().toString().trim();
					int index = text.indexOf("%");
					if (index > 0) {
						float f = Float.parseFloat(text.substring(0, index));
						if (f > halfValue && !baiduProgress) {
							final TextView tv = t;
							baiduProgress = true;
							SetClickUtil.openHook(tv.getContext());
							// handler.sendEmptyMessageDelayed(
							// MethodInt.BAIDU_PAUSE, 8000);

						}
					}
				}
			}
			break;
		}
	}

}
