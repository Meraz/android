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
        Intent intent = new Intent(this, SchemaUpdateService.class);
        intent.putExtra("URL", "https://se.timeedit.net/web/bth/db1/sched1/s.csv?tab=5&object=dv2544&type=root&startdate=20140101&enddate=20140620&p=0.m%2C2.w");
        startService(intent);
        setContentView(R.layout.activity_main);
        
    }


    private final static boolean verbose = true;

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

    @Override
	public void onListSelection(int index) {
    	System.out.println(index); // Debug ?
    	Intent intent;
    	switch (index) {
        case 0:
          intent = new Intent(getApplicationContext(), NewStudentActivity.class);
          startActivity(intent);
          break;

        case 1:
          intent = new Intent(getApplicationContext(), ScheduleActivity.class);
          startActivity(intent);
          break;

        case 2:
          intent = new Intent(getApplicationContext(), MyCoursesAndProgramActivity.class);
          startActivity(intent);
          break;
          
        case 3:
            intent = new Intent(getApplicationContext(), StudentUnionActivity.class);
            startActivity(intent);
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
    	dialog.show(manager, "chooseCityDialog");
    }
}
