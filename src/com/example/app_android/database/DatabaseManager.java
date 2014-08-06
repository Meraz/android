package com.example.app_android.database;   

import com.example.app_android.util.Utilities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseManager extends SQLiteOpenHelper{
		
	/*
	 * HOW TO ADD A NEW TABLE to this databasemanager
	 * 1. Add the new table to the enum
	 * 2. Create a new <TableClass> that derives from BaseTable.
	 * 3. Create an interface, <ITableClass> that will be used for the rest of the application to use this class, 
	 * 	this should contain access function to the DB ex. "void getCity(int i)", "boolean isTableEmpty()"
	 * 4. Add an allocation entry in the constructor of this file.
	 * 5. Create method in this class called "get<TableClass>" in which returns the type <ITableClass>
	 * 6. The newly created function "get<TableClass>" should call the private function getTable with an enum.
	 * 
	 * 
	 *  * Decided to NOT return BaseTable or derived class.
     *  * As this would result in public access to create and drop table, which is not very nice.
     *  * Could not come up with a way which would be less tricky than this.
     *  
     *  Good fun and have luck
     *  
     *  Rasmus Tilljander - tilljander.rasmus@gmail.com
	 */
    private static final String TAG = "Database";      
    private String mClass;
	
    private static final String DB_NAME = "bthapp.sqlite";
	private static final int DB_VERSION = 1;
    private static BaseTable[] TABLES;
    private static DatabaseManager mDatabaseManager;
    private int openedConnections = 0;
    
    public enum TableIndex {
        TOKEN,
        COURSES,
        FAVOURITE_COURSES,
        CALENDAREVENTS,
        MAPCOORDINATES;
    }
    
    // Singleton initialize instance
    public static void initialize (Context context) {

		
    	if(mDatabaseManager == null)
    		mDatabaseManager = new DatabaseManager(context);
    }
    
    // Singleton getinstance
    public static DatabaseManager getInstance() {
    	return mDatabaseManager;
    }

    // Constructor
    private DatabaseManager(Context context) {	     
	    super(context, DB_NAME, null, DB_VERSION);
	    mClass = getClass().getSimpleName();
	    
	    // Allocate array
	    TABLES = new BaseTable[TableIndex.values().length];
	    
	    // Create specific table
	    TABLES[0] = new TokenTable(this);
	    TABLES[1] = new CoursesTable(this);
	    TABLES[2] = new FavouriteCourseTable(this);
	    TABLES[3] = new CalendarEventTable(this);
	    TABLES[4] = new MapCoordinateTable(this);
    }
      
    private BaseTable getTable(TableIndex table) {
		if(Utilities.verbose) {Log.v(TAG, mClass + ":getTable()");}
    	return TABLES[table.ordinal()];
    }
    
    /* 
     * Decided to NOT return BaseTable or derived class.
     * As this would result in public access to create and drop table, which is not very nice.
     */
    public ITokenTable getTokenTable() {
		if(Utilities.verbose) {Log.v(TAG, mClass + ":getTokenTable()");}
    	return (ITokenTable)getTable(TableIndex.TOKEN);
    }
    
    public ICourseTable getCourseTable() {
		if(Utilities.verbose) {Log.v(TAG, mClass + ":getCourseTable()");}
    	return (ICourseTable)getTable(TableIndex.COURSES);
    }
    
    public IFavouriteCourseTable getFavouriteCourseTable() {
		if(Utilities.verbose) {Log.v(TAG, mClass + ":IFavouriteCourseTable()");}
    	return (IFavouriteCourseTable)getTable(TableIndex.FAVOURITE_COURSES);
    }
    
    public ICalendarEventTable getCalendarEventTable() {
		if(Utilities.verbose) {Log.v(TAG, mClass + ":ICalendarEventTable()");}
    	return (ICalendarEventTable)getTable(TableIndex.CALENDAREVENTS);
    }
    
    public IMapCoordinateTable getMapCoordinateTable() {
		if(Utilities.verbose) {Log.v(TAG, mClass + ":IMapCoordinateTable()");}
    	return (IMapCoordinateTable)getTable(TableIndex.MAPCOORDINATES);
    }
    
    /*
     * Overridden functions from  SQLiteOpenHelper
     */     
    @Override
    public void onCreate(SQLiteDatabase db) {
		if(Utilities.verbose) {Log.v(TAG, mClass + ":onCreate()");}
    	
	    for(int i = 0; i < TABLES.length; i++) {
			TABLES[i].createTable(db);
	    }
	    
	    for(int i = 0; i < TABLES.length; i++) {
			TABLES[i].fillTableWithDefaultData(db);
	    }	    
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if(Utilities.verbose) {Log.v(TAG, mClass + ":onUpgrade()");}
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
	    for(int i = 0; i < TABLES.length; i++) {
			TABLES[i].dropTable(db);
	    }
        onCreate(db);
    }
    
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if(Utilities.verbose) {Log.v(TAG, mClass + ":onDowngrade()");}
        onUpgrade(db, oldVersion, newVersion);
    }   
}