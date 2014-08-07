package com.example.app_android.database;


import com.example.app_android.util.Utilities;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
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
    
    private static final String RETRIEVE_TOKEN 				= "select " + COLUMN_TOKEN  			+ " from " + TABLE_NAME;
    private static final String RETRIEVE_EXPIREDATE 		= "select " + COLUMN_EXPIREDATE 		+ " from " + TABLE_NAME;
    private static final String RETRIEVE_TRANSACTION_FLAG 	= "select " + COLUMN_TRANSACTION_FLAG	+ " from " + TABLE_NAME;

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
		SQL_DEFAULT_VALUES = "";
	}
	
	// Overrides this function as I'm using another method when inserting
	@Override
	public void fillTableWithDefaultData(SQLiteDatabase db) throws SQLException, DBException {
		super.fillTableWithDefaultData(db);
		
		db.beginTransaction();

		ContentValues values = new ContentValues();
		values.put(COLUMN_TOKEN, "DefaultToken");
		values.put(COLUMN_EXPIREDATE, (int)-1);
		values.put(COLUMN_TRANSACTION_FLAG, (int)TransactionFlag.Unknown.ordinal());
		
		int result = 0;
		try {
			result = (int) db.insert(TABLE_NAME, null, values);
			db.setTransactionSuccessful();
			
		}catch(NullPointerException e) {
			if(Utilities.error) {Log.v(TAG, mClass + ":fillTableWithDefaultData()::db.insert()");}
			throw new DBException("NullPointerException. Message: " + e.getMessage());
		}
		catch(IllegalStateException e) {
			if(Utilities.error) {Log.v(TAG, mClass + ":fillTableWithDefaultData()::db.setTransactionSuccessful()");}
			throw new DBException("IllegalStateException. Message: " + e.getMessage());
		}
		finally{
			db.endTransaction();
		}
		
		if(result == 0) {
			throw new DBException("No entries removed in database.");
		}
	}
	
	// Returns token value as String. Inherited from ITokenTable
	@Override
	public String getTokenValue() {
		if(Utilities.verbose) {Log.v(TAG, mClass + ":getTokenValue()");}
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
		if(Utilities.verbose) {Log.v(TAG, mClass + ":getExpireDate()");}
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
	@Override
	public void updateToken(String tokenValue, long expireDate, TransactionFlag transaction_flag) throws DBException {
		if(Utilities.verbose) {Log.v(TAG, mClass + ":updateToken()");}
		SQLiteDatabase db = mHelper.getWritableDatabase();
		db.beginTransaction();
		int result = -1;
		
		ContentValues values = new ContentValues();
		values.put(COLUMN_TOKEN, tokenValue);
		values.put(COLUMN_EXPIREDATE, expireDate);
		values.put(COLUMN_TRANSACTION_FLAG, transaction_flag.ordinal());
		
		try {
			result = db.update(TABLE_NAME, values, null, null);
			db.setTransactionSuccessful();	
		}catch(NullPointerException e) {
			if(Utilities.error) {Log.v(TAG, mClass + ":fillTableWithDefaultData()::db.insert()");}
			throw new DBException("NullPointerException. Message: " + e.getMessage());
		}
		catch(IllegalStateException e) {
			if(Utilities.error) {Log.v(TAG, mClass + ":fillTableWithDefaultData()::db.setTransactionSuccessful()");}
			throw new DBException("IllegalStateException. Message: " + e.getMessage());
		}
		finally {
			db.endTransaction();
			db.close();				
		}
		
		if(result == -1) {
			throw new DBException("error at: " + mClass + ":fillTableWithDefaultData()");
		}
	}
	
	// Inherited from ITokenTable
	@Override
	public void updateTransactionFlag(TransactionFlag transaction_flag) throws DBException {
		if(Utilities.verbose) {Log.v(TAG, mClass + ":updateTransactionFlag()");}
		SQLiteDatabase db = mHelper.getWritableDatabase();
		db.beginTransaction();
		int result = -1;

		ContentValues values = new ContentValues();
		values.put(COLUMN_TRANSACTION_FLAG, transaction_flag.ordinal());
		try {
			result = db.update(TABLE_NAME, values, null, null);
			db.setTransactionSuccessful();
		}catch(NullPointerException e) {
			if(Utilities.error) {Log.v(TAG, mClass + ":fillTableWithDefaultData()::db.insert()");}
			throw new DBException("NullPointerException. Message: " + e.getMessage());
		}
		catch(IllegalStateException e) {
			if(Utilities.error) {Log.v(TAG, mClass + ":fillTableWithDefaultData()::db.setTransactionSuccessful()");}
			throw new DBException("IllegalStateException. Message: " + e.getMessage());
		}
		finally {
			db.endTransaction();
			db.close();				
		}
	}
	
	// Inherited from ITokenTable
	// Returns current transactionFlag on the token. There can only be one
	@Override
	public TransactionFlag getTransactionFlag(){
		if(Utilities.verbose) {Log.v(TAG, mClass + ":TransactionFlag()");}
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
		if(Utilities.verbose) {Log.v(TAG, mClass + ":PrintEntireToken()");}
		
		String token_value = getTokenValue();
		long expiredate = getExpireDate();
		TransactionFlag transactionFlag = getTransactionFlag();
		
		System.out.println("token_value: "+token_value);
		System.out.println("expiredate: "+expiredate);
		System.out.println("transactionFlag: "+transactionFlag.toString());
		
	}
}