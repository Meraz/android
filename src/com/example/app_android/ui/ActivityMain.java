package com.example.app_android.ui;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.view.View;
import android.widget.Toast;


import com.example.app_android.R;
import com.example.app_android.database.DatabaseManager;
import com.example.app_android.database.ITokenTable;
import com.example.app_android.services.ServiceManager;
import com.example.app_android.ui.LoginPrompt.LoginPromptCallback;
import com.example.app_android.util.Logger;
import com.example.app_android.util.MyBroadCastReceiver;
import com.example.app_android.util.MyBroadCastReceiver.Receiver;
import com.example.app_android.util.Utilities;

public class ActivityMain extends BaseActivity implements Receiver, LoginPromptCallback  {

	MenuItem syncActionItem;	

	private static final String TAG = "Main";
	private MyBroadCastReceiver mCheckLoginReceiver;
	int mIDCheckLoginService;
	private MyBroadCastReceiver mLoginReceiver;
	int mIDLoginService;
	/*
	 * This is the first function that is executed for this application.
	 * Specified in the file 'AndroidManifest.xml"
	 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onCreate()");
    	super.onCreate(savedInstanceState);
    	
        // Sets the content specified in the file in res/layout/activity_main.xml
        // This also specifies which fragment(s) to activate
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
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onResume()");
		
		// Working testcode. Should only need to be moved to another file later on
		if(mLoginReceiver == null) {
			mLoginReceiver = new MyBroadCastReceiver(TAG + "_LOGIN_START", TAG + "_LOGIN_UPDATE", TAG + "_LOGIN_STOP");
			mLoginReceiver.registerCallback(this);
		}
		mLoginReceiver.registerBroadCastReceiver(this);
		
		if(mCheckLoginReceiver == null) {
			mCheckLoginReceiver = new MyBroadCastReceiver(TAG + "_CHECK_LOGIN_START", TAG + "_CHECK_LOGIN_UPDATE", TAG + "_CHECK_LOGIN_STOP");
			mCheckLoginReceiver.registerCallback(this);
		} 
		
		mCheckLoginReceiver.registerBroadCastReceiver(this);
		super.onResume();
		super.onStart();
	}

	@Override
	protected void onStop() {
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onStop()");
		
		mLoginReceiver.unregisterBroadCastReceiver(this);
		mCheckLoginReceiver.unregisterBroadCastReceiver(this);
		
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


	public void startSyncIcon() {
		syncActionItem.setVisible(true);
		syncActionItem.setActionView(R.layout.item_action_sync_indicator);
	}

	public void stopSyncIcon() {
		 syncActionItem.setActionView(null); // stop
	}
	
	public void onButtonClicked(View view) {
		Intent intent;
		
		switch (view.getId()) {
		
		case R.id.newStudentButton:
			intent = new Intent(getApplicationContext(), ActivityNewStudent.class);
	        startActivity(intent);
			break;
			
		case R.id.coursesButton:
			intent = new Intent(getApplicationContext(), ActivityCourses.class);
	        startActivity(intent);
			break;
			
		case R.id.mapButton:
			if(Utilities.isNetworkAvailable(this)) {
        		int startLocation = -1; //TODO - Get saved startlocation and use it instead of bringing up the dialog every time
        		if(startLocation >= 0 && startLocation <= 1) {
					intent = new Intent(getApplicationContext(), ActivityMap.class);
					intent.putExtra("entryID", 0);
					intent.putExtra("startPositionID", startLocation);
					intent.putExtra("room", "unknown");
					startActivity(intent);
        		}
        		else
        		showDialog(); //Starts the map activity with user input
        	}
        	else
        		Toast.makeText(getApplicationContext(), "Missing internet connection", Toast.LENGTH_SHORT).show();
			break;
			
		case R.id.studentUnionButton:
			intent = new Intent(getApplicationContext(), ActivityStudentUnion.class);
	        startActivity(intent);
			break;
			
		case R.id.imageButton5:
			attemptLogin();
			break;
			
		case R.id.imageButton6:
			attemptLogout();
			break;
		}
	}
	
	private void showDialog() {
		FragmentManager manager = getFragmentManager();
		DialogChooseCity dialog = new DialogChooseCity();
		dialog.show(manager, "chooseCityDialog");
	}
	
	@Override
	public void onWorkerStart(Intent intent) {		
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onServiceStart()");
		
		int id = intent.getIntExtra("id", -1);
		
		if(id == mIDLoginService) {
			//Toast.makeText(getActivity(), "[TESTCODE] This should be replaced by a loading bar." , Toast.LENGTH_SHORT).show(); 
		}    	
	}	
	@Override
	public void onWorkerUpdate(Intent intent) {
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onServiceUpdate()");
		// TODO Auto-generated method stub
	}
	
	@Override
	public void onWorkerStop(Intent intent) {
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onServiceStop()");
		
		int id = intent.getIntExtra("id", -1);
		
		if(id == mIDCheckLoginService) {
			boolean loginRequired = intent.getBooleanExtra("loginRequired", true);
			if(loginRequired) {		
				LoginPrompt loginPrompt = new LoginPrompt(this, mLoginReceiver, this);
				loginPrompt.attempLogin();				
			}
			else{
				String message = intent.getStringExtra("message");
				Toast.makeText(this, "[TESTCODE] " + message , Toast.LENGTH_SHORT).show(); 
				stopSyncIcon();
				syncActionItem.setVisible(false);
			}
			
			// Check with server
			// Get server 
		}		
		else if(id == mIDLoginService) { // TODO hardcoded
			boolean success = intent.getBooleanExtra("success", true); // Always works if nothing else is said
			if(success)
				Toast.makeText(this, "[TESTCODE] Du är nu inloggad!!" , Toast.LENGTH_SHORT).show(); 
			else {
				String errorMessageShort = intent.getStringExtra("errorMessageShort");
				Toast.makeText(this, "[TESTCODE] Failed inlog. " + errorMessageShort, Toast.LENGTH_SHORT).show(); 
			}
			stopSyncIcon();
			syncActionItem.setVisible(false);
		}		
	}
	
	@Override
	public void onLoginButtonPressed(int workerID) {
		mIDLoginService = workerID;
	}	
	
	// TODO remove debug code
	private void attemptLogin() {
		// Check if login is required. This is only test code. Should be moved to correct place in future
		mIDCheckLoginService =	ServiceManager.getInstance().checkIfLoginIsRequired(this.getApplicationContext(), mCheckLoginReceiver);
		startSyncIcon();
	}
	
	// TODO remove debug code
	public void attemptLogout() {
		ITokenTable a = DatabaseManager.getInstance().getTokenTable();
		a.updateToken("test", 0, ITokenTable.TransactionFlag.Success);
	}	
}
