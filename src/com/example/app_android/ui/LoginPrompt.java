package com.example.app_android.ui;

import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.app_android.R;
import com.example.app_android.services.ServiceManager;
import com.example.app_android.util.Utilities;
import com.example.app_android.util.MyBroadcastReceiver;

public class LoginPrompt {
	
	public interface LoginPromptCallback {
		public void onLoginButtonPressed(int workerID);
	}
	
	private static final String TAG = "Login";
	private String mClassName;
	
	private Context mContext;
	private MyBroadcastReceiver mBroadCastReceiver;
	private LoginPromptCallback mCallback;	

	public LoginPrompt(Context context, MyBroadcastReceiver broadCastReceiver, LoginPromptCallback callback) {
		mClassName = getClass().getSimpleName();
		mContext = context;
		mBroadCastReceiver = broadCastReceiver;
		mCallback = callback;
	}
	
	public void attempLogin() {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":attempLogin()");}
		LayoutInflater layoutInflater = LayoutInflater.from(mContext);				

		View view = layoutInflater.inflate(R.layout.item_loginprompt, null);
		
		final AlertDialog.Builder alert = new AlertDialog.Builder(mContext);

		alert.setView(view);
		
        final EditText userInput = (EditText) view.findViewById(R.id.username);
        final EditText password = (EditText) view.findViewById(R.id.password);

		alert.setPositiveButton(mContext.getString(R.string.login_prompt_login), new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
			
			 String user_text = (userInput.getText()).toString();
			 String user_password = (password.getText()).toString();
			 
			 int id = ServiceManager.getInstance().requestToken(mContext.getApplicationContext(), mBroadCastReceiver, user_text, user_password);
			 mCallback.onLoginButtonPressed(id);
		  }
		});		
		alert.show();
	}
}
