package com.hdj.downapp_market.activity;

import com.hdj.downapp_market.R;
import com.hdj.downapp_market.presenter.ChoseMarketPresenter;
import com.hdj.downapp_market.view.IChoseMarketView;
import com.mz.bean.MARKET;
import com.mz.utils.EasyOperateClickUtil;
import com.mz.utils.GlobalConstants;
import com.mz.utils.Logger;
import com.mz.utils.SprefUtil;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ChoseMarketActivity extends BaseActivity implements
		IChoseMarketView {
	private RadioGroup rgMarket;
	private RadioButton rbBaiDu, rb360, rbYyb, rbWdj, rbPPAssistant, rbAnZhi,
			rbMu, rbGp;
	private ChoseMarketPresenter presenter;
	private TextView tvChoseMarketTips,tvGoogelAccount,tvGoogelAccountSrc,tvGoogelAccountSrc2,tvGoogelAccountDes,tvGoogelAccountAccessibleCount;
	private String marketTips;
	MARKET market;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chose_market);
		initView();
		setEvent();
		presenter = new ChoseMarketPresenter(this, this);
		
		setTitle(getString(R.string.choose_market));
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		presenter.init();
	}

	private void initView() {
		tvChoseMarketTips = (TextView) findViewById(R.id.tv_chose_market_tips);
		tvGoogelAccount = (TextView) findViewById(R.id.tv_googel_account);
		tvGoogelAccountSrc = (TextView) findViewById(R.id.tv_googel_account_src);
		tvGoogelAccountSrc2 = (TextView) findViewById(R.id.tv_googel_account_src2);
		tvGoogelAccountDes = (TextView) findViewById(R.id.tv_googel_account_des);
		tvGoogelAccountAccessibleCount = (TextView) findViewById(R.id.tv_googel_account_accessible_count);
		rgMarket = (RadioGroup) findViewById(R.id.rg_market);
		rbBaiDu = (RadioButton) findViewById(R.id.rb_bd);
		rb360 = (RadioButton) findViewById(R.id.rb_360);
		rbYyb = (RadioButton) findViewById(R.id.rb_yyb);
		rbWdj = (RadioButton) findViewById(R.id.rb_wdj);
		rbPPAssistant = (RadioButton) findViewById(R.id.rb_pp_assistant);
		rbAnZhi = (RadioButton) findViewById(R.id.rb_az);
		rbMu = (RadioButton) findViewById(R.id.rb_mu);
		rbGp = (RadioButton) findViewById(R.id.rb_gp);
		int value = SprefUtil.getInt(this, SprefUtil.C_MARKET,MARKET.MARKET_BAIDU.value());
		market = MARKET.valueOf(value);
		
		checkRadioButton(market);
	}

	private void setEvent() {
		rgMarket.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				presenter.changeMarket(checkedId);
			}
		});
		tvGoogelAccount.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				presenter.formatAccount();
			}
		});
	}

	@Override
	public void checkRadioButton(MARKET market) {
		tvGoogelAccount.setVisibility(View.GONE);
		switch (market) {
		case MARKET_BAIDU:
			rbBaiDu.setChecked(true);
			marketTips = getString(R.string.market_baidu);
			break;
		case MARKET_360:
			rb360.setChecked(true);
			marketTips = getString(R.string.market_360);
			break;
		case MARKET_YYB:
			rbYyb.setChecked(true);
			marketTips = getString(R.string.market_yyb);
			break;
		case MARKET_WDJ:
			rbWdj.setChecked(true);
			marketTips = getString(R.string.market_wdj);
			break;
		case MARKET_PP:
			rbPPAssistant.setChecked(true);
			marketTips = getString(R.string.market_pp_assistant);
			break;
		case MARKET_AZ:
			rbAnZhi.setChecked(true);
			marketTips = getString(R.string.market_anzhi);
			break;
		case MARKET_MU:
			rbMu.setChecked(true);
			marketTips = getString(R.string.market_miu);
			break;
		case MARKET_GP:
			rbGp.setChecked(true);
			marketTips = getString(R.string.market_gp);
//			tvGoogelAccount.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}
		tvChoseMarketTips.setText(String.format(
				getString(R.string.choose_market_tips), marketTips));
		if(SprefUtil.getBool(this, SprefUtil.C_GOOGLE_VPN, false)){
			if(market.value()==MARKET.MARKET_GP.value()){
				EasyOperateClickUtil.setAutoVpnFlag(this,EasyOperateClickUtil.AUTO_VPN);
			}else {
				EasyOperateClickUtil.setAutoVpnFlag(this,EasyOperateClickUtil.DO_NOT_AUTO_VPN);
			}
		}
		else {
			EasyOperateClickUtil.setAutoVpnFlag(this,EasyOperateClickUtil.DO_NOT_AUTO_VPN);
		}
		
	}

	@Override
	public void toast(String tips) {
		showToast(tips);
	}

	@Override
	public void onBackPressed() {
		setResult(Activity.RESULT_OK);
		finish();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		presenter.onActivityResults(requestCode, resultCode, data);
	}

	@Override
	public void setTextGoogelAccountSrc(String str) {
		tvGoogelAccountSrc2.setText(str);
	}

	@Override
	public void setTextGoolgeAccountDes(String str) {
		tvGoogelAccountDes.setText(str);
	}

	@Override
	public void setTextGoogelAccountAccessibleCount(String str) {
		tvGoogelAccountAccessibleCount.setText(str);
	}
}
