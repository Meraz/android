package com.example.app_android.ui;

import com.example.app_android.R;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FragmentCourses extends ListFragment {
	
	private static final String TAG = "FragmentCourses";
	private String mClassName;
	
	@Override
	public void onListItemClick(ListView l, View v, int pos, long id) {
		Intent intent = new Intent(getActivity().getApplicationContext(), ActivityDetailedCourse.class);
		intent.putExtra("courseCode", ((TextView)((RelativeLayout)getListView().getChildAt(pos)).getChildAt(0)).getText());	//Find the right courseCode in the course list and input it
		startActivity(intent);
	}

	@Override
	public void onAttach(Activity activity) {
		mClassName = getClass().getSimpleName();
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":onAttach()");}
		super.onAttach(activity);
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
		setListAdapter(new ArrayAdapter<String>(getActivity(), R.layout.item_course, R.id.course_button, ActivityCourses.coursesArray));
		getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	}

	@Override
	public void onStart() {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":onStart()");}
		setListAdapter(new ArrayAdapter<String>(getActivity(), R.layout.item_course, R.id.course_button, ActivityCourses.coursesArray));
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
}