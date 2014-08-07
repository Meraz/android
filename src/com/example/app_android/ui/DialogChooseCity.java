package com.example.app_android.ui;

import com.example.app_android.R;
import com.example.app_android.util.Utilities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class DialogChooseCity extends DialogFragment {	
	
	private static final String TAG = "CityDialog";
	private String mClassName;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {	
		mClassName = getClass().getSimpleName();
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":onCreateDialog()");}
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.choose_city_dialog);
		builder.setItems(
				R.array.cities_dialog, new DialogInterface.OnClickListener() {			
					@Override
					public void onClick(DialogInterface dialog, int option) {
						if(Utilities.verbose) {Log.v(TAG, mClassName + ":informStart()");} // TODO Does this work?
						assert option <= 0 && option >= 2;
						
						//Start the map activity
						Intent intent = new Intent(getActivity().getApplicationContext(), ActivityMap.class);
						intent.putExtra("entryID", 0);
						intent.putExtra("startPositionID", option);
						intent.putExtra("room", "unknown");
						startActivity(intent);
					}
				});
		Dialog dialog = builder.create();
		return dialog;
	}
}