package com.mz.bean;

public class AppInfo {
	String packageName;
	String appName;
	int status;
	public AppInfo() {
		// TODO Auto-generated constructor stub
	}
	public AppInfo(String packageName,String appName,int status) {
		this.packageName=packageName;
		this.appName=appName;
		this.status=status;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	@Override
	public String toString() {
		return "AppInfo [packageName=" + packageName + ", appName=" + appName
				+ ", status=" + status + "]";
	}
	

}
