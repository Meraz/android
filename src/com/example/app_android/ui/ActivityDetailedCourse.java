package com.example.app_android.ui;

import java.util.ArrayList;

import com.example.app_android.CourseBean;
import com.example.app_android.ExportToGCalFromTimeEditTask;
import com.example.app_android.R;
import com.example.app_android.database.DBException;
import com.example.app_android.database.DatabaseManager;
import com.example.app_android.database.NoRowsAffectedDBException;
import com.example.app_android.database.interfaces.ICourseTable;
import com.example.app_android.database.interfaces.IFavouriteCourseTable;
import com.example.app_android.util.CalendarUtilities;
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
	String courseCode;
	boolean isFavourite;
	MenuItem addOrRemoveButton;
	IFavouriteCourseTable favouriteCourseHelper;
	ICourseTable courseHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mClassName = getClass().getSimpleName();
		mTag = TAG;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detailed_course);
		
		//Get the intent bundle and the databases we need 
		Bundle bundle = getIntent().getExtras();
		courseCode = bundle.getString("courseCode");
		courseHelper = DatabaseManager.getInstance().getCourseTable();
		favouriteCourseHelper = DatabaseManager.getInstance().getFavouriteCourseTable();
		
		//Fetch course info
		CourseBean courseInfo = courseHelper.getCourse(courseCode);
		if(courseInfo == null) {
			Toast.makeText(getApplicationContext(), "Failed to retrieve info about this course,  sorry :<", Toast.LENGTH_SHORT).show();
			getActionBar().setTitle(courseCode);
		} else {
			getActionBar().setTitle(courseCode + " - " + courseInfo.getCourseName());
		
			((TextView)findViewById(R.id.course_responsible_text)).setText(courseInfo.getCourseResponsible());
			((TextView)findViewById(R.id.course_start_text)).setText(courseInfo.getStartDate());
			((TextView)findViewById(R.id.course_end_text)).setText(courseInfo.getEndDate());
			((TextView)findViewById(R.id.course_exam_text)).setText(courseInfo.getNextExamDate());
			((TextView)findViewById(R.id.course_litterature_text)).setText(courseInfo.getCourseLiterature());
			((TextView)findViewById(R.id.course_description_text)).setText(courseInfo.getCourseDescription());
		}
		
		//If the course code is present in the favouriteCourseDatabase, well, then it's one of the users favourites and we can mark it as a favourite
		isFavourite = favouriteCourseHelper.getAll().contains(courseCode);
	}
	
	@SuppressWarnings("unchecked")
	public void onExportButtonPressed(View exportButton) {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":onExportButtonPressed()");}
		
		if(Utilities.isNetworkAvailable(getApplicationContext())) {
			
			ArrayList<String> requests = new ArrayList<String>();
			requests.add(CalendarUtilities.buildTimeEditRequest(courseCode,
					((String) ((TextView)findViewById(R.id.course_start_text)).getText()).replace("-", ""),	//Removes the dashes since timeEdit don't like them.
					((String) ((TextView)findViewById(R.id.course_end_text)).getText())).replace("-", ""));
			ExportToGCalFromTimeEditTask exportTask = new ExportToGCalFromTimeEditTask(getApplicationContext(), null); //TODO - add sync item in action bar and start it after this call
			exportTask.execute(requests);
		}
		else 
			Toast.makeText(getApplicationContext(), "Missing internet connection", Toast.LENGTH_SHORT).show();
	}
	
	public void onRegisterCourseButtonPressed(View RegisterCourseButton) {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":onRegisterCourseButtonPressed()");}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":onCreateOptionsMenu()");}
		
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.layout.activity_detailed_course_action, menu);
	    addOrRemoveButton = menu.findItem(R.id.detailed_course_action_add_or_remove);
	    if(isFavourite)
	    	addOrRemoveButton.setIcon(R.drawable.ic_action_important);
	    else
	    	addOrRemoveButton.setIcon(R.drawable.ic_action_not_important);
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":onCreateOptionsMenu()");}
		switch (item.getItemId()) {
	    case R.id.detailed_course_action_add_or_remove:
	    	if(isFavourite) {
	    		boolean result = false;
	    			try {
						result = favouriteCourseHelper.remove(courseCode);
					} catch (DBException e) {		//Sorry for the duplicate code. Need java 7 to use one handler for multiple cathces
						Toast.makeText(getApplicationContext(), "Failed to remove course :(", Toast.LENGTH_SHORT).show();
					} catch (NoRowsAffectedDBException e) {
						Toast.makeText(getApplicationContext(), "Failed to remove course :(", Toast.LENGTH_SHORT).show();
					}	    		
	    		if(result) {
	    			isFavourite = false;
	    			addOrRemoveButton.setIcon(R.drawable.ic_action_not_important);
	    			Toast.makeText(getApplicationContext(), "Removed from favourites", Toast.LENGTH_SHORT).show();
	    		}
	    	}
	    	else {
	    		boolean result = false;
	    		try {
					result = favouriteCourseHelper.add(courseCode.split(" ")[0]); //Get only the course code, not the name
				} catch (NullPointerException e) {
					Toast.makeText(getApplicationContext(), "Failed to add course :(", Toast.LENGTH_SHORT).show();
				} catch (DBException e) {
					Toast.makeText(getApplicationContext(), "Failed to add course :(", Toast.LENGTH_SHORT).show();
				}	    		
	    		if(result) {
	    			isFavourite = true;
	    			addOrRemoveButton.setIcon(R.drawable.ic_action_important);
	    			Toast.makeText(getApplicationContext(), "Added to favourites", Toast.LENGTH_SHORT).show();
	    		}
	    	}
	    	break;
	    }
	        return super.onOptionsItemSelected(item);
	    }
}