package com.example.app_android;	 

public class ExportResultBean {
		int resultFlag		= 0; // 0 = Normal, 1 = All events up to date, 2 = No data recieved from timeedit, 3 = Event export failed, 4 = No calendar found
		int exportedCount 	= 0;
		int deletedCount 	= 0;
		int upToDateCount	= 0;
	 }