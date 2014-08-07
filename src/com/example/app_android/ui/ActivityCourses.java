package com.example.app_android.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.TimeZone;

import com.example.app_android.R;
import com.example.app_android.database.DatabaseManager;
import com.example.app_android.database.ICalendarEventTable;
import com.example.app_android.database.IFavouriteCourseTable;
import com.example.app_android.util.Utilities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityCourses extends BaseActivity {

	private static final String TAG = "CourseView";
	public static ArrayList<String> coursesArray;
	EditText courseCode;
	IFavouriteCourseTable favouriteCoursesHelper;
	MenuItem syncActionItem;
	MenuItem emptyScheduleItem;
	View courseList;
	TextView noCoursesText;
	
	private static final String CALENDAR_EVENT_TAG = "[This event was added by the BTH App]";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mClassName = getClass().getSimpleName();
		mTag = TAG;		
		super.onCreate(savedInstanceState);
		
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN); //This sneaky row stops the darn soft keyboard from popping up like some retarded wack-a-mole every time the activity is opened.
		
		favouriteCoursesHelper = DatabaseManager.getInstance().getFavouriteCourseTable();
		coursesArray = favouriteCoursesHelper.getAll();
		
		setContentView(R.layout.activity_courses);

		courseCode = (EditText) findViewById(R.id.courseCode);
		courseList = findViewById(R.id._container);
		noCoursesText = (TextView) findViewById(R.id.noCoursesDescription);

		if(favouriteCoursesHelper.isEmpty())
			courseList.setVisibility(View.GONE);
		else
			noCoursesText.setVisibility(View.GONE);
	}
 
	@Override
	protected void onRestart() {
		super.onRestart();
		//Restart this view to display the correct information
		finish();
		startActivity(getIntent());
	}

	@Override
	protected void onResume() {
		super.onResume();
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN); //This sneaky row stops the darn soft keyboard from popping up like some retarded wack-a-mole every time the activity is opened.
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":onCreateOptionsMenu()");}
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.layout.activity_courses_action, menu);
	    syncActionItem = menu.findItem(R.id.courses_action_sync);
	    emptyScheduleItem = menu.findItem(R.id.courses_menu_empty_schedule);
	    return super.onCreateOptionsMenu(menu);
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	if(Utilities.verbose) {Log.v(TAG, mClassName + ":onOptionsItemSelected()");}
    	switch (item.getItemId()) {
    	case R.id.courses_action_info:
    		Builder alert = new AlertDialog.Builder(this);
        	alert.setTitle("Courses");
        	alert.setMessage("Insert info about this view here");
        	alert.setPositiveButton("OK",null);
        	alert.show();
    		break;
    	case R.id.courses_action_sync:
    		exportSchedule();
    		break;
    	case R.id.courses_menu_empty_schedule:
    		int deletedRowsCount = deleteAllScheduleEvents();
    		Toast.makeText(getApplicationContext(), "Removed " + deletedRowsCount + " events from calendar"  , Toast.LENGTH_SHORT).show();
    		break;
    	}
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":onMenuOpened()");}
        if((featureId == Window.FEATURE_OPTIONS_PANEL || featureId == Window.FEATURE_ACTION_BAR ) && menu != null){
            if(menu.getClass().getSimpleName().equals("MenuBuilder")){
                try{
                    Method m = menu.getClass().getDeclaredMethod(
                        "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                }
                catch(NoSuchMethodException e){
                    Log.e(TAG, "onMenuOpened", e);
                }
                catch(Exception e){
                    throw new RuntimeException(e);
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

	public void addCourse(View view) {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":addCourse()");}
		String cCode = courseCode.getText().toString();
		if(!cCode.isEmpty()) {
			if(favouriteCoursesHelper.add(cCode)) {
				//Insert was successful. Now restart the activity to display the added element.
				finish();
				startActivity(getIntent());
			}
		}
		else {
			Toast.makeText(getApplicationContext(), "The input field cannot be empty!", Toast.LENGTH_SHORT).show();
		}
	}

	public void startCalendar(View view) {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":startCalendar()");}
    	//Start the calendar app
    	Uri uri = Uri.parse("content://com.android.calendar/time");
		Intent intent = new Intent("android.intent.action.VIEW", uri);
		startActivity(intent);
	}

	public void readCourses() {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":readCourses()");}
		coursesArray = favouriteCoursesHelper.getAll();
	}
	
	public void courseChecked(View v) { // TODO function needed?
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":courseChecked()");}
	}
	
	private int deleteAllScheduleEvents() {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":deleteAllScheduleEvents()");}
 		int rowsDeletedCount = getContentResolver().delete(CalendarContract.Events.CONTENT_URI,
 				Events.DESCRIPTION + " LIKE ? ", new String[] {"%" + CALENDAR_EVENT_TAG +"%"});
 		
 		return rowsDeletedCount;
	}
	
	@SuppressWarnings("unchecked") //Should be safe to ignore this warning. It complains about not knowing the type of arraylist being sent in exportTask.execute(requests)
	@SuppressLint("SimpleDateFormat")
	private void exportSchedule() {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":exportSchedule()");}
		if(Utilities.isNetworkAvailable(getApplicationContext())) {
			Utilities.VerboseLog(TAG, "Exporting schedule to Google Calendar");
			ArrayList<String> courseCodes = favouriteCoursesHelper.getAll();
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
				ExportToGCalFromTimeEditTask exportTask = new ExportToGCalFromTimeEditTask(getApplicationContext());
				exportTask.execute(requests);
				syncActionItem.setActionView(R.layout.item_action_sync_indicator);
		}
		else
			Toast.makeText(getApplicationContext(), "Missing internet connection", Toast.LENGTH_SHORT).show();
	}

	 private class ExportResult {
		 int resultFlag		= 0; // 0 = Normal, 1 = All events up to date, 2 = No data recieved from timeedit, 3 = Event export failed, 4 = No calendar found
		 int exportedCount 	= 0;
		 int deletedCount 	= 0;
		 int upToDateCount	= 0;
	 }

	 private class ExportToGCalFromTimeEditTask extends AsyncTask<ArrayList<String>, Void, ExportResult> {
		 final Context context;

		 public ExportToGCalFromTimeEditTask(Context context) {
			 this.context = context;
		 }

		 @Override
	     protected ExportResult doInBackground(ArrayList<String>... requests) {
	    	 ExportResult exportResult = new ExportResult();
	    	 int calendarID = findCalendarID();

	    	 if(calendarID != -1) // -1 indicates that no Google account is linked to this device
	    	 {
				//Get all events in the users calendar that has been added by this app
				ArrayList<String[]> oldCalendarEvents  = getCalendarEvents(context);

				for(int i = 0; i < requests[0].size(); ++i) {
					ArrayList<String[]> newLectureList = getTimeEditData(requests[0].get(i));
					if(newLectureList.size() > 0) {
						//Sort out the lectures from the oldCalendarEvents that are for other courses
						ArrayList<String[]> oldLectureList = getLecturesForCourse(newLectureList.get(0)[2], oldCalendarEvents); //The first parameter gets the course name for the relevant course from the first entry in the event list fetched from timeedit
						//Remove the events that has already been put into the calendar. Also remove any event that is present in the old event list but not the new one
						exportResult.upToDateCount += removeDuplicateEvents(newLectureList, oldLectureList);
						exportResult.deletedCount += removeOutdatedEvents(oldLectureList);
						if(!newLectureList.isEmpty()) {
							//Export all events for a given course
							try {
								for(int j = 0; j < newLectureList.size(); ++j) {
									exportScheduleEvent(newLectureList.get(j), calendarID);
									++exportResult.exportedCount;
								}
							} catch (ParseException e) {
								exportResult.resultFlag = 3; // Event export failed
								e.printStackTrace();
							}
						} else {
							exportResult.resultFlag = 1; // All events up to date
						}
					} else {
						exportResult.resultFlag = 2; // No data recieved from timeedit
					}
				}
			} else {
				exportResult.resultFlag = 4; // No calendar found
			}
			return exportResult; //The result flag defaults to 0 so if nothing goes wrong it will return the result flag "Normal"
		 }

		 @Override
		 protected void onPostExecute(ExportResult exportResult) {

			 switch (exportResult.resultFlag) {
			 	case 0: // Normal
			 		Toast.makeText(getApplicationContext(), exportResult.exportedCount + " Events added\n" + exportResult.deletedCount +" Events deleted\n" + exportResult.upToDateCount + " Events already synced", Toast.LENGTH_LONG).show();
			 		break;

			 	case 1: // All events up to date
			 		Toast.makeText(getApplicationContext(), "All " + exportResult.upToDateCount + " events are up to date", Toast.LENGTH_SHORT).show();
			 		break;

			 	case 2: // No data recieved from timeedit
			 		Toast.makeText(getApplicationContext(), "TimeEdit failed to return data for some or all courses\n" + exportResult.exportedCount + " Events added\n" + exportResult.deletedCount +" Events deleted\n" + exportResult.upToDateCount + " Events already synced", Toast.LENGTH_LONG).show();
			 		break;

			 	case 3: // Event export failed
			 		Toast.makeText(getApplicationContext(),"Some or all events failed to sync\n" + exportResult.exportedCount + " Events added\n" + exportResult.deletedCount +" Events deleted\n" + exportResult.upToDateCount + " Events already synced", Toast.LENGTH_LONG).show();
			 		break;

			 	case 4: // No calendar found
			 		Toast.makeText(getApplicationContext(), "Sync failed - No Google calendar found", Toast.LENGTH_SHORT).show();
			 		break;
			 }
			 syncActionItem.setActionView(null);
		 }
		 //Finds the id of a calendar connected to a gmail account
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

		 //Gets all events from the users calendar and returns the ones that were added by this app
		 private ArrayList<String[]> getCalendarEvents(Context context) {
				ArrayList<String[]> lectures = new ArrayList<String[]>();

			    Cursor cursor = context.getContentResolver().query( Uri.parse("content://com.android.calendar/events")	//Get a cursor so we can access the calendar events
			    		, new String[] { "calendar_id", "title", "description" , "dtstart", "dtend" }, null, null, null);
			    cursor.moveToFirst();


		        for (int i = 0; i < cursor.getCount(); ++i) {	//Cycle through all the elements and select those which has been added by this app
		        	String eventDescription = cursor.getString(2);
		        	  if(eventDescription != null && eventDescription.contains("[This event was added by the BTH App]")) {
		        		  	String[] eventInfo = new String[5];
		              		eventInfo[0] = cursor.getString(3);	//Start Time (ms)
		              		eventInfo[1] = cursor.getString(4);	//End Time (ms)
		              		eventInfo[2] = cursor.getString(1);	//Title
		              		eventInfo[3] = cursor.getString(2);	//Description
		              		eventInfo[4] = cursor.getString(0);	//ID

		              		lectures.add(eventInfo);
		              	}
		            cursor.moveToNext();
		        }
				return lectures;
			}

		 //Fetch updated events from timeedit
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

		 //Sorts out the events that are relevant for the inputed course
		 private ArrayList<String[]> getLecturesForCourse(String course, ArrayList<String[]> allLectures) {
				ArrayList<String[]> relevantLectures = new ArrayList<String[]>();

				for(int i = 0; i < allLectures.size(); ++i) {
					if(allLectures.get(i)[2].startsWith(course)) { //Check if the current lectures title begins with the course code we are looking for
						relevantLectures.add(allLectures.get(i));
					}
				}
				return relevantLectures;
			}

		 private int removeDuplicateEvents(ArrayList<String[]> newLectures, ArrayList<String[]> oldLectures) {
			 int duplicateCount = 0;
			 
				for(int i = 0; i < newLectures.size(); ++i) {
					for(int j = 0; j < oldLectures.size(); ++j) {
						if(newLectures.get(i)[0].equals(oldLectures.get(j)[0])
						&& newLectures.get(i)[1].equals(oldLectures.get(j)[1])
						&& (newLectures.get(i)[2] + " " + newLectures.get(i)[6] + " " + newLectures.get(i)[4] + " " + newLectures.get(i)[5]).equals(oldLectures.get(j)[2])
						&& (newLectures.get(i)[7] + " \n\n[This event was added by the BTH App]").equals(oldLectures.get(j)[3])) {
								newLectures.remove(i--); //Do -- after so we don't mess up the indices
								oldLectures.remove(j--);
								++duplicateCount;
								break;
						}
					}
				}
				return duplicateCount;
			}
		 
		 private int removeOutdatedEvents(ArrayList<String[]> outdatedEvents) {
			 ICalendarEventTable eventTable = DatabaseManager.getInstance().getCalendarEventTable();
			 
			 int deletedEvents = 0;
			 for(int i = 0; i < outdatedEvents.size(); ++i) {
				 String[] eventData = outdatedEvents.get(i);
				 int eventId = eventTable.getEventId(eventData[0], eventData[1], eventData[2], eventData[3]);
				 if(eventTable.remove(eventId)) {
					 if(deleteEvent(eventId)) {
						{
							++deletedEvents;
					 	}
					 }
				 }
			 }
			 
			 return deletedEvents;
		 }
		 
		 private boolean deleteEvent(long eventId) {
				Uri deleteUri = ContentUris.withAppendedId(Events.CONTENT_URI, eventId);
				int rows = getContentResolver().delete(deleteUri, null, null);
				return rows > 0; //Returns true if the event was deleted successfully
			}

		 @SuppressLint("SimpleDateFormat")
		 private String[] parseTimeEditData(String[] tokens) {
			String[] stringParts = new String[8];

			String[]	startDateParts 		= tokens[0].split("-"); //Start date
			String		startTimeString 	= tokens[1].substring(1); //Start time
			String[] 	endDateParts 		= tokens[2].substring(1).split("-"); //End date
			String	 	endTimeString 		= tokens[3].substring(1); //End time

			String startDate = startDateParts[0] + " " + startDateParts[1] + " " + startDateParts[2];
			String endDate = endDateParts[0] + " " + endDateParts[1] + " " + endDateParts[2];

			String timeZone = "GMT+02:00"; //TODO - Check why this value has to be set to +2. The exporter code specifies a timezone but it seems to be wrong even though it is set to Europe/Stockholm
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy MM dd HH:mm zzz");

			String startTimeFull 	=	startDate + " " + startTimeString + " " + timeZone;
			String endTimeFull		=	endDate + " " + endTimeString + " " + timeZone;
			try {
				stringParts[0] 	= 	Long.toString(dateFormat.parse(startTimeFull).getTime());
				stringParts[1]  = 	Long.toString(dateFormat.parse(endTimeFull).getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}

			int index = 4; //Skip past the time tokens

			if(tokens[index].indexOf('"') == 1) { 	//Course
				stringParts[2] = tokens[index++].substring(1) + tokens[index++];
			} else {
				stringParts[2] = tokens[index++].substring(1);
			}
			if(tokens[index].indexOf('"') == 1) {	//Group
				stringParts[3] = tokens[index++].substring(1) + tokens[index++];
			} else {
				stringParts[3] = tokens[index++].substring(1);
			}
			if(tokens[index].indexOf('"') == 1) { 	//Room
				stringParts[4] = tokens[index++].substring(1) + tokens[index++];
			} else {
				stringParts[4] = tokens[index++].substring(1);
			}
			if(tokens[index].indexOf('"') == 1) { 	//Booking person
				stringParts[5] = tokens[index++].substring(1) + tokens[index++];
			} else {
				stringParts[5] = tokens[index++].substring(1);
			}
			if(tokens[index].indexOf('"') == 1) {	//Purpose
				stringParts[6] = tokens[index++].substring(1) + tokens[index++];
			} else {
				stringParts[6] = tokens[index++].substring(1);
			}
			if(tokens[index].indexOf('"') == 1) {	//Extra info
				stringParts[7] = tokens[index++].substring(1) + tokens[index++];
			} else {
				stringParts[7] = tokens[index++].substring(1);
			}
			return stringParts;
		}

	 	private void exportScheduleEvent(String[] eventData, int calendarID) throws ParseException {
	 		//Prepare the event for insertion
	 		
	 		String title = eventData[2] + " " + eventData[6] + " " + eventData[4] + " " + eventData[5];
	 		String description  = eventData[7] + " \n\n" + CALENDAR_EVENT_TAG; //Add a tag to the description to let the user know that we are responsible for adding this event
	 		
	 		TimeZone timeZone = TimeZone.getDefault();
	 		ContentValues values = new ContentValues();
			values.put(CalendarContract.Events.DTSTART, eventData[0]);
			values.put(CalendarContract.Events.DTEND, eventData[1]);
			values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());
			values.put(CalendarContract.Events.TITLE, title);
			values.put(CalendarContract.Events.DESCRIPTION, description);
			values.put(CalendarContract.Events.CALENDAR_ID, calendarID);

			//Insert the event
			ContentResolver contentResolver = getContentResolver();
			Uri uri = contentResolver.insert(CalendarContract.Events.CONTENT_URI, values);

			String eventId = uri.getLastPathSegment();
			
			ICalendarEventTable eventTable = DatabaseManager.getInstance().getCalendarEventTable();
			eventTable.add(Integer.parseInt(eventId), title, description, eventData[0], eventData[1]);
	 	}
	}
}