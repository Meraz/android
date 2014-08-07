package com.example.app_android.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.app_android.database.interfaces.ITextTable;
import com.example.app_android.database.interfaces.ITokenTable.TransactionFlag;
import com.example.app_android.util.Utilities;

public class TextTable extends BaseTable implements ITextTable {
	
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

	public TextTable(SQLiteOpenHelper SQLiteOpenHelper) {
		super(SQLiteOpenHelper);
		SQL_CREATE_TABLE = LOCAl_SQL_CREATE_TABLE;
		SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
		SQL_DEFAULT_VALUES = "";
	}
	
	@Override 
	public void fillTableWithDefaultData(SQLiteDatabase db) throws android.database.SQLException , DBException {
		
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
	};

	@Override
	public String getText(TextIdentifier textIdentifier) {
		// TODO
		return "";
	}

	@Override
	public void setText(TextIdentifier textIdentifier, String text, int text_hash) {
		// TODO		
	}



}
