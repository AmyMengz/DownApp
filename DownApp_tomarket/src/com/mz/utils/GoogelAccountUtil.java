package com.mz.utils;

import java.lang.reflect.Method;

import android.content.ContentResolver;
import android.content.Context;

public class GoogelAccountUtil {
	public static String getPassword(Context context, String email,
			String password) {
		Class<?> clazz;
		try {
			clazz = Class
					.forName("com.google.android.gsf.loginservice.PasswordEncrypter");
			Method[] methods = clazz.getDeclaredMethods();
			for (int i = 0; i < methods.length; i++) {
				// Log.i("PasswordEncrypter","========method========================["+i+"]"+methods[i]);
			}
			Method method = clazz.getDeclaredMethod("encryptPassword",
					ContentResolver.class, String.class, String.class);
			if (method != null) {
				// Log.i("PasswordEncrypter","========encryptPassword========================"+method);
				// Log.i("PasswordEncrypter","========enc="+context.getContentResolver()+"  "+email+"  "+password);
				Object o = method.invoke(null, context.getContentResolver(),
						email, password);
				// Log.i("PasswordEncrypter","============================="+o);
				return o.toString();
			}

		} catch (Exception e) {
			e.printStackTrace();
			// Log.i("PasswordEncrypter","==========eeeeee==================="+e);
		}
		return "";

	}
}
