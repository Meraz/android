package com.example.app_android.database;

import com.example.app_android.R;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MapCoordinateTable extends BaseTable implements IMapCoordinateTable {
	
	private static final String TABLE_NAME = "coordinateTable";
	
	private static final String COLUMN_NAME 				= "name";
	private static final String COLUMN_DESCRIPTION 			= "description";
	private static final String COLUMN_COORDINATE_LATITUDE 	= "latitude";
	private static final String COLUMN_COORDINATE_LONGITUDE = "longitude";
	private static final String COLUMN_ICON_ID				= "iconId";
	
	private static final String COLUMN_NAME_TYPE 				= "VARCHAR(50) PRIMARY KEY";
	private static final String COLUMN_DESCRIPTION_TYPE 		= "TEXT";
	private static final String COLUMN_COORDINATE_LATITUDE_TYPE = "REAL";
	private static final String COLUMN_COORDINATE_LONGITUDE_TYPE = "REAL";
	private static final String COLUMN_ICON_ID_TYPE				= "INTEGER";
	
	private static final String LOCAL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " " +
	"("+
		COLUMN_NAME					+ " " + COLUMN_NAME_TYPE 				+ ", " +
		COLUMN_DESCRIPTION 			+ " " + COLUMN_DESCRIPTION_TYPE 		+ ", " +
		COLUMN_COORDINATE_LATITUDE 	+ " " + COLUMN_COORDINATE_LATITUDE_TYPE + ", " +
		COLUMN_COORDINATE_LONGITUDE + " " + COLUMN_COORDINATE_LONGITUDE_TYPE + ", " +
		COLUMN_ICON_ID				+ " " + COLUMN_ICON_ID_TYPE				+
	")";
	
	private static final String RETRIEVE_MARKER_INFO = "select * from " + TABLE_NAME + " where " + COLUMN_NAME + " = ?";
	
	private static final int MARKER_ICON_ID_HOUSE_A 	= 0;
	private static final int MARKER_ICON_ID_HOUSE_B 	= 1;
	private static final int MARKER_ICON_ID_HOUSE_C 	= 2;
	private static final int MARKER_ICON_ID_HOUSE_D 	= 3;
	private static final int MARKER_ICON_ID_HOUSE_G 	= 4;
	private static final int MARKER_ICON_ID_HOUSE_H 	= 5;
	private static final int MARKER_ICON_ID_HOUSE_J 	= 6;
	private static final int MARKER_ICON_ID_ROTUNDAN 	= 7;
	private static final int MARKER_ÌCON_ID_BSK_OFFICE 	= 8;
	
	public MapCoordinateTable(SQLiteOpenHelper SQLiteOpenHelper) {
		super(SQLiteOpenHelper);
		SQL_CREATE_TABLE = LOCAL_CREATE_TABLE;
		SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
	}
	
	public void fillTableWithDefaultData(SQLiteDatabase db) { //TODO save the names in an enum to avoid inline string comparisons
		
		final int defaultValueCount = 13;
		
		ContentValues values[] = new ContentValues[defaultValueCount];
		values[0] = new ContentValues();
		values[0].put(COLUMN_NAME, "HOUSE_A");
		values[0].put(COLUMN_DESCRIPTION, "Description missing");
		values[0].put(COLUMN_COORDINATE_LATITUDE, 56.182016);
		values[0].put(COLUMN_COORDINATE_LONGITUDE, 15.590502);
		values[0].put(COLUMN_ICON_ID, MARKER_ICON_ID_HOUSE_A);
		
		values[1] = new ContentValues();
		values[1].put(COLUMN_NAME, "HOUSE_B");
		values[1].put(COLUMN_DESCRIPTION, "Description missing");
		values[1].put(COLUMN_COORDINATE_LATITUDE, 56.180673);
		values[1].put(COLUMN_COORDINATE_LONGITUDE, 15.590691);
		values[1].put(COLUMN_ICON_ID, MARKER_ICON_ID_HOUSE_B);
		
		values[2] = new ContentValues();
		values[2].put(COLUMN_NAME, "HOUSE_C");
		values[2].put(COLUMN_DESCRIPTION, "Description missing");
		values[2].put(COLUMN_COORDINATE_LATITUDE, 56.181189);
		values[2].put(COLUMN_COORDINATE_LONGITUDE, 15.592303);
		values[2].put(COLUMN_ICON_ID, MARKER_ICON_ID_HOUSE_C);
		
		values[3] = new ContentValues();
		values[3].put(COLUMN_NAME, "HOUSE_D");
		values[3].put(COLUMN_DESCRIPTION, "Description missing");
		values[3].put(COLUMN_COORDINATE_LATITUDE, 56.181670);
		values[3].put(COLUMN_COORDINATE_LONGITUDE, 15.592375);
		values[3].put(COLUMN_ICON_ID, MARKER_ICON_ID_HOUSE_D);
		
		values[4] = new ContentValues();
		values[4].put(COLUMN_NAME, "HOUSE_G");
		values[4].put(COLUMN_DESCRIPTION, "Description missing");
		values[4].put(COLUMN_COORDINATE_LATITUDE, 56.181891);
		values[4].put(COLUMN_COORDINATE_LONGITUDE, 15.591308);
		values[4].put(COLUMN_ICON_ID, MARKER_ICON_ID_HOUSE_G);
		
		values[5] = new ContentValues();
		values[5].put(COLUMN_NAME, "HOUSE_H");
		values[5].put(COLUMN_DESCRIPTION, "Description missing");
		values[5].put(COLUMN_COORDINATE_LATITUDE, 56.182348);
		values[5].put(COLUMN_COORDINATE_LONGITUDE, 15.590819);
		values[5].put(COLUMN_ICON_ID, MARKER_ICON_ID_HOUSE_H);
		
		values[6] = new ContentValues();
		values[6].put(COLUMN_NAME, "HOUSE_J");
		values[6].put(COLUMN_DESCRIPTION, "Description missing");
		values[6].put(COLUMN_COORDINATE_LATITUDE, 56.182933);
		values[6].put(COLUMN_COORDINATE_LONGITUDE, 15.590401);
		values[6].put(COLUMN_ICON_ID, MARKER_ICON_ID_HOUSE_J);
		
		values[7] = new ContentValues();
		values[7].put(COLUMN_NAME, "HOUSE_K");
		values[7].put(COLUMN_DESCRIPTION, "Description missing");
		values[7].put(COLUMN_COORDINATE_LATITUDE, 56.181711);
		values[7].put(COLUMN_COORDINATE_LONGITUDE, 15.589841);
		values[7].put(COLUMN_ICON_ID, MARKER_ICON_ID_ROTUNDAN);
		
		values[8] = new ContentValues();
		values[8].put(COLUMN_NAME, "KARLSHAMN_HOUSE_A");
		values[8].put(COLUMN_DESCRIPTION, "Description missing");
		values[8].put(COLUMN_COORDINATE_LATITUDE, 56.163626);
		values[8].put(COLUMN_COORDINATE_LONGITUDE, 14.866623);
		values[8].put(COLUMN_ICON_ID, MARKER_ICON_ID_HOUSE_A);
		
		values[9] = new ContentValues();
		values[9].put(COLUMN_NAME, "KARLSHAMN_HOUSE_B");
		values[9].put(COLUMN_DESCRIPTION, "Description missing");
		values[9].put(COLUMN_COORDINATE_LATITUDE, 56.164464);
		values[9].put(COLUMN_COORDINATE_LONGITUDE, 14.866012);
		values[9].put(COLUMN_ICON_ID, MARKER_ICON_ID_HOUSE_B);
		
		values[10] = new ContentValues();
		values[10].put(COLUMN_NAME, "CAMPUS_KARLSKRONA");
		values[10].put(COLUMN_DESCRIPTION, "Description missing");
		values[10].put(COLUMN_COORDINATE_LATITUDE, 56.182242);
		values[10].put(COLUMN_COORDINATE_LONGITUDE, 15.590712);
		
		values[11] = new ContentValues();
		values[11].put(COLUMN_NAME, "CAMPUS_KARLSHAMN");
		values[11].put(COLUMN_DESCRIPTION, "Description missing");
		values[11].put(COLUMN_COORDINATE_LATITUDE, 56.164384);
		values[11].put(COLUMN_COORDINATE_LONGITUDE, 14.866024);
		
		values[12] = new ContentValues();
		values[12].put(COLUMN_NAME, "BSK_OFFICE");
		values[12].put(COLUMN_DESCRIPTION, "Description missing");
		values[12].put(COLUMN_COORDINATE_LATITUDE, 56.181975);
		values[12].put(COLUMN_COORDINATE_LONGITUDE, 15.589975);
		values[12].put(COLUMN_ICON_ID, MARKER_ÌCON_ID_BSK_OFFICE);
		
		db.beginTransaction();
		
		for(int i = 0; i < defaultValueCount; ++i) {
			db.insert(TABLE_NAME, null, values[i]);
		}
		
		db.setTransactionSuccessful();
		db.endTransaction();
	}
	
	@Override
	public LatLng getMapCoordinate(String name) {
		LatLng coordinates = null;
		
		SQLiteDatabase db = mHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(RETRIEVE_MARKER_INFO, new String[] { name });
		
		if(cursor.moveToFirst()) {
			coordinates = new LatLng(cursor.getFloat(2), cursor.getFloat(3));
		}
		return coordinates;
	}
	
	@Override
	public MarkerOptions getMapMarkerOptions(String name) {
		MarkerOptions options = null;
		
		SQLiteDatabase db = mHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(RETRIEVE_MARKER_INFO, new String[] { name });
		
		if(cursor.moveToFirst()) {
			options = new MarkerOptions();
			options.title("");	//The title is expected to be incluided in the snippet since the snippet is being formatted as HMTL
			options.snippet(cursor.getString(1));
			options.position(new LatLng(cursor.getFloat(2), cursor.getFloat(3)));
			options.icon(getIconFromId(cursor.getInt(4)));
		}
		
		db.close();
		return options;
	}
	
	private BitmapDescriptor getIconFromId(int iconId) {
		BitmapDescriptor icon = null;
		
		switch (iconId) {
		case MARKER_ICON_ID_HOUSE_A:
			icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_a);
			break;
			
		case MARKER_ICON_ID_HOUSE_B:
			icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_b);
			break;
			
		case MARKER_ICON_ID_HOUSE_C:
			icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_c);
			break;
			
		case MARKER_ICON_ID_HOUSE_D:
			icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_d);
			break;
			
		case MARKER_ICON_ID_HOUSE_G:
			icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_g);
			break;
			
		case MARKER_ICON_ID_HOUSE_H:
			icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_h);
			break;
			
		case MARKER_ICON_ID_HOUSE_J:
			icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_j);
			break;
			
		case MARKER_ICON_ID_ROTUNDAN:
			icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_rotundan);
			break;
			
		case MARKER_ÌCON_ID_BSK_OFFICE:
			icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_bsk);
			break;
		}
		return icon;
	}
}