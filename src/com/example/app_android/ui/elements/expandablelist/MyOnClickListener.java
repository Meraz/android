package com.example.app_android.ui.elements.expandablelist;

import android.view.View;
import android.view.View.OnClickListener;

public class MyOnClickListener implements OnClickListener {

	private int mID;
	private IButtonCallback mButtonCallback;
	
	public MyOnClickListener(IButtonCallback buttonCallback, int id) {
		mID = id;
		mButtonCallback = buttonCallback;
	}

	@Override
	public void onClick(View v) {	
		if(mButtonCallback != null)
			mButtonCallback.onButtonClick(mID);
	}
}
