package com.example.app_android;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.app_android.NewStudentFragment.ListSelectionListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class NewStudentActivity extends Activity implements ListSelectionListener {
	
	public static String[] mNewStudentArray;
	
	private static final String TAG = "NewStudentActivity";
	private String jsonString;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.new_student);
    }    

    @Override
	protected void onDestroy() {
		Log.i(TAG, getClass().getSimpleName() + ":entered onDestroy()");
		super.onDestroy();
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

    //Listener to handle interaction on the list 
    @Override
	public void onListSelection(int index) {
    	
    	NewStudentContentFragment fragment = (NewStudentContentFragment) getFragmentManager().findFragmentById(R.id.newStudentContentFragment);
    	ArrayList<String> finalResult = new ArrayList<String>();
    	//Check landscape or port layout
    	if(fragment != null && fragment.isInLayout()) {
			try {
				//Load and parse JSON
				JSONArray jsonArray = new JSONArray(jsonString);
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					String items = "";
					for(int j = 0; j<jsonObject.getJSONArray("items").length(); j++) {
						items = items + "- " + jsonObject.getJSONArray("items").getString(j) + "\n \n";
					}
					finalResult.add(jsonObject.getString("header")+" \n \n" + items);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		else {
			//Create new activity
			Intent intent = new Intent(getApplicationContext(), NewStudentContentActivity.class);
			intent.putExtra("res", jsonString);
			startActivity(intent);
		}	
		
	}
}
