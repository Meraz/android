package com.example.app_android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FragmentNewStudent extends ListFragment{
	
	private static final String TAG = "FragmentNewStudent";

	private String mData;
		
	@Override
	public void onListItemClick(ListView l, View v, int pos, long id) {
		getListView().setItemChecked(pos, true);
		onListSelection(pos);
	}
	
    // Listener to handle interaction on the list 
    public void onListSelection(int index) {    		
    	//Create new activity
		Intent intent = new Intent(getActivity().getApplicationContext(), ActivityNewStudentContent.class);
		intent.putExtra("res", mData);
		intent.putExtra("id", index);
		startActivity(intent);	
	}

	@Override
	public void onAttach(Activity activity) {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onAttach()");
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onCreate()");
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onCreateView()");
		return super.onCreateView(inflater, container, savedInstanceState);
	}


	@Override
	public void onActivityCreated(Bundle savedState) {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onActivityCreated()");
		super.onActivityCreated(savedState);
		
		connectTask task = new connectTask();
        task.execute();
        // ShowDetails(0); // Not used as of 2014-06-02 
	}

	@Override
	public void onStart() {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onStart()");
		super.onStart();
	}

	@Override
	public void onResume() {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onResume()");
		super.onResume();
	}

	@Override
	public void onPause() {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onPause()");
		super.onPause();
	}

	@Override
	public void onStop() {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onStop()");
		super.onStop();
	}

	@Override
	public void onDetach() {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onDetach()");
		super.onDetach();
	}

	@Override
	public void onDestroy() {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onDestroy()");
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onDestroyView()");
		super.onDestroyView();
	}
		
	/*
     * AsyncTask for connecting to server and print response in log
     */
    public class connectTask extends AsyncTask<Void, Void, String> {
    	//ProgressDialog mProgressDialog;
    	
    	@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
    	// Connect to server and fetch entire json string
    	@Override
		protected String doInBackground(Void... params) {
			String result = "";
			InputStream inStream = null;
			try {
				URL url = new URL("http://194.47.131.73/database-files-and-server-script/Script/newstudent.php");				
				HttpURLConnection urlCon = (HttpURLConnection)url.openConnection();
				inStream = urlCon.getInputStream();				
			} 
			catch (MalformedURLException e) {
				e.printStackTrace();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}	
			
			// Createreader
			BufferedReader readBuff = new BufferedReader(new InputStreamReader(inStream));
			
			try {
				String inputLine = "";
				while((inputLine = readBuff.readLine()) != null) {
					result = result + inputLine;
				}
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}
		
		@Override
		protected void onPostExecute(String result) {
			mData = result;
			
			// As name says, final result from this parsing
			ArrayList<String> finalResult = new ArrayList<String>();
			
			// Parse json string for "header" TAGS
			try {
				JSONArray jsonArray = new JSONArray(result); // Create an json array from data fetched from server
				JSONObject jsonObject = null; 
				for (int i = 0; i < jsonArray.length(); i++) {
					// Get a single object in the jsonArray
					jsonObject = jsonArray.getJSONObject(i);
					
					// put all entries with "header" tag in a array as we do not know how many there is.
					finalResult.add(jsonObject.getString("header")); 
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}			
			
			// Create string array, from parsed data, that is to represent the menu
			String[] lmenuArray = (String[]) finalResult.toArray(new String[finalResult.size()]);			
			
			// Set menu
			setListAdapter(new ArrayAdapter<String>(getActivity(), R.layout.item_main, lmenuArray));
			getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);			
			
			super.onPostExecute(result);
		}
    }
}
