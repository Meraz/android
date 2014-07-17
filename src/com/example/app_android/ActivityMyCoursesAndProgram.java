package com.example.app_android;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;
import com.example.app_android.InterfaceListSelectionListener;
import com.example.app_android.AdapterScheduleHelper.MyScheduleHelper;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityMyCoursesAndProgram extends Activity implements InterfaceListSelectionListener {

	private static final String TAG = "ActivityCoursesAndProgram";
	public static ArrayList<String> coursesAndProgramArray;
	EditText courseCode;
	AdapterCoursesHelper coursesHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mycoursesandprogram);
		
		courseCode = (EditText) findViewById(R.id.courseCode);
		coursesHelper = new AdapterCoursesHelper(this);
		coursesAndProgramArray = coursesHelper.readAllCourses();

    	//Intent intent = new Intent(this, ServiceSchemaUpdate.class);
    	//intent.putExtra("URL", "https://se.timeedit.net/web/bth/db1/sched1/s.csv?tab=5&object=dv2544&type=root&startdate=20140101&enddate=20140620&p=0.m%2C2.w");
    	//startService(intent);
	}
	
    @Override
	protected void onDestroy() {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onDestroy()");
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onPause()");
		super.onPause();
	}

	@Override
	protected void onRestart() {
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onRestart()");
		super.onRestart();
	}

	@Override
	protected void onResume() {
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onResume()");
		super.onResume();
	}

	@Override
	protected void onStart() {
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onStart()");
		super.onStart();		
	}

	@Override
	protected void onStop() {
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onStop()");
		super.onStop();
	}
	
	public void addCourse(View view) {
		String cCode = courseCode.getText().toString();
		long id = coursesHelper.insertData(cCode);
		if(id < 0) {
			Toast.makeText(this, "Unsuccessful", Toast.LENGTH_LONG).show();
		}
		else {
			Toast.makeText(this, "Insert successful", Toast.LENGTH_LONG).show();
			finish();
			startActivity(getIntent());			
		}		
	}
	
	@SuppressLint("SimpleDateFormat")
	public void exportSchedule(View view) throws InterruptedException, ExecutionException {
		if(Utilities.isNetworkAvailable(getApplicationContext())) {
			Logger.VerboseLog(TAG, "Exporting schedule to Google Calendar");
			ArrayList<String> courseCodes = coursesHelper.readAllCourses();
			ArrayList<String> requests = new ArrayList<String>();
		
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			String startDate = dateFormat.format(calendar.getTime());
			calendar.add(Calendar.MONTH, 6);
			String endDate = dateFormat.format(calendar.getTime());
		
			for(int i = 0; i < courseCodes.size(); ++i) {
				requests.add("https://se.timeedit.net/web/bth/db1/sched1/s.csv?tab=5&object=" + courseCodes.get(i) +
						"&type=root&startdate=" + startDate + "&enddate=" + endDate + "&p=0.m%2C2.w");
			}
				ExportToGCalFromTimeEditTask exportTask = new ExportToGCalFromTimeEditTask();
				exportTask.execute(requests);
		}
		else 
			Toast.makeText(getApplicationContext(), "Missing internet connection", Toast.LENGTH_SHORT).show();
	}
	
	public void readCourses() {
		coursesAndProgramArray = coursesHelper.readAllCourses();
	}
	
	public void courseChecked(View v) {
		Logger.VerboseLog(TAG, "Checked or Unchecked");
	}

	@Override
	public void onListSelection(int index) {
	}
	
	private ArrayList<String[]> getTimeEditData(String stringURL) {
		String inputLine;
		ArrayList<String[]> lectures = new ArrayList<String[]>();
		try {
			//Connect and fetch data
			URL url = new URL(stringURL);
			HttpURLConnection urlCon = (HttpURLConnection)url.openConnection();
			InputStream inStream = urlCon.getInputStream();
			BufferedReader readBuff = new BufferedReader(new InputStreamReader(inStream));

			//Save the data we are actually interested in and throw away the rest
			int count = 0;
			while((inputLine = readBuff.readLine()) != null) {
				if(count > 3) {	//The three first rows are not schedule entries
					String[] tokens = inputLine.split(",");
					String[] lecture =  parseTimeEditData(tokens);
					lectures.add(lecture);
				}
				count++;
			}			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return lectures;
	}
	
	private String[] parseTimeEditData(String[] tokens) {
		String[] stringParts = new String[10];
		
		stringParts[0] = tokens[0]; //Start date
		stringParts[1] = tokens[1].substring(1); //Start time
		stringParts[2] = tokens[2].substring(1); //End date
		stringParts[3] = tokens[3].substring(1); //End time
		
		int index = 4;
		
		if(tokens[index].indexOf('"') == 1) { 	//Course
			stringParts[4] = tokens[index++] + tokens[index++];
		} else {
			stringParts[4] = tokens[index++];
		}
		if(tokens[index].indexOf('"') == 1) {	//Group
			stringParts[5] = tokens[index++] + tokens[index++];
		} else {
			stringParts[5] = tokens[index++];
		}
		if(tokens[index].indexOf('"') == 1) { 	//Room
			stringParts[6] = tokens[index++] + tokens[index++];
		} else {
			stringParts[6] = tokens[index++];
		}
		if(tokens[index].indexOf('"') == 1) { 	//Booking person
			stringParts[7] = tokens[index++] + tokens[index++];
		} else {
			stringParts[7] = tokens[index++];
		}
		if(tokens[index].indexOf('"') == 1) {	//Purpose
			stringParts[8] = tokens[index++] + tokens[index++];
		} else {
			stringParts[8] = tokens[index++];
		}
		if(tokens[index].indexOf('"') == 1) {	//Extra info
			stringParts[9] = tokens[index++] + tokens[index++];
		} else {
			stringParts[9] = tokens[index++];
		}
		return stringParts;
	}
	
	private void exportScheduleEvent(String[] eventData, int calendarID) {
		long startTimeMillis = 0;
		long endTimeMillis = 0;
		
		
		//Prepare the time strings for conversion
		String[] startDateParts = eventData[0].split("-");
		String[] startTimeParts = eventData[1].split(":");
		String[] endDateParts 	= eventData[2].split("-");
		String[] endTimeParts 	= eventData[3].split(":");
		
		//Java.Util.Calendar uses 0 as January while TimeEdit uses 1 as January. This code fixes this.
		int startMonth = Integer.parseInt(startDateParts[1]) - 1;
		int endMonth = Integer.parseInt(endDateParts[1]) - 1;
		
		//Get start and end time in milliseconds
		Calendar beginTime = Calendar.getInstance();  //TODO Something is wrong with the epoch time. It pushes the events 1 months worth of milliseconds forward in time.
		beginTime.set(Integer.parseInt(startDateParts[0]), startMonth, Integer.parseInt(startDateParts[2]),
				Integer.parseInt(startTimeParts[0]), Integer.parseInt(startTimeParts[1]));
		startTimeMillis = beginTime.getTimeInMillis();
		
		Calendar endTime = Calendar.getInstance();
		endTime.set(Integer.parseInt(endDateParts[0]), endMonth, Integer.parseInt(endDateParts[2]),
				Integer.parseInt(endTimeParts[0]), Integer.parseInt(endTimeParts[1]));
		endTimeMillis  = endTime.getTimeInMillis();
		
	
		
		//Prepare the event for insertion
		ContentValues values = new ContentValues();
		TimeZone timeZone = TimeZone.getDefault();
		values.put(CalendarContract.Events.DTSTART, startTimeMillis);
		values.put(CalendarContract.Events.DTEND, endTimeMillis);
		values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());
		values.put(CalendarContract.Events.TITLE, eventData[4] + eventData[8] + eventData[6] + eventData[7]);
		values.put(CalendarContract.Events.DESCRIPTION, eventData[9]);
		values.put(CalendarContract.Events.CALENDAR_ID, calendarID);
		
		//Insert the event
		ContentResolver contentResolver = getContentResolver();
		contentResolver.insert(CalendarContract.Events.CONTENT_URI, values);
		
		//In case we want to save this somewhere we can get an uri from ContentResolver.insert and run this row to get an eventID
		//String eventID = uri.getLastPathSegment();
	}
	
	private int findCalendarID() {
		int returnCalendarID = -1;
		int[] calendarIDs = null;
		String[] calendarNames = null;
		
		//Get the calendar ID
		ContentResolver contentResolver = getContentResolver();
		String[] projection = new String[] {
		       CalendarContract.Calendars._ID,
		       CalendarContract.Calendars.ACCOUNT_NAME,
		       CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
		       CalendarContract.Calendars.NAME,
		       CalendarContract.Calendars.CALENDAR_COLOR
		};
		Cursor cursor = contentResolver.query(Uri.parse("content://com.android.calendar/calendars"), projection, null, null, null);
			if (cursor.moveToFirst()) {	//If there are any calendars at all
				int calendarCount = cursor.getCount();
				calendarIDs = new int[calendarCount];
				calendarNames = new String[calendarCount];
				for (int i = 0; i < calendarIDs.length; i++) {
					calendarIDs[i] = cursor.getInt(0);
					calendarNames[i] = cursor.getString(1);
					cursor.moveToNext();
				}
				cursor.close();
				
				//See if any of the found calendars is a Google calendar
				for(int i = 0; i < calendarCount; ++i) {
					if(calendarNames[i].contains("@gmail.com")) {
						returnCalendarID = calendarIDs[i];
						break;
					}
				}
			}
			return returnCalendarID;
	}
	
	 private class ExportToGCalFromTimeEditTask extends AsyncTask<ArrayList<String>, Void, Integer> {
		 
		 @Override
	     protected Integer doInBackground(ArrayList<String>... requests) {
	    	 int scheduleEventCount = 0;
				int calendarID = findCalendarID();
				if(calendarID != -1) // -1 indicates that no Google account is linked to this device
				{
					//Export all courses
					for(int i = 0; i < requests[0].size(); ++i) {
						ArrayList<String[]> lectureList = getTimeEditData(requests[0].get(i));
						int courseEventCount = lectureList.size();
						scheduleEventCount += courseEventCount;
						
						//Export all events for a given course
						for(int j = 0; j < courseEventCount; ++j) {
							exportScheduleEvent(lectureList.get(j), calendarID); 
						}
					}
				}
				else {
					return -1;
				}
				
				return scheduleEventCount;
	     }
		 
		 @Override
		 protected void onPostExecute(Integer scheduleEventCount) {
			 if(scheduleEventCount > 0) {
				 Toast.makeText(getApplicationContext(), scheduleEventCount + " events added to calendar", Toast.LENGTH_SHORT).show();
			 }
			 else if(scheduleEventCount == 0) {
				 Toast.makeText(getApplicationContext(), "No events found for the next six months", Toast.LENGTH_SHORT).show();
			 }
			 else {
				 Toast.makeText(getApplicationContext(), "Failed to export - No Google calendar found", Toast.LENGTH_SHORT).show();
			 }
		 }
	 }
}