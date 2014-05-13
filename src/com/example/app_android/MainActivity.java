package com.example.app_android;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.example.app_android.MainPageFragment.ListSelectionListener;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.util.Log;

public class MainActivity extends Activity implements ListSelectionListener {
	
	public static String[] mMainPageArray;
	private static final String TAG = "MainActivity";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get resources from stored string array
        mMainPageArray = getResources().getStringArray(R.array.main_page_list);
        
        setContentView(R.layout.activity_main); 	
    }
    

    @Override
	protected void onDestroy() {
		Log.i(TAG, getClass().getSimpleName() + ":entered onDestroy()");
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		Log.i(TAG, getClass().getSimpleName() + ":entered onPause()");
		super.onPause();
	}

	@Override
	protected void onRestart() {
		Log.i(TAG, getClass().getSimpleName() + ":entered onRestart()");
		super.onRestart();
	}

	@Override
	protected void onResume() {
		Log.i(TAG, getClass().getSimpleName() + ":entered onResume()");
		super.onResume();
	}

	@Override
	protected void onStart() {
		Log.i(TAG, getClass().getSimpleName() + ":entered onStart()");
		super.onStart();
	}

	@Override
	protected void onStop() {
		Log.i(TAG, getClass().getSimpleName() + ":entered onStop()");
		super.onStop();
	}
     
    @Override
	public void onListSelection(int index) {
    	System.out.println(index);
    	switch (index) {
		case 0:
			Intent intent = new Intent(getApplicationContext(), NewStudentActivity.class);
			startActivity(intent);
			break;
		case 1:
			Intent intent1 = new Intent(getApplicationContext(), ScheduleActivity.class);
			startActivity(intent1);
			break;
		case 2:
			Intent intent2 = new Intent(getApplicationContext(), MyCoursesAndProgramActivity.class);
			startActivity(intent2);
			break;
		case 4:
			showDialog();
			break;

		default:
			break;
		}	
	}
    
    public void showDialog() {
    	FragmentManager manager = getFragmentManager();
    	ChooseCityDialog dialog = new ChooseCityDialog();
    	dialog.show(manager, "chooseCidyDialog");
    }
}
