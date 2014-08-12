package com.example.app_android.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.app_android.database.interfaces.ITextTable;
import com.example.app_android.util.Utilities;

public class TextTable extends BaseTable implements ITextTable {
	
	private static final String TABLE_NAME = "general_text";
	
	private static final String COLUMN_ID = "id";	    
    private static final String COLUMN_TEXT = "text";
    private static final String COLUMN_TEXT_HASH = "text_hash";
    
    private static final String COLUMN_ID_TYPE = "INTEGER primary_key";
    private static final String COLUMN_TEXT_TYPE = "TEXT";
    private static final String COLUMN_TEXT_HASH_TYPE = "INTEGER";
    private static final String RETRIEVE_TEXT 		= "select " + COLUMN_TEXT  		+ " from " + TABLE_NAME + " where " + COLUMN_ID + "=" +"?";
    private static final String RETRIEVE_TEXT_HASH 	= "select " + COLUMN_TEXT_HASH  + " from " + TABLE_NAME + " where " + COLUMN_ID + "=" +"?";
  
    /*
    Code that was produced for usage but ended up not being used. Saving it for future reference. This may be removed safely if everything works. 
    2014-08-11
   	
   
    private static final String UPDATE_TEXT			= "update " + TABLE_NAME + " SET " + COLUMN_TEXT + "=?, " + COLUMN_TEXT_HASH + "=? where " + COLUMN_ID + "=?";

	// TEST CODE THAT WAS WRITTEN TO TEST DB queries
	 * 
	 *     http://sqlfiddle.com/#!5/70b3f/1
    create table general_text (id INTEGER PRIMARY_KEY, text TEXT, text_hash INTEGER);
    insert into general_text VALues (1, "Test1", 1);
	insert into general_text values (2, "Test2", 2);
	
	select text from general_text where id = 2;
	select text_hash from general_text where id = 2;	
	update general_text SET text='Alfred Schmidt', text_hash=515151515151 WHERE id = 2;
	
	select text from general_text where id = 2;
	select text_hash from general_text where id = 2;    
 
*/
    private static final String LOCAl_SQL_CREATE_TABLE = 
    "CREATE TABLE " + 
    TABLE_NAME + 
    " (" +
    	COLUMN_ID 					+ " " + COLUMN_ID_TYPE 				+ ", " +
    	COLUMN_TEXT 				+ " " + COLUMN_TEXT_TYPE 			+ ", " +
	    COLUMN_TEXT_HASH 			+ " " + COLUMN_TEXT_HASH_TYPE +
    ");";

	public TextTable(SQLiteOpenHelper SQLiteOpenHelper) {
		super(SQLiteOpenHelper);
		SQL_CREATE_TABLE = LOCAl_SQL_CREATE_TABLE;
		SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
		SQL_DEFAULT_VALUES = "";
	}
	
	@Override 
	public void fillTableWithDefaultData(SQLiteDatabase db) throws SQLException, DBException {
		db.beginTransaction();

		ContentValues values = new ContentValues();
		values.put(COLUMN_ID, "0");
		values.put(COLUMN_TEXT, "DEFAULT_VALUE_1");
		values.put(COLUMN_TEXT_HASH, 1);
		
		int result = 0;
		try {
			result = (int) db.insert(TABLE_NAME, null, values);
			db.setTransactionSuccessful();
			
		}catch(NullPointerException e) {
			if(Utilities.error) {Log.e(TAG, mClass + ":fillTableWithDefaultData()::db.insert()");}
			throw new DBException("NullPointerException. Message: " + e.getMessage());
		}
		catch(IllegalStateException e) {
			if(Utilities.error) {Log.e(TAG, mClass + ":fillTableWithDefaultData()::db.setTransactionSuccessful()");}
			throw new DBException("IllegalStateException. Message: " + e.getMessage());
		}
		/*
		 * This could also throw SQLException. Functions inherited from BaseTable do not need to catch SQLExceptions.
		 */
		finally{
			db.endTransaction();
			// Functions inherited from BaseTable MUST NOT call db.close();
		}
		
		if(result == 0) {
		    if(Utilities.error) {Log.e(TAG, mClass + ":fillTableWithDefaultData(); No entry was added to the database.");}
		    throw new DBException(mClass + ":fillTableWithDefaultData(); No entry was added to the database.");
		}
	};

	@Override
	public String getText(TextIdentifier textIdentifier) throws DBException, NoResultFoundDBException {
		if(Utilities.verbose) {Log.v(TAG, mClass + ":getText()");}
		
		SQLiteDatabase db = mHelper.getReadableDatabase();
		String returnText = null;
		
		try {
			Cursor cursor = db.rawQuery(RETRIEVE_TEXT, new String[] { Integer.toString(textIdentifier.ordinal())});
			if (cursor.moveToFirst()) {
				returnText = cursor.getString(0);
			}
		}
		catch(IllegalStateException e) {
			if(Utilities.error) {Log.v(TAG, mClass + ":getText()");}
			throw new DBException("IllegalStateException. Message: " + e.getMessage());
		}
		catch(SQLException e) {
			if(Utilities.error) {Log.v(TAG, mClass + ":getText(). SQLException. Something went wrong.");}
			throw new DBException("SQLException. Message: " + e.getMessage());
		}
		
		if(returnText == null) {
			throw new NoResultFoundDBException("error at: " + mClass + ":getText(); string is of nullvalue");
		}
		
		//	db.close(); // http://stackoverflow.com/questions/6608498/best-place-to-close-database-connection
		return returnText;
	}

	@Override
	public void updateText(TextIdentifier textIdentifier, String text, int text_hash) throws DBException, NoRowsAffectedDBException{
		if(Utilities.verbose) {Log.v(TAG, mClass + ":updateText()");}
		SQLiteDatabase db = mHelper.getWritableDatabase();
		db.beginTransaction();
		
		ContentValues values = new ContentValues();
		values.put(COLUMN_ID, textIdentifier.ordinal());
		values.put(COLUMN_TEXT, text);
		values.put(COLUMN_TEXT_HASH, text_hash);
		
		int result = 0;
		try {
			result = db.update(TABLE_NAME, values, null, null);	// TODO
			// More than one entry was changed. This functionality should not be supported and it's quite terrible if this starts happening.
			if(result > 1) {
				throw new DBException(mClass + ":updateText();" + " More than one entry was changed. Aborting.");
			}				
			db.setTransactionSuccessful();	
		}catch(NullPointerException e) {
			if(Utilities.error) {Log.e(TAG, mClass + ":updateText()::db.insert();");}
			throw new DBException("NullPointerException. Message: " + e.getMessage());
		}
		catch(IllegalStateException e) {
			if(Utilities.error) {Log.e(TAG, mClass + ":updateText()::db.setTransactionSuccessful();");}
			throw new DBException("IllegalStateException. Message: " + e.getMessage());
		}
		catch(SQLException e) {
			if(Utilities.error) {Log.e(TAG, mClass + ":updateText(); SQLException, something went wrong.");}
			throw new DBException("SQLException. Message: " + e.getMessage());
		}
		finally {
			db.endTransaction();
			//	db.close(); // http://stackoverflow.com/questions/6608498/best-place-to-close-database-connection		
		}		
		
		// If no rows were affected.
		if(result == 0) {
		    if(Utilities.error) {Log.e(TAG, mClass + ":updateText(); No entry was added to the database.");}
		    throw new NoRowsAffectedDBException(mClass + ":updateText(); No entry was added to the database.");
		}	
	}

	@Override
	public int getTextHash(TextIdentifier textIdentifier) throws DBException, NoResultFoundDBException {
	if(Utilities.verbose) {Log.v(TAG, mClass + ":getTextHash()");}
		
		SQLiteDatabase db = mHelper.getReadableDatabase();
		int returnHash = -1;
		db.beginTransaction();
		
		try {
			Cursor cursor = db.rawQuery(RETRIEVE_TEXT_HASH, new String[] { Integer.toString(textIdentifier.ordinal())});
			if (cursor.moveToFirst()) {
				returnHash = cursor.getInt(0);
			}
			db.setTransactionSuccessful();
		}catch(NullPointerException e) {
			if(Utilities.error) {Log.e(TAG, mClass + ":getTextHash()::db.insert();");}
			throw new DBException("NullPointerException. Message: " + e.getMessage());
		}
		catch(IllegalStateException e) {
			if(Utilities.error) {Log.e(TAG, mClass + ":getTextHash()::db.setTransactionSuccessful();");}
			throw new DBException("IllegalStateException. Message: " + e.getMessage());
		}
		catch(SQLException e) {
			if(Utilities.error) {Log.e(TAG, mClass + ":getTextHash(); SQLException, something went wrong.");}
			throw new DBException("SQLException. Message: " + e.getMessage());
		}
		finally {
			db.endTransaction();
			//	db.close(); // http://stackoverflow.com/questions/6608498/best-place-to-close-database-connection		
		}	
		
		// No value found
		if(returnHash == -1) {
			if(Utilities.error) {Log.e(TAG, mClass + ":getTextHash(); No result was found in database for TextIdentifier= " + textIdentifier.toString());}
			throw new NoResultFoundDBException(mClass + ":getTextHash(); No result was found in database for TextIdentifier= " + textIdentifier.toString());
		}
		return returnHash;
	}
}
