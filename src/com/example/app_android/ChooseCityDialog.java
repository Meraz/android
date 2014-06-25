package com.example.app_android;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

public class ChooseCityDialog extends DialogFragment {	
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Välj Stad");
		builder.setItems(
				R.array.cities_dialog, new DialogInterface.OnClickListener() {			
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(getActivity().getApplicationContext(), FullMapActivity.class);
						intent.putExtra("cityId", which);
						intent.putExtra("Room", "unknown");
						startActivity(intent);
					}
				});
		Dialog dialog = builder.create();
		return dialog;		
	}	
}