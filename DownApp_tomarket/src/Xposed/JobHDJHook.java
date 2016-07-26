package Xposed;

import com.mz.utils.EasyOperateClickUtil;
import com.mz.utils.Logger;

import android.os.Message;
import android.view.View;
import android.widget.Button;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

public class JobHDJHook extends JobAbstract {
	private static JobHDJHook instance;
	private JobHDJHook() {
		// TODO Auto-generated constructor stub
	}
	public static JobHDJHook getHDJHook(){
		if(instance==null){
			synchronized (JobHDJHook.class) {
				if(instance==null){
					instance=new JobHDJHook();
				}
			}
		}
		return instance;
	}

	@Override
	public void handleMethod(String packageName, ClassLoader classLoader) {
		HookMethod(View.class, "refreshDrawableState", packageName,
				MethodInt.VIEW_TOSTRING_HOOK);
	}
	@Override
	public void handleAftreMethod(String packageName,MethodHookParam param, int type) {
		// TODO Auto-generated method stub
		super.handleAftreMethod(packageName,param, type);
		Object obj;
		switch (type) {
		case MethodInt.VIEW_TOSTRING_HOOK:
			obj = param.thisObject;
			if (obj instanceof Button) {
				if(obj==null) return;
				Button btn = (Button) obj;
				if(btn.getText()==null) return;
				String s = btn.getText().toString();
				
				if ("Ò»¼ü²Ù×÷".equals(s)) {
					Logger.i("VIEW_TOSTRING_HOOK====="
							+ btn.getText()
							+ "===easy===="
							+ (EasyOperateClickUtil.getEasyTag(btn
									.getContext()) == EasyOperateClickUtil.CILCKED));
					if (EasyOperateClickUtil.getEasyTag(btn.getContext()) == EasyOperateClickUtil.CILCKED)
						return;
					Message msg = handler.obtainMessage();
					msg.what = MethodInt.HOOK;
					msg.obj = btn;
					handler.sendMessageDelayed(msg, 1000);
				}
			}
			break;

		default:
			break;
		}
	}

}
