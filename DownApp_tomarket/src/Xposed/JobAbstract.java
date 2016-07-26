package Xposed;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hdj.downapp_market.activity.SetClickUtil;
import com.mz.utils.CmdUtil;
import com.mz.utils.EasyOperateClickUtil;
import com.mz.utils.Logger;

import Xposed.HookMain.AppInfos_XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import de.robv.android.xposed.XposedHelpers;

public abstract class JobAbstract implements JobInterface {
	public boolean isYybDown = false, yybGo = false, yybProgress = false,yybLiuliang = false;
	public boolean isWdjDown = false, wdjLiuliang = false;
	public boolean isBaiduDown = false, baiduProgress = false,isBaiduDownc = false;// 3G情况下继续下载用的
	public boolean is360jumpc = false,is360Downc = false,is360liuliangc = false,_360Progress = false;
	public boolean 	isPPDown = false,isPPTextOk = false,isPPInstallClick = false,ppProgress=false;
	public boolean 	isAnZhiDown = false,anZhiLiuliang=false,aZProgress=false;
	public boolean MIUIProgress=false,isCanMIUIDown = false,MIUILiuliang=false;
	public boolean VPNConnectClicked = false;
	boolean gpInstall_click=false,gpAccountAdd=false;
	public JobAbstract() {
		// TODO Auto-generated constructor stub
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			/*----------------------------豌豆荚----------------------*/
			case MethodInt.WDJ_DOWN:
				if (msg.obj != null) {
					final TextView v = (TextView) msg.obj;
					boolean re = SetClickUtil.setClick(v);
					Logger.i("WDJ_DOWN=====cilickR=====" + re);
					if (!re) {
						Logger.i("WDJ_DOWN=====cilickR re=========" + re);// 等待可以下载
						Message msg2 = handler.obtainMessage();
						msg2.what = MethodInt.WDJ_DOWN;
						msg2.obj = msg.obj;
						handler.sendMessageDelayed(msg2, 1000);
					}
				}
				break;
			/*---------------------------PP助手------------------------*/
			case MethodInt.PP_DOWN:
				if (msg.obj != null) {
					final View view = (View) msg.obj;
					boolean res = SetClickUtil.setClick(view);
					Logger.i("PP_DOWN====cilickR======res" + res);
				}
				break;
			/*----------------------------百度手机助手----------------------*/
			case MethodInt.BAIDU_DOWN:
				if (msg.obj != null) {
					final View v = (View) msg.obj;
					boolean re = SetClickUtil.setClick(v);
					if (!re) {
						Message msg2 = handler.obtainMessage();
						msg2.what = MethodInt.BAIDU_DOWN;
						msg2.obj = msg.obj;
						handler.sendMessageDelayed(msg2, 1000);
					}
				}
				break;
			/*----------------------------360手机助手----------------------*/
			case MethodInt._360_DOWN:
				if (msg.obj != null) {
					View v = (View) msg.obj;
					boolean re = SetClickUtil.setClick(v);
					Logger.i("360_DOWN cilickR=========" + re);
				}
				break;
			/*----------------------------应用宝----------------------*/
			case MethodInt.YYB_DOWN:
				if (msg.obj != null) {
					final View v = (View) msg.obj;
					boolean re = SetClickUtil.setClick(v);

					Logger.i("YYBDOWN cilickR=========" + re);
				}
				break;

			/*----------------------------修改器----------------------*/
			case MethodInt.HOOK:
				if (msg.obj != null) {
					Button btn = (Button) msg.obj;
					if (EasyOperateClickUtil.getEasyTag(btn.getContext()) == EasyOperateClickUtil.CILCKED)
						return;
					boolean res = SetClickUtil.setClick(btn);
					EasyOperateClickUtil.setEasyTag(btn.getContext(),
							EasyOperateClickUtil.CILCKED);
					Logger.i("HOOK cilickR=========" + btn.getText() + "  res "
							+ res + "  easy "
							+ EasyOperateClickUtil.getEasyTag(btn.getContext()));
					boolean root = CmdUtil.isRoot();
					Logger.i("iiiiiiiroot====reBoot===" + root + " "
							+ CmdUtil.isRoot());
					if (!(root/* &&root2 */)) {
						if (EasyOperateClickUtil.getAutoRebootFlag(btn
								.getContext()) == EasyOperateClickUtil.AUTOREBOOT) {
							SetClickUtil.reBoot(btn.getContext());
						}
					}
				}
				break;
			case MethodInt.GP_ACCOUNT_DIALOG:
				if(msg.obj!=null){
					if(msg.obj instanceof Activity){
						Activity a=(Activity)msg.obj;
						Logger.i("GP-----------------a.finish()----------------");
						a.finish();
					}
				}
				break;

			// case MethodInt.BAIDU_PAUSE:
			// baiduProgress = false;
			// break;
			// case MethodInt._360_PAUSE:
			// _360Progress = false;
			// break;
			// case MethodInt.YYB_PAUSE:
			// yybProgress = false;
			// break;

			default:
				break;
			}
		}
	};


	public void handleAftreMethod(String packageName, MethodHookParam param, int type) {
		Object obj;
		switch (type) {
		case MethodInt.DIS:
			obj = param.thisObject;
			if (obj instanceof View) {
				View view = (View) obj;
				if (view instanceof TextView) {
					TextView te = (TextView) view;
					Logger.i("---------disp-----------------" + view.getId()
							+ "=====" + te.getText() + "===" + view.getWidth()
							+ "----" + obj.getClass().toString() + "  "
							+ obj.getClass().getSuperclass().toString()+"  "
							+ obj.getClass().getSuperclass().getSuperclass()
							+ "   " + "  " + view.getHeight() + "   "
							+ view.getWidth() + "  " + te.getWidth() + "  "
							+ te.getTextSize() + "  " + packageName);
				} else {
					Logger.i("---------disp-----------------" + view.getId()
							+ "===" + view.getWidth() + "----"
							+ obj.getClass().toString() + "  "
							+ obj.getClass().getSuperclass().toString() + "  "
							+ "  " + view.getHeight() + "   " + view.getWidth());
				}
			}
			break;
		case MethodInt.LIULINAG_DOWN:
			param.setResult(ConnectivityManager.TYPE_MOBILE);
			Logger.i("=========NetworkInfo============================"
					+ param.getResult()
					+ "=====================================");
			break;
		}
	};

	public void HookMethod(Class<?> clazz, String methodName,
			String packageName, final int type) {
		XposedHelpers.findAndHookMethod(clazz, methodName,
				new Object[] { new AppInfos_XC_MethodHook(type, packageName) });

	}

	public void HookMethod(final String class1, String methodName,
			final ClassLoader classLoader, final String packageName,
			final int type, Object... objects) {
		Object[] new_objects = new Object[objects.length + 1];
		for (int i = 0; i < objects.length; i++) {
			new_objects[i] = objects[i];
		}
		new_objects[objects.length] = new AppInfos_XC_MethodHook(type,
				packageName);
		XposedHelpers.findAndHookMethod(class1, classLoader, methodName,
				new_objects);
	}

	public void HookMethod(final Class<?> class1, String methodName,
			final String packageName, final int type, Object... objects) {
		Object[] new_objects = new Object[objects.length + 1];
		for (int i = 0; i < objects.length; i++) {
			new_objects[i] = objects[i];
		}
		new_objects[objects.length] = new AppInfos_XC_MethodHook(type,
				packageName);
		XposedHelpers.findAndHookMethod(class1, methodName, new_objects);
	}

}
