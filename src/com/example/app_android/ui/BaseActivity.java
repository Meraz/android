package com.example.app_android.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.example.app_android.R;

public class BaseActivity extends Activity {
	
	protected String mTitle = "About";
	protected String mMessage = "This dialog will show general information about the app. TODO - Add bragging rights";

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.layout.activity_about_window, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.about_info) {
        	Builder alert = new AlertDialog.Builder(this);
        	alert.setTitle(mTitle);
        	alert.setMessage(mMessage);
        	alert.setPositiveButton("OK",null);
        	alert.show();
        }
        return super.onOptionsItemSelected(item);
    }
}
