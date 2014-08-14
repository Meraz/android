package com.example.app_android.ui;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.app_android.R;
import com.example.app_android.database.DBException;
import com.example.app_android.database.DatabaseManager;
import com.example.app_android.database.NoRowsAffectedDBException;
import com.example.app_android.database.interfaces.ITokenTable;
import com.example.app_android.services.ServiceManager;
import com.example.app_android.ui.LoginPrompt.LoginPromptCallback;
import com.example.app_android.ui.courses_and_schedule.ActivityCourses;
import com.example.app_android.util.Utilities;
import com.example.app_android.util.MyBroadcastReceiver;
import com.example.app_android.util.MyBroadcastReceiver.Receiver;

public class ActivityMain extends BaseActivity implements Receiver, LoginPromptCallback  {
	
	private static final String TAG = "Main";

	private MyBroadcastReceiver mCheckLoginReceiver;
	int mIDCheckLoginService;
	private MyBroadcastReceiver mLoginReceiver;
	int mIDLoginService;
	
	private MenuItem mSyncActionItem;	
	
	/*
	 * This is the first function that is executed for this application.
	 * Specified in the file 'AndroidManifest.xml"
	 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	mClassName = getClass().getSimpleName();
    	mTag = TAG;
    	super.onCreate(savedInstanceState);
		mTitle = getString(R.string.infobox_title_mainmenu);
		mInfoBoxMessage = getString(R.string.infobox_text_mainmenu);
    	
        // Sets the content specified in the file in res/layout/activity_main.xml
        // This also specifies which fragment(s) to activate
        setContentView(R.layout.activity_main);
    }

	@Override
	protected void onStart() {
		super.onStart();
		
		// Working testcode. Should only need to be moved to another file later on
		if(mLoginReceiver == null) {
			mLoginReceiver = new MyBroadcastReceiver(TAG + "_LOGIN_START", TAG + "_LOGIN_UPDATE", TAG + "_LOGIN_STOP");
			mLoginReceiver.registerCallback(this);
		}
		mLoginReceiver.registerBroadCastReceiver(this);
		
		if(mCheckLoginReceiver == null) {
			mCheckLoginReceiver = new MyBroadcastReceiver(TAG + "_CHECK_LOGIN_START", TAG + "_CHECK_LOGIN_UPDATE", TAG + "_CHECK_LOGIN_STOP");
			mCheckLoginReceiver.registerCallback(this);
		} 
		
		mCheckLoginReceiver.registerBroadCastReceiver(this);
	//	super.onResume(); // TODO is this needed?
	}

	@Override
	protected void onStop() {
		super.onStop();
		
		mLoginReceiver.unregisterBroadCastReceiver(this);
		mCheckLoginReceiver.unregisterBroadCastReceiver(this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":onCreateOptionsMenu()");}
		
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.layout.menu_sync, menu);
	    mSyncActionItem = menu.findItem(R.id.courses_action_sync);
	    mSyncActionItem.setVisible(false);
	    return super.onCreateOptionsMenu(menu);
	}


	public void startSyncIcon() {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":startSyncIcon()");}
		
		mSyncActionItem.setVisible(true);
		mSyncActionItem.setActionView(R.layout.item_action_sync_indicator);
	}

	public void stopSyncIcon() {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":stopSyncIcon()");}
		
		 mSyncActionItem.setActionView(null); // stop
	}
	
	public void onButtonClicked(View view) {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":onButtonClicked()");}
		
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
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":showDialog()");}
		FragmentManager manager = getFragmentManager();
		DialogChooseCity dialog = new DialogChooseCity();
		dialog.show(manager, "chooseCityDialog");
	}
	
	@Override
	public void onWorkerStart(Intent intent) {		
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":onWorkerStart()");}
		
		int id = intent.getIntExtra("id", -1);
		
		if(id == mIDLoginService) {
			//Toast.makeText(getActivity(), "[TESTCODE] This should be replaced by a loading bar." , Toast.LENGTH_SHORT).show(); 
		}    	
	}	
	@Override
	public void onWorkerUpdate(Intent intent) {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":onWorkerUpdate()");}
	}
	
	@Override
	public void onWorkerStop(Intent intent) {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":onWorkerStop()");}
		
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
				mSyncActionItem.setVisible(false);
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
			mSyncActionItem.setVisible(false);
		}		
	}
	
	@Override
	public void onLoginButtonPressed(int workerID) {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":onLoginButtonPressed()");}
		mIDLoginService = workerID;
		startSyncIcon();
	}	
	
	// TODO Move this code to whereever you want login. atm debug code
	private void attemptLogin() {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":attemptLogin()");}
		// Check if login is required. This is only test code. Should be moved to correct place in future
		mIDCheckLoginService =	ServiceManager.getInstance().checkIfLoginIsRequired(this.getApplicationContext(), mCheckLoginReceiver);
	}
	
	// TODO Debug code to test login functionality
	public void attemptLogout() {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":attemptLogout()");}
		ITokenTable a = DatabaseManager.getInstance().getTokenTable();
		try {
			a.updateToken("test", 0, ITokenTable.TransactionFlag.Success);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoRowsAffectedDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}
