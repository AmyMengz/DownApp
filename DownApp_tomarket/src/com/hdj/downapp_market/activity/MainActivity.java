package com.hdj.downapp_market.activity;

import com.hdj.downapp_market.R;
import com.hdj.downapp_market.presenter.MainActivityPresenter;
import com.hdj.downapp_market.view.IMainActivityView;
import com.mz.utils.ActivityUtils;
import com.mz.utils.EasyOperateClickUtil;
import com.mz.utils.GlobalConstants;
import com.mz.utils.Logger;
import com.mz.utils.MyFileUtil;
import com.mz.utils.SprefUtil;
import com.mz.utils.TTSUtil;
import com.mz.utils.VpnUtil;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends BaseActivity implements IMainActivityView {

	private TextView tvPercentTips, tvInstallPeriodTips, tvRebootRecodeTips,
			tvChooseApk, tvChoseMarketTips;
	private static Button btn_down;
	private Button btnPercentSet, btnInstallPeriodSet;
	private EditText etPercent, etInstallPeriod;
	private ToggleButton tbTomarket, tbToHdjHook, tbAutoClick, tbAutoReboot,tbAutoVpn;
	private Context context;
	private MainActivityPresenter presenter;

	/**
	 * 供广播调用跳转到应用市场
	 */
	public static void handleDownload(Context context) {
		if(btn_down!=null){
			btn_down.performClick();
		}else {
			ActivityUtils.jumpMarket(context);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;
		presenter = new MainActivityPresenter(this, this);
		findViewById();
		presenter.init();
	}

	private void findViewById() {
		tvChoseMarketTips = (TextView) findViewById(R.id.tv_chose_market2);
		tvChooseApk = (TextView) findViewById(R.id.tv_choose_apk);
		tvPercentTips = (TextView) findViewById(R.id.tv_percent_tips);
		tvInstallPeriodTips = (TextView) findViewById(R.id.tv_install_tips);
		tvRebootRecodeTips = (TextView) findViewById(R.id.tv_reboot_recode_tips);
		btnPercentSet = (Button) findViewById(R.id.btn_percent_set);
		btn_down = (Button) findViewById(R.id.btn_down);
		btnInstallPeriodSet = (Button) findViewById(R.id.btn_install_time_set);
		etPercent = (EditText) findViewById(R.id.et_percent);
		etInstallPeriod = (EditText) findViewById(R.id.et_install_time);
		tbTomarket = (ToggleButton) findViewById(R.id.tb_to_market);
		tbToHdjHook = (ToggleButton) findViewById(R.id.tb_to_hdj_hook);
		tbAutoClick = (ToggleButton) findViewById(R.id.tb_auto_click);
		tbAutoReboot = (ToggleButton) findViewById(R.id.tb_auto_reboot);
		tbAutoVpn = (ToggleButton) findViewById(R.id.tb_auto_vpn);
		setEvent();
	}

	private void setEvent() {
		tvChoseMarketTips.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				presenter.openChoseMarket();
			}
		});
		tvChooseApk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				presenter.openChoseAPK();
			}
		});
		tbTomarket.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						presenter.tbTomarketCheck(isChecked);
					}
				});

		tbToHdjHook.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						presenter.tbToHdjHookCheck(isChecked);
					}
				});
		tbAutoClick.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						presenter.tbAutoClickChecked(isChecked);
					}
				});
		tbAutoReboot.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						presenter.tbAutoRebootChecked(isChecked);
					}
				});
		tbAutoVpn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				presenter.tbAutoVpnChecked(isChecked);
			}
		});
		btn_down.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				presenter.jumpMarket();
			}
		});

		btnPercentSet.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				presenter.btnPercentSetClick();
			}
		});

		btnInstallPeriodSet.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				presenter.btnInstallPerioSetClikc();
			}
		});
		etPercent.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			@Override
			public void afterTextChanged(Editable s) {
				presenter.setEtPercentTextWatcher(s);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {// 是否选择，没选择就不会继续
			switch (requestCode) {
			case GlobalConstants.requestMarket:
				presenter.getTvChooseMarket();
				presenter.getTvDownPercentTips();
				break;
			case GlobalConstants.requestAPK:
				presenter.getTvApkTips();
				break;
			default:
				break;
			}
		}
	}
	@Override
	protected void onResume() {
		super.onResume();
		presenter.isGooglePlayAccountFileAccessible();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		presenter=null;
	};

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.btn_clear_count:		
			presenter.clearCount();
			break;
		case R.id.btn_vpn_test:
//			Intent intent=new Intent();
//			intent.setAction("android.net.vpn.SETTINGS");
//			context.startActivity(intent);
			Logger.i("----------------------------VPN---------------------------"+VpnUtil.isVpnUsed()+"===="+VpnUtil.isVpnConnect());
			if(!VpnUtil.isVpnUsed()){
				ActivityUtils.openApkByDetail(context, GlobalConstants.PROXY_PN2, GlobalConstants.PROXY_CN2);

				//	ActivityUtils.openApkByDetail(context, GlobalConstants.PROXY_PN, GlobalConstants.PROXY_CN);
			}
			break;
		case  R.id.btn_tts_root_test:
			presenter.ttsRootTest();
			break;
		case R.id.btn_tts_googel_test:
			presenter.ttsGoogelTest();
			break;
		case R.id.btn_account_remove:
			final AccountManager accountManager = AccountManager.get(context);
			Account[] accounts = accountManager.getAccountsByType("com.google");
			for (int i = 0; i < accounts.length; i++) {
				final Account account = accounts[i];
				Logger.i("account======"+account.toString()+"   ");
					accountManager.removeAccount(account, null, null);
			}
			accounts = accountManager.getAccountsByType("com.google");
			Logger.i("ACCOUNT*********************************************************-----44-------"+accounts.length);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void setTvChoseApk(String tips) {
		tvChooseApk.setText(tips);
	}

	@Override
	public void setTvChooseMarket(String tips) {
		tvChoseMarketTips.setText(tips);
	}

	@Override
	public void toast(String tips) {
		showToast(tips);
	}

	@Override
	public void setTvDownPercentTips(String tips) {
		tvPercentTips.setText(tips);
	}

	@Override
	public String getEtPercent() {
		return etPercent.getText().toString().trim();
	}

	@Override
	public void setEtPercent(int flag, String tips) {
		switch (flag) {
		case GlobalConstants.FLAG_ET_ERROT:
			etPercent.setError(tips);
			break;
		case GlobalConstants.FLAG_ET_NORMAL:
			int percent = EasyOperateClickUtil.getHalfDownloadValue(context);
			etPercent.setText(String.valueOf(percent));
			break;

		default:
			break;
		}
	}

	@Override
	public String getEtInstallPeriod() {
		if (TextUtils.isEmpty(etInstallPeriod.getText())) {
			return "";
		}
		return etInstallPeriod.getText().toString().trim();
	}

	@Override
	public void setEtInstallPeriod(int flag, String tips) {
		switch (flag) {
		case GlobalConstants.FLAG_ET_ERROT:
			etInstallPeriod.setError(tips);
			break;
		case GlobalConstants.FLAG_ET_NORMAL:
			int installTime = SprefUtil.getInt(context,
					SprefUtil.C_INSTALL_TIME, 0);
			etInstallPeriod.setText(String.valueOf(installTime));
		}
	}

	@Override
	public void setTvInstallPeriodTips() {
		int installTime = SprefUtil.getInt(context,
				SprefUtil.C_INSTALL_TIME, 0);
		tvInstallPeriodTips.setText(String.format(
				context.getString(R.string.set_install_tips), installTime));
	}

	@Override
	public void setAutoMarketChecked() {
		boolean autoMarket = SprefUtil.getBool(context,
				SprefUtil.C_AUTO_MARKET, true);
		tbTomarket.setChecked(autoMarket);
	}

	@Override
	public void setAutoHookChecked() {
		boolean autoHook = SprefUtil.getBool(context,
				SprefUtil.C_AUTO_HOOK, true);
		tbToHdjHook.setChecked(autoHook);
	}

	@Override
	public void setAutoClickChecked() {
		tbAutoClick
				.setChecked((EasyOperateClickUtil.getAutoClickFlag(context) == EasyOperateClickUtil.AUTOCLICK) ? true
						: false);
	}

	@Override
	public void setAutoRebootChecked() {
		tbAutoReboot
				.setChecked((EasyOperateClickUtil.getAutoRebootFlag(context) == EasyOperateClickUtil.AUTOREBOOT) ? true
						: false);
	}//
	@Override
	public void setAutoVPNChecked() {
		
		tbAutoVpn.setChecked(SprefUtil.getBool(context, SprefUtil.C_GOOGLE_VPN, false));
	}

	@Override
	public void setRebootTime(String tips) {
		tvRebootRecodeTips.setText(tips);
	}

	@Override
	public void setTitles(String tips) {
		setTitle(tips);
	}
}
