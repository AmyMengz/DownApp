package com.hdj.downapp_market.presenter;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.hdj.downapp_market.R;
import com.hdj.downapp_market.activity.ChoseMarketActivity;
import com.hdj.downapp_market.view.IChoseMarketView;
import com.mz.bean.MARKET;
import com.mz.utils.EasyOperateClickUtil;
import com.mz.utils.MyFileUtil;
import com.mz.utils.GlobalConstants;
import com.mz.utils.Logger;
import com.mz.utils.SprefUtil;

public class ChoseMarketPresenter {
	MARKET market;
	IChoseMarketView choseMarketView=null;
	Context context;
	
	public ChoseMarketPresenter(Context context,IChoseMarketView choseMarketView) {
		this.choseMarketView=choseMarketView;
		this.context=context;
	}
	public void init() {
		goolgeDesAccountFileCheck();
		
	}
	/**
	 * Google账户文件是否存在
	 */
	public void goolgeDesAccountFileCheck(){
		int totalAccount=MyFileUtil.getGoogleAccountCount();
		int index=MyFileUtil.getAccountIndex();
		Logger.i("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"+totalAccount+"===="+index);
		if(totalAccount>0&&index<totalAccount){
			//choseMarketView.setTextGoolgeAccountDes(String.format(context.getString(R.string.google_market_account_des), (new File(MyFileUtil.checkDir(), GlobalConstants.FILE_DES_GOOGLE_ACCOUNT)).getAbsolutePath()));
			choseMarketView.setTextGoogelAccountSrc(context.getString(R.string.google_market_account_src2_ok));
		}
		else {
			choseMarketView.setTextGoolgeAccountDes(context.getString(R.string.google_market_account_src2));

			choseMarketView.setTextGoolgeAccountDes(context.getString(R.string.google_market_account_des_not_exist));
		}
		choseMarketView.setTextGoogelAccountAccessibleCount(String.format(context.getString(R.string.google_market_account_accessible_count), totalAccount-index));
	}
	/**
	 * 更改应用市场选择
	 * @param checkedId
	 */
	public void changeMarket(int checkedId){
		switch (checkedId) {
		case R.id.rb_bd:
			market = MARKET.MARKET_BAIDU;
			break;
		case R.id.rb_360:
			market = MARKET.MARKET_360;
			break;
		case R.id.rb_yyb:
			market = MARKET.MARKET_YYB;
			break;
		case R.id.rb_wdj:
			market = MARKET.MARKET_WDJ;
			choseMarketView.toast(context.getString(R.string.wdj_no_half));
			break;
		case R.id.rb_pp_assistant:
			market = MARKET.MARKET_PP;
			break;
		case R.id.rb_az:
			market = MARKET.MARKET_AZ;
			break;
		case R.id.rb_mu:
			market = MARKET.MARKET_MU;
			break;
		case R.id.rb_gp:
			market = MARKET.MARKET_GP;
			break;
		default:
			break;
		}
		if(checkedId==R.id.rb_wdj){
			choseMarketView.toast(context.getString(R.string.wdj_no_half));
		}
		SprefUtil.putInt(context, SprefUtil.C_MARKET, market.value());
		choseMarketView.checkRadioButton(market);
	}
	/**
	 * 格式化google账户信息
	 */
	public void formatAccount() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType(".txt text/plain");// 设置类型，我这里是任意类型，任意后缀的可以这样写。
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		((ChoseMarketActivity)context).startActivityForResult(intent, GlobalConstants.requestSDText);
	}
	public void onActivityResults(int requestCode, int resultCode, Intent data) {
		if(resultCode==Activity.RESULT_OK&&requestCode==GlobalConstants.requestSDText){
			Uri uri=data.getData();
			Logger.i("uri======="+uri);
			if(!uri.toString().endsWith(".txt"))
			{
				choseMarketView.toast(context.getString(R.string.google_market_account_src_must_txt));
				return;
			}
			try {
				File sourceFile=new File(new URI(uri.toString()));
				choseMarketView.setTextGoogelAccountSrc(context.getString(R.string.google_market_account_src)+ sourceFile.getAbsolutePath());

				boolean result=MyFileUtil.formatGoogelAccountFile(context, sourceFile);
				if(result) 
					//choseMarketView.setTextGoolgeAccountDes(String.format(context.getString(R.string.google_market_account_des), (new File(MyFileUtil.checkDir(), GlobalConstants.FILE_DES_GOOGLE_ACCOUNT)).getAbsolutePath()));
					goolgeDesAccountFileCheck();
				else {
					choseMarketView.setTextGoolgeAccountDes(context.getString(R.string.google_market_account_des_failed));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
