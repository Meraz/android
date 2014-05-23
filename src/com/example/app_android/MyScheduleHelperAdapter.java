package com.example.app_android;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyScheduleHelperAdapter {
	
	MyScheduleHelper helper;
	public MyScheduleHelperAdapter(Context context) {
		helper = new MyScheduleHelper(context);
	}
	public long insertData(String[] event) {
		SQLiteDatabase db = helper.getWritableDatabase();
		int index = 4;
		ContentValues contentValues = new ContentValues();
		contentValues.put(helper.StartDate, event[0]);
		contentValues.put(helper.StartTime, event[1]);
		contentValues.put(helper.EndDate, event[2]);
		contentValues.put(helper.EndTime, event[3]);
		if(event[index].indexOf('"') == 1) {
			contentValues.put(helper.Course, event[index++]+event[index++]);
		} else {
			contentValues.put(helper.Course, event[index++]);
		}
		if(event[index].indexOf('"') == 1) {
			contentValues.put(helper.Group, event[index++]+event[index++]);
		} else {
			contentValues.put(helper.Group, event[index++]);
		}
		if(event[index].indexOf('"') == 1) {
			contentValues.put(helper.Room, event[index++]+event[index++]);
		} else {
			contentValues.put(helper.Room, event[index++]);
		}
		if(event[index].indexOf('"') == 1) {
			contentValues.put(helper.Person, event[index++]+event[index++]);
		} else {
			contentValues.put(helper.Person, event[index++]);
		}
		if(event[index].indexOf('"') == 1) {
			contentValues.put(helper.Moment, event[index++]+event[index++]);
		} else {
			contentValues.put(helper.Moment, event[index++]);
		}
		if(event[index].indexOf('"') == 1) {
			contentValues.put(helper.Note, event[index++]+event[index++]);
		} else {
			contentValues.put(helper.Note, event[index++]);
		}
		long id = db.insert(helper.TABLE_NAME, null, contentValues);
		db.close();
		return id;
	}
	
	public void resetData() {
		SQLiteDatabase db = helper.getWritableDatabase();
		try {
			db.execSQL(helper.DROP_TABLE);
			helper.onCreate(db);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String[] readAllEvent() {
		SQLiteDatabase db = helper.getReadableDatabase();
		//String[] columns = {helper.UID, helper.StartDate, helper.StartTime, helper.EndDate, helper.EndTime, helper.Course, helper.Moment, helper.Group, helper.Person, helper.Room, helper.Note, helper.URL, helper.MyName, helper.Program};		
		String[] columns = {helper.UID, helper.StartDate, helper.StartTime, helper.EndDate, helper.EndTime, helper.Course, helper.Group, helper.Room, helper.Person, helper.Moment,  helper.Note};		
		Cursor cursor = db.query(helper.TABLE_NAME, columns, null, null, null, null, null);
		ArrayList<String> event = new ArrayList<String>();
		
		while (cursor.moveToNext()) {
			int index1 = cursor.getColumnIndex(helper.UID);
			int index2 = cursor.getColumnIndex(helper.StartDate);
			int index3 = cursor.getColumnIndex(helper.StartTime);
			int index4 = cursor.getColumnIndex(helper.EndDate);
			int index5 = cursor.getColumnIndex(helper.EndTime);
			int index6 = cursor.getColumnIndex(helper.Course);
			int index7 = cursor.getColumnIndex(helper.Group);
			int index8 = cursor.getColumnIndex(helper.Room);
			int index9 = cursor.getColumnIndex(helper.Person);
			int index10 = cursor.getColumnIndex(helper.Moment);
			int index11 = cursor.getColumnIndex(helper.Note);
			int cid = cursor.getInt(index1);
			String courseCode = cursor.getString(index6);
			String room = cursor.getString(index8);
			String person = cursor.getString(index9);
			event.add(courseCode+" "+room);
			System.out.println(courseCode+" "+room+" "+cursor.getString(index3));
		}
		db.close();
		return (String[]) event.toArray(new String [event.size()]);
	}
	
	public String[] readStartTime() {
		SQLiteDatabase db = helper.getReadableDatabase();
		//String[] columns = {helper.UID, helper.StartDate, helper.StartTime, helper.EndDate, helper.EndTime, helper.Course, helper.Moment, helper.Group, helper.Person, helper.Room, helper.Note, helper.URL, helper.MyName, helper.Program};		
		String[] columns = {helper.UID, helper.StartTime};		
		Cursor cursor = db.query(helper.TABLE_NAME, columns, null, null, null, null, null);
		ArrayList<String> event = new ArrayList<String>();
		
		while (cursor.moveToNext()) {
			int index1 = cursor.getColumnIndex(helper.UID);
			int index2 = cursor.getColumnIndex(helper.StartTime);
			int cid = cursor.getInt(index1);
			String startTime = cursor.getString(index2);
			event.add(startTime);
		}
		db.close();
		return (String[]) event.toArray(new String [event.size()]);
	}
	
	public String[] readStartTime2(String startDate, String endDate) {
		SQLiteDatabase db = helper.getReadableDatabase();
		//String[] columns = {helper.UID, helper.StartDate, helper.StartTime, helper.EndDate, helper.EndTime, helper.Course, helper.Moment, helper.Group, helper.Person, helper.Room, helper.Note, helper.URL, helper.MyName, helper.Program};		
		String[] columns = {helper.UID, helper.StartDate, helper.EndDate,helper.StartTime};		
		String[] values = {startDate, endDate};
		Cursor cursor = db.query(helper.TABLE_NAME, columns, helper.StartDate + " BETWEEN ? AND ?", values, null, null, null);
		ArrayList<String> event = new ArrayList<String>();
		
		while (cursor.moveToNext()) {
			int index1 = cursor.getColumnIndex(helper.UID);
			int index2 = cursor.getColumnIndex(helper.StartTime);
			int cid = cursor.getInt(index1);
			String startTime = cursor.getString(index2);
			event.add(startTime);
		}
		db.close();
		return (String[]) event.toArray(new String [event.size()]);
	}
	
	public String[] readEndTime() {
		SQLiteDatabase db = helper.getReadableDatabase();
		//String[] columns = {helper.UID, helper.StartDate, helper.StartTime, helper.EndDate, helper.EndTime, helper.Course, helper.Moment, helper.Group, helper.Person, helper.Room, helper.Note, helper.URL, helper.MyName, helper.Program};		
		String[] columns = {helper.UID, helper.EndTime};		
		Cursor cursor = db.query(helper.TABLE_NAME, columns, null, null, null, null, null);
		ArrayList<String> event = new ArrayList<String>();
		
		while (cursor.moveToNext()) {
			int index1 = cursor.getColumnIndex(helper.UID);
			int index2 = cursor.getColumnIndex(helper.EndTime);
			int cid = cursor.getInt(index1);
			String endTime = cursor.getString(index2);
			event.add(endTime);
		}
		db.close();
		return (String[]) event.toArray(new String [event.size()]);
	}
	
	public String[] readEndTime2(String startDate, String endDate) {
		SQLiteDatabase db = helper.getReadableDatabase();
		//String[] columns = {helper.UID, helper.StartDate, helper.StartTime, helper.EndDate, helper.EndTime, helper.Course, helper.Moment, helper.Group, helper.Person, helper.Room, helper.Note, helper.URL, helper.MyName, helper.Program};		
		String[] columns = {helper.UID, helper.StartDate, helper.EndDate,helper.EndTime};		
		String[] values = {startDate, endDate};
		Cursor cursor = db.query(helper.TABLE_NAME, columns, helper.StartDate + " BETWEEN ? AND ?", values, null, null, null);
		ArrayList<String> event = new ArrayList<String>();
		
		while (cursor.moveToNext()) {
			int index1 = cursor.getColumnIndex(helper.UID);
			int index2 = cursor.getColumnIndex(helper.EndTime);
			int cid = cursor.getInt(index1);
			String endTime = cursor.getString(index2);
			event.add(endTime);
		}
		db.close();
		return (String[]) event.toArray(new String [event.size()]);
	}
	
	public String[] readRoom() {
		SQLiteDatabase db = helper.getReadableDatabase();
		//String[] columns = {helper.UID, helper.StartDate, helper.StartTime, helper.EndDate, helper.EndTime, helper.Course, helper.Moment, helper.Group, helper.Person, helper.Room, helper.Note, helper.URL, helper.MyName, helper.Program};		
		String[] columns = {helper.UID, helper.Room};		
		Cursor cursor = db.query(helper.TABLE_NAME, columns, null, null, null, null, null);
		ArrayList<String> event = new ArrayList<String>();
		
		while (cursor.moveToNext()) {
			int index1 = cursor.getColumnIndex(helper.UID);
			int index2 = cursor.getColumnIndex(helper.Room);
			int cid = cursor.getInt(index1);
			String room = cursor.getString(index2);
			event.add(room);
		}
		db.close();
		return (String[]) event.toArray(new String [event.size()]);
	}
	
	public String[] readRoom2(String startDate, String endDate) {
		SQLiteDatabase db = helper.getReadableDatabase();
		//String[] columns = {helper.UID, helper.StartDate, helper.StartTime, helper.EndDate, helper.EndTime, helper.Course, helper.Moment, helper.Group, helper.Person, helper.Room, helper.Note, helper.URL, helper.MyName, helper.Program};		
		String[] columns = {helper.UID, helper.StartDate, helper.EndDate,helper.Room};		
		String[] values = {startDate, endDate};
		Cursor cursor = db.query(helper.TABLE_NAME, columns, helper.StartDate + " BETWEEN ? AND ?", values, null, null, null);
		ArrayList<String> event = new ArrayList<String>();
		
		while (cursor.moveToNext()) {
			int index1 = cursor.getColumnIndex(helper.UID);
			int index2 = cursor.getColumnIndex(helper.Room);
			int cid = cursor.getInt(index1);
			String room = cursor.getString(index2);
			event.add(room);
		}
		db.close();
		return (String[]) event.toArray(new String [event.size()]);
	}
	
	public String[] readMoment() {
		SQLiteDatabase db = helper.getReadableDatabase();
		//String[] columns = {helper.UID, helper.StartDate, helper.StartTime, helper.EndDate, helper.EndTime, helper.Course, helper.Moment, helper.Group, helper.Person, helper.Room, helper.Note, helper.URL, helper.MyName, helper.Program};		
		String[] columns = {helper.UID, helper.Moment};		
		Cursor cursor = db.query(helper.TABLE_NAME, columns, null, null, null, null, null);
		ArrayList<String> event = new ArrayList<String>();
		
		while (cursor.moveToNext()) {
			int index1 = cursor.getColumnIndex(helper.UID);
			int index2 = cursor.getColumnIndex(helper.Moment);
			int cid = cursor.getInt(index1);
			String moment = cursor.getString(index2);
			event.add(moment);
		}
		db.close();
		return (String[]) event.toArray(new String [event.size()]);
	}
	
	public String[] readMoment2(String startDate, String endDate) {
		SQLiteDatabase db = helper.getReadableDatabase();
		//String[] columns = {helper.UID, helper.StartDate, helper.StartTime, helper.EndDate, helper.EndTime, helper.Course, helper.Moment, helper.Group, helper.Person, helper.Room, helper.Note, helper.URL, helper.MyName, helper.Program};		
		String[] columns = {helper.UID, helper.StartDate, helper.EndDate, helper.Moment};
		String[] values = {startDate, endDate};
		Cursor cursor = db.query(helper.TABLE_NAME, columns, helper.StartDate + " BETWEEN ? AND ?", values, null, null, null);
		ArrayList<String> event = new ArrayList<String>();
		System.out.println("Start: "+startDate+", End: "+endDate );
		while (cursor.moveToNext()) {
			int index1 = cursor.getColumnIndex(helper.UID);
			int index2 = cursor.getColumnIndex(helper.Moment);
			int cid = cursor.getInt(index1);
			String moment = cursor.getString(index2);
			event.add(moment);
			System.out.println(moment);
		}
		db.close();
		return (String[]) event.toArray(new String [event.size()]);
	}
			
	static class MyScheduleHelper extends SQLiteOpenHelper {
		private static final String DATABASE_NAME = "MySchedule";
		private static final int DATABASE_VERSION = 6;
		private static final String TABLE_NAME = "ScheduleEvent";
		private static final String UID = "_id";
		private static final String StartDate = "StartDate";
		private static final String StartTime = "StartTime";
		private static final String EndDate = "EndDate";
		private static final String EndTime = "EndTime";
		private static final String Course = "Course";
		private static final String Moment = "Moment";
		private static final String Group = "MGroup";
		private static final String Person = "Person";
		private static final String Room = "Room";
		private static final String Note = "Note";
		private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+StartDate+" NUMERIC, "+StartTime+" NUMERIC, "+EndDate+" NUMERIC, "+EndTime+" NUMERIC, "+Course+" VARCHAR(10), "+Group+" VARCHAR(20), "+Room+" VARCHAR(10), "+Person+" VARCHAR(50), "+Moment+" VARCHAR(20), "+Note+" VARCHAR(255) );";
		private static final String DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;

		public MyScheduleHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);		
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			try {
			db.execSQL(CREATE_TABLE);
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			try {
				db.execSQL(DROP_TABLE);
				onCreate(db);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
