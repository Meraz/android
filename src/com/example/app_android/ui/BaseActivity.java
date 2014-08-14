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
	protected String mActionBarTitle;	
	
	protected String mInfoBoxTitle = "About";		
	protected String mInfoBoxMessage = "This dialog will show general information about the app. TODO - Add bragging rights";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
		Assert.assertNotNull("You must specify mTag in " + getClass().getSimpleName(), mTag);
		Assert.assertNotNull("You must specify mClassName in " + getClass().getSimpleName(), mClassName);

		if(Utilities.verbose) {Log.v(mTag, mClassName + ":onCreate()");}
        super.onCreate(savedInstanceState);
    }    

    @Override
	protected void onDestroy() {
		if(Utilities.verbose) {Log.v(mTag, mClassName + ":onDestroy()");}
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		if(Utilities.verbose) {Log.v(mTag, mClassName + ":onPause()");}
		super.onPause();
	}

	@Override
	protected void onRestart() {
		if(Utilities.verbose) {Log.v(mTag, mClassName + ":onRestart()");}
		super.onRestart();
	}

	@Override
	protected void onResume() {
		if(Utilities.verbose) {Log.v(mTag, mClassName + ":onResume()");}
		super.onResume();
		
		getActionBar().setTitle(mActionBarTitle);   
	}

	@Override
	protected void onStart() {
		if(Utilities.verbose) {Log.v(mTag, mClassName + ":onStart()");}
		super.onStart();
	}

	@Override
	protected void onStop() {
		if(Utilities.verbose) {Log.v(mTag, mClassName + ":onStop()");}
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
        	alert.setTitle(mInfoBoxTitle);
        	alert.setMessage(mInfoBoxMessage);
        	alert.setPositiveButton("OK",null);
        	alert.show();
        }
        return super.onOptionsItemSelected(item);
    }
    
    
}
