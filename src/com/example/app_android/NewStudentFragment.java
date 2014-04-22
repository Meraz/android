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
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class NewStudentFragment extends ListFragment{
	
	private static final String TAG = "NewStudentFragment";
	private ListSelectionListener mListener = null;

	public static String[] mNewStudentArray;
	
	public interface ListSelectionListener {
		public void onListSelection(int index);
	}
		
	@Override
	public void onListItemClick(ListView l, View v, int pos, long id) {
		getListView().setItemChecked(pos, true);
		mListener.onListSelection(pos);
	}

	@Override
	public void onAttach(Activity activity) {
		Log.i(TAG, getClass().getSimpleName() + ":entered onAttach()");
		super.onAttach(activity);
		
		try {
			mListener = (ListSelectionListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()+ " must implement OnArticleSelectedListener");
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");
		return super.onCreateView(inflater, container, savedInstanceState);
	}


	@Override
	public void onActivityCreated(Bundle savedState) {
		Log.i(TAG, getClass().getSimpleName() + ":entered onActivityCreated()");
		super.onActivityCreated(savedState);
		connectTask task = new connectTask();
        task.execute();
	}

	@Override
	public void onStart() {
		Log.i(TAG, getClass().getSimpleName() + ":entered onStart()");
		super.onStart();
	}

	@Override
	public void onResume() {
		Log.i(TAG, getClass().getSimpleName() + ":entered onResume()");
		super.onResume();
	}

	@Override
	public void onPause() {
		Log.i(TAG, getClass().getSimpleName() + ":entered onPause()");
		super.onPause();
	}

	@Override
	public void onStop() {
		Log.i(TAG, getClass().getSimpleName() + ":entered onStop()");
		super.onStop();
	}

	@Override
	public void onDetach() {
		Log.i(TAG, getClass().getSimpleName() + ":entered onDetach()");
		super.onDetach();
	}

	@Override
	public void onDestroy() {
		Log.i(TAG, getClass().getSimpleName() + ":entered onDestroy()");
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		Log.i(TAG, getClass().getSimpleName() + ":entered onDestroyView()");
		super.onDestroyView();
	}
	
	/*
     * AsyncTask for connecting to server and print response in log
     */
    public class connectTask extends AsyncTask<Void, Void, Void> {
    	ProgressDialog mProgressDialog;
    	
    	@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
    	//Connect to server and handle response
		@Override
		protected Void doInBackground(Void... params) {
			URL url;
			String inputLine = "";
			String result = "";
			ArrayList<String> finalResult = new ArrayList<String>();
			try {
				url = new URL("http://bth.djazz.se/sp/?p=checklista");
				
				HttpURLConnection urlCon = (HttpURLConnection)url.openConnection();
				InputStream inStream = urlCon.getInputStream();
				BufferedReader readBuff = new BufferedReader(new InputStreamReader(inStream));
				//Print all result in log
				while((inputLine = readBuff.readLine()) != null) {
					//System.out.println(inputLine);
					result = result + inputLine;
				}			
				JSONArray jsonArray = new JSONArray(result);
				//System.out.println(jsonArray);
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					String items = "";
					for(int j = 0; j<jsonObject.getJSONArray("items").length(); j++) {
						items = items + "- " + jsonObject.getJSONArray("items").getString(j) + "\n \n";
					}
					finalResult.add(jsonObject.getString("header")+" \n \n" + items);
				}
				mNewStudentArray = (String[]) finalResult.toArray(new String[finalResult.size()]);
				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			return null;
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

		
		@Override
		protected void onPostExecute(Void result) {
			
			setListAdapter(new ArrayAdapter<String>(getActivity(), R.layout.main_page_item, mNewStudentArray));
			getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			super.onPostExecute(result);
		}
    }

}
