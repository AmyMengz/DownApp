package com.hdj.downapp_market.presenter;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import com.hdj.downapp_market.R;
import com.hdj.downapp_market.activity.ChoseDownAppActivity;
import com.hdj.downapp_market.activity.MainActivity;
import com.hdj.downapp_market.view.IChoseDownApkView;
import com.mz.bean.AppInfo;
import com.mz.db.DBDao;
import com.mz.utils.ActivityUtils;
import com.mz.utils.GlobalConstants;
import com.mz.utils.SprefUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ChoseDownAppPresenter {

	private Context context;
	private IChoseDownApkView choseDownApkView;
	private DBDao dbDao;
	public ChoseDownAppPresenter(Context context,IChoseDownApkView choseDownApkView) {
		this.context=context;
		this.choseDownApkView=choseDownApkView;
		dbDao = DBDao.getInstance(context);
	}
	/**
	 * 添加到数据库
	 */
	public void addToDB(){
		if (!TextUtils.isEmpty(choseDownApkView.getIAPPName())
				&& choseDownApkView.getIPackageName().length() > 0) {
			AppInfo app = new AppInfo();
			app.setAppName(choseDownApkView.getIAPPName());
			app.setPackageName(choseDownApkView.getIPackageName());
			dbDao.insertAPP(app);
			choseDownApkView.notifyList();
			choseDownApkView.setIAPPName("");
			choseDownApkView.setIPackageName("");
		}	
	}
	public void itemClick(int position){
		choseDownApkView.notifyListSelect(position);
		choseDownApkView.notifyApkChoseTips();
	}
	public void itemLongClick(int position){
		showChooseDialog(position);
	}
	
	public void addFromSD(){
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("*/*");// 设置类型，我这里是任意类型，任意后缀的可以这样写。
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		((ChoseDownAppActivity)context).startActivityForResult(intent, GlobalConstants.requestSD);
	}
	public void OnActivityResult(int requestCode, int resultCode, Intent data){
		if (resultCode == Activity.RESULT_OK) {// 是否选择，没选择就不会继续
			switch (requestCode) {
			case GlobalConstants.requestSD:
				Uri uri = data.getData();// 得到uri，后面就是将uri转化成file的过程。
				try {
					File file = new File(new URI(uri.toString()));
					String path = file.getAbsolutePath();
					String appName = path.substring(path.lastIndexOf("/") + 1,
							path.length());
					String packageName = ActivityUtils.getPackageName(context, path);
					choseDownApkView.toast(packageName);
					if (!TextUtils.isEmpty(packageName)) {
						
						AppInfo app = new AppInfo();
						app.setAppName(appName);
						app.setPackageName(packageName);
						dbDao.insertAPP(app);
						choseDownApkView.notifyList();
					}
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}
	
	public List<AppInfo> loadAllApp(){
		return dbDao.loadAll();
	}
	
	protected void showChooseDialog(final int position) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		String[] items = { context.getString(R.string.change_item),context.getString(R.string.delete_item) };
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case 0:
					showChangeDialog(position);
					break;
				case 1:
					showDeleteDialog(position);
					break;
				}
			}
		});
		builder.create().show();
	}
	public void showChangeDialog(final int position) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(context.getString(R.string.change_item_info));
		View view = LayoutInflater.from(context).inflate(
				R.layout.dialog_chang_item, null);
		builder.setView(view);
		final EditText et_appName = (EditText) view.findViewById(R.id.app_name);
		final EditText et_packageName = (EditText) view
				.findViewById(R.id.app_package_name);
		final String oldPN = choseDownApkView.getIPackagebyPosition(position);
		et_appName.setText(choseDownApkView.getIAPPNamebyPosition(position));
		et_packageName.setText(choseDownApkView.getIPackagebyPosition(position));
		builder.setPositiveButton(context.getString(R.string.dialog_ok),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						choseDownApkView.setIAPPNameByPosition(position,et_appName.getText().toString());
						choseDownApkView.setIPackageNameByPosition(position,et_packageName.getText().toString());
						AppInfo app = new AppInfo(et_packageName.getText()
								.toString(), et_appName.getText().toString(), 0);
						boolean result = dbDao.update(app, oldPN);
						if (!result)
							choseDownApkView.toast(context.getString(R.string.change_qpp_error));
						choseDownApkView.notifyList();
					}
				});
		builder.setNegativeButton(context.getString(R.string.dialog_cancle),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						builder.create().dismiss();
					}
				});
		builder.create().show();

	}

	public void showDeleteDialog(final int position) {
		AlertDialog dialog = new AlertDialog.Builder(context)
				.setPositiveButton(context.getString(R.string.dialog_ok),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dbDao.remove(choseDownApkView.getIAPPInfoByPosition(position));
								if (choseDownApkView.getSelection() == position) {
//									if(choseDownApkView.getSelection()>=1)
										choseDownApkView.setSelection(position - 1);
									
								}
								choseDownApkView.notifyList();
							}
						})
				.setNegativeButton(context.getString(R.string.cancel),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						}).create();
		dialog.setTitle(context.getString(R.string.dialog_tips));
		dialog.show();
	}


}
