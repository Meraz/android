package com.example.app_android.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdapterTokenDb {
	
	/* This will probably be used later on.
    private static final int STATUS_READY = 0;
    private static final int STATUS_WAITING_FOR_UPDATE = 1;
    */
	
	TokenDbHelper mHelper;
	public AdapterTokenDb(Context context) {
		mHelper = new TokenDbHelper(context);
	}
	
	public String getToken() {
		SQLiteDatabase db = mHelper.getReadableDatabase();
		
	    Cursor cursor = db.rawQuery(TokenDbHelper.RETRIEVE_TOKEN, null);
	    String token = "";
	    
	    if (cursor.moveToFirst()) {
	    	token = cursor.getString(0); // Database SHOULD only hold one. If there's ever more than one entry, somethings wrong. Should add check for this...
	    }
	    db.close();

		return token;
	}
	
	public int getExpireDate(){
		SQLiteDatabase db = mHelper.getReadableDatabase();
		
	    Cursor cursor = db.rawQuery(TokenDbHelper.RETRIEVE_TRANSACTION_FLAG, null);
	    int transactionFlag = -1;
	    
	    if (cursor.moveToFirst()) {
	    	transactionFlag = cursor.getInt(0); // Database SHOULD only hold one. If there's ever more than one entry, somethings wrong. Should add check for this...
	    }
	    db.close();
	    return transactionFlag;
	}
		
	// returns the row ID of the last row inserted, if this insert is successful. -1 otherwise.
	public void updateToken(String token, int expireDate, int transaction_flag) {
		SQLiteDatabase db = mHelper.getWritableDatabase();
		db.beginTransaction();

		ContentValues values = new ContentValues();
		values.put(TokenDbHelper.COLUMN_TOKEN, token);
		values.put(TokenDbHelper.COLUMN_EXPIREDATE, expireDate);
		values.put(TokenDbHelper.COLUMN_TRANSACTION_FLAG, transaction_flag);
	    db.update(TokenDbHelper.TABLE_NAME, values, null, null);

		db.setTransactionSuccessful();
		db.endTransaction();
	} 
	
	// Not tested yet
	public void updateTransactionFlag(int transaction_flag) {
		SQLiteDatabase db = mHelper.getWritableDatabase();
		db.beginTransaction();

		ContentValues values = new ContentValues();
		values.put(TokenDbHelper.COLUMN_TRANSACTION_FLAG, transaction_flag);
	    db.update(TokenDbHelper.TABLE_NAME, values, null, null);

		db.setTransactionSuccessful();
		db.endTransaction();
	}
	
	public int getTransactionFlag(){
		SQLiteDatabase db = mHelper.getReadableDatabase();
		
	    Cursor cursor = db.rawQuery(TokenDbHelper.RETRIEVE_TOKEN, null);
	    int transactionFlag = -1;
	    
	    if (cursor.moveToFirst()) {
	    	transactionFlag = cursor.getInt(0); // Database SHOULD only hold one. If there's ever more than one entry, somethings wrong. Should add check for this...
	    }
	    db.close();
	    return transactionFlag;
	}
		
	private final class TokenDbHelper extends SQLiteOpenHelper {
		
		// If you change the database schema, you must increment the database version.
		private static final int DATABASE_VERSION = 1;
	    private static final String DATABASE_NAME = "token.sqlite";	    
	    public static final String TABLE_NAME = "mytoken";
	    public static final String COLUMN_TOKEN = "token_value";	    
	    public static final String COLUMN_EXPIREDATE = "expire_date";
	    public static final String COLUMN_TRANSACTION_FLAG = "transaction_flag";
	    private static final String COLUMN_TOKEN_TYPE = "TEXT";
	    private static final String COLUMN_EXPIREDATE_TYPE = "INTEGER";
	    private static final String COLUMN_TRANSACTION_FLAG_TYPE = "INTEGER";
	  
	    /* Not used anymore, used together with SQLiteStatement. Saving this code for future reference to use of SQLiteStatement. 2014-07-23
	    public static final String INSERT_ALL = "INSERT INTO " + TABLE_NAME +
	    		"("+
	    			COLUMN_TOKEN + ", " + COLUMN_EXPIREDATE + ", " + COLUMN_TRANSACTION_FLAG +
	    		") " +
	    		"VALUES (?, ?, ?)"; 
	    */
	    
	    public static final String RETRIEVE_TOKEN 				= "select " + COLUMN_TOKEN  			+ " from " + TABLE_NAME;
	    public static final String RETRIEVE_EXPIREDATE 			= "select " + COLUMN_EXPIREDATE 		+ " from " + TABLE_NAME;
	    public static final String RETRIEVE_TRANSACTION_FLAG 	= "select " + COLUMN_TRANSACTION_FLAG	+ " from " + TABLE_NAME;

	    //create table mytoken (token_value TEXT, expire_date INTEGER, status INTEGER);
	    private static final String SQL_CREATE_TABLE = 
	    "CREATE TABLE " + 
	    TABLE_NAME + 
	    " (" +
		    COLUMN_TOKEN 					+ " " + COLUMN_TOKEN_TYPE 				+ ", " +
		    COLUMN_EXPIREDATE 				+ " " + COLUMN_EXPIREDATE_TYPE 			+ ", " +
		    COLUMN_TRANSACTION_FLAG 		+ " " + COLUMN_TRANSACTION_FLAG_TYPE +
	    ");";
	    
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

