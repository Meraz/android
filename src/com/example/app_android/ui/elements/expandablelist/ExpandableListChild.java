package com.example.app_android.ui.elements.expandablelist;
 
public class ExpandableListChild {
 
    private String mText;
    private String mTag;
    ExpandableListMetaButton mMetaButton; 
    
    public ExpandableListChild() {
    	mText = null;
    	mTag = null;
    	mMetaButton = null;
    }
     
    public String getText() {
        return mText;
    }
    public void setText(String text) {
        mText = text;
    }
    public String getTag() {
        return mTag;
    }
    public void setTag(String tag) {
        mTag = tag;
    }
    
    public ExpandableListMetaButton getButton() {
    	return mMetaButton;
    }
    
    public void setMetaButton(ExpandableListMetaButton button) {
    	mMetaButton = button;
    }
}