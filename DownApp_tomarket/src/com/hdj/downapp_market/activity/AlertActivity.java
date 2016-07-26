package com.hdj.downapp_market.activity;

import com.hdj.downapp_market.R;
import com.hdj.downapp_market.R.drawable;
import com.hdj.downapp_market.R.string;
import com.mz.utils.GlobalConstants;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class AlertActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent intent=getIntent();
		int flag=intent.getIntExtra(GlobalConstants.ALERT_ACTIVITY_FLAG, GlobalConstants.ALERT_ACTIVITY_NO_ROOT);
		if(flag==GlobalConstants.ALERT_ACTIVITY_NO_ROOT){
			setTitle(getString(R.string.neet_root));
		}else if (flag==GlobalConstants.ALERT_ACTIVITY_NO_ACCOUNT) {
			setTitle(getString(R.string.google_market_no_more_account));
		}
		ImageView imgae=new ImageView(this);
		imgae.setBackgroundResource(R.drawable.ic_launcher);
		setContentView(imgae);
	}

}
