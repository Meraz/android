package com.example.app_android.database;

import java.util.ArrayList;

import com.example.app_android.database.interfaces.IFavouriteCourseTable;
import com.example.app_android.util.Utilities;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
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
	public void add(String courseCode) throws DBException, NoRowsAffectedDBException  {
		if(Utilities.verbose) {Log.v(TAG, mClass + ":add()");}
		SQLiteDatabase db = mHelper.getWritableDatabase();
		db.beginTransaction();		
		ContentValues contentValues = new ContentValues();
		contentValues.put(COLUMN_COURSE_CODE, courseCode);
		
		long result = -1;
		try {
			result = db.insert(TABLE_NAME, null, contentValues);
			db.setTransactionSuccessful();
		}catch(NullPointerException e) {
			if(Utilities.error) {Log.e(TAG, mClass + ":add()::db.insert();");}
			throw new DBException("NullPointerException. Message: " + e.getMessage());
		}
		catch(IllegalStateException e) {
			if(Utilities.error) {Log.e(TAG, mClass + ":add()::db.setTransactionSuccessful();");}
			throw new DBException("IllegalStateException. Message: " + e.getMessage());
		}
		catch(SQLException e) {
			if(Utilities.error) {Log.e(TAG, mClass + ":add(); SQLException, something went wrong.");}
			throw new DBException("SQLException. Message: " + e.getMessage());
		}
		finally{
			db.endTransaction();
		}
		
		if(result == -1) {
		    if(Utilities.error) {Log.e(TAG, mClass + ":add(); No entry was added to the database.");}
		    throw new NoRowsAffectedDBException(mClass + ":add(); No entry was added to the database.");
		}
	}

	@Override
	public void remove(String courseCode) throws DBException, NoRowsAffectedDBException {
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
		catch(SQLException e) {
			if(Utilities.error) {Log.e(TAG, mClass + ":remove(); SQLException, something went wrong.");}
			throw new DBException("SQLException. Message: " + e.getMessage());
		}
		finally{
			db.endTransaction();
		}
				
		if(deletedRowCount == 0) {
		      if(Utilities.error) {Log.e(TAG, mClass + ":remove(); No entries removed in database.");}
		      throw new NoRowsAffectedDBException(mClass + ":remove(); No entries removed in database.");
		}
	}

	@Override
	public ArrayList<String> getAll() throws DBException, NoResultFoundDBException {
		if(Utilities.verbose) {Log.v(TAG, mClass + ":getAll()");}
		
		SQLiteDatabase db = mHelper.getWritableDatabase();
		db.beginTransaction();			
		ArrayList<String> favouriteCourses = new ArrayList<String>();
		String[] columns = {COLUMN_COURSE_CODE};
		
		try {
			Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
			int index;
			while(cursor.moveToNext()) {
				index = cursor.getColumnIndex(COLUMN_COURSE_CODE);
				String courseCode = cursor.getString(index);
				favouriteCourses.add(courseCode);
			}
			db.setTransactionSuccessful();
		}catch(NullPointerException e) {
			if(Utilities.error) {Log.e(TAG, mClass + ":getAll()::db.insert();");}
			throw new DBException("NullPointerException. Message: " + e.getMessage());
		}
		catch(IllegalStateException e) {
			if(Utilities.error) {Log.e(TAG, mClass + ":getAll()::db.setTransactionSuccessful();");}
			throw new DBException("IllegalStateException. Message: " + e.getMessage());
		}
		catch(SQLException e) {
			if(Utilities.error) {Log.e(TAG, mClass + ":getAll(); SQLException, something went wrong.");}
			throw new DBException("SQLException. Message: " + e.getMessage());
		}
		finally {
			db.endTransaction();
		}
		
		if(favouriteCourses.size() == 0) {
			if(Utilities.error) {Log.e(TAG, mClass + ":getAll(); No result was found in database for courseCode.");}
			throw new NoResultFoundDBException(mClass + ":getAll(); No result was found in database for courseCode.");
		}
		return favouriteCourses;
	}

	@Override
	public boolean isEmpty() throws DBException {
		if(Utilities.verbose) {Log.v(TAG, mClass + ":isEmpty()");}
		SQLiteDatabase db = mHelper.getWritableDatabase();
		db.beginTransaction();		
		boolean empty = false;
		try {
			Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
			if(cursor.getCount() <= 0) {
				empty = true;
			}
			db.setTransactionSuccessful();
		}catch(NullPointerException e) {
			if(Utilities.error) {Log.e(TAG, mClass + ":isEmpty()::db.insert();");}
			throw new DBException("NullPointerException. Message: " + e.getMessage());
		}
		catch(IllegalStateException e) {
			if(Utilities.error) {Log.e(TAG, mClass + ":isEmpty()::db.setTransactionSuccessful();");}
			throw new DBException("IllegalStateException. Message: " + e.getMessage());
		}
		catch(SQLException e) {
			if(Utilities.error) {Log.e(TAG, mClass + ":isEmpty(); SQLException, something went wrong.");}
			throw new DBException("SQLException. Message: " + e.getMessage());
		}
		finally {
			db.endTransaction();
		}
		return empty;
	}
}