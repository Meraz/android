package com.example.app_android;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ScheduleActivity extends FragmentActivity implements ScheduleDayFragment.Communicator, ActionBar.TabListener{

	public static String[] mScheduleArray;
	private static final String TAG = "ScheduleActivity";
	private ActionBar actionBar;
	private TextView date;
	private Calendar displayCal;
	private int tabId;
	MyScheduleHelperAdapter mMySchemaHelper;
	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
	private final static boolean verbose = true;
		
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Fragment mScheduleFragment = new ScheduleDayFragment();
        setContentView(R.layout.schedule_main); 
        //Get resources from stored string array
        date = (TextView) findViewById(R.id.scheduleDate);
        displayCal = Calendar.getInstance(); 
        Date displayDate = new Date(displayCal.getTimeInMillis());
        if(tabId == 0) {
        	
        } else {
        	SimpleDateFormat df = new SimpleDateFormat("w");
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
    	//test = (TextView) findViewById(child.getId());
    	text = (String) rowTextView.getText();
    	String[] nameAndId = text.split(" ");
    	Intent intent = new Intent(getApplicationContext(), FullMapActivity.class);
    	intent.putExtra("cityId", -1); 
    	intent.putExtra("Room", nameAndId[2]);
    	startActivity(intent);
    }
    
    public void next(View view) {
    	String startDate;
		String endDate;
    	if(tabId == 0) {
    		displayCal.set(displayCal.DATE, (displayCal.get(displayCal.DATE)+1));
    		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date displayDate = new Date(displayCal.getTimeInMillis());
            SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
            date.setText(df2.format(displayDate));
            startDate = df.format(displayDate);
            endDate = df.format(displayDate);
			ScheduleDayFragment dayFrag = new ScheduleDayFragment();
			dayFrag.setDate(new String[] {startDate, endDate});
		    getFragmentManager().beginTransaction().replace(R.id.main_page_container, dayFrag).commit();
    	}
    	else {
    		displayCal.set(displayCal.DATE, (displayCal.get(displayCal.DATE)+1));    		
    		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    		Date displayDate = new Date(displayCal.getTimeInMillis());
            SimpleDateFormat df2 = new SimpleDateFormat("w");
            date.setText("Vecka "+df2.format(displayDate));
            startDate = df.format(displayDate);
            displayCal.set(displayCal.DAY_OF_WEEK, 7);
            displayDate = new Date(displayCal.getTimeInMillis());
            endDate = df.format(displayDate);
			ScheduleDayFragment dayFrag = new ScheduleDayFragment();
			dayFrag.setDate(new String[] {startDate, endDate});
			getFragmentManager().beginTransaction().replace(R.id.main_page_container, dayFrag).commit();
    	}
    }
    
    public void prev(View view) {
    	String startDate;
		String endDate;
    	if(tabId == 0) {
    		displayCal.set(displayCal.DATE, (displayCal.get(displayCal.DATE)-1));
    		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date displayDate = new Date(displayCal.getTimeInMillis());
            SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
            date.setText(df2.format(displayDate));
            startDate = df.format(displayDate);
            endDate = df.format(displayDate);
			ScheduleDayFragment dayFrag = new ScheduleDayFragment();
			dayFrag.setDate(new String[] {startDate, endDate});
		    getFragmentManager().beginTransaction().replace(R.id.main_page_container, dayFrag).commit();
    	}
    	else {
    		displayCal.set(displayCal.WEEK_OF_YEAR, (displayCal.get(displayCal.WEEK_OF_YEAR)-1));
    		
    		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    		Date displayDate = new Date(displayCal.getTimeInMillis());
            SimpleDateFormat df2 = new SimpleDateFormat("w");
            date.setText("Vecka "+df2.format(displayDate));
            endDate = df.format(displayDate);
            displayCal.set(displayCal.DAY_OF_WEEK, 1);
            displayDate = new Date(displayCal.getTimeInMillis());
            startDate = df.format(displayDate);
            displayCal.set(displayCal.DAY_OF_WEEK, 7);
			ScheduleDayFragment dayFrag = new ScheduleDayFragment();
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
		// TODO Auto-generated method stub
		String startDate;
		String endDate;
		if (tab.getPosition() == 0) {
			tabId = 0;
    		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date displayDate = new Date(displayCal.getTimeInMillis());
            SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
            date.setText(df2.format(displayDate));
            startDate = df.format(displayDate);
            endDate = df.format(displayDate);
			ScheduleDayFragment dayFrag = new ScheduleDayFragment();
			dayFrag.setDate(new String[] {startDate, endDate});
		    getFragmentManager().beginTransaction().replace(R.id.main_page_container, dayFrag).commit();
		}	else {
			tabId = 1;
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date displayDate = new Date(displayCal.getTimeInMillis());
            SimpleDateFormat df2 = new SimpleDateFormat("w");
            date.setText("Vecka "+df2.format(displayDate));
            startDate = df.format(displayDate);
            displayCal.set(displayCal.DAY_OF_WEEK, 7);
            displayDate = new Date(displayCal.getTimeInMillis());
            endDate = df.format(displayDate);
			ScheduleDayFragment dayFrag = new ScheduleDayFragment();
			dayFrag.setDate(new String[] {startDate, endDate});
			getFragmentManager().beginTransaction().replace(R.id.main_page_container, dayFrag).commit();
		}
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
