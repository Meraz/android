package com.example.app_android;


import com.example.app_android.MyCoursesFragment.ListSelectionListener;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MyCoursesAndProgramActivity extends Activity implements ListSelectionListener{

	private static final String TAG = "MyCoursesAndProgramActivity";
	public static String[] mMyCoursesAndProgramArray;
	EditText courseCode;
	MyCoursesHelperAdapter mMyCoursesHelper;
	private final static boolean verbose = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_courses_and_program);
		courseCode = (EditText) findViewById(R.id.courseCode);
		mMyCoursesHelper = new MyCoursesHelperAdapter(this);
		mMyCoursesAndProgramArray = mMyCoursesHelper.readAllCourses();
		
	}
	
    @Override
	protected void onDestroy() {
    	if (verbose)
    		Log.v(TAG, getClass().getSimpleName() + ":entered onDestroy()");
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		if (verbose)
    		Log.v(TAG, getClass().getSimpleName() + ":entered onPause()");
		super.onPause();
	}

	@Override
	protected void onRestart() {
		if (verbose)
    		Log.v(TAG, getClass().getSimpleName() + ":entered onRestart()");
		super.onRestart();
	}

	@Override
	protected void onResume() {
		if (verbose)
    		Log.v(TAG, getClass().getSimpleName() + ":entered onResume()");
		super.onResume();
	}

	@Override
	protected void onStart() {
		if (verbose)
    		Log.v(TAG, getClass().getSimpleName() + ":entered onStart()");
		super.onStart();		
	}

	@Override
	protected void onStop() {
		if (verbose)
    		Log.v(TAG, getClass().getSimpleName() + ":entered onStop()");
		super.onStop();
	}
	
	public void addCourse(View view) {
		String cCode = courseCode.getText().toString();
		long id = mMyCoursesHelper.insertData(cCode);
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
		mMyCoursesAndProgramArray = mMyCoursesHelper.readAllCourses();
	}
	
	public void courseChecked(View v) {
		if (verbose)
    		Log.v(TAG, "Checked");
	}

	@Override
	public void onListSelection(int index) {
		// TODO Auto-generated method stub
		
	}
}
