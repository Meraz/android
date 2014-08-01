package com.example.app_android.ui;


import com.example.app_android.R;
import com.example.app_android.ui.newstudent.ActivityChecklist;
import com.example.app_android.ui.newstudent.ActivityCourseMaterial;
import com.example.app_android.ui.newstudent.ActivityStudentPortal;
import com.example.app_android.ui.newstudent.ActivityStudentCentre;
import com.example.app_android.ui.newstudent.ActivityResidence;
import com.example.app_android.ui.newstudent.ActivityStudentUnion;
import com.example.app_android.util.Logger;

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

	// Interface for communication between fragment and activity
	public interface NewStudentListener {
			
	}
	
	private static final String TAG = "MainMenu";
	private String[] mMenu;
	
	@SuppressWarnings("unused")
	private NewStudentListener mActivity; // TODO this shoulde be used in the future. Therefore @SuppressWarnings

	@Override
	public void onListItemClick(ListView l, View v, int pos, long id) {
		getListView().setItemChecked(pos, true);
		
		onListSelection(pos);
	}
	
    // Listener to handle interaction on the list 
    private void onListSelection(int index) {   
    	Logger.VerboseLog(TAG, "::Tapped on index " + index);
    	
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
