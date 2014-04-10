package com.example.app_android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Build;

public class MainActivity extends Activity {
	
	TextView textView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.pageContent);
        connectTask task = new connectTask();
        task.execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
 
    /*
     * AsyncTask for connecting to server and print response in log
     */
    public class connectTask extends AsyncTask<Void, Void, Void> {
    	ProgressDialog mProgressDialog;
    	
    	@Override
		protected void onPreExecute() {
			mProgressDialog = new ProgressDialog(MainActivity.this);
			mProgressDialog.setTitle("Connecting To Server");
			mProgressDialog.setMessage("connecting...");
			mProgressDialog.show();
			super.onPreExecute();
		}
    	
		@Override
		protected Void doInBackground(Void... params) {
			URL url;
			String inputLine = "";
			try {
				url = new URL("https://studentportal.bth.se/web/studentportal.nsf/web.xsp/ny_student");
						
				HttpsURLConnection urlCon = (HttpsURLConnection)url.openConnection();
				InputStream inStream = urlCon.getInputStream();
				BufferedReader readBuff = new BufferedReader(new InputStreamReader(inStream));
				
				while((inputLine = readBuff.readLine()) != null) {
					System.out.println(inputLine);
				}
				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			return null;
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

		
		@Override
		protected void onPostExecute(Void result) {
			mProgressDialog.dismiss();
			super.onPostExecute(result);
		}
    }

}
