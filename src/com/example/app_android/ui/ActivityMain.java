package com.example.app_android.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.app_android.R;
import com.example.app_android.database.DatabaseManager;
import com.example.app_android.services.ServiceManager;
import com.example.app_android.ui.FragmentMain.InterfaceActivityMain;
import com.example.app_android.util.Logger;

public class ActivityMain extends BaseActivity implements InterfaceActivityMain{
	private static final String TAG = "Main";

	MenuItem syncActionItem;	
	/*
	 * This is the first function that is executed for this application.
	 * Specified in the file 'AndroidManifest.xml"
	 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onCreate()");
    	super.onCreate(savedInstanceState);
    	ServiceManager.initialize(getApplicationContext());
    	DatabaseManager.initialize(getApplicationContext());
    	
        // Sets the content specified in the file in res/layout/activity_main.xml
        // This also specifies which fragment to active
        setContentView(R.layout.activity_main);
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
	    inflater.inflate(R.layout.menu_sync, menu);
	    syncActionItem = menu.findItem(R.id.courses_action_sync);
	    syncActionItem.setVisible(false);
	    return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void startSyncIcon() {
		syncActionItem.setVisible(true);
		syncActionItem.setActionView(R.layout.item_action_sync_indicator);
	}

	@Override
	public void stopSyncIcon() {
		 syncActionItem.setActionView(null); // stop
	}
}
