package com.hdj.downapp_market.view;

import com.mz.bean.AppInfo;

public interface IChoseDownApkView {
	public void setIAPPName(String appName);
	public void setIPackageName(String packageName);
	public String getIAPPName();
	public String getIPackageName();
	public void setIAPPNameByPosition(int position,String appName);
	public void setIPackageNameByPosition(int position,String packageName);
	public String getIAPPNamebyPosition(int position);
	public String getIPackagebyPosition(int position);
	public AppInfo getIAPPInfoByPosition(int position);
	public void notifyList();
	public void notifyListSelect(int position);
	public void notifyApkChoseTips();
	public void setSelection(int position);
	public int getSelection();
	public void toast(String tips);
}
