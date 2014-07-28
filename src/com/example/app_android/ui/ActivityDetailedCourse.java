package com.example.app_android.ui;

import com.example.app_android.R;
import com.example.app_android.util.Logger;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class ActivityDetailedCourse extends Activity {
	private static final String TAG = "ActivityDetailedCourse";
	String courseCode;
	MenuItem addOrRemoveButton;
	boolean isFavourite = true; //TODO - recieve this from bundle
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detailed_course);
		
		Bundle bundle = getIntent().getExtras();
		courseCode = bundle.getString("courseCode");
		getActionBar().setTitle(courseCode); //TODO - add course name here too
		
		((TextView)findViewById(R.id.course_responsible_text)).setText("[Hardcode]Betty Bergqvist");
		((TextView)findViewById(R.id.course_start_text)).setText("[Hardcode]2014-09-01");
		((TextView)findViewById(R.id.course_end_text)).setText("[Hardcode]2015-04-01");
		((TextView)findViewById(R.id.course_exam_text)).setText("[Hardcode]2014-11-01");
		((TextView)findViewById(R.id.course_litterature_text)).setText("[Hardcode]C++101");
		((TextView)findViewById(R.id.course_description_text)).setText("[Hardcode]THISISLOTSOFTEXTSOIHAZTOSCROLL!THISISLOTSOFTEXTSOIHAZTOSCROLL!THISISLOTSOFTEXTSOIHAZTOSCROLL!THISISLOTSOFTEXTSOIHAZTOSCROLL!THISISLOTSOFTEXTSOIHAZTOSCROLL!THISISLOTSOFTEXTSOIHAZTOSCROLL!THISISLOTSOFTEXTSOIHAZTOSCROLL!THISISLOTSOFTEXTSOIHAZTOSCROLL!THISISLOTSOFTEXTSOIHAZTOSCROLL!THISISLOTSOFTEXTSOIHAZTOSCROLL!THISISLOTSOFTEXTSOIHAZTOSCROLL!THISISLOTSOFTEXTSOIHAZTOSCROLL!THISISLOTSOFTEXTSOIHAZTOSCROLL!THISISLOTSOFTEXTSOIHAZTOSCROLL!THISISLOTSOFTEXTSOIHAZTOSCROLL!THISISLOTSOFTEXTSOIHAZTOSCROLL!THISISLOTSOFTEXTSOIHAZTOSCROLL!THISISLOTSOFTEXTSOIHAZTOSCROLL!THISISLOTSOFTEXTSOIHAZTOSCROLL!THISISLOTSOFTEXTSOIHAZTOSCROLL!THISISLOTSOFTEXTSOIHAZTOSCROLL!THISISLOTSOFTEXTSOIHAZTOSCROLL!THISISLOTSOFTEXTSOIHAZTOSCROLL!HELPI'MTRAPPEDINANANDROIDAPPFACTORY!THISISLOTSOFTEXTSOIHAZTOSCROLL!THISISLOTSOFTEXTSOIHAZTOSCROLL!THISISLOTSOFTEXTSOIHAZTOSCROLL!THISISLOTSOFTEXTSOIHAZTOSCROLL!");
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
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
		switch (item.getItemId()) {
	    case R.id.detailed_course_action_info:
	   		Builder alert = new AlertDialog.Builder(this);
	   		alert.setTitle("Course " + courseCode);
	   		alert.setMessage("Insert info about this view here~'("); //TODO
	   		alert.setPositiveButton("OK",null);
    		alert.show();
    		break;
	    case R.id.detailed_course_action_add_or_remove:
	    	if(isFavourite) {
	    		isFavourite = false;
	    		//TODO - remove from database
	    		addOrRemoveButton.setIcon(R.drawable.ic_action_not_important);
	    	}
	    	else {
	    		isFavourite = true;
	    		//TODO - add to database
	    		addOrRemoveButton.setIcon(R.drawable.ic_action_important);
	    	}
	    	break;
	    }
	        return super.onOptionsItemSelected(item);
	    }
}