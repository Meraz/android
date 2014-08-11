package com.example.app_android.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.app_android.database.interfaces.ITextTable;
import com.example.app_android.database.interfaces.ITokenTable.TransactionFlag;
import com.example.app_android.util.Utilities;

public class TextTable extends BaseTable implements ITextTable {
	
	/*
	 * 
	 * http://sqlfiddle.com/#!5/70b3f/1
	 * // Create
			create table general_text (id INTEGER PRIMARY_KEY, text TEXT, text_hash INTEGER);
			
			insert into general_text VALues (0, "abrgh", 514);
			insert into general_text values (2, "abrgh", 514);
			
			
			
			// Retrieve and update
			select text from general_text where id = 2;
			select text_hash from general_text where id = 2;
			
			update general_text SET text='Alfred Schmidt', text_hash=515151515151 WHERE id = 2;
			
			select text from general_text where id = 2;
			select text_hash from general_text where id = 2;
	 */
	
	
	private static final String TABLE_NAME = "general_text";
	
	private static final String COLUMN_ID = "id";	    
    private static final String COLUMN_TEXT = "text";
    private static final String COLUMN_TEXT_HASH = "text_hash";
    
    private static final String COLUMN_ID_TYPE = "primary key INTEGER";
    private static final String COLUMN_TEXT_TYPE = "TEXT";
    private static final String COLUMN_TEXT_HASH_TYPE = "INTEGER";
    
    private static final String RETRIEVE_TEXT 		= "select " + COLUMN_TEXT  			+ " from " + TABLE_NAME + "where " + COLUMN_ID + "=" +"?";
    private static final String RETRIEVE_TEXT_HASH 	= "select " + COLUMN_TEXT_HASH  			+ " from " + TABLE_NAME + "where " + COLUMN_ID + "=" +"?";
    private static final String UPDATE_TEXT			= "update " + TABLE_NAME + " SET " + COLUMN_TEXT + "=?, " + COLUMN_TEXT_HASH + "=? where " + COLUMN_ID + "=?";

    private static final String LOCAl_SQL_CREATE_TABLE = 
    "CREATE TABLE " + 
    TABLE_NAME + 
    " (" +
    	COLUMN_ID 					+ " " + COLUMN_ID_TYPE 				+ ", " +
    	COLUMN_TEXT 				+ " " + COLUMN_TEXT_TYPE 			+ ", " +
	    COLUMN_TEXT_HASH 		+ " " + COLUMN_TEXT_HASH_TYPE +
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
		values.put(COLUMN_ID, "1");
		values.put(COLUMN_TEXT, "TETX");
		values.put(COLUMN_TEXT_HASH, 51515151);
		
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
	public String getText(TextIdentifier textIdentifier) throws DBException {
		// TODO
		return "";
	}

	@Override
	public void setText(TextIdentifier textIdentifier, String text, int text_hash) throws DBException, NoRowsAffectedDBException{
		if(Utilities.verbose) {Log.v(TAG, mClass + ":updateToken()");}
		SQLiteDatabase db = mHelper.getWritableDatabase();
		db.beginTransaction();
		
		ContentValues values = new ContentValues();
		values.put(COLUMN_ID, textIdentifier.ordinal());
		values.put(COLUMN_TEXT, text);
		values.put(COLUMN_TEXT_HASH, text_hash);
		
		int result = 0;
		try {
			result = db.update(TABLE_NAME, values, null, null);
			db.setTransactionSuccessful();	
		}catch(NullPointerException e) {
			if(Utilities.error) {Log.v(TAG, mClass + ":setText()::db.insert()");}
			throw new DBException("NullPointerException. Message: " + e.getMessage());
		}
		catch(IllegalStateException e) {
			if(Utilities.error) {Log.v(TAG, mClass + ":setText()::db.setTransactionSuccessful()");}
			throw new DBException("IllegalStateException. Message: " + e.getMessage());
		}
		finally {
			db.endTransaction();
			//	db.close(); // http://stackoverflow.com/questions/6608498/best-place-to-close-database-connection		
		}
		
		if(result == 0) {
			throw new NoRowsAffectedDBException("error at: " + mClass + ":setText()");
		}	
	}
}
