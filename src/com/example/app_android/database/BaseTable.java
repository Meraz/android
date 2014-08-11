package com.example.app_android.database;

import com.example.app_android.util.Utilities;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BaseTable {
	
	/*
	 * All tables created to be used in this databasemanager-system must inherit this class. 
	 * Used to ease the functionality of create, initialize, drop and logging
	 * 
 	 * All functions inherited may throw SQLException. BUT ONLY FUNCTION INHERITED FROM THIS CLASS. 
 	 * All (other) table interfaces should throw DBException instead. Read DBException for more information.
	 */
	
	protected static final String TAG = "Database";
	
	protected SQLiteOpenHelper mHelper;
	protected String SQL_CREATE_TABLE;
	protected String SQL_DROP_TABLE;
	protected String SQL_DEFAULT_VALUES;

	protected String mClass;	// TODO rename mClassName, diden't want to do this when found. Might change many files.
		
	public BaseTable(SQLiteOpenHelper SQLiteOpenHelper) {
		mClass = getClass().getSimpleName();
		
		mHelper = SQLiteOpenHelper;
	}
	
	public void createTable(SQLiteDatabase db) throws SQLException, DBException {
		if(Utilities.verbose) {Log.v(TAG, mClass + ":createTable()");}
		if(SQL_CREATE_TABLE != null && !SQL_CREATE_TABLE.isEmpty())
			db.execSQL(SQL_CREATE_TABLE);
	}

	public void dropTable(SQLiteDatabase db) throws SQLException, DBException {
		if(Utilities.verbose) {Log.v(TAG, mClass + ":dropTable()");}
		if(SQL_DROP_TABLE != null && !SQL_DROP_TABLE.isEmpty())
			db.execSQL(SQL_DROP_TABLE);		
	}
	
	public void fillTableWithDefaultData(SQLiteDatabase db) throws SQLException, DBException {
		if(Utilities.verbose) {Log.v(TAG, mClass + ":fillTableWithDefaultData()");}
		if(SQL_DEFAULT_VALUES != null && !SQL_DEFAULT_VALUES.isEmpty())
			db.execSQL(SQL_DEFAULT_VALUES);		
	}
}