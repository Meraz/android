package com.example.app_android.ui;

import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.app_android.R;
import com.example.app_android.services.ServiceManager;
import com.example.app_android.util.Logger;
import com.example.app_android.util.MyBroadCastReceiver;

public class LoginPrompt {
	
	public interface LoginPromptCallback {
		public void onLoginButtonPressed(int workerID);
	}
	
	private static final String TAG = "Login";

	private Context mContext;
	private MyBroadCastReceiver mBroadCastReceiver;
	private LoginPromptCallback mCallback;
	

	public LoginPrompt(Context context, MyBroadCastReceiver broadCastReceiver, LoginPromptCallback callback) {
		mContext = context;
		mBroadCastReceiver = broadCastReceiver;
		mCallback = callback;
	}
	
	public void attempLogin() {
		
		LayoutInflater layoutInflater = LayoutInflater.from(mContext);				

		View view = layoutInflater.inflate(R.layout.item_loginprompt, null);
		
		final AlertDialog.Builder alert = new AlertDialog.Builder(mContext);

		alert.setView(view);
		
        final EditText userInput = (EditText) view.findViewById(R.id.username);
        final EditText password = (EditText) view.findViewById(R.id.password);

		alert.setPositiveButton("Logga in", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
			
			 String user_text = (userInput.getText()).toString();
			 String user_password = (password.getText()).toString();
			 Logger.VerboseLog(TAG, user_text);
			 Logger.VerboseLog(TAG, user_password);
			 
			 int id = ServiceManager.getInstance().requestToken(mContext.getApplicationContext(), mBroadCastReceiver, user_text, user_password);
			 mCallback.onLoginButtonPressed(id);
		  }
		});		
		alert.show();
	}
}
