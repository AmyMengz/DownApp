package com.hdj.downapp_market.activity;

import java.util.List;

import com.hdj.downapp_market.R;
import com.hdj.downapp_market.presenter.ChoseDownAppPresenter;
import com.hdj.downapp_market.view.IChoseDownApkView;
import com.mz.bean.AppInfo;
import com.mz.utils.GlobalConstants;
import com.mz.utils.ListViewHelpUtil;
import com.mz.utils.Logger;
import com.mz.utils.SprefUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class ChoseDownAppActivity extends BaseActivity implements
		IChoseDownApkView {
	private ListView lv_appInfo;
	private TextView tvChooseApk;
	private EditText et_add_pn, et_add_app_name;
	private Button btn_get_app_sd, btn_addToDB;
	private MyAdapter adapter;
	private List<AppInfo> list;
	private int selection;
	private ChoseDownAppPresenter presenter;
	private String downPackageName = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_download_app);
		selection = SprefUtil.getInt(this, SprefUtil.C_SELECTION, -1);
		presenter = new ChoseDownAppPresenter(this, this);
		initView();
		setEvent();
		notifyApkChoseTips();
		ListViewHelpUtil.setListViewHeightBasedOnChildren(lv_appInfo);
		setTitle(getString(R.string.choose_or_add_apk));
	}

	private void initView() {
		lv_appInfo = (ListView) findViewById(R.id.lv_app_choose_list);
		list = presenter.loadAllApp();
		if (list != null)
			adapter = new MyAdapter(this, list);
		adapter.setSelection(selection);
		lv_appInfo.setAdapter(adapter);
		tvChooseApk = (TextView) findViewById(R.id.tv_choose_apk);
		btn_get_app_sd = (Button) findViewById(R.id.btn_get_app_sd);
		btn_addToDB = (Button) findViewById(R.id.btn_package_name_set);
		et_add_pn = (EditText) findViewById(R.id.et_package_name);
		et_add_app_name = (EditText) findViewById(R.id.et_app_name);
	}

	private void setEvent() {
		btn_addToDB.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				presenter.addToDB();

			}
		});
		lv_appInfo.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				presenter.itemClick(position);
			}
		});
		lv_appInfo.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				presenter.itemLongClick(position);
				return true;
			}
		});
		btn_get_app_sd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				presenter.addFromSD();

			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		presenter.OnActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void setIAPPName(String appName) {
		et_add_app_name.setText(appName);
	}

	@Override
	public void setIPackageName(String packageName) {
		et_add_pn.setText(packageName);
	}

	@Override
	public String getIAPPName() {
		return et_add_app_name.getText().toString();
	}

	@Override
	public String getIPackageName() {
		return et_add_pn.getText().toString();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		presenter = null;
	}

	@Override
	public void notifyList() {
		List<AppInfo> list2 = presenter.loadAllApp();
		list.clear();
		list.addAll(list2);
		adapter.setList(list);
		adapter.setSelection(selection);
		// adapter.setSelection(selection);
		adapter.notifyDataSetChanged();
		notifyApkChoseTips();
		ListViewHelpUtil.setListViewHeightBasedOnChildren(lv_appInfo);
	}

	@Override
	public void notifyListSelect(int position) {
		adapter.setSelection(position);
		adapter.notifyDataSetChanged();
		setSelection(position);
	}

	@Override
	public void notifyApkChoseTips() {
		List<AppInfo> list2 = presenter.loadAllApp();
		Logger.i("=======================sele" + selection);
		if (list2 == null || list2.size() <= 0) {
			setSelection(-1);
			tvChooseApk.setText(getString(R.string.choose_to_add_apk));

		} else {
			if (selection == -1) {
				setSelection(0);
				adapter.setSelection(selection);
			}
			tvChooseApk.setText(String.format(
					getString(R.string.current_pn_tips), list.get(selection)
							.getPackageName()));
		}
	}

	@Override
	public void toast(String tips) {
		showToast(tips);
	}

	@Override
	public String getIAPPNamebyPosition(int position) {
		return list.get(position).getAppName();
	}

	@Override
	public String getIPackagebyPosition(int position) {
		return list.get(position).getPackageName();
	}

	@Override
	public void setIAPPNameByPosition(int position, String appName) {
		list.get(position).setAppName(appName);
	}

	@Override
	public void setIPackageNameByPosition(int position, String packageName) {
		list.get(position).setPackageName(packageName);
	}

	@Override
	public AppInfo getIAPPInfoByPosition(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public void setSelection(int selection) {
		this.selection = selection;
		SprefUtil.putInt(this, SprefUtil.C_SELECTION, selection);
	}

	@Override
	public int getSelection() {
		// TODO Auto-generated method stub
		return selection;
	}

	@Override
	public void onBackPressed() {
		setResult(Activity.RESULT_OK);
		finish();
	}
}
