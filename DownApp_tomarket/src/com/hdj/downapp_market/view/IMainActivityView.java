package com.hdj.downapp_market.view;

public interface IMainActivityView {
	public void setTvChooseMarket(String tips);
	public void setTvChoseApk(String tips);
	public void toast(String tips);
	public void setTvDownPercentTips(String tips);
	public String getEtPercent();
	public void setEtPercent(int flag,String tips);
	
	public String getEtInstallPeriod();
	public void setEtInstallPeriod(int flag,String tips);
	public void setTvInstallPeriodTips();
	
	public void setAutoMarketChecked();
	public void setAutoHookChecked();
	public void setAutoClickChecked();
	public void setAutoRebootChecked();
	public void setAutoVPNChecked();
	
	public void setRebootTime(String tips);
	
	public void setTitles(String tips);

}
