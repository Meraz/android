package com.example.app_android.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
	public void fillTableWithDefaultData(SQLiteDatabase db) {
		db.beginTransaction();
			
		//TODO - remove test data
		ContentValues values = new ContentValues();
		values.put(COLUMN_COURSE_CODE, "testCode");
		db.insert(TABLE_NAME, null, values);

		db.setTransactionSuccessful();
		db.endTransaction();
	}

	@Override
	public boolean add(String courseCode) {
		SQLiteDatabase db = mHelper.getWritableDatabase();
		db.beginTransaction();
		
		ContentValues contentValues = new ContentValues();
		contentValues.put(COLUMN_COURSE_CODE, courseCode);
		long id = db.insert(TABLE_NAME, null, contentValues);
		
		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();
		return id >= 0;
	}

	@Override
	public boolean remove(String courseCode) {
		SQLiteDatabase db = mHelper.getWritableDatabase();
		db.beginTransaction();
		
		int deletedRowCount = db.delete(TABLE_NAME, COLUMN_COURSE_CODE + "=" +"'" + courseCode + "'", null);
		
		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();
		return deletedRowCount > 0;
	}

	@Override
	public ArrayList<String> getAll() {
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
		db.close();
		return favouriteCourses;
	}

	@Override
	public boolean isEmpty() {
		SQLiteDatabase db = mHelper.getWritableDatabase();
		
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
		boolean empty = cursor.getCount() <= 0;
		
		db.close();
		return empty;
	}
}