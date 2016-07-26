package Xposed;

import com.hdj.downapp_market.activity.SetClickUtil;
import com.mz.utils.EasyOperateClickUtil;
import com.mz.utils.Logger;

import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

public class Job360 extends JobAbstract {
	private static Job360 instance;
	private Job360() {
		// TODO Auto-generated constructor stub
	}
	public static Job360 get360(){
		if(instance==null){
			synchronized (Job360.class) {
				if(instance==null){
					instance=new Job360();
				}
			}
		}
		return instance;
	}

	@Override
	public void handleMethod(String packageName, ClassLoader classLoader) {
		is360jumpc = false;
		is360Downc = false;
		is360liuliangc = false;
		_360Progress = false;
		HookMethod(View.class, "refreshDrawableState", packageName,
				MethodInt.VIEW_TOSTRING_360_DOWN);
		HookMethod(TextView.class, "isSuggestionsEnabled", packageName,
				MethodInt.VIEW_TOSTRING_360_TV_PROGRESS);
		HookMethod(View.class, "dispatchTouchEvent", packageName,
				MethodInt.DIS, MotionEvent.class);
	}
	@Override
	public void handleAftreMethod(String packageName,MethodHookParam param, int type) {
		// TODO Auto-generated method stub
		super.handleAftreMethod(packageName,param, type);
		Object obj;
		switch (type) {
		case MethodInt.VIEW_TOSTRING_360_DOWN:
			obj = param.thisObject;
			if (obj instanceof TextView) {// 2131493934 点击跳过
				TextView t = (TextView) obj;

				if ("跳过".equals(t.getText().toString().trim())) {
					if (is360jumpc)
						return;
					is360jumpc = true;
					is360Downc = false;
					SetClickUtil.setClick(t);
				}
			}
			if (obj instanceof TextView) {// 2131493934
				TextView t = (TextView) obj;
				if ("使用流量下载".equals(t.getText().toString().trim())) {
					Logger.i("360_DOWN----流量-tttt--" + t.getId() + "----"
							+ t.getText());
					if (is360liuliangc)
						return;
					is360liuliangc = true;
					boolean res=SetClickUtil.setClick(t);
					Logger.i("360_DOWN----流量-tttt--" + t.getId() + "----"
							+ t.getText()+res);
				}
			}
			
			if (obj instanceof View) {// 点击下载按钮
				View v = (View) obj;
				if ((v.getId() == 2131493240) && (v.getWidth() > 0)) {
					if (is360Downc || !is360jumpc)
						return;
					is360Downc = true;
					Message msg = handler.obtainMessage();
					msg.what = MethodInt._360_DOWN;
					msg.obj = v;
					handler.sendMessageDelayed(msg, 200);// 这里多等2s才能正常下载
				}
			}
			break;
		case MethodInt.VIEW_TOSTRING_360_TV_PROGRESS:
			obj = param.thisObject;
			if (obj instanceof TextView) {// 2131493243 下载TextView
				TextView t = (TextView) obj;
				if (t.getId() == 2131493243 && t.getWidth() > 0) {

//					int halfFlag = EasyOperateClickUtil.getBaiduHalfFlag(t
//							.getContext());
//					if (halfFlag == EasyOperateClickUtil.NO_HALF)
//						return;
					int halfValue = EasyOperateClickUtil
							.getHalfDownloadValue(t.getContext());
					if (halfValue == 100)
						return;
					String text = t.getText().toString().trim();
					int index = text.indexOf("%");
					if (index > 0) {
						float f = Float
								.parseFloat(text.substring(0, index));
						if (f > halfValue && !_360Progress) {
							final TextView tv = t;
							_360Progress = true;
							SetClickUtil.openHook(tv.getContext());
							handler.sendEmptyMessageDelayed(
									MethodInt._360_PAUSE, 8000);
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
