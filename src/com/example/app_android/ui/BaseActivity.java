package com.example.app_android.ui;

import junit.framework.Assert;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.example.app_android.R;
import com.example.app_android.util.Utilities;

public class BaseActivity extends Activity {

	private static final String TAG = "OptionsMenu";
	
	protected String mClassName;
	protected String mTag;
	
	protected String mTitle = "About";
	protected String mInfoBoxMessage = "This dialog will show general information about the app. TODO - Add bragging rights";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
		Assert.assertNotNull("You must specify mTag in " + getClass().getSimpleName(), mTag);
		Assert.assertNotNull("You must specify mClassName in " + getClass().getSimpleName(), mClassName);

    	Utilities.VerboseLog(mTag, mClassName + ":entered onCreate()");
        super.onCreate(savedInstanceState);
    }    

    @Override
	protected void onDestroy() {
    	Utilities.VerboseLog(mTag, mClassName + ":entered onDestroy()");
		super.onDestroy();
	}

	@Override
	protected void onPause() {
    	Utilities.VerboseLog(mTag, mClassName + ":entered onPause()");
		super.onPause();
	}

	@Override
	protected void onRestart() {
    	Utilities.VerboseLog(mTag, mClassName + ":entered onRestart()");
		super.onRestart();
	}

	@Override
	protected void onResume() {
    	Utilities.VerboseLog(mTag, mClassName + ":entered onResume()");
		super.onResume();
	}

	@Override
	protected void onStart() {
    	Utilities.VerboseLog(mTag, mClassName + ":entered onStart()");
		super.onStart();
	}

	@Override
	protected void onStop() {
    	Utilities.VerboseLog(mTag, mClassName + ":entered onPause()");
		super.onStop();
	} 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if(Utilities.verbose) {Log.v(TAG, "BaseActivity" + ":onCreateOptionsMenu()");}
		
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.layout.activity_about_window, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	if(Utilities.verbose) {Log.v(TAG, getLocalClassName() + ":onOptionsItemSelected()");}
    	
        if(item.getItemId() == R.id.about_info) {
        	Builder alert = new AlertDialog.Builder(this);
        	alert.setTitle(mTitle);
        	alert.setMessage(mInfoBoxMessage);
        	alert.setPositiveButton("OK",null);
        	alert.show();
        }
        return super.onOptionsItemSelected(item);
    }
}
