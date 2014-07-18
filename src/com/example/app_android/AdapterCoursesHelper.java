package com.example.app_android;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdapterCoursesHelper {
	
	MyCoursesHelper helper;
	public AdapterCoursesHelper(Context context) {
		helper = new MyCoursesHelper(context);
	}
	public long insertData(String courseCode) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(MyCoursesHelper.CCode, courseCode);
		long id = db.insert(MyCoursesHelper.TABLE_NAME, null, contentValues);
		db.close();
		return id;
	}
	
	public ArrayList<String> readAllCourses() {
		SQLiteDatabase db = helper.getReadableDatabase();
		String[] columns = {MyCoursesHelper.UID, MyCoursesHelper.CCode};		
		Cursor cursor = db.query(MyCoursesHelper.TABLE_NAME, columns, null, null, null, null, null);
		ArrayList<String> courses = new ArrayList<String>();
		
		while (cursor.moveToNext()) {
			int index = cursor.getColumnIndex(MyCoursesHelper.CCode);
			String courseCode = cursor.getString(index);
			courses.add(courseCode);
		}
		db.close();
		return courses;
	}
	
	public boolean empty() {
		boolean result;
		SQLiteDatabase db = helper.getReadableDatabase();
		String[] columns = {MyCoursesHelper.UID, MyCoursesHelper.CCode};		
		Cursor cursor = db.query(MyCoursesHelper.TABLE_NAME, columns, null, null, null, null, null);
		
		if (cursor.getCount() > 0)
			result = false;
		else
			result = true;
		
		db.close();
		return result;
	}
	
	public void removeCourse(String course) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.delete(MyCoursesHelper.TABLE_NAME, MyCoursesHelper.CCode + "=" + course, null);
		db.close();
	}
	
	private static class MyCoursesHelper extends SQLiteOpenHelper {
		private static final String DATABASE_NAME = "MyCourses";
		private static final int DATABASE_VERSION = 1;
		private static final String TABLE_NAME = "CourseCode";
		private static final String UID = "_id";
		private static final String CCode = "CourseCode";
		private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+CCode+" VARCHAR(8));";
		private static final String DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;

		public MyCoursesHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);		
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO
			try {
			db.execSQL(CREATE_TABLE);
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO
			try {
				db.execSQL(DROP_TABLE);
				onCreate(db);
			} catch (SQLException e) {
				// TODO
				e.printStackTrace();
			}
		}
	}
}
