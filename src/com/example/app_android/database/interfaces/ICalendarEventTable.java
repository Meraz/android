package com.example.app_android.database.interfaces;

import com.example.app_android.database.DBException;
import com.example.app_android.database.NoResultFoundDBException;
import com.example.app_android.database.NoRowsAffectedDBException;

public interface ICalendarEventTable {

	/*
	 * Saves the inputed data as a database entry
	 */
	public boolean add(long id, String title, String description ,String startTime, String endTime) throws DBException, NoRowsAffectedDBException;
	
	/*
	 * Finds and removes the entry with the inputed id
	 */
	public void remove(long id) throws DBException, NoRowsAffectedDBException;	
	
	/*
	 * Finds a unique event by the inputed parameters and returns its Id
	 */
	public int getEventId(String title, String description, String startTime, String endTime) throws DBException, NoResultFoundDBException;
}