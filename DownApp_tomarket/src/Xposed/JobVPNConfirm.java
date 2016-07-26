package Xposed;

import com.hdj.downapp_market.activity.SetClickUtil;
import com.mz.utils.Logger;

import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.TextView;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

public class JobVPNConfirm extends JobAbstract {
	private static JobVPNConfirm instance;
	private JobVPNConfirm(){
		
	}
	public static JobVPNConfirm getVPNConfirm(){
		if(instance==null){
			synchronized (PoxyJob.class) {
				if(instance==null){
					instance=new JobVPNConfirm();
				}
			}
		}
		return instance;
	}

	@Override
	public void handleMethod(String packageName, ClassLoader classLoader) {
		HookMethod(View.class, "dispatchTouchEvent", packageName,
				MethodInt.DIS, MotionEvent.class);
		HookMethod(View.class, "refreshDrawableState", packageName,
				MethodInt.VPN_CONFIRM_SET);
	}
	@Override
	public void handleAftreMethod(String packageName, MethodHookParam param,
			int type) {
		// TODO Auto-generated method stub
		super.handleAftreMethod(packageName, param, type);
		Object obj;
		switch (type) {
		case MethodInt.VPN_CONFIRM_SET:
			obj=param.thisObject;
			if(obj instanceof CheckBox){
				CheckBox checkBox = (CheckBox) obj;
				Logger.i("--POXY-----------checkBox------------------"+checkBox+"======="+checkBox.getText()+"------"+checkBox.isChecked());
				if(!checkBox.isChecked()){
					boolean res=SetClickUtil.setClick(checkBox);
					Logger.i("--POXY-------checkBox----setClick--------"+checkBox+"======="+checkBox.getText()+"------"+checkBox.isChecked()+"res:"+res);

				}
			}
			if(obj instanceof TextView){
				TextView textView = (TextView) obj;
				if(!TextUtils.isEmpty(textView.getText()))
				if("È·¶¨".equals(textView.getText().toString())){
					boolean res=SetClickUtil.setClick(textView);
					Logger.i("--POXY-----------textView------------------"+textView+"======="+textView.getText()+"----res--"+res);

				}
			}
			break;
		}
	}

}
