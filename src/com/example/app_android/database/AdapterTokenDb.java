package com.example.app_android.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdapterTokenDb {
	
	TokenDbHelper helper;
	public AdapterTokenDb(Context context) {
		helper = new TokenDbHelper(context);
	}
	
	
	
	private final class TokenDbHelper extends SQLiteOpenHelper {
		
		// If you change the database schema, you must increment the database version.
		private static final int DATABASE_VERSION = 1;
	    private static final String DATABASE_NAME = "Token.db";
	    
	    private static final String TABLE_NAME = "mytoken";
	    private static final String ROW_TOKEN = "token_value";
	    private static final String ROW_EXPIREDATE = "expire_date";
	    
	    private static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + ROW_TOKEN + " TEXT, " + ROW_EXPIREDATE + " INTEGER);";
	    
		private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;

	    public TokenDbHelper(Context context) {
	        super(context, DATABASE_NAME, null, DATABASE_VERSION);
	    }
	    
	    public void onCreate(SQLiteDatabase db) {
	        db.execSQL(SQL_CREATE_TABLE);
	    }
	    
	    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	        // This database is only a cache for online data, so its upgrade policy is
	        // to simply to discard the data and start over
	        db.execSQL(SQL_DELETE_ENTRIES);
	        onCreate(db);
	    }
	    
	    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	        onUpgrade(db, oldVersion, newVersion);
	    }
	}
}

