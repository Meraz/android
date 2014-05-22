package com.example.app_android;

import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.widget.Toast;

class MyTabsListener implements TabListener {
    public Fragment fragment;
    public Context context;

    public MyTabsListener(Fragment fragment, Context context) {
                this.fragment = fragment;
                this.context = context;

    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
                Toast.makeText(context, "Reselected!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
                Toast.makeText(context, "Selected!", Toast.LENGTH_SHORT).show();
                ft.replace(R.id.main_page_container, fragment);
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
                Toast.makeText(context, "Unselected!", Toast.LENGTH_SHORT).show();
                ft.remove(fragment);
    }
    
}
