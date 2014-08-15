package com.example.app_android.database;

import com.example.app_android.R;
import com.example.app_android.database.interfaces.IMapPlaceTable;
import com.example.app_android.util.MapPlaceIdentifiers;
import com.example.app_android.util.Utilities;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MapPlaceTable extends BaseTable implements IMapPlaceTable {
	
	private static final String TABLE_NAME = "coordinateTable";
	
	private static final String COLUMN_NAME 				= "name";
	private static final String COLUMN_DESCRIPTION 			= "description";
	private static final String COLUMN_COORDINATE_LATITUDE 	= "latitude";
	private static final String COLUMN_COORDINATE_LONGITUDE = "longitude";
	private static final String COLUMN_TOGGLE_ID			= "toggleId";
	private static final String COLUMN_ICON_ID				= "iconId";
	
	private static final String COLUMN_NAME_TYPE 				= "VARCHAR(50) PRIMARY KEY";
	private static final String COLUMN_DESCRIPTION_TYPE 		= "TEXT";
	private static final String COLUMN_COORDINATE_LATITUDE_TYPE = "REAL";
	private static final String COLUMN_COORDINATE_LONGITUDE_TYPE = "REAL";
	private static final String COLUMN_TOGGLE_ID_TYPE			= "INTEGER";
	private static final String COLUMN_ICON_ID_TYPE				= "INTEGER";
	
	private static final String LOCAL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " " +
	"("+
		COLUMN_NAME					+ " " + COLUMN_NAME_TYPE 				+ ", " +
		COLUMN_DESCRIPTION 			+ " " + COLUMN_DESCRIPTION_TYPE 		+ ", " +
		COLUMN_COORDINATE_LATITUDE 	+ " " + COLUMN_COORDINATE_LATITUDE_TYPE + ", " +
		COLUMN_COORDINATE_LONGITUDE + " " + COLUMN_COORDINATE_LONGITUDE_TYPE + ", " +
		COLUMN_TOGGLE_ID			+ " " + COLUMN_TOGGLE_ID_TYPE			+ ", " +
		COLUMN_ICON_ID				+ " " + COLUMN_ICON_ID_TYPE				+
	")";
	
	private static final String RETRIEVE_MARKER_INFO 	= "select * from " + TABLE_NAME + " where " + COLUMN_NAME + " = ?";
	private static final String RETRIEVE_TOGGLE_EQUAL_TO 	= "select * from " + TABLE_NAME + " where " + COLUMN_TOGGLE_ID + " = ?";
	private static final String RETRIEVE_TOGGLE_NOT_EQUAL_TO = "select * from " + TABLE_NAME + " where " + COLUMN_TOGGLE_ID + " != ?";
	
	public MapPlaceTable(SQLiteOpenHelper SQLiteOpenHelper) {
		super(SQLiteOpenHelper);
		SQL_CREATE_TABLE = LOCAL_CREATE_TABLE;
		SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
	}
	
	public void fillTableWithDefaultData(SQLiteDatabase db) throws SQLException, DBException{
		super.fillTableWithDefaultData(db);
		final int defaultValueCount = 13; //TODO remove test values
		
		ContentValues values[] = new ContentValues[defaultValueCount];
		for (int i = 0; i < defaultValueCount; ++i) {
			values[i] = new ContentValues();
		}
		
		values[0].put(COLUMN_NAME, "HOUSE_A");
		values[0].put(COLUMN_DESCRIPTION,"<b>Hus A<br>Våning 1:</b><br> IT- och Serviceavdelningen<br><b>Våning 2:</b><br> Reception<br><b>Våning 3:</b><br> Konferensrum");
		values[0].put(COLUMN_COORDINATE_LATITUDE, 56.182016);
		values[0].put(COLUMN_COORDINATE_LONGITUDE, 15.590502);
		values[0].put(COLUMN_ICON_ID, MapPlaceIdentifiers.MARKER_ICON_ID_HOUSE_A);
		values[0].put(COLUMN_TOGGLE_ID, MapPlaceIdentifiers.TOGGLE_ID_CAMPUS_HOUSES);
		
		values[1].put(COLUMN_NAME, "HOUSE_B");
		values[1].put(COLUMN_DESCRIPTION, "<b>Hus B</b><br>Region Blekinge");
		values[1].put(COLUMN_COORDINATE_LATITUDE, 56.180673);
		values[1].put(COLUMN_COORDINATE_LONGITUDE, 15.590691);
		values[1].put(COLUMN_ICON_ID, MapPlaceIdentifiers.MARKER_ICON_ID_HOUSE_B);
		values[1].put(COLUMN_TOGGLE_ID, MapPlaceIdentifiers.TOGGLE_ID_CAMPUS_HOUSES);
		
		values[2].put(COLUMN_NAME, "HOUSE_C");
		values[2].put(COLUMN_DESCRIPTION, "<b>Hus C<br>Våning 1:</b><br> Källarplan<br><b>Våning 2:</b><br>Föreläsningssalar, Toaletter<br><b>Våning 3:</b><br> Föreläsningssalar, Toaletter<br><b>Våning 4:</b><br>Institutionen för Fysisk Planering");
		values[2].put(COLUMN_COORDINATE_LATITUDE, 56.181189);
		values[2].put(COLUMN_COORDINATE_LONGITUDE, 15.592303);
		values[2].put(COLUMN_ICON_ID, MapPlaceIdentifiers.MARKER_ICON_ID_HOUSE_C);
		values[2].put(COLUMN_TOGGLE_ID, MapPlaceIdentifiers.TOGGLE_ID_CAMPUS_HOUSES);
		
		values[3].put(COLUMN_NAME, "HOUSE_D");
		values[3].put(COLUMN_DESCRIPTION, "<b>Hus D<br>Våning 1:</b><br>Region Blekinge<br><b>Våning 2:</b><br>Café Länken, Studieytor, Mikrovågsugnar");
		values[3].put(COLUMN_COORDINATE_LATITUDE, 56.181670);
		values[3].put(COLUMN_COORDINATE_LONGITUDE, 15.592375);
		values[3].put(COLUMN_ICON_ID, MapPlaceIdentifiers.MARKER_ICON_ID_HOUSE_D);
		values[3].put(COLUMN_TOGGLE_ID, MapPlaceIdentifiers.TOGGLE_ID_CAMPUS_HOUSES);
		
		values[4].put(COLUMN_NAME, "HOUSE_G");
		values[4].put(COLUMN_DESCRIPTION, "<b>Hus G<br>Våning 1:</b><br>Källarplan<br><b>Våning 2:</b><br>Föreläsningssalar, Studenthälsan, Konfuciusinstitutet, Datorsalar, Toaletter<br><b>Våning 3:</b><br>Föreläsningssalar, Toaletter, Datorsalar<br><b>Våning 4:</b><br>Föreläsningssalar, Datorsalar, Toaletter<br><b>Våning 5:</b><br> Föreläsningssalar, Datorsalar, Toaletter");
		values[4].put(COLUMN_COORDINATE_LATITUDE, 56.181891);
		values[4].put(COLUMN_COORDINATE_LONGITUDE, 15.591308);
		values[4].put(COLUMN_ICON_ID, MapPlaceIdentifiers.MARKER_ICON_ID_HOUSE_G);
		values[4].put(COLUMN_TOGGLE_ID, MapPlaceIdentifiers.TOGGLE_ID_CAMPUS_HOUSES);
		
		values[5].put(COLUMN_NAME, "HOUSE_H");
		values[5].put(COLUMN_DESCRIPTION, "<b>Hus H<br>Våning 1:</b><br>Toaletter<br><b>Våning 2:</b><br>Bibliotek<br><b>Våning 3:</b><br> Bibliotek, Datorsalar, Grupprum<br><b>Våning 4:</b><br>Institiutionen för kommunikationssytem, Datorsalar, Föreläsningssalar<br><b>Våning 5:</b><br> Institutionen för Hälsa");
		values[5].put(COLUMN_COORDINATE_LATITUDE, 56.182348);
		values[5].put(COLUMN_COORDINATE_LONGITUDE, 15.590819);
		values[5].put(COLUMN_ICON_ID, MapPlaceIdentifiers.MARKER_ICON_ID_HOUSE_H);
		values[5].put(COLUMN_TOGGLE_ID, MapPlaceIdentifiers.TOGGLE_ID_CAMPUS_HOUSES);
		
		values[6].put(COLUMN_NAME, "HOUSE_J");
		values[6].put(COLUMN_DESCRIPTION, "<b>Hus J<br>Våning 1:</b><br>Restaurang Bistro, föreläsningssalar, multisalen, toaletter<br><b>Våning 2:</b><br>Kontor, Konferensrum, Studentcentrum, Studentkårens expedition, Toalett<br><b>Våning 3:</b><br>Kontor<br><b>Våning 4:</b><br>Ledningsenheten & Ledningskansli");
		values[6].put(COLUMN_COORDINATE_LATITUDE, 56.182933);
		values[6].put(COLUMN_COORDINATE_LONGITUDE, 15.590401);
		values[6].put(COLUMN_ICON_ID, MapPlaceIdentifiers.MARKER_ICON_ID_HOUSE_J);
		values[6].put(COLUMN_TOGGLE_ID, MapPlaceIdentifiers.TOGGLE_ID_CAMPUS_HOUSES);
		
		values[7].put(COLUMN_NAME, "HOUSE_K");
		values[7].put(COLUMN_DESCRIPTION, "<b>Studentpub Rotundan</b>");
		values[7].put(COLUMN_COORDINATE_LATITUDE, 56.181711);
		values[7].put(COLUMN_COORDINATE_LONGITUDE, 15.589841);
		values[7].put(COLUMN_ICON_ID, MapPlaceIdentifiers.MARKER_ICON_ID_ROTUNDAN);
		values[7].put(COLUMN_TOGGLE_ID, MapPlaceIdentifiers.TOGGLE_ID_STUDENT_PUBS);
		
		values[8].put(COLUMN_NAME, "KARLSHAMN_HOUSE_A");
		values[8].put(COLUMN_DESCRIPTION, "<b>Hus A<br>Våning 1:</b><br>Institutionen för Teknik och Estetik<br>Institutionen för Datalogi och Datorsystemteknik<br><b>Våning 2:</b><br>Bibliotek");
		values[8].put(COLUMN_COORDINATE_LATITUDE, 56.163626);
		values[8].put(COLUMN_COORDINATE_LONGITUDE, 14.866623);
		values[8].put(COLUMN_ICON_ID, MapPlaceIdentifiers.MARKER_ICON_ID_HOUSE_A);
		values[8].put(COLUMN_TOGGLE_ID, MapPlaceIdentifiers.TOGGLE_ID_CAMPUS_HOUSES );
		
		values[9].put(COLUMN_NAME, "KARLSHAMN_HOUSE_B");
		values[9].put(COLUMN_DESCRIPTION, "<b>Hus B</b><br>Institutionen för Teknik och Estetik");
		values[9].put(COLUMN_COORDINATE_LATITUDE, 56.164464);
		values[9].put(COLUMN_COORDINATE_LONGITUDE, 14.866012);
		values[9].put(COLUMN_ICON_ID, MapPlaceIdentifiers.MARKER_ICON_ID_HOUSE_B);
		values[9].put(COLUMN_TOGGLE_ID, MapPlaceIdentifiers.TOGGLE_ID_CAMPUS_HOUSES);
		
		values[10].put(COLUMN_NAME, "BSK_OFFICE");
		values[10].put(COLUMN_DESCRIPTION, "<b>Blekinge Studentkårs kansli</b>");
		values[10].put(COLUMN_COORDINATE_LATITUDE, 56.181975);
		values[10].put(COLUMN_COORDINATE_LONGITUDE, 15.589975);
		values[10].put(COLUMN_ICON_ID, MapPlaceIdentifiers.MARKER_ÌCON_ID_BSK_OFFICE);
		values[10].put(COLUMN_TOGGLE_ID, MapPlaceIdentifiers.TOGGLE_ID_STUDENT_UNION);
		
		//TEST VALUES
		values[11].put(COLUMN_NAME, "J2170");
		values[11].put(COLUMN_DESCRIPTION, "Föreläsningssal");
		values[11].put(COLUMN_COORDINATE_LATITUDE, 56.182810);
		values[11].put(COLUMN_COORDINATE_LONGITUDE, 15.590314);
		values[11].put(COLUMN_ICON_ID, MapPlaceIdentifiers.MARKER_ICON_ID_DEBUG);
		values[11].put(COLUMN_TOGGLE_ID, MapPlaceIdentifiers.TOGGLE_ID_NO_TOGGLE);
		
		values[12].put(COLUMN_NAME, "G311");
		values[12].put(COLUMN_DESCRIPTION, "Datorsal");
		values[12].put(COLUMN_COORDINATE_LATITUDE, 56.181890);
		values[12].put(COLUMN_COORDINATE_LONGITUDE, 15.591293);
		values[12].put(COLUMN_ICON_ID, MapPlaceIdentifiers.MARKER_ICON_ID_DEBUG);
		values[12].put(COLUMN_TOGGLE_ID, MapPlaceIdentifiers.TOGGLE_ID_NO_TOGGLE);
		
		db.beginTransaction();
		
		int result = -1;
		try {
			for(int i = 0; i < defaultValueCount; ++i) {
				result = (int) db.insert(TABLE_NAME, null, values[i]);
				if(result == -1 ) {
			        if(Utilities.error) {Log.e(TAG, mClass + ":fillTableWithDefaultData(); No entry was added to the database.");}
			        throw new DBException(mClass + ":fillTableWithDefaultData(); No entry was added to the database.");
				}
			}
			db.setTransactionSuccessful();
		}catch(NullPointerException e) {
			if(Utilities.error) {Log.e(TAG, mClass + ":fillTableWithDefaultData()::db.insert();");}
			throw new DBException("NullPointerException. Message: " + e.getMessage());
		}
		catch(IllegalStateException e) {
			if(Utilities.error) {Log.e(TAG, mClass + ":fillTableWithDefaultData()::db.setTransactionSuccessful();");}
			throw new DBException("IllegalStateException. Message: " + e.getMessage());
		}
		/*
		 * This could also throw SQLException. Functions inherited from BaseTable do not need to catch SQLExceptions.
		 */
		finally {
			db.endTransaction();
			// Functions inherited from BaseTable MUST NOT call db.close();
		}
	}
	
	@Override
	public void add(String name, String description, float latitude, float longitude, int iconId, int toggleId) throws DBException, NoRowsAffectedDBException {
		if(Utilities.verbose) {Log.v(TAG, mClass + ":add()");}
		
		SQLiteDatabase db = mHelper.getWritableDatabase();
		db.beginTransaction();		
		ContentValues contentValues = new ContentValues();
		contentValues.put(COLUMN_NAME, name);
		contentValues.put(COLUMN_DESCRIPTION, description);
		contentValues.put(COLUMN_COORDINATE_LATITUDE, latitude);
		contentValues.put(COLUMN_COORDINATE_LONGITUDE, longitude);
		contentValues.put(COLUMN_ICON_ID, iconId);
		contentValues.put(COLUMN_TOGGLE_ID, toggleId);
		
		int result = -1;
		try {
			result = (int) db.insert(TABLE_NAME, null, contentValues);
			db.setTransactionSuccessful();
		}catch(NullPointerException e) {
			if(Utilities.error) {Log.e(TAG, mClass + ":add()::db.insert();");}
			throw new DBException("NullPointerException. Message: " + e.getMessage());
		}
		catch(IllegalStateException e) {
			if(Utilities.error) {Log.e(TAG, mClass + ":add()::db.setTransactionSuccessful();");}
			throw new DBException("IllegalStateException. Message: " + e.getMessage());
		}
		catch(SQLException e) {
			if(Utilities.error) {Log.e(TAG, mClass + ":add(); SQLException, something went wrong.");}
			throw new DBException("SQLException. Message: " + e.getMessage());
		}
		finally{
			db.endTransaction();
		}
		
		if(result == -1) {
		    if(Utilities.error) {Log.e(TAG, mClass + ":add(); No entry was added to the database.");}
		    throw new NoRowsAffectedDBException(mClass + ":add(); No entry was added to the database.");
		}
	}
	
	@Override
	public LatLng getMapCoordinate(String name) throws DBException, NoResultFoundDBException {
		if(Utilities.verbose) {Log.v(TAG, mClass + ":getMapCoordinate()");}
		
		SQLiteDatabase db = mHelper.getReadableDatabase();
		db.beginTransaction();		
		LatLng coordinates = null;
		try {
			Cursor cursor = db.rawQuery(RETRIEVE_MARKER_INFO, new String[] { name });
			if(cursor.moveToFirst()) {
				coordinates = new LatLng(cursor.getFloat(2), cursor.getFloat(3));
			}
			else{
				if(Utilities.error) {Log.e(TAG, mClass + ":getMapCoordinate(); No result was found in database for name:= " + name);}
				throw new NoResultFoundDBException(mClass + ":getMapCoordinate(); No result was found in database for name= " + name);
			}
			db.setTransactionSuccessful();
		}catch(NullPointerException e) {
			if(Utilities.error) {Log.e(TAG, mClass + ":getMapCoordinate()::db.insert();");}
			throw new DBException("NullPointerException. Message: " + e.getMessage());
		}
		catch(IllegalStateException e) {
			if(Utilities.error) {Log.e(TAG, mClass + ":getMapCoordinate()::db.setTransactionSuccessful();");}
			throw new DBException("IllegalStateException. Message: " + e.getMessage());
		}
		catch(SQLException e) {
			if(Utilities.error) {Log.e(TAG, mClass + ":getMapCoordinate(); SQLException, something went wrong.");}
			throw new DBException("SQLException. Message: " + e.getMessage());
		}
		finally{
			db.endTransaction();
		}
		return coordinates;
	}
	
	@Override
	public MarkerOptions getMapMarkerOptions(String name) throws DBException, NoResultFoundDBException {
		if(Utilities.verbose) {Log.v(TAG, mClass + ":getMapMarkerOptions()");}

		SQLiteDatabase db = mHelper.getReadableDatabase();
		db.beginTransaction();
		
		MarkerOptions options = null;		
		try {
			Cursor cursor = db.rawQuery(RETRIEVE_MARKER_INFO, new String[] { name });
			
			if(cursor.moveToFirst()) {
				options = new MarkerOptions();
				options.title("");	//The title is expected to be included in the snippet since the snippet is being formatted as HMTL
				options.snippet(cursor.getString(1));
				options.position(new LatLng(cursor.getFloat(2), cursor.getFloat(3)));
				options.icon(getIconFromId(cursor.getInt(5)));
			}
			else{
				if(Utilities.error) {Log.e(TAG, mClass + ":getMapMarkerOptions(); No result was found in database for name:= " + name);}
				throw new NoResultFoundDBException(mClass + ":getMapMarkerOptions(); No result was found in database for name= " + name);
			}	
		}catch(NullPointerException e) {
			if(Utilities.error) {Log.e(TAG, mClass + ":getMapMarkerOptions()::db.insert();");}
			throw new DBException("NullPointerException. Message: " + e.getMessage());
		}
		catch(IllegalStateException e) {
			if(Utilities.error) {Log.e(TAG, mClass + ":getMapMarkerOptions()::db.setTransactionSuccessful();");}
			throw new DBException("IllegalStateException. Message: " + e.getMessage());
		}
		catch(SQLException e) {
			if(Utilities.error) {Log.e(TAG, mClass + ":getMapMarkerOptions(); SQLException, something went wrong.");}
			throw new DBException("SQLException. Message: " + e.getMessage());
		}
		finally{
			db.endTransaction();
		}
		
		return options;
	}
	
	//If the getNonEqual flag is not set the function returns all names that have the inputed toggle id.
	//If the getNonEqual flag is set the function returns all names that doesn't have the inputed toggle id.
	@Override
	public String[] getAllNamesByToggleId(int toggleId, boolean getNonEqual) throws DBException, NoResultFoundDBException {
		if(Utilities.verbose) {Log.v(TAG, mClass + ":getAllWithoutToggleId");}
		
		SQLiteDatabase db = mHelper.getReadableDatabase();
		db.beginTransaction();
		String[] names;
		try {
			Cursor cursor;
			if(!getNonEqual) {
				cursor = db.rawQuery(RETRIEVE_TOGGLE_EQUAL_TO, new String[] { Integer.toString(toggleId)});
			}
			else {
				cursor = db.rawQuery(RETRIEVE_TOGGLE_NOT_EQUAL_TO, new String[] { Integer.toString(toggleId)});
			}
			
			int rowCount = cursor.getCount();
			if(rowCount >= 0) {
				names = new String[rowCount];
				for(int i = 0; i < rowCount; ++i) {
					cursor.moveToNext();
					names[i] = cursor.getString(0);
				}
			}
			else {
				if(Utilities.error) {Log.e(TAG, mClass + ":getAllNamesByToggleId(); No result was found in database for toggleId:= " + toggleId);}
				throw new NoResultFoundDBException(mClass + ":getAllNamesByToggleId(); No result was found in database for toggleId= " + toggleId);
			}
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
		finally{
			db.endTransaction();
		}
    
		return names;
	}
	
	private BitmapDescriptor getIconFromId(int iconId) {
		if(Utilities.verbose) {Log.v(TAG, mClass + ":BitmapDescriptor()");}
		BitmapDescriptor icon = null;
		
		switch (iconId) {
		case MapPlaceIdentifiers.MARKER_ICON_ID_HOUSE_A:
			icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_a);
			break;
			
		case MapPlaceIdentifiers.MARKER_ICON_ID_HOUSE_B:
			icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_b);
			break;
			
		case MapPlaceIdentifiers.MARKER_ICON_ID_HOUSE_C:
			icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_c);
			break;
			
		case MapPlaceIdentifiers.MARKER_ICON_ID_HOUSE_D:
			icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_d);
			break;
			
		case MapPlaceIdentifiers.MARKER_ICON_ID_HOUSE_G:
			icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_g);
			break;
			
		case MapPlaceIdentifiers.MARKER_ICON_ID_HOUSE_H:
			icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_h);
			break;
			
		case MapPlaceIdentifiers.MARKER_ICON_ID_HOUSE_J:
			icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_j);
			break;
			
		case MapPlaceIdentifiers.MARKER_ICON_ID_ROTUNDAN:
			icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_rotundan);
			break;
			
		case MapPlaceIdentifiers.MARKER_ÌCON_ID_BSK_OFFICE:
			icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_bsk);
			break;
		case MapPlaceIdentifiers.MARKER_ICON_ID_DEBUG:
			icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher);
			break;
		}
		return icon;
	}
}