package Xposed;

import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public interface JobInterface {
	public abstract void handleMethod(String packageName,ClassLoader classLoader);
	public abstract void handleAftreMethod(String packageName,MethodHookParam param,int type);
	public void HookMethod(Class<?> clazz, String methodName,
			String packageName, final int type);
	public void HookMethod(final String class1, String methodName,
			final ClassLoader classLoader, final String packageName,final int type, Object... objects);
	public void HookMethod(final Class<?> class1, String methodName,
			final String packageName, final int type, Object... objects);
}
