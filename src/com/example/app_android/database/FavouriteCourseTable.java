package com.example.app_android.database;

import java.util.ArrayList;

import com.example.app_android.database.interfaces.IFavouriteCourseTable;
import com.example.app_android.util.Utilities;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FavouriteCourseTable extends BaseTable implements IFavouriteCourseTable {
	private static final String TABLE_NAME = "favouriteCourses";
	
	private static final String COLUMN_COURSE_CODE = "courseCode";
	
	private static final String COULUMN_COURSE_CODE_TYPE = "VARCHAR(8) PRIMARY KEY";
	 
	private static final String LOCAL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " " +
				"("+
					COLUMN_COURSE_CODE	 	+ " " + 	COULUMN_COURSE_CODE_TYPE +
				")";
	 
	public FavouriteCourseTable(SQLiteOpenHelper SQLiteOpenHelper) {
		super(SQLiteOpenHelper);
		SQL_CREATE_TABLE = LOCAL_CREATE_TABLE;
		SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
	}
	 
	@Override
	public void fillTableWithDefaultData(SQLiteDatabase db) throws SQLException, DBException {
		super.fillTableWithDefaultData(db);
		db.beginTransaction();
			
		//TODO - remove test data
		ContentValues values = new ContentValues();
		values.put(COLUMN_COURSE_CODE, "testCode");		
		int result = -1;
		try {
			result = (int) db.insert(TABLE_NAME, null, values);
			db.setTransactionSuccessful();
			
		}catch(NullPointerException e) {
			if(Utilities.error) {Log.v(TAG, mClass + ":fillTableWithDefaultData()::db.insert();");}
			throw new DBException("NullPointerException. Message: " + e.getMessage());
		}
		catch(IllegalStateException e) {
			if(Utilities.error) {Log.v(TAG, mClass + ":fillTableWithDefaultData()::db.setTransactionSuccessful();");}
			throw new DBException("IllegalStateException. Message: " + e.getMessage());
		}
		finally{
			db.endTransaction();
		}
		
		if(result == -1) {
			throw new DBException("Database error");
		}
	}

	@Override
	public boolean add(String courseCode) throws DBException {
		if(Utilities.verbose) {Log.v(TAG, mClass + ":add()");}
		SQLiteDatabase db = mHelper.getWritableDatabase();
		db.beginTransaction();		
		ContentValues contentValues = new ContentValues();
		contentValues.put(COLUMN_COURSE_CODE, courseCode);
		
		int result = -1;
		try {
			result = (int) db.insert(TABLE_NAME, null, contentValues);
			db.setTransactionSuccessful();
		}catch(NullPointerException e) {
			if(Utilities.error) {Log.v(TAG, mClass + ":add()::db.insert();");}
			throw new DBException("NullPointerException. Message: " + e.getMessage());
		}
		catch(IllegalStateException e) {
			if(Utilities.error) {Log.v(TAG, mClass + ":add()::db.setTransactionSuccessful();");}
			throw new DBException("IllegalStateException. Message: " + e.getMessage());
		}
		finally{
			db.endTransaction();
			//	db.close(); // http://stackoverflow.com/questions/6608498/best-place-to-close-database-connection
		}
		
		if(result == -1) {
			throw new DBException("Database error");
		}
		return true;
	}

	@Override
	public boolean remove(String courseCode) throws DBException, NoRowsAffectedDBException {
		if(Utilities.verbose) {Log.v(TAG, mClass + ":remove()");}
		SQLiteDatabase db = mHelper.getWritableDatabase();
		db.beginTransaction();
		
		int deletedRowCount;
		try {
			deletedRowCount = db.delete(TABLE_NAME, COLUMN_COURSE_CODE + "=" +"'" + courseCode + "'", null);
			db.setTransactionSuccessful();
		}catch(NullPointerException e) {
			if(Utilities.error) {Log.v(TAG, mClass + ":remove()::db.delete();");}
			throw new DBException("NullPointerException. Message: " + e.getMessage());
		}
		catch(IllegalStateException e) {
			if(Utilities.error) {Log.v(TAG, mClass + ":remove()::db.setTransactionSuccessful();");}
			throw new DBException("IllegalStateException. Message: " + e.getMessage());
		}
		finally{
			db.endTransaction();
			//	db.close(); // http://stackoverflow.com/questions/6608498/best-place-to-close-database-connection
		}
				
		if(deletedRowCount == 0) {
			throw new NoRowsAffectedDBException("No entries removed in database.");
		}
		return true;
	}

	@Override
	public ArrayList<String> getAll() {
		if(Utilities.verbose) {Log.v(TAG, mClass + ":getAll()");}
		ArrayList<String> favouriteCourses = new ArrayList<String>();
		
		SQLiteDatabase db = mHelper.getReadableDatabase();
		String[] columns = {COLUMN_COURSE_CODE};
		Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
		
		int index;
		while(cursor.moveToNext()) {
			index = cursor.getColumnIndex(COLUMN_COURSE_CODE);
			String courseCode = cursor.getString(index);
			favouriteCourses.add(courseCode);
		}
		//	db.close(); // http://stackoverflow.com/questions/6608498/best-place-to-close-database-connection
		return favouriteCourses;
	}

	@Override
	public boolean isEmpty() {
		if(Utilities.verbose) {Log.v(TAG, mClass + ":isEmpty()");}
		SQLiteDatabase db = mHelper.getWritableDatabase();
		
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
		boolean empty = cursor.getCount() <= 0;
		
		//	db.close(); // http://stackoverflow.com/questions/6608498/best-place-to-close-database-connection
		return empty;
	}
}