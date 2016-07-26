package Xposed;

import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

public class JobProxy2 extends JobAbstract {

	@Override
	public void handleMethod(String packageName, ClassLoader classLoader) {
		// TODO Auto-generated method stub

	}
	@Override
	public void handleAftreMethod(String packageName, MethodHookParam param,
			int type) {
		// TODO Auto-generated method stub
		super.handleAftreMethod(packageName, param, type);
	}

}
