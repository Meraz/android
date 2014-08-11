package com.example.app_android.database.interfaces;

import com.example.app_android.database.DBException;
import com.example.app_android.database.NoResultFoundDBException;
import com.example.app_android.database.NoRowsAffectedDBException;

public interface ICalendarEventTable {

	public boolean add(long id, String title, String description ,String startTime, String endTime) throws DBException, NoRowsAffectedDBException;
	
	

	public void remove(long id) throws DBException, NoRowsAffectedDBException;	
	
	/*
	 * Throws @NoResultFoundDBException if nothing is found in database
	 */
	public int getEventId(String title, String description, String startTime, String endTime) throws DBException, NoResultFoundDBException;
}