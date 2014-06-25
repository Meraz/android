package com.example.app_android;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentNewStudentContent extends Fragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_studentcontent,container, false);
		return view;
	}
	
	public void setText(String item) {
		TextView view = (TextView) getView().findViewById(R.id.detailsText);
		view.setText(item);
	}
}
