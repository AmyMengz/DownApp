package Xposed;

import com.hdj.downapp_market.activity.SetClickUtil;
import com.mz.utils.EasyOperateClickUtil;
import com.mz.utils.Logger;

import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

public class JobPP extends JobAbstract {
	private static JobPP instance;
	private JobPP() {
		// TODO Auto-generated constructor stub
	}
	public static JobPP getPP(){
		if(instance==null){
			synchronized (JobPP.class) {
				if(instance==null)
					instance=new JobPP();
			}
		}
		return instance;
	}

	@Override
	public void handleMethod(String packageName, ClassLoader classLoader) {
		isPPDown = false;
		isPPTextOk = false;
		isPPInstallClick = false;
		ppProgress=false;
		HookMethod(View.class, "refreshDrawableState", packageName,
				MethodInt.PP_DOWN);
		HookMethod(View.class, "dispatchTouchEvent", packageName,
				MethodInt.DIS, MotionEvent.class);
		HookMethod(View.class, "setVisibility", packageName,
				MethodInt.VIEW_TOSTRING_PP_TV_LOAD, int.class);
		HookMethod(TextView.class, "isSuggestionsEnabled", packageName,
				MethodInt.VIEW_TOSTRING_PP_TV_PROGRESS);
	}


	@Override
	public void handleAftreMethod(String packageName, MethodHookParam param,int type) {
		// TODO Auto-generated method stub
		super.handleAftreMethod(packageName, param, type);
		Object obj;
		switch (type) {
		case MethodInt.VIEW_TOSTRING_PP_TV_LOAD:
			obj = param.thisObject;// 确保可见后点击
			if (obj instanceof View) {
				View view = (View) obj;
				if (view.getId() == 2131558573) {
					isPPTextOk = true;
				}
			}
		case MethodInt.PP_DOWN:
			obj = param.thisObject;
			if(obj instanceof TextView){
				TextView view = (TextView) obj;
				if (view.getId() == 2131558974 && "取消".equals(view.getText())) {
					boolean res = SetClickUtil.setClick(view);
					Logger.i("PP_DOWN====cancle    " + view.getId() + "==="+ view.getWidth() + res);
				}
				if(view.getId() == 2131558975&&"点击继续".equals(view.getText())){//流量继续
					boolean res = SetClickUtil.setClick(view);
					Logger.i("PP_DOWN====liuliang   " + view.getText() + "==="+ view.getWidth() + res);
				}
			}
			if (obj instanceof View) {
				View view = (View) obj;
				if (view.getId() == 2131558573 && view.getWidth() > 120) {
					Logger.i("PP_DOWN    view" + view.getId() + "==="
							+ view.getWidth() + isPPDown);
					if (isPPDown || !isPPTextOk)
						return;
					isPPDown = true;
					Message msg = handler.obtainMessage();
					msg.what = MethodInt.PP_DOWN;
					msg.obj = view;
					handler.sendMessageDelayed(msg, 200);// 这里多等2s才能正常下载
				}	
			}
			break;
			//2131558557
		case MethodInt.VIEW_TOSTRING_PP_TV_PROGRESS:
			obj = param.thisObject;
			if (obj instanceof TextView) {
				TextView text = (TextView) obj;
				if(text.getId()==2131558557){
					Logger.i("VIEW_TOSTRING_PP_TV_PROGRESS    " + text.getId()+ "===" + text.getWidth() + "==" + text.getText()+" "+text.getVisibility());
//					int halfFlag = EasyOperateClickUtil.getBaiduHalfFlag(text.getContext());
//					if (halfFlag == EasyOperateClickUtil.NO_HALF) {
//						return;
//					}
					int halfValue = EasyOperateClickUtil.getHalfDownloadValue(text.getContext());
					if (halfValue == 100)
						return;
					String str = text.getText().toString().trim();
					int index = str.indexOf("%");
					int start = 0;
					if (index > 0) {
						float f = Float.parseFloat(str.substring(start, index));
						Logger.i("PPPROGRESS=========ppProgress==="+ppProgress+"--f--"+f);
						if (f > halfValue && !ppProgress) {
							final TextView tv = text;
							ppProgress = true;
							SetClickUtil.openHook(tv.getContext());
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
