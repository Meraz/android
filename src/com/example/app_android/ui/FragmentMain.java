package com.example.app_android.ui;

import com.example.app_android.Cache;
import com.example.app_android.R;
import com.example.app_android.services.ServiceManager;
import com.example.app_android.util.Logger;
import com.example.app_android.util.MyBroadCastReceiver;
import com.example.app_android.util.Utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.CursorJoiner.Result;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentMain extends ListFragment implements MyBroadCastReceiver.Receiver{

	private static final String TAG = "Mainmenu";
	private static final String blekingeStudentUnionPackageName = "se.bthstudent.android.bsk";
	private static String[] mMainMenu;

	private MyBroadCastReceiver mCheckLoginReceiver;
	int mIDCheckLoginService;
	private MyBroadCastReceiver mLoginReceiver;
	int mIDLoginService;
	
	// Interface for communication between fragment and activity
	public interface InterfaceActivityMain {
		void aFunction(); // Test function for fragment to communicate with activity
	}
	
	// This is the method that is invoked when clicking a menuobject on the mainpage.
	@Override
	public void onListItemClick(ListView l, View v, int pos, long id) {
		onListSelection(pos);
	}	
	private void onListSelection(int index) {
    	Logger.VerboseLog(TAG, "::Tapped on index " + index);
    	Intent intent;
    	switch (index) {
        case 0: //New student view
          intent = new Intent(getActivity().getApplicationContext(), ActivityNewStudent.class);
          startActivity(intent);
          break;

        case 1://courses
          intent = new Intent(getActivity().getApplicationContext(), ActivityCourses.class);
          startActivity(intent);
          break;
          
        case 2: //map
        	if(Utilities.isNetworkAvailable(getActivity())) {
        		int startLocation = Cache.getDefaultMapLocation();
        		if(startLocation >= 0 && startLocation <= 1) {
					intent = new Intent(getActivity().getApplicationContext(), ActivityMap.class);
					intent.putExtra("entryID", 0);
					intent.putExtra("startPositionID", startLocation);
					intent.putExtra("room", "unknown");
					startActivity(intent);
        		}
        		else
        		showDialog(); //Starts the map activity with user input
        	}
        	else
        		Toast.makeText(getActivity(), "Missing internet connection", Toast.LENGTH_SHORT).show();
          break;
          
        case 3: //student Union
          launchApp(blekingeStudentUnionPackageName);
          break;

        default:
          break;
      }
	}
    
    private void showDialog() {
    	FragmentManager manager = getFragmentManager();
    	DialogChooseCity dialog = new DialogChooseCity();
    	dialog.show(manager, "chooseCityDialog");
    }
    
    // Attempts to start to app with the inputed packageName. If it doesn't exist it opens the apps market page
    private void launchApp(String packageName) {
    	
    	Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage(packageName);
    	if (intent != null) {
    		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
   		    startActivity(intent);
   		}
   		else  { // If the app isn't installed, send the user to the apps store page
   		    intent = new Intent(Intent.ACTION_VIEW);
  		    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
   		    intent.setData(Uri.parse("market://details?id=" + packageName));
    	    startActivity(intent);
    	}
    }
    	
	// Called when a fragment is first attached to its activity. 
	// onCreate() will be called after this
	@Override
	public void onAttach(Activity activity) {		
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onAttach()");
		super.onAttach(activity);
		
    	// Get resources from stored string array
    	// This can be found in /res/values/strings.xml
        mMainMenu = getResources().getStringArray(R.array.main_page_list);

		try {
			//mListener = (InterfaceListSelectionListener) activity; // TODO
		} 
		catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
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
		
		setListAdapter(new ArrayAdapter<String>(getActivity(), R.layout.item_main, mMainMenu));
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
		if(mLoginReceiver == null) {
			mLoginReceiver = new MyBroadCastReceiver(TAG + "_LOGIN_START", TAG + "_LOGIN_UPDATE", TAG + "_LOGIN_STOP");
			mLoginReceiver.registerCallback(this);
		}
		mLoginReceiver.registerBroadCastReceiver(getActivity());
		
		for(int i = 0; i < myBroadCastReceiver.length; i++) {
			if(myBroadCastReceiver[i] == null)
			{
				myBroadCastReceiver[i] = new MyBroadCastReceiver("FRAGMENT_MAIN_1_START"+i, "FRAGMENT_MAIN_1_UPDATE"+i, "FRAGMENT_MAIN_1_STOP"+i);
				myBroadCastReceiver[i].registerCallback(this);
			}
			myBroadCastReceiver[i].registerBroadCastReceiver(getActivity());
	    	//ServiceManager.getInstance().requestToken(getActivity().getApplicationContext(), myBroadCastReceiver[i]);
			idForLoginService =	ServiceManager.getInstance().checkIfLoginIsRequired(getActivity().getApplicationContext(), myBroadCastReceiver[i]);
		}

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
		for(int i = 0; i < myBroadCastReceiver.length; i++) {
			myBroadCastReceiver[i].unregisterBroadCastReceiver(getActivity());
		}
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

	@Override
	public void onServiceStart(Intent intent) {		
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onServiceStart()");
    	Toast.makeText(getActivity(), "[TESTCODE] Attempting to update content; key: " + intent.getIntExtra("id", -1), Toast.LENGTH_SHORT).show(); // TODO Engrish/swenglish
		
	}
	
	@Override
	public void onServiceUpdate(Intent intent) {
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onServiceUpdate()");
		// TODO Auto-generated method stub
	}
	
	@Override
	public void onServiceStop(Intent intent) {
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onServiceStop()");
		
		int id = intent.getIntExtra("id", -1);
		
		if(id == idForLoginService) {
			boolean loginRequired = intent.getBooleanExtra("loginRequired", true);
			if(loginRequired) {		
				LoginPrompt loginPrompt = new LoginPrompt(getActivity(), mLoginReceiver);
				loginPrompt.attempLogin();				
			}
			// Check with server
			// Get server 
		}
		else {		
			String errorMessageShort = intent.getStringExtra("errorMessageShort");
			Toast.makeText(getActivity(), "[TESTCODE] Update failed. " + errorMessageShort, Toast.LENGTH_LONG).show();
		}
	}	
}
