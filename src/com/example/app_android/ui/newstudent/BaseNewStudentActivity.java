package com.example.app_android.ui.newstudent;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import com.example.app_android.R;
import com.example.app_android.ui.elements.expandablelist.BaseExpandableListGroup;
import com.example.app_android.ui.elements.expandablelist.IButtonCallback;
import com.example.app_android.ui.elements.expandablelist.MyBaseExpandableListAdapter;
import com.example.app_android.util.Logger;

abstract public class BaseNewStudentActivity extends Activity implements IButtonCallback{
	
	protected static final String TAG = "NewstudentMenu";
	protected MyBaseExpandableListAdapter mExpandableListAdapter;
	protected ArrayList<BaseExpandableListGroup> mExpandableListItems;
	protected ExpandableListView mExpandableList;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onCreate()");
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_studentportal); 
        
		mExpandableList = (ExpandableListView) findViewById(R.id.ExpandableList);
		mExpandableListItems = SetStandardGroups();
    }        
    
	abstract protected ArrayList<BaseExpandableListGroup> SetStandardGroups();

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
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onPause()");
		super.onStop();
	}

	@Override
	abstract public void onButtonClick(int id);
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.layout.activity_main_action, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.main_action_info) {
        	Builder alert = new AlertDialog.Builder(this);
        	alert.setTitle("About");
        	alert.setMessage("This dialog will show general information about the app. TODO - Add bragging rights");
        	alert.setPositiveButton("OK",null);
        	alert.show();
        }
        return super.onOptionsItemSelected(item);
    }
}
