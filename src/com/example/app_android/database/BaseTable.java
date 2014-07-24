package com.example.app_android.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseTable {
	protected SQLiteOpenHelper mHelper;
	protected String SQL_CREATE_TABLE;
	protected String SQL_DROP_TABLE;
	protected String SQL_DEFAULT_VALUES;
		
	public BaseTable(SQLiteOpenHelper SQLiteOpenHelper) {
		mHelper = SQLiteOpenHelper;
	}
	
	public void createTable(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_TABLE);
	}

	public void dropTable(SQLiteDatabase db) {
        db.execSQL(SQL_DROP_TABLE);		
	}
	
	public void fillTableWithDefaultData(SQLiteDatabase db) {
       db.execSQL(SQL_DEFAULT_VALUES);		
	}
}
