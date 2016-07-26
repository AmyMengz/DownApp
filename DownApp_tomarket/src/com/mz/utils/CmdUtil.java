package com.mz.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import android.text.TextUtils;

public class CmdUtil {
	
	public static Process run(String cmd) throws Exception{
		ProcessBuilder builder=new ProcessBuilder("su");
		Process process=builder.start();
		DataOutputStream dos=new DataOutputStream(process.getOutputStream());
		dos.writeBytes(cmd+"\n");
		dos.flush();
		dos.writeBytes("exit \n");
		dos.flush();
		dos.close();
		process.waitFor();
		int result = process.exitValue();
		return process;
	}
	public static String readResult(Process process) throws Exception{
		DataInputStream in=new DataInputStream(process.getInputStream());
		StringBuffer sb=new StringBuffer();
		byte[] buffer=new byte[1024];
		int tmp=-1;
		while ((tmp=in.read(buffer))!=-1) {
			sb.append(new String(buffer, 0, tmp));
		}
		return sb.toString();
	}
	public static boolean isRoot() {
		try {
			Process p = run("date ");
			p.waitFor();
			String out = readResult(p);
			if (!TextUtils.isEmpty(out))
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}


}
