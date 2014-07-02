package com.example.app_android;


import com.example.app_android.InterfaceListSelectionListener;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityMyCoursesAndProgram extends Activity implements InterfaceListSelectionListener{

	private static final String TAG = "ActivityCoursesAndProgram";
	public static String[] coursesAndProgramArray;
	EditText courseCode;
	AdapterCoursesHelper coursesHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mycoursesandprogram);
		courseCode = (EditText) findViewById(R.id.courseCode);
		coursesHelper = new AdapterCoursesHelper(this);
		coursesAndProgramArray = coursesHelper.readAllCourses();
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
	
	public void readCourses() {
		coursesAndProgramArray = coursesHelper.readAllCourses();
	}
	
	public void courseChecked(View v) {
		Logger.VerboseLog(TAG, "Checked or Unchecked");
	}

	@Override
	public void onListSelection(int index) {
		// TODO Auto-generated method stub
	}
}
