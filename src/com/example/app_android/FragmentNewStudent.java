package com.example.app_android;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FragmentNewStudent extends ListFragment{

	public interface NewStudentListener {
			
	}
	
	// Interface for communication between fragment and activity
	public interface InterfaceActivityMain {
		
	}
	
	private static final String TAG = "FragmentNewStudent";
	private String[] mMenu;
	private NewStudentListener mActivity;

		
	@Override
	public void onListItemClick(ListView l, View v, int pos, long id) {
		getListView().setItemChecked(pos, true);
		onListSelection(pos);
	}
	
    // Listener to handle interaction on the list 
    private void onListSelection(int index) {    		
    	//Create new activity
		Intent intent = new Intent(getActivity().getApplicationContext(), ActivityResidence.class);
		//intent.putExtra("id", index);
		startActivity(intent);	
	}

	@Override
	public void onAttach(Activity activity) {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onAttach()");   	
		super.onAttach(activity);
    
		// Get resources from stored string array
    	// This can be found in /res/values/strings.xml
        mMenu = getResources().getStringArray(R.array.new_student_menu);        
        
        // Try to cast activity to the listener interface
		try {
			mActivity = (NewStudentListener) activity;
		} 
		// Throw an exception if failed
		catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement listener interface defined in " + getClass().getSimpleName());
		}		
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
		
		setListAdapter(new ArrayAdapter<String>(getActivity(), R.layout.item_main, mMenu));
		getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
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
