package com.example.app_android;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdapterScheduleHelper {
	
	MyScheduleHelper helper;
	public AdapterScheduleHelper(Context context) {
		helper = new MyScheduleHelper(context);
	}
	public long insertData(String[] event) {
		SQLiteDatabase db = helper.getWritableDatabase();
		int index = 4;
		ContentValues contentValues = new ContentValues();
		contentValues.put(MyScheduleHelper.StartDate, event[0]);
		contentValues.put(MyScheduleHelper.StartTime, event[1]);
		contentValues.put(MyScheduleHelper.EndDate, event[2]);
		contentValues.put(MyScheduleHelper.EndTime, event[3]);
		if(event[index].indexOf('"') == 1) {
			contentValues.put(MyScheduleHelper.Course, event[index++]+event[index++]);
		} else {
			contentValues.put(MyScheduleHelper.Course, event[index++]);
		}
		if(event[index].indexOf('"') == 1) {
			contentValues.put(MyScheduleHelper.Group, event[index++]+event[index++]);
		} else {
			contentValues.put(MyScheduleHelper.Group, event[index++]);
		}
		if(event[index].indexOf('"') == 1) {
			contentValues.put(MyScheduleHelper.Room, event[index++]+event[index++]);
		} else {
			contentValues.put(MyScheduleHelper.Room, event[index++]);
		}
		if(event[index].indexOf('"') == 1) {
			contentValues.put(MyScheduleHelper.Person, event[index++]+event[index++]);
		} else {
			contentValues.put(MyScheduleHelper.Person, event[index++]);
		}
		if(event[index].indexOf('"') == 1) {
			contentValues.put(MyScheduleHelper.Moment, event[index++]+event[index++]);
		} else {
			contentValues.put(MyScheduleHelper.Moment, event[index++]);
		}
		if(event[index].indexOf('"') == 1) {
			contentValues.put(MyScheduleHelper.Note, event[index++]+event[index++]);
		} else {
			contentValues.put(MyScheduleHelper.Note, event[index++]);
		}
		long id = db.insert(MyScheduleHelper.TABLE_NAME, null, contentValues);
		db.close();
		return id;
	}
	
	public void resetData() {
		SQLiteDatabase db = helper.getWritableDatabase();
		try {
			db.execSQL(MyScheduleHelper.DROP_TABLE);
			helper.onCreate(db);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String[] readAllEvent() {
		SQLiteDatabase db = helper.getReadableDatabase();
		//String[] columns = {helper.UID, helper.StartDate, helper.StartTime, helper.EndDate, helper.EndTime, helper.Course, helper.Moment, helper.Group, helper.Person, helper.Room, helper.Note, helper.URL, helper.MyName, helper.Program};		
		String[] columns = {MyScheduleHelper.UID, MyScheduleHelper.StartDate, MyScheduleHelper.StartTime, MyScheduleHelper.EndDate, MyScheduleHelper.EndTime, MyScheduleHelper.Course, MyScheduleHelper.Group, MyScheduleHelper.Room, MyScheduleHelper.Person, MyScheduleHelper.Moment,  MyScheduleHelper.Note};		
		Cursor cursor = db.query(MyScheduleHelper.TABLE_NAME, columns, null, null, null, null, null);
		ArrayList<String> event = new ArrayList<String>();
		
		while (cursor.moveToNext()) {
			//int index1 = cursor.getColumnIndex(MyScheduleHelper.UID);
			//int index2 = cursor.getColumnIndex(MyScheduleHelper.StartDate);
			int index3 = cursor.getColumnIndex(MyScheduleHelper.StartTime);
			//int index4 = cursor.getColumnIndex(MyScheduleHelper.EndDate);
			//int index5 = cursor.getColumnIndex(MyScheduleHelper.EndTime);
			int index6 = cursor.getColumnIndex(MyScheduleHelper.Course);
			//int index7 = cursor.getColumnIndex(MyScheduleHelper.Group);
			int index8 = cursor.getColumnIndex(MyScheduleHelper.Room);
			//int index9 = cursor.getColumnIndex(MyScheduleHelper.Person);
			//int index10 = cursor.getColumnIndex(MyScheduleHelper.Moment);
			//int index11 = cursor.getColumnIndex(MyScheduleHelper.Note);
			//int cid = cursor.getInt(index1);
			String courseCode = cursor.getString(index6);
			String room = cursor.getString(index8);
			//String person = cursor.getString(index9);
			event.add(courseCode+" "+room);
			System.out.println(courseCode+" "+room+" "+cursor.getString(index3));
		}
		db.close();
		return (String[]) event.toArray(new String [event.size()]);
	}
	
	public String[] readStartTime() {
		SQLiteDatabase db = helper.getReadableDatabase();
		//String[] columns = {helper.UID, helper.StartDate, helper.StartTime, helper.EndDate, helper.EndTime, helper.Course, helper.Moment, helper.Group, helper.Person, helper.Room, helper.Note, helper.URL, helper.MyName, helper.Program};		
		String[] columns = {MyScheduleHelper.UID, MyScheduleHelper.StartTime};		
		Cursor cursor = db.query(MyScheduleHelper.TABLE_NAME, columns, null, null, null, null, null);
		ArrayList<String> event = new ArrayList<String>();
		
		while (cursor.moveToNext()) {
			//int index1 = cursor.getColumnIndex(MyScheduleHelper.UID);
			int index2 = cursor.getColumnIndex(MyScheduleHelper.StartTime);
			//int cid = cursor.getInt(index1);
			String startTime = cursor.getString(index2);
			event.add(startTime);
		}
		db.close();
		return (String[]) event.toArray(new String [event.size()]);
	}
	
	public String[] readStartTime2(String startDate, String endDate) {
		SQLiteDatabase db = helper.getReadableDatabase();
		//String[] columns = {helper.UID, helper.StartDate, helper.StartTime, helper.EndDate, helper.EndTime, helper.Course, helper.Moment, helper.Group, helper.Person, helper.Room, helper.Note, helper.URL, helper.MyName, helper.Program};		
		String[] columns = {MyScheduleHelper.UID, MyScheduleHelper.StartDate, MyScheduleHelper.EndDate,MyScheduleHelper.StartTime};		
		String[] values = {startDate, endDate};
		Cursor cursor = db.query(MyScheduleHelper.TABLE_NAME, columns, MyScheduleHelper.StartDate + " BETWEEN ? AND ?", values, null, null, null);
		ArrayList<String> event = new ArrayList<String>();
		
		while (cursor.moveToNext()) {
			//int index1 = cursor.getColumnIndex(MyScheduleHelper.UID);
			int index2 = cursor.getColumnIndex(MyScheduleHelper.StartTime);
			//int cid = cursor.getInt(index1);
			String startTime = cursor.getString(index2);
			event.add(startTime);
		}
		db.close();
		return (String[]) event.toArray(new String [event.size()]);
	}
	
	public String[] readEndTime() {
		SQLiteDatabase db = helper.getReadableDatabase();
		//String[] columns = {helper.UID, helper.StartDate, helper.StartTime, helper.EndDate, helper.EndTime, helper.Course, helper.Moment, helper.Group, helper.Person, helper.Room, helper.Note, helper.URL, helper.MyName, helper.Program};		
		String[] columns = {MyScheduleHelper.UID, MyScheduleHelper.EndTime};		
		Cursor cursor = db.query(MyScheduleHelper.TABLE_NAME, columns, null, null, null, null, null);
		ArrayList<String> event = new ArrayList<String>();
		
		while (cursor.moveToNext()) {
			//int index1 = cursor.getColumnIndex(MyScheduleHelper.UID);
			int index2 = cursor.getColumnIndex(MyScheduleHelper.EndTime);
			//int cid = cursor.getInt(index1);
			String endTime = cursor.getString(index2);
			event.add(endTime);
		}
		db.close();
		return (String[]) event.toArray(new String [event.size()]);
	}
	
	public String[] readEndTime2(String startDate, String endDate) {
		SQLiteDatabase db = helper.getReadableDatabase();
		//String[] columns = {helper.UID, helper.StartDate, helper.StartTime, helper.EndDate, helper.EndTime, helper.Course, helper.Moment, helper.Group, helper.Person, helper.Room, helper.Note, helper.URL, helper.MyName, helper.Program};		
		String[] columns = {MyScheduleHelper.UID, MyScheduleHelper.StartDate, MyScheduleHelper.EndDate,MyScheduleHelper.EndTime};		
		String[] values = {startDate, endDate};
		Cursor cursor = db.query(MyScheduleHelper.TABLE_NAME, columns, MyScheduleHelper.StartDate + " BETWEEN ? AND ?", values, null, null, null);
		ArrayList<String> event = new ArrayList<String>();
		
		while (cursor.moveToNext()) {
			//int index1 = cursor.getColumnIndex(MyScheduleHelper.UID);
			int index2 = cursor.getColumnIndex(MyScheduleHelper.EndTime);
			//int cid = cursor.getInt(index1);
			String endTime = cursor.getString(index2);
			event.add(endTime);
		}
		db.close();
		return (String[]) event.toArray(new String [event.size()]);
	}
	
	public String[] readRoom() {
		SQLiteDatabase db = helper.getReadableDatabase();
		//String[] columns = {helper.UID, helper.StartDate, helper.StartTime, helper.EndDate, helper.EndTime, helper.Course, helper.Moment, helper.Group, helper.Person, helper.Room, helper.Note, helper.URL, helper.MyName, helper.Program};		
		String[] columns = {MyScheduleHelper.UID, MyScheduleHelper.Room};		
		Cursor cursor = db.query(MyScheduleHelper.TABLE_NAME, columns, null, null, null, null, null);
		ArrayList<String> event = new ArrayList<String>();
		
		while (cursor.moveToNext()) {
			//int index1 = cursor.getColumnIndex(MyScheduleHelper.UID);
			int index2 = cursor.getColumnIndex(MyScheduleHelper.Room);
			//int cid = cursor.getInt(index1);
			String room = cursor.getString(index2);
			event.add(room);
		}
		db.close();
		return (String[]) event.toArray(new String [event.size()]);
	}
	
	public String[] readRoom2(String startDate, String endDate) {
		SQLiteDatabase db = helper.getReadableDatabase();
		//String[] columns = {helper.UID, helper.StartDate, helper.StartTime, helper.EndDate, helper.EndTime,helper.Course, helper.Moment,
		//helper.Group, helper.Person, helper.Room, helper.Note, helper.URL, helper.MyName, helper.Program};		
		String[] columns = {MyScheduleHelper.UID, MyScheduleHelper.StartDate, MyScheduleHelper.EndDate,MyScheduleHelper.Room};		
		String[] values = {startDate, endDate};
		Cursor cursor = db.query(MyScheduleHelper.TABLE_NAME, columns, MyScheduleHelper.StartDate + " BETWEEN ? AND ?", values, null, null, null);
		ArrayList<String> event = new ArrayList<String>();
		
		while (cursor.moveToNext()) {
			//int index1 = cursor.getColumnIndex(MyScheduleHelper.UID);
			int index2 = cursor.getColumnIndex(MyScheduleHelper.Room);
			//int cid = cursor.getInt(index1);
			String room = cursor.getString(index2);
			event.add(room);
		}
		db.close();
		return (String[]) event.toArray(new String [event.size()]);
	}
	
	public String[] readMoment() {
		SQLiteDatabase db = helper.getReadableDatabase();
		//String[] columns = {helper.UID, helper.StartDate, helper.StartTime, helper.EndDate, helper.EndTime, helper.Course, helper.Moment, helper.Group, helper.Person, helper.Room, helper.Note, helper.URL, helper.MyName, helper.Program};		
		String[] columns = {MyScheduleHelper.UID, MyScheduleHelper.Moment};		
		Cursor cursor = db.query(MyScheduleHelper.TABLE_NAME, columns, null, null, null, null, null);
		ArrayList<String> event = new ArrayList<String>();
		
		while (cursor.moveToNext()) {
			//int index1 = cursor.getColumnIndex(MyScheduleHelper.UID);
			int index2 = cursor.getColumnIndex(MyScheduleHelper.Moment);
			//int cid = cursor.getInt(index1);
			String moment = cursor.getString(index2);
			event.add(moment);
		}
		db.close();
		return (String[]) event.toArray(new String [event.size()]);
	}
	
	public String[] readMoment2(String startDate, String endDate) {
		SQLiteDatabase db = helper.getReadableDatabase();
		//String[] columns = {helper.UID, helper.StartDate, helper.StartTime, helper.EndDate, helper.EndTime, helper.Course, helper.Moment, helper.Group, helper.Person, helper.Room, helper.Note, helper.URL, helper.MyName, helper.Program};		
		String[] columns = {MyScheduleHelper.UID, MyScheduleHelper.StartDate, MyScheduleHelper.EndDate, MyScheduleHelper.Moment};
		String[] values = {startDate, endDate};
		Cursor cursor = db.query(MyScheduleHelper.TABLE_NAME, columns, MyScheduleHelper.StartDate + " BETWEEN ? AND ?", values, null, null, null);
		ArrayList<String> event = new ArrayList<String>();
		System.out.println("Start: "+startDate+", End: "+endDate );
		while (cursor.moveToNext()) {
			//int index1 = cursor.getColumnIndex(MyScheduleHelper.UID);
			int index2 = cursor.getColumnIndex(MyScheduleHelper.Moment);
			//int cid = cursor.getInt(index1);
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