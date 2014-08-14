package com.example.app_android.ui.courses_and_schedule;

import java.lang.reflect.Method;
import java.util.ArrayList;

import com.example.app_android.R;
import com.example.app_android.database.DBException;
import com.example.app_android.database.DatabaseManager;
import com.example.app_android.database.NoResultFoundDBException;
import com.example.app_android.database.NoRowsAffectedDBException;
import com.example.app_android.database.interfaces.ICourseTable;
import com.example.app_android.database.interfaces.IFavouriteCourseTable;
import com.example.app_android.ui.BaseActivity;
import com.example.app_android.util.Utilities;

import android.annotation.SuppressLint;
import android.app.ListFragment;
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
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityCourses extends BaseActivity {

	private static final String 	TAG = "CourseView";
	
	public static ArrayList<String> mCourseCodeList;
	public static ArrayList<String> mFavouriteCoursesList;
	
	private ICourseTable 			mCoursesTable;
	private IFavouriteCourseTable 	mFavouriteCoursesTable;
	
	private MenuItem 				mSyncActionItem;
	private View 					mFavouriteCourseListView;
	private TextView 				mNoCoursesText;
	private AutoCompleteTextView 	mSearchField;
	private ArrayAdapter<String> 	mSearchAdapter;
	private ListFragment			mListFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mClassName = getClass().getSimpleName();
		mTag = TAG;
		mActionBarTitle = getString(R.string.actionbar_courses);
		super.onCreate(savedInstanceState);
		mInfoBoxTitle = getString(R.string.infobox_title_courses);
		mInfoBoxMessage = getString(R.string.infobox_text_courses);
		
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN); //This sneaky row stops the darn soft keyboard from popping up like some retarded wack-a-mole every time the activity is opened.
		
		setContentView(R.layout.activity_courses);
		
		//Get database variables
		mCoursesTable			= DatabaseManager.getInstance().getCourseTable();
		mFavouriteCoursesTable 	= DatabaseManager.getInstance().getFavouriteCourseTable();
		try {
			mFavouriteCoursesList = mFavouriteCoursesTable.getAll();
		} catch (DBException e) {
			mFavouriteCoursesList = new ArrayList<String>();
			e.printStackTrace();
		} catch (NoResultFoundDBException e) {
			mFavouriteCoursesList = new ArrayList<String>();
		}
		
		//Get layout variables
		mSearchField 		= (AutoCompleteTextView) findViewById(R.id.course_search_input);
		mFavouriteCourseListView = findViewById(R.id.courseListView);
		mNoCoursesText 			= (TextView) findViewById(R.id.noCoursesDescription);
		
		initializeDropDownSearchField();
		
		//Set what should be displayed in the center of the view
		boolean isEmpty = false;
		try {
			isEmpty = mFavouriteCoursesTable.isEmpty();
		} catch (DBException e) {
			isEmpty = false;
			e.printStackTrace();
		}
		
		if(isEmpty)
			mFavouriteCourseListView.setVisibility(View.GONE);
		else
			mNoCoursesText.setVisibility(View.GONE);
		
		initializePopupMenu();
	}
 
	@Override
	protected void onRestart() {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":onRestart()");}
		super.onRestart();
		//Restart this view to display the correct information
		finish();
		startActivity(getIntent());
	}

	@Override
	protected void onResume() {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":onResume()");}
		super.onResume();
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN); //This sneaky row stops the darn soft keyboard from popping up like some retarded wack-a-mole every time the activity is opened.
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":onCreateOptionsMenu()");}
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.layout.activity_courses_action, menu);
	    mSyncActionItem = menu.findItem(R.id.courses_Action_sync_indicator);
	    mSyncActionItem.setVisible(false);
	    return super.onCreateOptionsMenu(menu);
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	if(Utilities.verbose) {Log.v(TAG, mClassName + ":onOptionsItemSelected()");}
    	switch (item.getItemId()) {
    		
    	case R.id.courses_menu_empty_schedule:
    		int deletedRowsCount = deleteAllScheduleEvents();
    		Toast.makeText(getApplicationContext(), deletedRowsCount + " " + 
    				getResources().getString(R.string.toast_courses_menu_empty_schedule)  , Toast.LENGTH_SHORT).show();
    		break;
    		
    	case R.id.courses_menu_sync_schedule:
    		exportSchedule();
    		break;
    	}
        return super.onOptionsItemSelected(item);
    }
     
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
    	if(Utilities.verbose) {Log.v(TAG, mClassName + ":onMenuOpened()");}
    	
    	//Make icons visible in overflow menu
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
    
	public void startCalendar(View view) {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":startCalendar()");}
    	//Start the calendar app
    	Uri uri = Uri.parse("content://com.android.calendar/time");
		Intent intent = new Intent("android.intent.action.VIEW", uri);
		startActivity(intent);
	}
	
	private void initializeDropDownSearchField() {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":initializeDropDownSearchField()");}
		ArrayList<String> courseCodeList = null;
		try {
			courseCodeList = mCoursesTable.getAllCourseCodes();
		} catch (DBException e) {
			courseCodeList = new ArrayList<String>();
			e.printStackTrace();
		} catch (NoResultFoundDBException e) {
			courseCodeList = new ArrayList<String>();
			e.printStackTrace();
		} 
		ArrayList<String> courseNamesList = null;
		try {
			courseNamesList = mCoursesTable.getAllCourseNames();
		} catch (DBException e) {
			courseNamesList = new ArrayList<String>(courseCodeList.size());
			for(int i = 0; i < courseCodeList.size(); ++i) {
				courseNamesList.add("");
			}
			
			e.printStackTrace();
		} catch (NoResultFoundDBException e) {
			courseNamesList = new ArrayList<String>(courseCodeList.size());
			for(int i = 0; i < courseCodeList.size(); ++i) {
				courseNamesList.add("");
			}
			
			e.printStackTrace();
		}
		
		int courseCount = courseCodeList.size();
		
		String[] courseCodes = new String[courseCount];
		String[] courseNames = new String[courseCount];
		courseCodes = courseCodeList.toArray(courseCodes);
		courseNames = courseNamesList.toArray(courseNames);
		
		String[] adapterInput = new String[courseCount];
		for (int i = 0; i < courseCount; ++i) {
			adapterInput[i] = courseCodes[i] + " - " + courseNames[i];
		}
		
		mSearchAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, adapterInput);
		mSearchField.setAdapter(mSearchAdapter);
		mSearchField.setThreshold(0);
		mSearchField.setOnItemClickListener(new OnItemClickListener() {
			@Override
		    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent = new Intent(getApplicationContext(), ActivityDetailedCourse.class);
				intent.putExtra("courseCode",((String) arg0.getItemAtPosition(arg2)).split(" ")[0]); //Send only the course code, not the concatenated name
				startActivity(intent);
		    }
		});
	}
	
	private void initializePopupMenu() {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":initializePopupMenu()");}
		//Initialize the popup menu for long press on course item
				mListFragment = (ListFragment) getFragmentManager().findFragmentById(R.id.courseListView);
				mListFragment.getListView().setOnItemLongClickListener(new OnItemLongClickListener() {
					@Override
					public boolean onItemLongClick(AdapterView<?> parent, View view,
							final int position, long id) {
						
						final boolean isFavourite = true; //TODO - ask the favourite course table if the course exists and set this flag accordingly

						
						PopupMenu popupMenu = new PopupMenu(getApplicationContext(), view);
						popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
							
							@Override
							public boolean onMenuItemClick(MenuItem item) {
								switch(item.getItemId()) {
								
								case R.id.course_long_press_view_detailed_info:
									Intent intent = new Intent(getApplicationContext(), ActivityDetailedCourse.class);
									intent.putExtra("courseCode", mFavouriteCoursesList.get(position));
									startActivity(intent);
									break;
									
								case R.id.course_long_press_export_schedule:
									exportSingleCourseSchedule(mFavouriteCoursesList.get(position));
									break;
									
								case R.id.course_long_press_add_or_remove_favourite:
									if(isFavourite) {
										try {
											mFavouriteCoursesTable.remove(mFavouriteCoursesList.get(position));
											Toast.makeText(getApplicationContext(), R.string.toast_courses_confirm_removed_favourite_course, Toast.LENGTH_SHORT).show();
											//Restart this view to display the correct information
											finish();
											startActivity(getIntent());
										} catch (DBException e) {
											Toast.makeText(getApplicationContext(), R.string.toast_courses_failed_remove_favourite , Toast.LENGTH_SHORT).show();
											e.printStackTrace();
										} catch (NoRowsAffectedDBException e) {
											Toast.makeText(getApplicationContext(), R.string.toast_courses_failed_remove_favourite, Toast.LENGTH_SHORT).show();
											e.printStackTrace();
										}
									}
									else {
										try {
											mFavouriteCoursesTable.add(mFavouriteCoursesList.get(position));
											Toast.makeText(getApplicationContext(), R.string.toast_courses_confirm_added_favourite_course, Toast.LENGTH_SHORT).show();
											//Restart this view to display the correct information
											finish();
											startActivity(getIntent());
										} catch (DBException e) {
											Toast.makeText(getApplicationContext(), R.string.toast_courses_failed_add_favourite, Toast.LENGTH_SHORT).show();
											e.printStackTrace();
										} catch (NoRowsAffectedDBException e) {
											Toast.makeText(getApplicationContext(), R.string.toast_courses_failed_add_favourite, Toast.LENGTH_SHORT).show();
											e.printStackTrace();
										}
									}
									break;
								}
								return true;
							}
						});
						popupMenu.inflate(R.menu.course_long_press);
						//TODO - use the isfavorutie flag to decide which string should be presented on the add/remove favourite item and set it accordingly here
						popupMenu.show();
						
						return true; //Indicate that this event has been consumed. Omnomnomnomnomnom :3
					}
				});
	}
	
	//Removes all schedule events that has a specific tag in the description
	private int deleteAllScheduleEvents() {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":deleteAllScheduleEvents()");}
 		int rowsDeletedCount = getContentResolver().delete(CalendarContract.Events.CONTENT_URI,
 				Events.DESCRIPTION + " LIKE ? ", new String[] {"%" + ExportToGCalFromTimeEditTask.CALENDAR_EVENT_TAG +"%"});
 		
 		return rowsDeletedCount;
	}
	
	@SuppressWarnings("unchecked")
	private void exportSingleCourseSchedule(String courseCode) {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":exportSingleCourseSchedule()");}
		if(Utilities.isNetworkAvailable(getApplicationContext())) {
			ArrayList<String> requests = new ArrayList<String>();
			CourseBean course;
			try {
				course = mCoursesTable.getCourse(courseCode);
			} catch (DBException e) {
				course = null;
				e.printStackTrace();
			} catch (NoResultFoundDBException e) {
				course = null;
				e.printStackTrace();
			}
			if(course != null) {
				requests.add(CalendarUtilities.buildTimeEditRequest(course.getCourseCode(),
						course.getStartDate().replaceAll("-", ""), //Removes the dashes since timeEdit doesn't like them.
						course.getEndDate().replaceAll("-", "")));
				ExportToGCalFromTimeEditTask exportTask = new ExportToGCalFromTimeEditTask(getApplicationContext(), mSyncActionItem);
				mSyncActionItem.setVisible(true);
			    mSyncActionItem.setActionView(R.layout.item_action_sync_indicator);
				exportTask.execute(requests);
			}
			else
				Toast.makeText(getApplicationContext(), R.string.toast_courses_failed_export_schedule, Toast.LENGTH_SHORT).show();
		}
		else 
			Toast.makeText(getApplicationContext(), R.string.toast_general_missing_internet_connection, Toast.LENGTH_SHORT).show();
	}
	
	@SuppressWarnings("unchecked") //Should be safe to ignore this warning. It complains about not knowing the type of arraylist being sent in exportTask.execute(requests)
	@SuppressLint("SimpleDateFormat")
	private void exportSchedule() {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":exportSchedule()");}
		if(Utilities.isNetworkAvailable(getApplicationContext())) {
			ArrayList<String> courseCodes;
			try {
				courseCodes = mFavouriteCoursesTable.getAll();
			} catch (DBException e) {
				courseCodes = new ArrayList<String>();
				e.printStackTrace();
			} catch (NoResultFoundDBException e) {
				courseCodes = new ArrayList<String>();
			}

			//Get course information and build timeedit request strings from it. Then start an export task.
			if(!courseCodes.isEmpty())
			{
				ArrayList<String> requests = new ArrayList<String>();
				for(int i = 0; i < courseCodes.size(); ++i) {
					CourseBean course;
					try {
						course = mCoursesTable.getCourse(courseCodes.get(i));
					} catch (DBException e) {
						course = null;
						e.printStackTrace();
					} catch (NoResultFoundDBException e) {
						course = null;
						e.printStackTrace();
					}
					if(course != null)
						requests.add(CalendarUtilities.buildTimeEditRequest(courseCodes.get(i),
								course.getStartDate().replaceAll("-", "") , course.getEndDate().replaceAll("-", "")));
				}
				if(requests.size() > 0) {
					ExportToGCalFromTimeEditTask exportTask = new ExportToGCalFromTimeEditTask(getApplicationContext(), mSyncActionItem);
					exportTask.execute(requests);
					mSyncActionItem.setVisible(true);
				    mSyncActionItem.setActionView(R.layout.item_action_sync_indicator);
				}
			}
			else
				Toast.makeText(getApplicationContext(), R.string.toast_courses_failed_export_schedule_no_courses, Toast.LENGTH_SHORT).show();
		}
		else
			Toast.makeText(getApplicationContext(), R.string.toast_general_missing_internet_connection, Toast.LENGTH_SHORT).show();
	}
}