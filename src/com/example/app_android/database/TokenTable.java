package com.example.app_android.database;

import com.example.app_android.database.interfaces.ITokenTable;
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
		
		int result = -1;
		try {
			result = (int) db.insert(TABLE_NAME, null, values);
			db.setTransactionSuccessful();
		}catch(NullPointerException e) {
			if(Utilities.error) {Log.e(TAG, mClass + ":fillTableWithDefaultData()::db.insert();");}
			throw new DBException("NullPointerException. Message: " + e.getMessage());
		}
		catch(IllegalStateException e) {
			if(Utilities.error) {Log.e(TAG, mClass + ":fillTableWithDefaultData()::db.setTransactionSuccessful();");}
			throw new DBException("IllegalStateException. Message: " + e.getMessage());
		}
		finally{
			db.endTransaction();
		}
		
		if(result == -1) {
	        if(Utilities.error) {Log.e(TAG, mClass + ":fillTableWithDefaultData(); No entry was added to the database.");}
	        throw new DBException(mClass + ":fillTableWithDefaultData(); No entry was added to the database.");
		}
	}
	
	// Returns token value as String. Inherited from ITokenTable
	@Override
	public String getTokenValue() throws DBException, NoResultFoundDBException {
		if(Utilities.verbose) {Log.v(TAG, mClass + ":getTokenValue()");}
		SQLiteDatabase db = mHelper.getReadableDatabase();
	    db.beginTransaction();
	    String token = null;
	    try{
		    Cursor cursor = db.rawQuery(RETRIEVE_TOKEN, null);
		    if (cursor.moveToFirst()) {
		    	token = cursor.getString(0);
		    }
		    else {
				if(Utilities.error) {Log.e(TAG, mClass + ":getTokenValue(); No result was found in database.");}
				throw new NoResultFoundDBException(mClass + ":getTokenValue(); No result was found in database.");
		    }
		    db.setTransactionSuccessful();
		}catch(NullPointerException e) {
			if(Utilities.error) {Log.e(TAG, mClass + ":getTokenValue()::db.insert();");}
			throw new DBException("NullPointerException. Message: " + e.getMessage());
		}
		catch(IllegalStateException e) {
			if(Utilities.error) {Log.e(TAG, mClass + ":getTokenValue()::db.setTransactionSuccessful();");}
			throw new DBException("IllegalStateException. Message: " + e.getMessage());
		}
		catch(SQLException e) {
			if(Utilities.error) {Log.e(TAG, mClass + ":getTokenValue(); SQLException, something went wrong.");}
			throw new DBException("SQLException. Message: " + e.getMessage());
		}
		finally {
			db.endTransaction();	
		}
		if(token == null) {
			if(Utilities.error) {Log.e(TAG, mClass + ":getTokenValue(); No result was found in database.");}
			throw new NoResultFoundDBException(mClass + ":getTokenValue(); No result was found in database.");
		}

		return token;
	}
	
	// Inherited from ITokenTable
	// Returns the expiredate for this current token in int. 
	public long getExpireDate() throws DBException, NoResultFoundDBException {
		if(Utilities.verbose) {Log.v(TAG, mClass + ":getExpireDate()");}
		SQLiteDatabase db = mHelper.getReadableDatabase();
	    db.beginTransaction();
	    long expiredate = -1;
	    try{
		    Cursor cursor = db.rawQuery(RETRIEVE_EXPIREDATE, null);
		    if (cursor.moveToFirst()) {
		    	expiredate = cursor.getLong(0);
		    }
		    else {
				if(Utilities.error) {Log.e(TAG, mClass + ":getExpireDate(); No result was found in database.");}
				throw new NoResultFoundDBException(mClass + ":getExpireDate(); No result was found in database.");
		    }
		    db.setTransactionSuccessful();
		}catch(NullPointerException e) {
			if(Utilities.error) {Log.e(TAG, mClass + ":getExpireDate()::db.insert();");}
			throw new DBException("NullPointerException. Message: " + e.getMessage());
		}
		catch(IllegalStateException e) {
			if(Utilities.error) {Log.e(TAG, mClass + ":getExpireDate()::db.setTransactionSuccessful();");}
			throw new DBException("IllegalStateException. Message: " + e.getMessage());
		}
		catch(SQLException e) {
			if(Utilities.error) {Log.e(TAG, mClass + ":getTokenValue(); SQLException, something went wrong.");}
			throw new DBException("SQLException. Message: " + e.getMessage());
		}
		finally {
			db.endTransaction();		
		}
		if(expiredate == -1) {
			if(Utilities.error) {Log.e(TAG, mClass + ":getExpireDate(); No result was found in database.");}
			throw new NoResultFoundDBException(mClass + ":getExpireDate(); No result was found in database.");
		}
		
	    return expiredate;
	}
		
	// Inherited from ITokenTable
	@Override
	public void updateToken(String tokenValue, long expireDate, TransactionFlag transaction_flag) throws DBException, NoRowsAffectedDBException{
		if(Utilities.verbose) {Log.v(TAG, mClass + ":updateToken()");}
		SQLiteDatabase db = mHelper.getWritableDatabase();
		db.beginTransaction();
		
		ContentValues values = new ContentValues();
		values.put(COLUMN_TOKEN, tokenValue);
		values.put(COLUMN_EXPIREDATE, expireDate);
		values.put(COLUMN_TRANSACTION_FLAG, transaction_flag.ordinal());
		
		int result = 0;
		try {
			result = db.update(TABLE_NAME, values, null, null);
			db.setTransactionSuccessful();	
		}catch(NullPointerException e) {
			if(Utilities.error) {Log.e(TAG, mClass + ":updateToken()::db.insert();");}
			throw new DBException("NullPointerException. Message: " + e.getMessage());
		}
		catch(IllegalStateException e) {
			if(Utilities.error) {Log.e(TAG, mClass + ":updateToken()::db.setTransactionSuccessful();");}
			throw new DBException("IllegalStateException. Message: " + e.getMessage());
		}
		catch(SQLException e) {
			if(Utilities.error) {Log.e(TAG, mClass + ":updateToken(); SQLException, something went wrong.");}
			throw new DBException("SQLException. Message: " + e.getMessage());
		}
		finally {
			db.endTransaction();	
		}
		
	    if(result == -1) {
	        if(Utilities.error) {Log.e(TAG, mClass + ":updateToken(); No entry was added to the database.");}
	        throw new NoRowsAffectedDBException(mClass + ":updateToken(); No entry was added to the database.");
	    }	        
	}
	
	// Inherited from ITokenTable
	@Override
	public void updateTransactionFlag(TransactionFlag transaction_flag) throws DBException, NoRowsAffectedDBException{
		if(Utilities.verbose) {Log.v(TAG, mClass + ":updateTransactionFlag()");}
		SQLiteDatabase db = mHelper.getWritableDatabase();
		db.beginTransaction();
		int result = 0;

		ContentValues values = new ContentValues();
		values.put(COLUMN_TRANSACTION_FLAG, transaction_flag.ordinal());
		try {
			result = db.update(TABLE_NAME, values, null, null);
			db.setTransactionSuccessful();
		}catch(NullPointerException e) {
			if(Utilities.error) {Log.e(TAG, mClass + ":updateTransactionFlag()::db.insert();");}
			throw new DBException("NullPointerException. Message: " + e.getMessage());
		}
		catch(IllegalStateException e) {
			if(Utilities.error) {Log.e(TAG, mClass + ":updateTransactionFlag()::db.setTransactionSuccessful();");}
			throw new DBException("IllegalStateException. Message: " + e.getMessage());
		}
		catch(SQLException e) {
			if(Utilities.error) {Log.e(TAG, mClass + ":updateTransactionFlag(); SQLException, something went wrong.");}
			throw new DBException("SQLException. Message: " + e.getMessage());
		}
		finally {
			db.endTransaction();		
		}
		if(result == 0) {
			 if(Utilities.error) {Log.e(TAG, mClass + ":updateTransactionFlag(); Token not updated.");}
			throw new NoRowsAffectedDBException(mClass + ":updateTransactionFlag(); Token not updated.");
		}
	}
	
	// Inherited from ITokenTable
	// Returns current transactionFlag on the token. There can only be one
	@Override
	public TransactionFlag getTransactionFlag() throws DBException, NoResultFoundDBException{
		if(Utilities.verbose) {Log.v(TAG, mClass + ":TransactionFlag()");}
		
		SQLiteDatabase db = mHelper.getReadableDatabase();
	    db.beginTransaction();
	    
	    TransactionFlag transactionFlag = TransactionFlag.Unknown;
	    try{
		    Cursor cursor = db.rawQuery(RETRIEVE_TRANSACTION_FLAG, null);	    
		    if (cursor.moveToFirst()) {
		    	// Database SHOULD only hold one. If there's ever more than one entry, somethings wrong. Should add check for this...
		    	transactionFlag = TransactionFlag.values()[cursor.getInt(0)]; 
		    }
		    else {
				if(Utilities.error) {Log.e(TAG, mClass + ":getExpireDate(); No result was found in database.");}
				throw new NoResultFoundDBException(mClass + ":getExpireDate(); No result was found in database.");
		    }
		    db.setTransactionSuccessful();
		}catch(NullPointerException e) {
			if(Utilities.error) {Log.e(TAG, mClass + ":getExpireDate()::db.insert();");}
			throw new DBException("NullPointerException. Message: " + e.getMessage());
		}
		catch(IllegalStateException e) {
			if(Utilities.error) {Log.e(TAG, mClass + ":getExpireDate()::db.setTransactionSuccessful();");}
			throw new DBException("IllegalStateException. Message: " + e.getMessage());
		}
		catch(SQLException e) {
			if(Utilities.error) {Log.e(TAG, mClass + ":getTokenValue(); SQLException, something went wrong.");}
			throw new DBException("SQLException. Message: " + e.getMessage());
		}
		finally {
			db.endTransaction();	
		}
		if(transactionFlag == TransactionFlag.Unknown) {
			if(Utilities.error) {Log.e(TAG, mClass + ":getExpireDate(); No result was found in database.");}
			throw new NoResultFoundDBException(mClass + ":getExpireDate(); No result was found in database.");
		}
	    return transactionFlag;
	}

	@Override
	public void PrintEntireToken() {
		if(Utilities.verbose) {Log.v(TAG, mClass + ":PrintEntireToken()");}
		
		String token_value = null;
		try {
			token_value = getTokenValue();
		} catch (DBException e) {
			e.printStackTrace();
		} catch (NoResultFoundDBException e) {
			token_value = "NO_VALUE";
			e.printStackTrace();
		}
		long expiredate = -1;
		try {
			expiredate = getExpireDate();
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoResultFoundDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TransactionFlag transactionFlag = TransactionFlag.Unknown;
		try {
			transactionFlag = getTransactionFlag();
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoResultFoundDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Log.v(TAG,"token_value: "+token_value);
		Log.v(TAG,"expiredate: "+expiredate);
		Log.v(TAG,"transactionFlag: "+transactionFlag.toString());	
	}
}