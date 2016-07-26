package com.mz.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.hdj.downapp_market.R;
import com.umeng.analytics.c;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

public class MyFileUtil extends CommonFileUtil {
	
	/**
	 * 复制语音提醒到SD卡
	 * 
	 * @param context
	 * @param source
	 * @param target
	 */
	public static void copySoundFile(Context context, String source,
			String target) {
		InputStream is = null;
		FileOutputStream fos = null;
		try {
			if (!isSdCardOK()) {
				Toast.makeText(context, context.getString(R.string.no_sd_card),
						Toast.LENGTH_SHORT).show();
				return;
			}
			File dir = checkDir();
			File targetFile = new File(dir, target);
			if (targetFile.exists())
				return;
			targetFile.createNewFile();
			is = context.getResources().getAssets().open(source);
			fos = new FileOutputStream(targetFile);
			int tmp = -1;
			byte[] buf = new byte[1024];
			while ((tmp = is.read(buf)) != -1) {
				fos.write(buf, 0, tmp);
			}
			fos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
					is = null;
				}
				if (fos != null) {
					fos.close();
					fos = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 纪录重启次数与时间
	 */
	public static void recordReboot() {
		FileWriter writer = null;
		try {
			File dir = checkDir();
			File rebootRecord = new File(dir, GlobalConstants.RECORD_REBOOT);
			if (!rebootRecord.exists()) {
				rebootRecord.createNewFile();
			}
			writer = new FileWriter(rebootRecord, true);
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd hh:mm:ss");
			String time = dateFormat
					.format(new Date(System.currentTimeMillis()));
			writer.write(time + "\n");
			writer.flush();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null) {
					writer.close();
					writer = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取重启次数纪录
	 * 
	 * @return
	 */
	public static int getRebootTimes() {
		File dir = checkDir();
		File rebootRecord = new File(dir, GlobalConstants.RECORD_REBOOT);
		if (!rebootRecord.exists()) {
			return 0;
		}
		BufferedReader reader = null;
		int count = 0;
		try {
			reader = new BufferedReader(new FileReader(rebootRecord));
			String tmp = null;
			while ((tmp = reader.readLine()) != null) {
				count++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
					reader = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return count;
	}

	/**
	 * 清除重启纪录
	 */
	public static void removeRebootRecord() {
		File dir = checkDir();
		File rebootRecord = new File(dir, GlobalConstants.RECORD_REBOOT);
		if (rebootRecord.exists()) {
			rebootRecord.delete();
		}
	}

	/********************************************* Google账户 ***********************************************************************/
	/**
	 * 获取账户信息
	 * 
	 * @return
	 */
	public static Map<String, String> getAccount() {
		Map<String, String> account = null;
		File dir = MyFileUtil.checkDir();
		int countIndex = 0;
		File file = new File(dir, GlobalConstants.FILE_DES_GOOGLE_ACCOUNT);
		File fileCount = new File(dir,
				GlobalConstants.FILE_GOOGLE_ACCOUNT_COUNT);
		BufferedReader accountReader = null;
		countIndex = getAccountIndex();
		if (!file.exists()) {
			return null;
		} else {
			try {
				accountReader = new BufferedReader(new FileReader(file));
				String tmp = null;
				int count = 0;
				while ((countIndex > count)&& (tmp = accountReader.readLine()) != null) {
					count++;
				}
				Logger.i("getAccount===========-----------tmp-------" + count
						+ "  " + tmp);
				if (tmp != null) {
					Logger.i("getAccount===========-----------tmp-------" + tmp);
					String email = tmp.substring(0, tmp.indexOf(","));
					String pass = tmp.substring(tmp.indexOf(",") + 1,
							tmp.lastIndexOf(","));
					account = new HashMap<String, String>();
					account.put(GlobalConstants.MAP_EMAIL, email);
					account.put(GlobalConstants.MAP_PASS, pass);
				}
			} catch (Exception e) {
				e.printStackTrace();
				Logger.i("getAccount=======e========================" + e);

			} finally {
				try {
					if (accountReader != null) {
						accountReader.close();
						accountReader = null;
					}
//					if (countReader != null) {
//						countReader.close();
//						countReader = null;
//					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return account;
	}

	/**
	 * 更改当前账号索引
	 */
	public static void addAccountIndex() {
		File dir = MyFileUtil.checkDir();
		int countIndex = -1;
		File fileCount = new File(dir,
				GlobalConstants.FILE_GOOGLE_ACCOUNT_COUNT);
		BufferedWriter countWriter = null;
		try {
			countIndex = getAccountIndex();
			Logger.i("getAccount-------countIndex----"+countIndex);
			countWriter = new BufferedWriter(new FileWriter(fileCount));
			countWriter.write(countIndex + 1 + "");
			Logger.i("getAccount-------+getAccountIndex()----"+getAccountIndex());
			countWriter.flush();
		} catch (Exception e) {
			e.printStackTrace();
			Logger.i("getAccount=======e========================" + e);
		} finally {
			try {
				if (countWriter != null) {
					countWriter.flush();
					countWriter.close();
					countWriter = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取当前账号索引
	 * 
	 * @return
	 */
	public static int getAccountIndex() {
		File dir = MyFileUtil.checkDir();
		int countIndex = 0;
		File fileCount = new File(dir,GlobalConstants.FILE_GOOGLE_ACCOUNT_COUNT);
		BufferedReader countReader = null;
		try {
			if (!fileCount.exists()) {
				fileCount.createNewFile();
				countIndex = 0;
			} else {
				countReader = new BufferedReader(new FileReader(fileCount));
				String tmp = countReader.readLine();
//				Logger.i("getAccount=======ctmp===================--==" + tmp);
				if (tmp != null)
					countIndex = Integer.parseInt(tmp);
//				Logger.i("getAccount=======countIndex===================--==" + countIndex);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Logger.i("getAccount=======e========================" + e);
		} finally {
			try {
				if (countReader != null) {
					countReader.close();
					countReader = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return countIndex;
	}

	/**
	 * Google Account信息转换
	 * 
	 * @param context
	 * @param sorce
	 */
	public static boolean formatGoogelAccountFile(Context context, File sorce) {
		File dir = checkDir();
		BufferedReader br = null;
		BufferedWriter bw = null;
		File destination = new File(dir,
				GlobalConstants.FILE_DES_GOOGLE_ACCOUNT);
		if (!destination.exists()) {
			try {
				destination.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			br = new BufferedReader(new FileReader(sorce));
			bw = new BufferedWriter(new FileWriter(destination));
			String tmp = null;
			while ((tmp = br.readLine()) != null) {
				int endE = tmp.indexOf(",");
				String email = tmp.substring(0, endE);
//				 Log.i("PasswordEncrypter","========email========================"+email);
				int start = tmp.indexOf(",");
				int end = tmp.lastIndexOf(",");
				String oldPass = tmp.substring(start + 1, end);
//				 Log.i("PasswordEncrypter","========oldPass========================"+oldPass);
				String newPass = GoogelAccountUtil.getPassword(context, email,
						oldPass);
				if (TextUtils.isEmpty(newPass)) {
					return false;
				}
//				 Log.i("PasswordEncrypter","========newPass========================"+newPass);
				String s = tmp.replace(oldPass, newPass);
//				 Log.i("PasswordEncrypter","========tmp========================"+tmp);
//				 Log.i("PasswordEncrypter","========s========================"+s);
				bw.write(s + "\n");
			}
			bw.flush();
			deleteFile(new File(checkDir(), GlobalConstants.FILE_GOOGLE_ACCOUNT_COUNT));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (br != null) {
					br.close();
					br = null;
				}
				if (bw != null) {
					bw.close();
					bw = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 判断Goolge Account 更改后的账户文件是否存在
	 * @return
	 */
	public static boolean isGoogleAccountDesExist(){
		File dir = checkDir();
		File des = new File(dir, GlobalConstants.FILE_DES_GOOGLE_ACCOUNT);
		return des.exists();
	}
	/**
	 * 获取账户信息条数
	 * @return
	 */
	public static int getGoogleAccountCount(){
		int count = 0;
		File file = new File(checkDir(), GlobalConstants.FILE_DES_GOOGLE_ACCOUNT);
		if(!isGoogleAccountDesExist()){
			return 0;
		}
		BufferedReader reader = null;
		try{
			reader=new BufferedReader(new FileReader(file));
			String tmp=null;
			while ((tmp=reader.readLine())!=null) {
				count++;
			}
			return count;
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			try {
				if(reader!=null){
					reader.close();reader=null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return count;
	}
	
	public static void recodeAccount(String name){
		Map<String, String> account=getAccount();
		String accountStr=null;
		if(account!=null){
			accountStr=account.get(GlobalConstants.MAP_EMAIL)+","+account.get(GlobalConstants.MAP_PASS)+",\n";
		}
		File file=new File(checkDir(),name/* GlobalConstants.FILE_GOOGLE_ACCOUNT_RECODE*/);
		 RandomAccessFile randomFile=null;
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			randomFile= new RandomAccessFile(file, "rw");
			  long fileLength = randomFile.length();
			  randomFile.seek(fileLength);
			  randomFile.writeBytes(accountStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			try {
				if(randomFile!=null){
					randomFile.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
