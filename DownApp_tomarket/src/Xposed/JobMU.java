package Xposed;

import com.hdj.downapp_market.activity.SetClickUtil;
import com.mz.utils.EasyOperateClickUtil;
import com.mz.utils.Logger;

import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

public class JobMU extends JobAbstract {
	private static JobMU instance;
	private JobMU() {
		// TODO Auto-generated constructor stub
	}
	public static JobMU getMU(){
		if(instance==null){
			synchronized (JobMU.class) {
				if(instance==null){
					instance=new JobMU();
				}
			}
		}
		return instance;
	}

	@Override
	public void handleMethod(String packageName, ClassLoader classLoader) {
		isCanMIUIDown = false;
		MIUILiuliang=false;
		HookMethod(View.class, "refreshDrawableState", packageName,
				MethodInt.MU_DOWN);
		HookMethod(View.class, "getMeasuredWidth", packageName,
				MethodInt.MU_DOWN);
		HookMethod(TextView.class, "isSuggestionsEnabled", packageName,
				MethodInt.MU_DOWN);
		HookMethod(View.class, "dispatchTouchEvent", packageName,
				MethodInt.DIS, MotionEvent.class);
		
		//下载进度
		HookMethod(ProgressBar.class, "setProgress", packageName,
						MethodInt.MU_TV_PROGRESS,int.class);
	}
	@Override
	public void handleAftreMethod(String packageName,MethodHookParam param, int type) {
		// TODO Auto-generated method stub
		super.handleAftreMethod(packageName,param, type);
		Object obj;
		switch (type) {
		case MethodInt.MU_DOWN:
			obj = param.thisObject;
//			param.getClass();
			if(obj instanceof TextView){
				TextView view = (TextView)obj;
				if((view.getId()==118358028||view.getId()==2131361804)&&"安装".equals(view.getText().toString())&&view.getTextSize()>23){
					Logger.i("MIUI===============id"+view.getId()+view.getText()+"=="+view.getHeight()+"   "+view.getWidth()+"  "+view.getTextSize());
					boolean res=SetClickUtil.setClick(view);
					Logger.i("MIUI======================clickR===="+res);
				}
				//  2131361896
				if((view.getId()==118358146||view.getId()==2131361893)&&"刷新".equals(view.getText().toString())){
					boolean res=SetClickUtil.setClick(view);
					Logger.i("MIUI===========刷新===========clickR===="+res);
				}
				//同意并免费使用
				if(view.getId()==16908313&&view.getWidth()>0){
					boolean res=SetClickUtil.setClick(view);
					Logger.i("MIUI=========同意并免费使用========="+res+"    "+view.getText());
				}
				//安装中
				if((view.getId()==118358028||view.getId()==2131361804)&&("安装中".equals(view.getText().toString())||"已安装".equals(view.getText().toString()))){
					Logger.i("MIUI=======安装中================="+view.getId()+"   "+view.getWidth()+"  "+view.getText());
					SetClickUtil.openHook(view.getContext());
				}
//				
				if((!TextUtils.isEmpty(view.getText()))&&("进入商店".equals(view.getText().toString()))){
					Logger.i("MIUI=======进入商店================="+view.getId()+"   "+view.getWidth()+"  "+view.getText());
					boolean res=SetClickUtil.setClick(view);
					Logger.i("MIUI=========进入商店Click========="+res+"    "+view.getText());
				}
			}
			break;
		case MethodInt.MU_TV_PROGRESS:
			obj = param.thisObject;
			if(obj instanceof ProgressBar){
				ProgressBar view = (ProgressBar) obj;
				Logger.i("MU_TV_PROGRESS================"+view.getProgress()+"   "+view.getId());
//				int halfFlag = EasyOperateClickUtil.getBaiduHalfFlag(view.getContext());
//				if (halfFlag == EasyOperateClickUtil.NO_HALF) {
//					return;
//				}
				int halfValue = EasyOperateClickUtil.getHalfDownloadValue(view.getContext());
				if (halfValue == 100)
					return;
				int muProgress=view.getProgress();
				if(muProgress>halfValue&&!MIUIProgress){
					final View view2=view;;
					MIUIProgress = true;
					SetClickUtil.openHook(view2.getContext());
				}
			}
			break;

		default:
			break;
		}
	}

}
