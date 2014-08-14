package com.example.app_android.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.app_android.R;
import com.example.app_android.util.Utilities;

public class ActivityStudentUnion extends BaseActivity {
	
	private static final String TAG = "MainMenu";
	
	private static final String blekingeStudentUnionPackageName = "se.bthstudent.android.bsk"; // TODO, move it to xml or something

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	mClassName = getClass().getSimpleName();    
    	mTag = TAG;
    	super.onCreate(savedInstanceState);
    	mInfoBoxTitle = getString(R.string.infobox_title_studentunion);
		mInfoBoxMessage = getString(R.string.infobox_text_studentunion);
		
        setContentView(R.layout.activity_studentunion);
    }
	
    // Temporary step to have original function untouched and still have the functionality set in xml 	// TODO 
	public void launchApp(View view) {
    	launchApp(blekingeStudentUnionPackageName);
    }
	
    // Attempts to start to app with the inputed packageName. If it doesn't exist it opens the apps market page
    private void launchApp(String packageName) {
    	if(Utilities.verbose) {Log.v(mTag, mClassName + ":launchApp()");}
    	Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
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
}
