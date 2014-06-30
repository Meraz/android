package com.example.app_android;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
public class ActivityNewStudentContent extends Activity {
	
	private final static String TAG = "NewStudentContentActivity";
	private String json;
	private int id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onCreate()");
		super.onCreate(savedInstanceState);
		
		// WTF? this just returns if in landscape mode... (?)
		/*if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
	    	finish();
	    	return;
	    }*/
		
		Bundle bundle = getIntent().getExtras();
        json = bundle.getString("res");
        id = bundle.getInt("id");
        String items = "";
        try {
			JSONArray arr = new JSONArray(json);
			JSONObject jsonObject = arr.getJSONObject(id);
			
			JSONArray test = jsonObject.getJSONArray("items");
			
			int limit = test.length();
			
			for(int j = 0; j < limit; j++) {
				items = items + "- " + jsonObject.getJSONArray("items").getString(j) + "\n \n";
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setContentView(R.layout.activity_studentcontent);
		TextView view = (TextView) findViewById(R.id.detailsText);
		view.setText(items);
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
}
