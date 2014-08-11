package com.example.app_android.database.interfaces;

import com.example.app_android.database.DBException;
import com.example.app_android.database.NoRowsAffectedDBException;

public interface ICalendarEventTable {

	public boolean add(long id, String title, String description ,String startTime, String endTime) throws NoRowsAffectedDBException, DBException;
	
	public boolean remove(long id) throws NoRowsAffectedDBException, DBException;
	
	//Returns -1 if the event is not present in the database
	public int getEventId(String title, String description, String startTime, String endTime); // TODO should throw exception if nothing is found
}