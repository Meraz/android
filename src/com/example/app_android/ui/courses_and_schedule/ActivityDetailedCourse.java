package com.example.app_android.ui.courses_and_schedule;

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

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityDetailedCourse extends BaseActivity {
	private static final String TAG = "CourseView";
	String 					mCourseCode;
	boolean 				mIsFavourite;
	MenuItem 				mAddOrRemoveFavouriteButton;
	IFavouriteCourseTable 	mFavouriteCourseTable;
	ICourseTable 			mCourseTable;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mClassName = getClass().getSimpleName();
		mTag = TAG;
		super.onCreate(savedInstanceState);
		mInfoBoxTitle = getString(R.string.infobox_title_detailed_courses);
		mInfoBoxMessage = getString(R.string.infobox_text_detailed_courses);
		
		setContentView(R.layout.activity_detailed_course);
		
		//Get the intent bundle and the databases we need 
		Bundle bundle = getIntent().getExtras();
		mCourseCode = bundle.getString("courseCode");
		mCourseTable = DatabaseManager.getInstance().getCourseTable();
		mFavouriteCourseTable = DatabaseManager.getInstance().getFavouriteCourseTable();
		
		//Fetch course info
		CourseBean courseInfo;
		try {
			courseInfo = mCourseTable.getCourse(mCourseCode);
		} catch (DBException e) {
			courseInfo = null;
			e.printStackTrace();
		} catch (NoResultFoundDBException e) {
			courseInfo = null;
			e.printStackTrace();
		}
		if(courseInfo == null) {
			Toast.makeText(getApplicationContext(), R.string.toast_detailed_course_failed_retrieve_info, Toast.LENGTH_SHORT).show();
			mActionBarTitle = mCourseCode;
		} else {
			mActionBarTitle = mCourseCode + " - " + courseInfo.getCourseName();
		
			//Set the textviews text to the fetched data
			((TextView)findViewById(R.id.course_responsible_text)).setText(courseInfo.getCourseResponsible());
			((TextView)findViewById(R.id.course_start_text)).setText(courseInfo.getStartDate());
			((TextView)findViewById(R.id.course_end_text)).setText(courseInfo.getEndDate());
			((TextView)findViewById(R.id.course_exam_text)).setText(courseInfo.getNextExamDate());
			((TextView)findViewById(R.id.course_litterature_text)).setText(courseInfo.getCourseLiterature());
			((TextView)findViewById(R.id.course_description_text)).setText(courseInfo.getCourseDescription());
		}
		
		//If the course code is present in the favouriteCourseDatabase, well, then it's one of the users favourites and we can mark it as a favourite
		try {
			mIsFavourite = mFavouriteCourseTable.getAll().contains(mCourseCode);
		} catch (DBException e) {
			mIsFavourite = false;
			e.printStackTrace();
		} catch (NoResultFoundDBException e) {
			mIsFavourite = false;
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void onExportButtonPressed(View exportButton) {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":onExportButtonPressed()");}
		if(Utilities.isNetworkAvailable(getApplicationContext())) {
			ArrayList<String> requests = new ArrayList<String>();
			requests.add(CalendarUtilities.buildTimeEditRequest(mCourseCode,
					((String) ((TextView)findViewById(R.id.course_start_text)).getText()).replaceAll("-", ""),	//Removes the dashes since timeEdit don't like them.
					((String) ((TextView)findViewById(R.id.course_end_text)).getText())).replaceAll("-", ""));
			ExportToGCalFromTimeEditTask exportTask = new ExportToGCalFromTimeEditTask(getApplicationContext(), null); //TODO - add sync item in action bar and start it after this call
			exportTask.execute(requests);
		}
		else 
			Toast.makeText(getApplicationContext(), R.string.toast_general_missing_internet_connection, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":onCreateOptionsMenu()");}
		
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.layout.activity_detailed_course_action, menu);
	    mAddOrRemoveFavouriteButton = menu.findItem(R.id.detailed_course_action_add_or_remove);
	    if(mIsFavourite)
	    	mAddOrRemoveFavouriteButton.setIcon(R.drawable.ic_action_important);
	    else
	    	mAddOrRemoveFavouriteButton.setIcon(R.drawable.ic_action_not_important);
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":onCreateOptionsMenu()");}
		switch (item.getItemId()) {
	    case R.id.detailed_course_action_add_or_remove:
	    	if(mIsFavourite) {
	    		boolean result = true;
	    			try {
						mFavouriteCourseTable.remove(mCourseCode);
					} catch (DBException e) {		//Sorry for the duplicate code. Need java 7 to use one handler for multiple catches
						Toast.makeText(getApplicationContext(), R.string.toast_courses_failed_remove_favourite, Toast.LENGTH_SHORT).show();
						result = false;
					} catch (NoRowsAffectedDBException e) {
						Toast.makeText(getApplicationContext(), R.string.toast_courses_failed_remove_favourite, Toast.LENGTH_SHORT).show();
						result = false;
					}	    		
	    		if(result) {
	    			mIsFavourite = false;
	    			mAddOrRemoveFavouriteButton.setIcon(R.drawable.ic_action_not_important);
	    			Toast.makeText(getApplicationContext(), R.string.toast_courses_confirm_removed_favourite_course, Toast.LENGTH_SHORT).show();
	    		}
	    	}
	    	else {
	    		boolean result = true;
	    		try {
					mFavouriteCourseTable.add(mCourseCode.split(" ")[0]); 	//Get only the course code, not the name
				} catch (DBException e) {		//Sorry for the duplicate code. Need java 7 to use one handler for multiple catches
					Toast.makeText(getApplicationContext(), R.string.toast_courses_failed_add_favourite, Toast.LENGTH_SHORT).show();
					result = false;
				} catch (NoRowsAffectedDBException e) {
					Toast.makeText(getApplicationContext(), R.string.toast_courses_failed_add_favourite, Toast.LENGTH_SHORT).show();
					result = false;
				}	    		
	    		if(result) {
	    			mIsFavourite = true;
	    			mAddOrRemoveFavouriteButton.setIcon(R.drawable.ic_action_important);
	    			Toast.makeText(getApplicationContext(), R.string.toast_courses_confirm_removed_favourite_course, Toast.LENGTH_SHORT).show();
	    		}
	    	}
	    	break;
	    }
	        return super.onOptionsItemSelected(item);
	    }
}