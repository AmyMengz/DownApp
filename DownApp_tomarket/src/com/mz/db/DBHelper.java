package com.mz.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{
	private static final String DBNAME="downApp";
	public static final String C_PACKAGE_NAME="package_name";
	public static final String C_APP_NAME="app_name";
	public static final String C_STATUS="status";
	public static final String TABLE_APP="app_list";

	public DBHelper(Context context) {
		super(context, DBNAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		 db.execSQL("create table app_list(package_name VARCHAR(255) PRIMARY KEY,app_name VARCHAR(255),status INTEGER)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
