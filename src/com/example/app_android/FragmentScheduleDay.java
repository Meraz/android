package com.example.app_android;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class FragmentScheduleDay extends ListFragment {
	
	private static final String TAG = "ScheduleFragment";
	private Communicator mListener = null;
	AdapterScheduleHelper mMySchemaHelper;
	private String[] mDates;
	private final static boolean verbose = true;

	public interface Communicator {
		public void onListSelection(int index);
	}
	
	public void setDate(String[] c) {
		mDates = c;
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int pos, long id) {
		getListView().setItemChecked(pos, true);
		mListener.onListSelection(pos);
	}
		
	@Override
	public void onAttach(Activity activity) {
		if (verbose)
    		Log.v(TAG, getClass().getSimpleName() + ":entered onAttach()");
		super.onAttach(activity);
		
		try {
			mListener = (Communicator) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnArticleSelectedListener");
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		if (verbose)
    		Log.v(TAG, getClass().getSimpleName() + ":entered onCreate()");
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		if (verbose)
    		Log.v(TAG, getClass().getSimpleName() + ":entered onCreate()");
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedState) {
		if (verbose)
    		Log.v(TAG, getClass().getSimpleName() + ":entered onActivityCreated()");
		super.onActivityCreated(savedState);
		mMySchemaHelper = new AdapterScheduleHelper(getActivity().getApplicationContext());
		String[] t1 = mMySchemaHelper.readStartTime2(mDates[0], mDates[1]);
		String[] t2 = mMySchemaHelper.readEndTime2(mDates[0], mDates[1]);
		String[] t3 = mMySchemaHelper.readMoment2(mDates[0], mDates[1]);
		String[] t4 = mMySchemaHelper.readRoom2(mDates[0], mDates[1]);
				
		//Add all items to customized adapter
		AdapterScheduleCustom adapter = new AdapterScheduleCustom(getActivity(), t1, t2, t3, t4);
	
		getListView().getChildCount();
		setListAdapter(adapter);
		getListView().setChoiceMode(ListView.CHOICE_MODE_NONE);					
	}

	@Override
	public void onStart() {
		if (verbose)
    		Log.v(TAG, getClass().getSimpleName() + ":entered onStart()");
		super.onStart();
	}

	@Override
	public void onResume() {
		if (verbose)
    		Log.v(TAG, getClass().getSimpleName() + ":entered onResume()");
		super.onResume();
	}

	@Override
	public void onPause() {
		if (verbose)
    		Log.v(TAG, getClass().getSimpleName() + ":entered onPause()");
		super.onPause();
	}

	@Override
	public void onStop() {
		if (verbose)
    		Log.v(TAG, getClass().getSimpleName() + ":entered onStop()");
		super.onStop();
	}

	@Override
	public void onDetach() {
		if (verbose)
    		Log.v(TAG, getClass().getSimpleName() + ":entered onDetach()");
		super.onDetach();
	}

	@Override
	public void onDestroyView() {
		if (verbose)
    		Log.v(TAG, getClass().getSimpleName() + ":entered onDestroyView()");
		super.onDestroyView();
	}
}
