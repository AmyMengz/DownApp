package com.hdj.downapp.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.mz.utils.GlobalConstants;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

public class IMEIUtil2 extends FileCacheUtil {
	private boolean isRecentlyIMEIEqual = false;
	private Activity activity;
	private String oldIMEI;
	private String newIMEI;

	public IMEIUtil2(Activity context) {
		super(GlobalConstants.CACHE_DIRECTORY);
		this.activity = context;
	}

	/**
	 * 
	 * @return true 这次同上次的IMEI--相同<br>
	 *         false 这次同上次的IMEI--不相同
	 */
	public boolean isRecentlyIMEIEqual() {
		return isRecentlyIMEIEqual;
	}

	/**
	 * 获取上、本次IMEI，并保存本次IMEI
	 */
	public void initIMEI() {
		oldIMEI = readOldIMEI();
		newIMEI = getIMEI();
		saveIMEI(newIMEI);
		isRecentlyIMEIEqual = !TextUtils.isEmpty(newIMEI) && newIMEI.equals(oldIMEI);
	}

	public String getNewIMEI() {
		return newIMEI;
	}

	public String getOldIMEI() {
		return oldIMEI;
	}

	/**
	 * 
	 * @return 读取上次IMEI
	 */
	private String readOldIMEI() {
		File file2 = getFile(FileCacheUtil.FILE_NAME);
		String text = null;
		if (file2.exists()) {
			FileInputStream fis;
			try {
				fis = new FileInputStream(file2);
				BufferedReader br = new BufferedReader(new InputStreamReader(fis));
				text = br.readLine();
				br.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return text;
	}

	/**
	 * 保存IMEI
	 * 
	 * @param newIMEI
	 */
	private void saveIMEI(String newIMEI) {
		File file2 = getFile(FileCacheUtil.FILE_NAME);
		if (file2.exists()) {
			try {
				FileOutputStream fos = new FileOutputStream(file2);
				fos.write(newIMEI.getBytes());
				fos.flush();
				fos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @return 这次的IMEI
	 */
	private String getIMEI() {
		TelephonyManager tm = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = tm.getDeviceId();
		return imei;
	}

}
