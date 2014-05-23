package com.example.app_android;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
public class NewStudentContentActivity extends Activity {
	
	private final static String TAG = "NewStudentContentActivity";
	private String json;
	private int id;
	private final static boolean verbose = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
	    	finish();
	    	return;
	    }
		Bundle bundle = getIntent().getExtras();
        json = bundle.getString("res");
        id = bundle.getInt("id");
        String items = "";
        try {
			JSONArray arr = new JSONArray(json);
			JSONObject jsonObject = arr.getJSONObject(id);
			
			for(int j = 0; j<jsonObject.getJSONArray("items").length(); j++) {
				items = items + "- " + jsonObject.getJSONArray("items").getString(j) + "\n \n";
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setContentView(R.layout.student_content);
		TextView view = (TextView) findViewById(R.id.detailsText);
		view.setText(items);
	}

    @Override
	protected void onDestroy() {
    	if (verbose)
    		Log.v(TAG, getClass().getSimpleName() + ":entered onDestroy()");
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		if (verbose)
    		Log.v(TAG, getClass().getSimpleName() + ":entered onPause()");
		super.onPause();
	}

	@Override
	protected void onRestart() {
		if (verbose)
    		Log.v(TAG, getClass().getSimpleName() + ":entered onRestart()");
		super.onRestart();
	}

	@Override
	protected void onResume() {
		if (verbose)
    		Log.v(TAG, getClass().getSimpleName() + ":entered onResume()");
		super.onResume();
	}

	@Override
	protected void onStart() {
		if (verbose)
    		Log.v(TAG, getClass().getSimpleName() + ":entered onStart()");
		super.onStart();
	}

	@Override
	protected void onStop() {
		if (verbose)
    		Log.v(TAG, getClass().getSimpleName() + ":entered onStop()");
		super.onStop();
	}
}
