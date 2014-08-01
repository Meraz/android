package com.example.app_android.database;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MapCoordinateTable extends BaseTable implements IMapCoordinateTable {

	private static final String TABLE_NAME = "coordinateTable";
	
	private static final String COLUMN_NAME = "name";
	private static final String COLUMN_DESCRIPTION = "description";
	private static final String COLUMN_COORDINATE_LATITUDE = "latitude";
	private static final String COLUMN_COORDINATE_LONGITUDE = "longitude";
	
	private static final String COLUMN_NAME_TYPE = "VARCHAR(50) PRIMARY KEY";
	private static final String COLUMN_DESCRIPTION_TYPE = "TEXT";
	private static final String COLUMN_COORDINATE_LATITUDE_TYPE = "REAL";
	private static final String COLUMN_COORDINATE_LONGITUDE_TYPE = "REAL";
	
	private static final String LOCAL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " " +
	"("+
		COLUMN_NAME	+ " " + COLUMN_NAME_TYPE + ", " +
		COLUMN_DESCRIPTION + " " + COLUMN_DESCRIPTION_TYPE + ", " +
		COLUMN_COORDINATE_LATITUDE + " " + COLUMN_COORDINATE_LATITUDE_TYPE + ", " +
		COLUMN_COORDINATE_LONGITUDE + " " + COLUMN_COORDINATE_LONGITUDE_TYPE +
	")";
	
	private static final String RETRIEVE_MARKER_INFO = "select * from " + TABLE_NAME + " where " + COLUMN_NAME + " = ?";
	
	public MapCoordinateTable(SQLiteOpenHelper SQLiteOpenHelper) {
		super(SQLiteOpenHelper);
		SQL_CREATE_TABLE = LOCAL_CREATE_TABLE;
		SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
	}
	
	public void fillTableWithDefaultData(SQLiteDatabase db) { //TODO saves the names in an enum to avoid inline string comparisons
		
		final int defaultValueCount = 10;
		
		ContentValues values[] = new ContentValues[defaultValueCount];
		values[0] = new ContentValues();
		values[0].put(COLUMN_NAME, "HOUSE_A");
		values[0].put(COLUMN_DESCRIPTION, "Description missing");
		values[0].put(COLUMN_COORDINATE_LATITUDE, 56.182016);
		values[0].put(COLUMN_COORDINATE_LONGITUDE, 15.590502);
		
		values[1] = new ContentValues();
		values[1].put(COLUMN_NAME, "HOUSE_B");
		values[1].put(COLUMN_DESCRIPTION, "Description missing");
		values[1].put(COLUMN_COORDINATE_LATITUDE, 56.180673);
		values[1].put(COLUMN_COORDINATE_LONGITUDE, 15.590691);
		
		values[2] = new ContentValues();
		values[2].put(COLUMN_NAME, "HOUSE_C");
		values[2].put(COLUMN_DESCRIPTION, "Description missing");
		values[2].put(COLUMN_COORDINATE_LATITUDE, 56.181237);
		values[2].put(COLUMN_COORDINATE_LONGITUDE, 15.592322);
		
		values[3] = new ContentValues();
		values[3].put(COLUMN_NAME, "HOUSE_D");
		values[3].put(COLUMN_DESCRIPTION, "Description missing");
		values[3].put(COLUMN_COORDINATE_LATITUDE, 56.181670);
		values[3].put(COLUMN_COORDINATE_LONGITUDE, 15.592375);
		
		values[4] = new ContentValues();
		values[4].put(COLUMN_NAME, "HOUSE_G");
		values[4].put(COLUMN_DESCRIPTION, "Description missing");
		values[4].put(COLUMN_COORDINATE_LATITUDE, 56.181891);
		values[4].put(COLUMN_COORDINATE_LONGITUDE, 15.591308);
		
		values[5] = new ContentValues();
		values[5].put(COLUMN_NAME, "HOUSE_H");
		values[5].put(COLUMN_DESCRIPTION, "Description missing");
		values[5].put(COLUMN_COORDINATE_LATITUDE, 56.182348);
		values[5].put(COLUMN_COORDINATE_LONGITUDE, 15.590819);
		
		values[6] = new ContentValues();
		values[6].put(COLUMN_NAME, "HOUSE_J");
		values[6].put(COLUMN_DESCRIPTION, "Description missing");
		values[6].put(COLUMN_COORDINATE_LATITUDE, 56.182933);
		values[6].put(COLUMN_COORDINATE_LONGITUDE, 15.590401);
		
		values[7] = new ContentValues();
		values[7].put(COLUMN_NAME, "HOUSE_K");
		values[7].put(COLUMN_DESCRIPTION, "Description missing");
		values[7].put(COLUMN_COORDINATE_LATITUDE, 56.181816);
		values[7].put(COLUMN_COORDINATE_LONGITUDE, 15.589894);
		
		values[8] = new ContentValues();
		values[8].put(COLUMN_NAME, "KARLSHAMN_HOUSE_A");
		values[8].put(COLUMN_DESCRIPTION, "Description missing");
		values[8].put(COLUMN_COORDINATE_LATITUDE, 56.163626);
		values[8].put(COLUMN_COORDINATE_LONGITUDE, 14.866623);
		
		values[9] = new ContentValues();
		values[9].put(COLUMN_NAME, "KARLSHAMN_HOUSE_B");
		values[9].put(COLUMN_DESCRIPTION, "Description missing");
		values[9].put(COLUMN_COORDINATE_LATITUDE, 56.163626);
		values[9].put(COLUMN_COORDINATE_LONGITUDE, 14.866012);
		
		db.beginTransaction();
		
		for(int i = 0; i < defaultValueCount; ++i) {
			db.insert(TABLE_NAME, null, values[i]);
		}
		
		db.setTransactionSuccessful();
		db.endTransaction();
	}

	@Override
	public MarkerOptions getMapMarkerOptions(String name) {
		MarkerOptions options = new MarkerOptions();
		
		SQLiteDatabase db = mHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(RETRIEVE_MARKER_INFO, new String[] { name });
		
		if(cursor.moveToFirst()) {
			options.title("");	//The title is expected to be incluided in the snippet since the snippet is being formatted as HMTL
			options.snippet(cursor.getString(1));
			options.position(new LatLng(cursor.getFloat(2), cursor.getFloat(3)));
		}
		
		db.close();
		return options;
	}
}