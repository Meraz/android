package com.example.app_android.database.interfaces;

import com.example.app_android.database.DBException;

public interface ICalendarEventTable {

	public boolean add(long id, String title, String description ,String startTime, String endTime) throws DBException;
	
	public boolean remove(long id) throws DBException;
	
	//Returns -1 if the event is not present in the database
	public int getEventId(String title, String description, String startTime, String endTime);
}