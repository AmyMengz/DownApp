package com.hdj.downapp.util;

import java.io.File;
import java.io.IOException;

import android.os.Environment;

public class FileCacheUtil {
	private File mCacheFile;

	public static String FILE_NAME = "aaa.txt";

	private String directoryName;

	/**
	 * @param directoryName
	 *            目录名
	 */
	public FileCacheUtil(String directoryName) {
		this.directoryName = directoryName;
	}

	/**
	 * 
	 * @param fileName
	 *            文件名
	 * @return
	 */
	public File getFile(String fileName) {
		if (mCacheFile == null) {
			mCacheFile = new File(Environment.getExternalStorageDirectory(), directoryName);
		}
		File file2 = new File(mCacheFile, fileName);
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			if (!mCacheFile.exists()) {
				mCacheFile.mkdirs();
			}
			try {
				file2.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file2;
	}

}
