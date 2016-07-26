package com.mz.utils;

import java.io.File;

import android.os.Environment;

public class CommonFileUtil {
	/**
	 * ���ļ����
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
	 * ɾ��ָ���ļ�
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
