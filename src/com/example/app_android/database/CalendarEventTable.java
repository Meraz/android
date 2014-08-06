package com.example.app_android.database;

import com.example.app_android.util.Utilities;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CalendarEventTable extends BaseTable implements ICalendarEventTable{

	private static final String TABLE_NAME = "calendarEvents";
	
	private static final String COLUMN_ID 			= "id";
	private static final String COLUMN_TITLE 		= "title";
	private static final String COLUMN_DESCRIPTION 	= "description";
	private static final String COLUMN_STARTTIME 	= "startTime";
	private static final String COLUMN_ENDTIME 		= "endTime";
	
	private static final String COLUMN_ID_TYPE 			= "INTEGER PRIMARY KEY";
	private static final String COLUMN_TITLE_TYPE 		= "VARCHAR(255)";
	private static final String COLUMN_DESCRIPTION_TYPE = "TEXT";
	private static final String COLUMN_STARTTIME_TYPE 	= "VARCHAR(15)";
	private static final String COLUMN_ENDTIME_TYPE 	= "VARCHAR(15)";
	
	private static final String LOCAL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " " +
			"("+
				COLUMN_ID 			+ " " + COLUMN_ID_TYPE 			+ ", " +
				COLUMN_TITLE 		+ " " + COLUMN_TITLE_TYPE 		+ ", " +
				COLUMN_DESCRIPTION 	+ " " + COLUMN_DESCRIPTION_TYPE + ", " +
				COLUMN_STARTTIME 	+ " " + COLUMN_STARTTIME_TYPE 	+ ", " +
				COLUMN_ENDTIME 		+ " " + COLUMN_ENDTIME_TYPE 	+
			")";
	
	private static final String RETRIEVE_EVENT  = "select * from " + TABLE_NAME + " where " + COLUMN_TITLE +" = ? and "+
	COLUMN_DESCRIPTION + " = ? and " + COLUMN_STARTTIME + " = ? and " + COLUMN_ENDTIME + " = ?";
	
	public CalendarEventTable(SQLiteOpenHelper SQLiteOpenHelper) {
		super(SQLiteOpenHelper);
		SQL_CREATE_TABLE = LOCAL_CREATE_TABLE;
		SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
	}

	@Override
	public boolean add(long id, String title, String description ,String startTime, String endTime) {
		if(Utilities.verbose) {Log.v(TAG, mClass + ":createTable()");}
		SQLiteDatabase db = mHelper.getWritableDatabase();
		db.beginTransaction();
		
		ContentValues values = new ContentValues();
		values.put(COLUMN_ID, id);
		values.put(COLUMN_TITLE, title);
		values.put(COLUMN_DESCRIPTION, description);
		values.put(COLUMN_STARTTIME, startTime);
		values.put(COLUMN_ENDTIME, endTime);
		long resultId = db.insert(TABLE_NAME, null, values);
		
		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();
		
		return resultId >= 0;
	}

	@Override
	public boolean remove(long id) {
		if(Utilities.verbose) {Log.v(TAG, mClass + ":remove()");}
		SQLiteDatabase db = mHelper.getWritableDatabase();
		db.beginTransaction();
		
		int deletedRowCount = db.delete(TABLE_NAME, COLUMN_ID + "=" + "'" + id + "'", null);
		
		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();
		
		return deletedRowCount > 0;
	}

	@Override
	public int getEventId(String title, String description, String startTime, String endTime) {
		if(Utilities.verbose) {Log.v(TAG, mClass + ":getEventId()");}
		int id = -1;
		
		SQLiteDatabase db = mHelper.getWritableDatabase();
		db.beginTransaction();
		
		Cursor cursor = db.rawQuery(RETRIEVE_EVENT, new String[] { title, description, startTime, endTime });
		if (cursor.moveToFirst()) {
			id = cursor.getInt(0);
		}
		
		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();
		 
		return id;
	}
}