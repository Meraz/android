package com.example.app_android.ui.courses_and_schedule;

public abstract class CalendarUtilities {
	
	public static class ExportResultBean {
		public int resultFlag		= 0; // 0 = Normal, 1 = All events up to date, 2 = No data recieved from timeedit, 3 = Event export failed, 4 = No calendar found
		public int exportedCount 	= 0;
		public int deletedCount 	= 0;
		public int upToDateCount	= 0;
	 }
	
	//TODO - Fix so that this function can take an array of courseCodes to reduce the amount of requests needed.
	public static String buildTimeEditRequest(String courseCode, String fromDate, String toDate) {
		return "https://se.timeedit.net/web/bth/db1/sched1/s.csv?tab=5&object=" + courseCode +
				"&type=root&startdate=" + fromDate + "&enddate=" + toDate + "&p=0.m%2C2.w";
	}
}
