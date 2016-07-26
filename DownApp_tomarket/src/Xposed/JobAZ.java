package Xposed;

import java.lang.reflect.Field;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.hdj.downapp_market.activity.SetClickUtil;
import com.mz.utils.EasyOperateClickUtil;
import com.mz.utils.Logger;

import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

public class JobAZ extends JobAbstract {
	private static JobAZ instance;
	private JobAZ() {
		// TODO Auto-generated constructor stub
	}
	public static JobAZ getAZ(){
		if(instance==null){
			synchronized (JobAZ.class) {
				if(instance==null) 
					instance=new JobAZ();
			}
		}
		return instance;
	}

	@Override
	public void handleMethod(String packageName, ClassLoader classLoader) {
		isAnZhiDown = false;
		anZhiLiuliang=false;
		aZProgress=false;
		HookMethod(View.class, "refreshDrawableState", packageName,
				MethodInt.AZ_DOWN);
		HookMethod(View.class, "dispatchTouchEvent", packageName,
				MethodInt.DIS, MotionEvent.class);
		HookMethod("com.anzhi.market.ui.widget.MarketProgressBar", "onDraw", classLoader, packageName, MethodInt.AZ_TV_PROGRESS, Canvas.class);
	}
	@Override
	public void handleAftreMethod(String packageName,MethodHookParam param, int type) {
		// TODO Auto-generated method stub
		super.handleAftreMethod(packageName,param, type);
		Object obj;
		switch (type) {
		case MethodInt.AZ_DOWN://2131230959按钮
			obj = param.thisObject;
			if (obj instanceof TextView) {
				TextView view = (TextView) obj;
				if("继续下载".equals(view.getText().toString())){//流量时
					if(anZhiLiuliang) return;
					anZhiLiuliang=true;
					Logger.i("ANZHILIULI========"+view.getId()+"===="+view.getWidth()+view.getText());
					SetClickUtil.openHook(view.getContext());
				}
				
			}
			if (obj instanceof View) {
				View view = (View) obj;
				if(view.getId()==2131230956){
					if(isAnZhiDown) return;
					isAnZhiDown=true;
					Logger.i("ANZHI========"+view.getId()+"===="+view.getWidth());
					boolean res=SetClickUtil.setClick(view);
					Logger.i("ANZHI========"+res);
				}
				
			}
			break;
		case MethodInt.AZ_TV_PROGRESS:
			obj = param.thisObject;
			float progress=0;
			if (obj.getClass().toString().equals("class com.anzhi.market.ui.widget.MarketProgressBar")) {
				View view = (View) obj;
//				int halfFlag = EasyOperateClickUtil.getBaiduHalfFlag(view.getContext());
//				if (halfFlag == EasyOperateClickUtil.NO_HALF) {
//					return;
//				}
				int halfValue = EasyOperateClickUtil.getHalfDownloadValue(view.getContext());
				if (halfValue == 100)
					return;
				
				Class<?> clazz=obj.getClass();				
				try{
				Field field=clazz.getDeclaredField("u");
				field.setAccessible(true);
				Object result=field.get(obj);
				if(result instanceof Float){
					progress=(Float)result;
				}
				}catch(Exception e){
					Logger.i("EXCEPT================"+e);
				};
				if(progress*100>halfValue&&!aZProgress){
					final View view2=view;;
					aZProgress = true;
					SetClickUtil.openHook(view2.getContext());
				}
			}
			break;

		default:
			break;
		}
	}

}
