package com.mz.utils;

import java.io.File;

import android.os.Environment;

public class CommonFileUtil {
	/**
	 * 父文件检测
	 * 
	 * @return
	 */
	public static File checkDir() {
		File file = new File(GlobalConstants.EXTRA_PATH);
		if (!file.exists()) {
			file.mkdirs();
		}
		return file;
	}

	public static boolean isSdCardOK() {
		return Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
	}
	
	/**
	 * 删除指定文件
	 * @param path
	 * @return
	 */
	public static boolean deleteFile(File file) {
		if(file.exists()){
			return file.delete();
		}
		else {
			return false;
		}
	}

}
