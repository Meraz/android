package com.example.app_android.ui;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ArrayList;

import com.example.app_android.ExportToGCalFromTimeEditTask;
import com.example.app_android.R;
import com.example.app_android.database.DBException;
import com.example.app_android.database.DatabaseManager;
import com.example.app_android.database.interfaces.ICourseTable;
import com.example.app_android.database.interfaces.IFavouriteCourseTable;
import com.example.app_android.util.CalendarUtilities;
import com.example.app_android.util.Utilities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityCourses extends BaseActivity {

	private static final String TAG = "CourseView";
	public static ArrayList<String> coursesArray;
	public static ArrayList<String> favouriteCoursesArray;
	
	private ICourseTable 			coursesHelper;
	private IFavouriteCourseTable 	favouriteCoursesHelper;
	
	private MenuItem 				syncActionItem;
	private View 					favouriteCourseListView;
	private TextView 				noCoursesText;
	private AutoCompleteTextView 	searchField;
	private ArrayAdapter<String> 	searchAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mClassName = getClass().getSimpleName();
		mTag = TAG;		
		super.onCreate(savedInstanceState);
		mInfoBoxMessage = "Insert info about this view here";
		
		
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN); //This sneaky row stops the darn soft keyboard from popping up like some retarded wack-a-mole every time the activity is opened.
		
		setContentView(R.layout.activity_courses);
		
		//Get database variables
		coursesHelper			= DatabaseManager.getInstance().getCourseTable();
		favouriteCoursesHelper 	= DatabaseManager.getInstance().getFavouriteCourseTable();
		favouriteCoursesArray 	= favouriteCoursesHelper.getAll();
		
		//Get layout variables
		searchField 		= (AutoCompleteTextView) findViewById(R.id.course_search_input);
		favouriteCourseListView = findViewById(R.id.courseListView);
		noCoursesText 			= (TextView) findViewById(R.id.noCoursesDescription);
		
		initializeDropDownSearchField();
		
		//Set what should be viewed in the center of the view
		if(favouriteCoursesHelper.isEmpty())
			favouriteCourseListView.setVisibility(View.GONE);
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
	    return super.onCreateOptionsMenu(menu);
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	if(Utilities.verbose) {Log.v(TAG, mClassName + ":onOptionsItemSelected()");}
    	switch (item.getItemId()) {
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
    	
    	//Make icons visable in overflow menu
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
		String cCode = searchField.getText().toString();
		if(!cCode.isEmpty()) {
			boolean result = false;

				try {
					result = favouriteCoursesHelper.add(cCode);
				} catch (NullPointerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
			if(result) {
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
		favouriteCoursesArray = favouriteCoursesHelper.getAll();
	}
	
	public void courseChecked(View v) { // TODO function needed?
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":courseChecked()");}
	}
	
	private void initializeDropDownSearchField() {
		ArrayList<String> courseCodeList = coursesHelper.getAllCourseCodes(); 
		ArrayList<String> courseNamesList = coursesHelper.getAllCourseNames();
		
		int courseCount = courseCodeList.size();
		
		String[] courseCodes = new String[courseCount];
		String[] courseNames = new String[courseCount];
		courseCodes = courseCodeList.toArray(courseCodes);
		courseNames = courseNamesList.toArray(courseNames);
		
		String[] adapterInput = new String[courseCount];
		for (int i = 0; i < courseCount; ++i) {
			adapterInput[i] = courseCodes[i] + " - " + courseNames[i];
		}
		
		searchAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, adapterInput);
		searchField.setAdapter(searchAdapter);
		searchField.setThreshold(0);
		searchField.setOnItemClickListener(new OnItemClickListener() {
		    
			@Override
		    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent = new Intent(getApplicationContext(), ActivityDetailedCourse.class);
				intent.putExtra("courseCode",((String) arg0.getItemAtPosition(arg2)).split(" ")[0]); //Send only the course code, not the concatinated name
				startActivity(intent);
		    }
		});
	}
	
	private int deleteAllScheduleEvents() {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":deleteAllScheduleEvents()");}
 		int rowsDeletedCount = getContentResolver().delete(CalendarContract.Events.CONTENT_URI,
 				Events.DESCRIPTION + " LIKE ? ", new String[] {"%" + ExportToGCalFromTimeEditTask.CALENDAR_EVENT_TAG +"%"});
 		
 		return rowsDeletedCount;
	}
	
	@SuppressWarnings("unchecked") //Should be safe to ignore this warning. It complains about not knowing the type of arraylist being sent in exportTask.execute(requests)
	@SuppressLint("SimpleDateFormat")
	private void exportSchedule() {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":exportSchedule()");}
		
		if(Utilities.isNetworkAvailable(getApplicationContext())) {
			ArrayList<String> courseCodes = favouriteCoursesHelper.getAll();
			ArrayList<String> requests = new ArrayList<String>();

			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			String startDate = dateFormat.format(calendar.getTime());
			calendar.add(Calendar.MONTH, 6);
			String endDate = dateFormat.format(calendar.getTime());

			for(int i = 0; i < courseCodes.size(); ++i) {
				requests.add(CalendarUtilities.buildTimeEditRequest(courseCodes.get(i), startDate, endDate)); //TODO - set start and end date to the start and end of the course. This can be fetched from the database.
			}
				ExportToGCalFromTimeEditTask exportTask = new ExportToGCalFromTimeEditTask(getApplicationContext(), syncActionItem);
				exportTask.execute(requests);
				syncActionItem.setActionView(R.layout.item_action_sync_indicator);
		}
		else
			Toast.makeText(getApplicationContext(), "Missing internet connection", Toast.LENGTH_SHORT).show();
	}
}