package com.example.app_android.database;


import com.example.app_android.util.Logger;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TokenTable extends BaseTable implements ITokenTable{

	private static final String TABLE_NAME = "token";
	
	private static final String COLUMN_TOKEN = "token_value";	    
    private static final String COLUMN_EXPIREDATE = "expire_date";
    private static final String COLUMN_TRANSACTION_FLAG = "transaction_flag";
    
    private static final String COLUMN_TOKEN_TYPE = "TEXT";
    private static final String COLUMN_EXPIREDATE_TYPE = "INTEGER";
    private static final String COLUMN_TRANSACTION_FLAG_TYPE = "INTEGER";
  
    public static final String INSERT_ALL = "INSERT INTO " + TABLE_NAME +
    		"("+
    			COLUMN_TOKEN + ", " + COLUMN_EXPIREDATE + ", " + COLUMN_TRANSACTION_FLAG +
    		") " +
    		"VALUES (?, ?, ?)";     
    
    private static final String RETRIEVE_TOKEN 				= "select " + COLUMN_TOKEN  			+ " from " + TABLE_NAME;
    private static final String RETRIEVE_EXPIREDATE 		= "select " + COLUMN_EXPIREDATE 		+ " from " + TABLE_NAME;
    private static final String RETRIEVE_TRANSACTION_FLAG 	= "select " + COLUMN_TRANSACTION_FLAG	+ " from " + TABLE_NAME;

    //create table mytoken (token_value TEXT, expire_date INTEGER, status INTEGER);
    private static final String LOCAl_SQL_CREATE_TABLE = 
    "CREATE TABLE " + 
    TABLE_NAME + 
    " (" +
	    COLUMN_TOKEN 					+ " " + COLUMN_TOKEN_TYPE 				+ ", " +
	    COLUMN_EXPIREDATE 				+ " " + COLUMN_EXPIREDATE_TYPE 			+ ", " +
	    COLUMN_TRANSACTION_FLAG 		+ " " + COLUMN_TRANSACTION_FLAG_TYPE +
    ");";
			
	// CONSTRUCTOR
	public TokenTable(SQLiteOpenHelper SQLiteOpenHelper) {
		super(SQLiteOpenHelper);
		SQL_CREATE_TABLE = LOCAl_SQL_CREATE_TABLE;
		SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
		SQL_DEFAULT_VALUES = INSERT_ALL;
	}
	
	// Overrides this function as I'm using another method when inserting
	// TODO, this function should return data and throw exception if nothing happens
	@Override
	public void fillTableWithDefaultData(SQLiteDatabase db) {
		db.beginTransaction();

		ContentValues values = new ContentValues();
		values.put(COLUMN_TOKEN, "DefaultToken");
		values.put(COLUMN_EXPIREDATE, (int)-1);
		values.put(COLUMN_TRANSACTION_FLAG, (int)TransactionFlag.Unknown.ordinal());
		
	    @SuppressWarnings("unused")
		long result = db.insert(TABLE_NAME, null, values); // TODO, this function should return data and throw exception if nothing happens

		db.setTransactionSuccessful();
		db.endTransaction();
	}
	
	// Returns token value as String. Inherited from ITokenTable
	@Override
	public String getTokenValue() {
		SQLiteDatabase db = mHelper.getReadableDatabase();
		
	    Cursor cursor = db.rawQuery(RETRIEVE_TOKEN, null);
	    String token = "";
	    
	    if (cursor.moveToFirst()) {
	    	token = cursor.getString(0); // Database SHOULD only hold one. If there's ever more than one entry, somethings wrong. Should add check for this...
	    }
	    db.close();

		return token;
	}
	
	// Inherited from ITokenTable
	// Returns the expiredate for this current token in int. 
	public long getExpireDate(){
		SQLiteDatabase db = mHelper.getReadableDatabase();
		
	    Cursor cursor = db.rawQuery(RETRIEVE_EXPIREDATE, null);
	    long expiredate = -1;
	    
	    if (cursor.moveToFirst()) {
	    	expiredate = cursor.getLong(0); // Database SHOULD only hold one. If there's ever more than one entry, somethings wrong. Should add check for this...
	    }
	    db.close();
	    return expiredate;
	}
		
	// Inherited from ITokenTable
	// Updates token with new tokenvalue, expiredate and sets the new transaction_flag.
	// returns the amount of rows updated. 0 if none. -1 error
	@Override
	public int updateToken(String tokenValue, long expireDate, TransactionFlag transaction_flag) {
		SQLiteDatabase db = mHelper.getWritableDatabase();
		db.beginTransaction();
		int result = -1;
		
		try {
			ContentValues values = new ContentValues();
			values.put(COLUMN_TOKEN, tokenValue);
			values.put(COLUMN_EXPIREDATE, expireDate);
			values.put(COLUMN_TRANSACTION_FLAG, transaction_flag.ordinal());
			result = db.update(TABLE_NAME, values, null, null);
			db.setTransactionSuccessful();	
		}
		catch(Exception e) {
			Logger.ErrorLog("Error TokenTable. Message: " + e.getMessage());
			return -1;
		}
		finally {
			db.endTransaction();
			db.close();				
		}
		return result;
	}
	
	// Inherited from ITokenTable
	@Override
	public void updateTransactionFlag(TransactionFlag transaction_flag) {
		SQLiteDatabase db = mHelper.getWritableDatabase();
		db.beginTransaction();

		ContentValues values = new ContentValues();
		values.put(COLUMN_TRANSACTION_FLAG, transaction_flag.ordinal());
	    db.update(TABLE_NAME, values, null, null);

		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();
	}
	
	// Inherited from ITokenTable
	// Returns current transactionFlag on the token. There can only be one
	@Override
	public TransactionFlag getTransactionFlag(){
		SQLiteDatabase db = mHelper.getReadableDatabase();
		
	    Cursor cursor = db.rawQuery(RETRIEVE_TRANSACTION_FLAG, null);
	    TransactionFlag transactionFlag = TransactionFlag.Unknown;
	    
	    if (cursor.moveToFirst()) {
	    	// Database SHOULD only hold one. If there's ever more than one entry, somethings wrong. Should add check for this...
	    	transactionFlag = TransactionFlag.values()[cursor.getInt(0)]; 
	    }
	    db.close();
	    return transactionFlag;
	}

	@Override
	public void PrintEntireToken() {
		String token_value = getTokenValue();
		long expiredate = getExpireDate();
		TransactionFlag transactionFlag = getTransactionFlag();
		
		System.out.println("token_value: "+token_value);
		System.out.println("expiredate: "+expiredate);
		System.out.println("transactionFlag: "+transactionFlag.toString());
		
	}
}