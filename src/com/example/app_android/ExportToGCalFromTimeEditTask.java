package com.example.app_android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.app_android.database.DBException;
import com.example.app_android.database.DatabaseManager;
import com.example.app_android.database.NoRowsAffectedDBException;
import com.example.app_android.database.interfaces.ICalendarEventTable;

public class ExportToGCalFromTimeEditTask extends AsyncTask<ArrayList<String>, Void, ExportResultBean> {
	 final Context context;
	 final MenuItem syncItem;
	 public static final String CALENDAR_EVENT_TAG = "[This event was added by the BTH App]";

	 public ExportToGCalFromTimeEditTask(Context context, MenuItem syncItem) {	//TODO - Make a callback to set syncitem instead of inputing it here. This is only a hack.
		 this.context = context;
		 this.syncItem = syncItem;
	 }

	 @Override
    protected ExportResultBean doInBackground(ArrayList<String>... requests) {
		 ExportResultBean exportResult = new ExportResultBean();
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
	 protected void onPostExecute(ExportResultBean exportResult) {

		 switch (exportResult.resultFlag) {
		 	case 0: // Normal
		 		Toast.makeText(context, exportResult.exportedCount + " Events added\n" + exportResult.deletedCount +" Events deleted\n" + exportResult.upToDateCount + " Events already synced", Toast.LENGTH_LONG).show();
		 		break;

		 	case 1: // All events up to date
		 		Toast.makeText(context, "All " + exportResult.upToDateCount + " events are up to date", Toast.LENGTH_SHORT).show();
		 		break;

		 	case 2: // No data recieved from timeedit
		 		Toast.makeText(context, "TimeEdit failed to return data for some or all courses\n" + exportResult.exportedCount + " Events added\n" + exportResult.deletedCount +" Events deleted\n" + exportResult.upToDateCount + " Events already synced", Toast.LENGTH_LONG).show();
		 		break;

		 	case 3: // Event export failed
		 		Toast.makeText(context,"Some or all events failed to sync\n" + exportResult.exportedCount + " Events added\n" + exportResult.deletedCount +" Events deleted\n" + exportResult.upToDateCount + " Events already synced", Toast.LENGTH_LONG).show();
		 		break;

		 	case 4: // No calendar found
		 		Toast.makeText(context, "Sync failed - No Google calendar found", Toast.LENGTH_SHORT).show();
		 		break;
		 }
		 if(syncItem != null)//TODO - Make a callback to set syncitem instead of inputing it here. This is only a hack.
			 syncItem.setActionView(null);
	 }
	 //Finds the id of a calendar connected to a gmail account
	 private int findCalendarID() {
			int returnCalendarID = -1;
			int[] calendarIDs = null;
			String[] calendarNames = null;

			//Get the calendar ID
			String[] projection = new String[] {
			       CalendarContract.Calendars._ID,
			       CalendarContract.Calendars.ACCOUNT_NAME,
			       CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
			       CalendarContract.Calendars.NAME,
			       CalendarContract.Calendars.CALENDAR_COLOR
			};
			Cursor cursor = context.getContentResolver().query(Uri.parse("content://com.android.calendar/calendars"), projection, null, null, null);
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
			 boolean result = false;
			 try {
				eventTable.remove(eventId);
			} catch (DBException e) {
				// TODO
				e.printStackTrace();
			} catch (NoRowsAffectedDBException e) {
				// TODO
				e.printStackTrace();
			}
			 if(result) {
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
			int rows = context.getContentResolver().delete(deleteUri, null, null);
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
		ContentResolver contentResolver = context.getContentResolver();
		Uri uri = contentResolver.insert(CalendarContract.Events.CONTENT_URI, values);

		String eventId = uri.getLastPathSegment();
		
		ICalendarEventTable eventTable = DatabaseManager.getInstance().getCalendarEventTable();
		try {
			eventTable.add(Integer.parseInt(eventId), title, description, eventData[0], eventData[1]);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoRowsAffectedDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}