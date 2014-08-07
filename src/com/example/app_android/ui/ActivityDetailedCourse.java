package com.example.app_android.ui;

import com.example.app_android.CourseBean;
import com.example.app_android.R;
import com.example.app_android.database.DBException;
import com.example.app_android.database.DatabaseManager;
import com.example.app_android.database.ICourseTable;
import com.example.app_android.database.IFavouriteCourseTable;
import com.example.app_android.util.Utilities;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
			getActionBar().setTitle(courseCode + " - " + courseInfo.getCourseName()); //TODO - add course name here too
		
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
	    		isFavourite = false;
	    		boolean result = false;
	    			try {
						result = favouriteCourseHelper.remove(courseCode);
					} catch (NullPointerException e) {
						// TODO 
						e.printStackTrace();
					} catch (DBException e) {
						// TODO 
						e.printStackTrace();
					}	    		
	    		if(result) {
	    			addOrRemoveButton.setIcon(R.drawable.ic_action_not_important);
	    			Toast.makeText(getApplicationContext(), courseCode + " was removed from favourite courses", Toast.LENGTH_SHORT).show();
	    		}
	    		else
	    			Toast.makeText(getApplicationContext(), "Failed to remove course :(", Toast.LENGTH_SHORT).show();
	    	}
	    	else {
	    		isFavourite = true;
	    		boolean result = false;
	    		try {
					result = favouriteCourseHelper.add(courseCode);
				} catch (NullPointerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	    		
	    		if(result) {
	    			addOrRemoveButton.setIcon(R.drawable.ic_action_important);
	    			Toast.makeText(getApplicationContext(), courseCode + " was added to favourite courses", Toast.LENGTH_SHORT).show();
	    		}
	    		else
	    			Toast.makeText(getApplicationContext(), "Failed to add course :(", Toast.LENGTH_SHORT).show();
	    	}
	    	break;
	    }
	        return super.onOptionsItemSelected(item);
	    }
}