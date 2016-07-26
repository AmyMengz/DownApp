package com.hdj.downapp_market.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class BaseActivity extends Activity {
	Toast toast=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	protected void showToast(String tips) {
		toast = toast == null ? Toast.makeText(getApplicationContext(), "",
				Toast.LENGTH_SHORT) : toast;
		toast.setText(tips);
		toast.show();
	}


}
