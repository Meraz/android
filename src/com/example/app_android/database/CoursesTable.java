package com.example.app_android.database;


import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CoursesTable extends BaseTable implements ICourseTable{

	private static final String TABLE_NAME = "courses";
	
	private static final String COULMN_UID = "id";
	private static final String COULUMN_COURSE_CODE = "courseCode";
    
    private static final String COULMN_UID_TYPE = "INTEGER PRIMARY KEY AUTOINCREMENT";
    private static final String COULUMN_COURSE_CODE_TYPE = "VARCHAR(8)";
    


	private static final String LOCAL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " " +
			"("+
				COULMN_UID 			+ " " + COULMN_UID_TYPE + ", " +
				COULUMN_COURSE_CODE + " " + COULUMN_COURSE_CODE_TYPE + 
			")";
	
 

	// CONSTRUCTOR
	public CoursesTable(SQLiteOpenHelper SQLiteOpenHelper) {
		super(SQLiteOpenHelper);
		System.out.println(LOCAL_CREATE_TABLE);
		SQL_CREATE_TABLE = LOCAL_CREATE_TABLE;
		SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
		SQL_DEFAULT_VALUES = "";
	}
	
	// Overrides this function as I'm using another method when inserting
	@Override
	public void fillTableWithDefaultData(SQLiteDatabase db) {
		
	}
	
	public long insertData(String courseCode) {
		SQLiteDatabase db = mHelper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(COULUMN_COURSE_CODE, courseCode);
		long id = db.insert(TABLE_NAME, null, contentValues);
		db.close();
		return id;
	}
	
	public ArrayList<String> readAllCourses() {
		SQLiteDatabase db = mHelper.getReadableDatabase();
		String[] columns = {COULMN_UID, COULUMN_COURSE_CODE};		
		Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
		ArrayList<String> courses = new ArrayList<String>();
		
		while (cursor.moveToNext()) {
			int index = cursor.getColumnIndex(COULUMN_COURSE_CODE);
			String courseCode = cursor.getString(index);
			courses.add(courseCode);
		}
		db.close();
		return courses;
	}
	
	public boolean empty() {
		boolean result;
		SQLiteDatabase db = mHelper.getReadableDatabase();
		String[] columns = {COULMN_UID, COULUMN_COURSE_CODE};		
		Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
		
		if (cursor.getCount() > 0)
			result = false;
		else
			result = true;
		
		db.close();
		return result;
	}
	
	public void removeCourse(String course) {
		SQLiteDatabase db = mHelper.getWritableDatabase();
		db.delete(TABLE_NAME, COULUMN_COURSE_CODE + "=" + course, null);
		db.close();
	}	
}