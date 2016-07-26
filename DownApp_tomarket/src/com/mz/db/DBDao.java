package com.mz.db;

import java.util.ArrayList;
import java.util.List;

import u.aly.l;

import com.mz.bean.AppInfo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBDao {
	static DBDao instance;
	Context context;

	private DBDao(Context context) {
		this.context = context;
	}

	public static DBDao getInstance(Context context) {
		if (instance == null) {
			synchronized (context) {
				if (instance == null) {
					instance = new DBDao(context);
				}
			}
		}
		return instance;
	}

	public SQLiteDatabase getConnection() {
		SQLiteDatabase db = null;
		db = new DBHelper(context).getWritableDatabase();
		return db;
	}

	public void insertAPP(AppInfo app) {
		if (app == null)
			return;
		SQLiteDatabase db = getConnection();
		try {
			String sql = "insert into app_list(" + DBHelper.C_PACKAGE_NAME
					+ "," + DBHelper.C_APP_NAME + " ," + DBHelper.C_STATUS
					+ ") values (?,?,?);";
			Object[] bindArgs = { app.getPackageName(), app.getAppName(),app.getStatus() };
			db.execSQL(sql, bindArgs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null) {
				db.close();
			}
		}
	}

	public List<AppInfo> loadAll() {
		SQLiteDatabase db = getConnection();
		Cursor cursor = null;
		List<AppInfo> list = new ArrayList<AppInfo>();
		try {
			String sql = "select * from app_list;";
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				AppInfo app = new AppInfo();
				app.setAppName(cursor.getString(cursor
						.getColumnIndex(DBHelper.C_APP_NAME)));
				app.setPackageName(cursor.getString(cursor
						.getColumnIndex(DBHelper.C_PACKAGE_NAME)));
				app.setStatus(cursor.getInt(cursor
						.getColumnIndex(DBHelper.C_STATUS)));
				list.add(app);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null) {
				db.close();
			}
		}
		return list;
	}

	public void remove(AppInfo app) {
		SQLiteDatabase db = getConnection();
		try {
			String sql = "delete from " + DBHelper.TABLE_APP + " where "
					+ DBHelper.C_PACKAGE_NAME + " =?";
			Object[] bindArgs = { app.getPackageName() };
			db.execSQL(sql, bindArgs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null)
				db.close();
		}
	}
	public boolean update(AppInfo app,String packageName){
		SQLiteDatabase db=getConnection();
		try{
//			String sql="update "+DBHelper.TABLE_APP+" set "+DBHelper.C_APP_NAME+" =\"?\""+" where "+DBHelper.C_PACKAGE_NAME+"=\"?\";";
			String sql="update "+DBHelper.TABLE_APP+" set "+DBHelper.C_APP_NAME+"=\""+app.getAppName()+"\","+DBHelper.C_PACKAGE_NAME+"=\""
					+app.getPackageName()
					+"\" where "+DBHelper.C_PACKAGE_NAME+"=\""+packageName+"\";";
			Object[] bindArgs = { app.getAppName(),packageName };
			db.execSQL(sql);
			return true;
		}catch(Exception e){
			return false;
		}
	}

}
