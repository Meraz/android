package com.example.app_android;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ScheduleActivity extends FragmentActivity implements ScheduleFragment.Communicator, ActionBar.TabListener{

	public static String[] mScheduleArray;
	private static final String TAG = "ScheduleActivity";
	private ActionBar actionBar;
	MyScheduleHelperAdapter mMySchemaHelper;
	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
		
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get resources from stored string array
        mScheduleArray = new String[]{"J1610", "C230"};
        Fragment mScheduleFragment = new ScheduleFragment();
        setContentView(R.layout.schedule_main); 
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
 
        // For each of the sections in the app, add a tab to the action bar.
        actionBar.addTab(actionBar.newTab().setText("Day").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("Week").setTabListener(this));
        //mMySchemaHelper = new MyScheduleHelperAdapter(this);
        //mMySchemaHelper.readAllEvent();
    }
    
    public void getRoomLocation(View view) {
    	TextView rowTextView;
    	String text;
    	LinearLayout row = (LinearLayout) view.getParent();
    	rowTextView = (TextView) row.getChildAt(2);
    	//test = (TextView) findViewById(child.getId());
    	text = (String) rowTextView.getText();
    	String[] nameAndId = text.split(" ");
    	
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
		// TODO Auto-generated method stub
		Intent intent = new Intent(getApplicationContext(), FullMapActivity.class);
		intent.putExtra("cityId", 0); // 0 -> Karlskrona
		intent.putExtra("Room", "unknown");
		startActivity(intent);
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		if (tab.getPosition() == 0) {
			ScheduleFragment simpleListFragment = new ScheduleFragment();
		    getFragmentManager().beginTransaction().replace(R.id.main_page_container, simpleListFragment).commit();
		}	else {
		    ScheduleFragment androidversionlist = new ScheduleFragment();
			getFragmentManager().beginTransaction().replace(R.id.main_page_container, androidversionlist).commit();
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
