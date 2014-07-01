package com.example.app_android;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("SimpleDateFormat") //Warns about not being given a locale. This is irrelevant since this app is intended for use in Sweden only
public class ActivitySchedule extends FragmentActivity implements FragmentScheduleDay.Communicator, ActionBar.TabListener{

	public static String[] mScheduleArray;
	private static final String TAG = "ScheduleActivity";
	private TextView date;
	private Calendar displayCal;
	private int tabId;
	AdapterScheduleHelper mMySchemaHelper;
	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
	private final static boolean verbose = true;
		
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Fragment mScheduleFragment = new ScheduleDayFragment();
        setContentView(R.layout.activity_schedule); 
        //Get resources from stored string array
        date = (TextView) findViewById(R.id.scheduleDate);
        displayCal = Calendar.getInstance(); 
        Date displayDate = new Date(displayCal.getTimeInMillis());
        if(tabId == 0) {
        	// Empty ? 
        } else {
        	SimpleDateFormat df = new SimpleDateFormat("w");	//Week
            date.setText(df.format(displayDate));
        }   
        
        //c.set(Calendar.WEEK_OF_YEAR, (c.get(Calendar.WEEK_OF_YEAR)+1));
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
 
        // For each of the sections in the app, add a tab to the action bar.
        actionBar.addTab(actionBar.newTab().setText("Day").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("Week").setTabListener(this));       
    }
    
    public void getRoomLocation(View view) {
    	TextView rowTextView;
    	String text;
    	LinearLayout row = (LinearLayout) view.getParent();
    	rowTextView = (TextView) row.getChildAt(2);
    	text = (String) rowTextView.getText();
    	String[] nameAndId = text.split(" ");
    	Intent intent = new Intent(getApplicationContext(), ActivityMap.class);
    	intent.putExtra("entryID", 1);
    	intent.putExtra("startPositionID", -1);
    	intent.putExtra("room", nameAndId[2]);
    	startActivity(intent);
    }
    
    public void next(View view) {
    	String startDate;
		String endDate;
    	if(tabId == 0) {
    		displayCal.set(Calendar.DATE, (displayCal.get(Calendar.DATE)+1));
    		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date displayDate = new Date(displayCal.getTimeInMillis());
            SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
            date.setText(df2.format(displayDate));
            startDate = df.format(displayDate);
            endDate = df.format(displayDate);
			FragmentScheduleDay dayFrag = new FragmentScheduleDay();
			dayFrag.setDate(new String[] {startDate, endDate});
		    getFragmentManager().beginTransaction().replace(R.id.main_page_container, dayFrag).commit();
    	}
    	else {
    		displayCal.set(Calendar.DATE, (displayCal.get(Calendar.DATE)+1));    		
    		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    		Date displayDate = new Date(displayCal.getTimeInMillis());
            SimpleDateFormat df2 = new SimpleDateFormat("w");
            date.setText("Week "+df2.format(displayDate));
            startDate = df.format(displayDate);
            displayCal.set(Calendar.DAY_OF_WEEK, 7);
            displayDate = new Date(displayCal.getTimeInMillis());
            endDate = df.format(displayDate);
			FragmentScheduleDay dayFrag = new FragmentScheduleDay();
			dayFrag.setDate(new String[] {startDate, endDate});
			getFragmentManager().beginTransaction().replace(R.id.main_page_container, dayFrag).commit();
    	}
    }
    
    public void prev(View view) {
    	String startDate;
		String endDate;
    	if(tabId == 0) {
    		displayCal.set(Calendar.DATE, (displayCal.get(Calendar.DATE)-1));
    		
    		SimpleDateFormat 	df 			= new SimpleDateFormat("yyyy-MM-dd");
            Date 				displayDate = new Date(displayCal.getTimeInMillis());
            SimpleDateFormat 	df2 		= new SimpleDateFormat("yyyy-MM-dd");
            FragmentScheduleDay dayFrag 	= new FragmentScheduleDay();
            date.setText(df2.format(displayDate));
            startDate = df.format(displayDate);
            endDate = df.format(displayDate);
			dayFrag.setDate(new String[] {startDate, endDate});
		    getFragmentManager().beginTransaction().replace(R.id.main_page_container, dayFrag).commit();
    	}
    	else {
    		displayCal.set(Calendar.WEEK_OF_YEAR, (displayCal.get(Calendar.WEEK_OF_YEAR)-1));
    		
    		SimpleDateFormat 	df 			= new SimpleDateFormat("yyyy-MM-dd");
    		Date 				displayDate = new Date(displayCal.getTimeInMillis());
            SimpleDateFormat 	df2 		= new SimpleDateFormat("w");
            FragmentScheduleDay dayFrag 	= new FragmentScheduleDay();
            
            date.setText("Vecka "+df2.format(displayDate));
            endDate 	= df.format(displayDate);            
            displayCal.set(Calendar.DAY_OF_WEEK, 1);
            displayDate = new Date(displayCal.getTimeInMillis());
            startDate = df.format(displayDate);            
            displayCal.set(Calendar.DAY_OF_WEEK, 7);            			
			dayFrag.setDate(new String[] {startDate, endDate});
			getFragmentManager().beginTransaction().replace(R.id.main_page_container, dayFrag).commit();
    	}
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
		// TODO Auto-generated method stub
		//Intent intent = new Intent(getApplicationContext(), FullMapActivity.class);
		//intent.putExtra("cityId", 0); // 0 -> Karlskrona
		//intent.putExtra("Room", "unknown");
		//startActivity(intent);
	}
		
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		String startDate;
		String endDate;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        Date displayDate = new Date(displayCal.getTimeInMillis());
        startDate = df.format(displayDate);
        endDate = df.format(displayDate);
        FragmentScheduleDay dayFrag = new FragmentScheduleDay();
        dayFrag.setDate(new String[] {startDate, endDate});
        
		if (tab.getPosition() == 0) {
			tabId = 0;
            date.setText(df2.format(displayDate));
		}	else {
			tabId = 1;
            date.setText("Week "+df2.format(displayDate));
            displayCal.set(Calendar.DAY_OF_WEEK, 7);
		}
		getFragmentManager().beginTransaction().replace(R.id.main_page_container, dayFrag).commit();
	}
	@Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
            getActionBar().setSelectedNavigationItem(savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
        }
    }
 
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar().getSelectedNavigationIndex());
    }    

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
	}
	
}
