package Xposed;

import com.hdj.downapp_market.activity.SetClickUtil;
import com.mz.utils.Logger;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

public class JobMUDownloader extends JobAbstract {
	private static JobMUDownloader instance;

	private JobMUDownloader() {
		// TODO Auto-generated constructor stub
	}
	public static JobMUDownloader getMULoader() {
		if (instance == null) {
			synchronized (JobMUDownloader.class) {
				if (instance == null) {
					instance = new JobMUDownloader();
				}
			}
		}
		return instance;
	}
	@Override
	public void handleMethod(String packageName, ClassLoader classLoader) {
		MIUIProgress = false;
		HookMethod(View.class, "dispatchTouchEvent", packageName,
				MethodInt.DIS, MotionEvent.class);
		HookMethod(View.class, "refreshDrawableState", packageName,
				MethodInt.MU_SYSTEM_DOWN);
		HookMethod(Dialog.class, "show", packageName,
				MethodInt.MU_SYSTEM_DOWN);
	}	

	@Override
	public void handleAftreMethod(String packageName,MethodHookParam param, int type) {
		// TODO Auto-generated method stub
		super.handleAftreMethod(packageName,param, type);
		Object obj;
		switch (type) {
		// 启用迅雷加速
		case MethodInt.MU_SYSTEM_DOWN:
			obj = param.thisObject;
			if (obj instanceof TextView) {
				TextView text = (TextView) obj;
				if (text.getId() == 269156393 && text.getWidth() > 0) {
					Logger.i("MIUI===DOWN======SYSTEM=================" + text.getText()+"-----result------ "+param.getResult()+"   "+param.method);
				}
				if (text.getId() == 16908313 && text.getWidth() > 0) {
					boolean res = SetClickUtil.setClick(text);
					Logger.i("MIUI===DOWN======SYSTEM=============click16908313===="
							+ res + "    " + text.getText()+"-----result------ "+param.getResult()+"   "+param.method );
				}
			}
			//启动迅雷加速器的dialog 
			if(obj instanceof Dialog){
				Dialog dia=(Dialog) obj;
				LayoutInflater layoutInflater=dia.getLayoutInflater();
				View view=dia.findViewById(16908313);
				if(view instanceof Button){
					Button btn= (Button)view;
					boolean res = SetClickUtil.setClick(btn);
					Logger.i("MIUI===DOWN======SYSTEM------------------"+btn.getText()+btn.getId()+"-------res--"+res);
				}
			}
			break;
		default:
			break;
		}
	}

}
