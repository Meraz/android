package com.example.app_android.ui;


import com.example.app_android.R;
import com.example.app_android.ui.newstudent.ActivityChecklist;
import com.example.app_android.ui.newstudent.ActivityCourseMaterial;
import com.example.app_android.ui.newstudent.ActivityStudentPortal;
import com.example.app_android.ui.newstudent.ActivityStudentCentre;
import com.example.app_android.ui.newstudent.ActivityResidence;
import com.example.app_android.ui.newstudent.ActivityStudentUnion;
import com.example.app_android.util.Utilities;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FragmentNewStudent extends ListFragment{

	// Interface for communicating to the activity // TODO This might not be needed at all in the future
	public interface NewStudentListener {
			
	}
	
	private static final String TAG = "MainMenu";
	private String mClassName;
	
	private String[] mMenu;
	
	@SuppressWarnings("unused")
	private NewStudentListener mActivity; // TODO This might not be needed at all in the future
	
	@Override
	public void onAttach(Activity activity) {
		mClassName = getClass().getSimpleName();
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":onAttach()");}
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
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":onCreate()");}
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":onCreateView()");}
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedState) {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":onActivityCreated()");}
		super.onActivityCreated(savedState);
		
		setListAdapter(new ArrayAdapter<String>(getActivity(), R.layout.item_main, mMenu));
		getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	}

	@Override
	public void onStart() {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":onStart()");}
		super.onStart();
	}

	@Override
	public void onResume() {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":onResume()");}
		super.onResume();
	}

	@Override
	public void onPause() {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":onPause()");}
		super.onPause();
	}

	@Override
	public void onStop() {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":onStop()");}
		super.onStop();
	}

	@Override
	public void onDetach() {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":onDetach()");}
		super.onDetach();
	}

	@Override
	public void onDestroy() {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":onDestroy()");}
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":onDestroyView()");}
		super.onDestroyView();
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int pos, long id) {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":onListItemClick()");}
		getListView().setItemChecked(pos, true);
		
		onListSelection(pos);
	}
	
    // Listener to handle interaction on the list 
    private void onListSelection(int index) {   
    	if(Utilities.verbose) {Log.v(TAG, mClassName + ":onListSelection()");}
    	
    	// Create new activity
    	Intent intent = null;
    	switch (index) {
        case 0:
			intent = new Intent(getActivity().getApplicationContext(), ActivityChecklist.class);
			startActivity(intent);
          break;
          
        case 1:
			intent = new Intent(getActivity().getApplicationContext(), ActivityResidence.class);
			startActivity(intent);
          break;
          
        case 2:
			intent = new Intent(getActivity().getApplicationContext(), ActivityStudentPortal.class);
			startActivity(intent);
          break;

        case 3:
			intent = new Intent(getActivity().getApplicationContext(), ActivityStudentCentre.class);
			startActivity(intent);
          break;
                    
        case 4:
			intent = new Intent(getActivity().getApplicationContext(), ActivityStudentUnion.class);
			startActivity(intent);
          break;
          
        case 5:
			intent = new Intent(getActivity().getApplicationContext(), ActivityCourseMaterial.class);
			startActivity(intent);
            break;
          
        case 6:    
		//	intent = new Intent(getActivity().getApplicationContext(), ActivityMap.class);
		//	startActivity(intent);
          break;

        default:
          break;
      }
	}

}
