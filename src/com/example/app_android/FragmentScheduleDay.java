package com.example.app_android;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class FragmentScheduleDay extends ListFragment {
	
	private static final String TAG = "ScheduleFragment";
	private Communicator listener = null;
	AdapterScheduleHelper scheduleHelper;
	private String[] dates;

	public interface Communicator {
		public void onListSelection(int index);
	}
	
	public void setDate(String[] datesToSet) {
		dates = datesToSet;
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int pos, long id) {
		getListView().setItemChecked(pos, true);
		listener.onListSelection(pos);
	}
		
	@Override
	public void onAttach(Activity activity) {
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onAttach()");
		super.onAttach(activity);
		
		try {
			listener = (Communicator) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnArticleSelectedListener");
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onCreate()");
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onCreate()");
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedState) {
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onActivityCreated()");
		super.onActivityCreated(savedState);
		scheduleHelper = new AdapterScheduleHelper(getActivity().getApplicationContext());
		String[] t1 = scheduleHelper.readStartTime2(dates[0], dates[1]);
		String[] t2 = scheduleHelper.readEndTime2(dates[0], dates[1]);
		String[] t3 = scheduleHelper.readMoment2(dates[0], dates[1]);
		String[] t4 = scheduleHelper.readRoom2(dates[0], dates[1]);
				
		//Add all items to customized adapter
		AdapterScheduleCustom adapter = new AdapterScheduleCustom(getActivity(), t1, t2, t3, t4);
	
		getListView().getChildCount();
		setListAdapter(adapter);
		getListView().setChoiceMode(ListView.CHOICE_MODE_NONE);
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
	public void onDestroyView() {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onDestroyView()");
		super.onDestroyView();
	}
}