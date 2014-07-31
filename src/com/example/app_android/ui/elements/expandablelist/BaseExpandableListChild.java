package com.example.app_android.ui.elements.expandablelist;

import android.widget.Button;
 
public class BaseExpandableListChild {
 
    private String mName;
    private String mTag;
    private boolean mHasButton;
    private int mButtonID;
    
    
    public BaseExpandableListChild() {
    	mName = null;
    	mTag = null;
    	mHasButton = false;
    	mButtonID = -1;
    }
     
    public String getName() {
        return mName;
    }
    public void setName(String Name) {
        mName = Name;
    }
    public String getTag() {
        return mTag;
    }
    public void setTag(String Tag) {
        mTag = Tag;
    }
    
    public int getButtonID() {
    	return mButtonID;
    }
    
    public void setButtonID(int buttonID) {
    	mHasButton = true;
    	mButtonID = buttonID;
    }
    
    public void resetButton() {
    	mHasButton = false;
    	mButtonID = -1;
    }    
    
    public boolean hasButton() {
    	return mHasButton;
    }    
}