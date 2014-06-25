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

import com.example.app_android.InterfaceListSelectionListener;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

public class ActivityNewStudent extends Activity implements InterfaceListSelectionListener {
	
	//private String[] mNewStudentArray;
	private JSONArray mJsonArray;
	
	private static final String TAG = "NewStudentActivity";
	private String jsonString;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connectTask task = new connectTask();
        task.execute();
        setContentView(R.layout.new_student);        
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

    //Listener to handle interaction on the list 
    @Override
	public void onListSelection(int index) {    	
    	FragmentNewStudentContent fragment = (FragmentNewStudentContent) getFragmentManager().findFragmentById(R.id.newStudentContentFragment);
    	
    	//Check landscape or port layout
    	if(fragment != null && fragment.isInLayout()) {
			try {
				//Load and parse JSON
				JSONObject jsonObject = mJsonArray.getJSONObject(index);
				String items = "";
				for(int j = 0; j<jsonObject.getJSONArray("items").length(); j++) {
					items = items + "- " + jsonObject.getJSONArray("items").getString(j) + "\n \n";
				}
				fragment.setText(items);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		else {
			//Create new activity
			Intent intent = new Intent(getApplicationContext(), ActivityNewStudentContent.class);
			intent.putExtra("res", jsonString);
			intent.putExtra("id", index);
			startActivity(intent);
		}			
	}
    /*
     * AsyncTask for connecting to server and print response in log
     */
    public class connectTask extends AsyncTask<Void, Void, String[]> {
    	ProgressDialog mProgressDialog;
    	
    	@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
    	//Connect to server and handle response
		@Override
		protected String[] doInBackground(Void... params) {
			URL url;
			String inputLine = "";
			String result = "";
			String[] newStudentArray = null;
			ArrayList<String> finalResult = new ArrayList<String>();
			try {
				url = new URL("http://194.47.131.73/test.php");
				
				HttpURLConnection urlCon = (HttpURLConnection)url.openConnection();
				InputStream inStream = urlCon.getInputStream();
				BufferedReader readBuff = new BufferedReader(new InputStreamReader(inStream));
				//Print all result in log
				while((inputLine = readBuff.readLine()) != null) {
					//System.out.println(inputLine);
					result = result + inputLine;
				}			
				jsonString = result;
				mJsonArray = new JSONArray(result);
				//System.out.println(jsonArray);
				for (int i = 0; i < mJsonArray.length(); i++) {
					JSONObject jsonObject = mJsonArray.getJSONObject(i);					
					finalResult.add(jsonObject.getString("header"));					
				}
				newStudentArray = (String[]) finalResult.toArray(new String[finalResult.size()]);
				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				System.out.println(e.toString());
				e.printStackTrace();
			}			
			return newStudentArray;
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}
		
		@Override
		protected void onPostExecute(String[] result) {
			//mNewStudentArray = result;
			super.onPostExecute(result);
		}
    }
}