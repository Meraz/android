package com.example.app_android.database;

public interface ICalendarEventTable {

	public boolean add(long id, String title, String description ,String startTime, String endTime);
	
	public boolean remove(long id);
	
	//Returns -1 if the event is not present in the database
	public int getEventId(String title, String description, String startTime, String endTime);
}