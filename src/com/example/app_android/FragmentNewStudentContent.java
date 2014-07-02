package com.example.app_android;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class FragmentNewStudentContent extends Fragment{
	
	private static final String TAG = "FragmentNewStudentContent";
	
	public void setText(String item) {
		TextView view = (TextView) getView().findViewById(R.id.detailsText);
		view.setText(item);
	}
	
	
	// Called when a fragment is first attached to its activity. 
	// onCreate() will be called after this
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
		
		View view = inflater.inflate(R.layout.activity_studentcontent,container, false);
		return view;	
		//return super.onCreateView(inflater, container, savedInstanceState);
	}
	

	@Override
	public void onActivityCreated(Bundle savedState) {		
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onActivityCreated()");
		super.onActivityCreated(savedState);
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
}
