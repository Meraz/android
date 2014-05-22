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
import android.widget.Toast;

public class ScheduleActivity extends Activity implements ScheduleFragment.Communicator{

	public static String[] mScheduleArray;
	private static final String TAG = "ScheduleActivity";
	private ActionBar actionBar;
	MyScheduleHelperAdapter mMySchemaHelper;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get resources from stored string array
        mScheduleArray = new String[]{"J1610", "C230"};
        Fragment mScheduleFragment = new ScheduleFragment();
        setContentView(R.layout.schedule_main); 
        actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.Tab tab0 = actionBar.newTab();
        tab0.setText("Day");
        tab0.setTabListener(new MyTabsListener(mScheduleFragment, getApplicationContext()));
        ActionBar.Tab tab1 = actionBar.newTab();
        tab1.setText("Week");
        tab1.setTabListener(new MyTabsListener(mScheduleFragment, getApplicationContext()));
        
        actionBar.addTab(tab0);
        actionBar.addTab(tab1);
        mMySchemaHelper = new MyScheduleHelperAdapter(this);
        mMySchemaHelper.readAllEvent();
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
	
}
