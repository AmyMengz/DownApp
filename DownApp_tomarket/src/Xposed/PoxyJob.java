package Xposed;

import com.hdj.downapp_market.activity.SetClickUtil;
import com.mz.utils.Logger;
import com.mz.utils.VpnUtil;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Switch;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

public class PoxyJob extends JobAbstract {
	private static PoxyJob instance;

	private PoxyJob() {

	}

	public static PoxyJob getPoxy() {
		if (instance == null) {
			synchronized (PoxyJob.class) {
				if (instance == null) {
					instance = new PoxyJob();
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
				MethodInt.POXY_SET);
	}

	@Override
	public void handleAftreMethod(String packageName, MethodHookParam param,
			int type) {
		// TODO Auto-generated method stub
		super.handleAftreMethod(packageName, param, type);
		Object obj;
		switch (type) {
		case MethodInt.POXY_SET:
			obj = param.thisObject;
			if (obj instanceof Switch) {
				Switch switchPoxy = (Switch) obj;
				if (switchPoxy.getId() == 2131296284
						&& switchPoxy.getWidth() > 0) {
					if (!switchPoxy.isChecked()) {
						boolean res = SetClickUtil.setSimulateClick(switchPoxy, 0, 0);
						Logger.i("--POXY-------switchPoxy----setClick--------"
								+ switchPoxy + "=======" + switchPoxy.getText()
								+ "------" + switchPoxy.isChecked() + "::res:"
								+ res);
					}
				}
			}
			break;

		default:
			break;
		}
	}

}
