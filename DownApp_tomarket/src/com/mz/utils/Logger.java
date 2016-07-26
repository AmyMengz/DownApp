package com.mz.utils;


import android.util.Log;

public class Logger {
	public static void i(String tips) {
		if (GlobalConstants.ISLOG)
			Log.i(GlobalConstants.TAG, tips);
	}
	public static void i(String key,String tips) {
		if (GlobalConstants.ISLOG)
			Log.i(key, tips);
	}
	public static void e(String tips) {
		if (GlobalConstants.ISLOG)
			Log.i(GlobalConstants.TAGEX, tips);
	}

}
