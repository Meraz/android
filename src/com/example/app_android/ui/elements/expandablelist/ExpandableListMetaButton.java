package com.example.app_android.ui.elements.expandablelist;

import android.widget.Button;

public class ExpandableListMetaButton {
	
	private int mVisible;
	private String mText;
	private int mActionID;

	public ExpandableListMetaButton() {
		resetButton();
	}    
	
    public void resetButton() {
		mVisible = Button.INVISIBLE;
		mText = "Button";
		mActionID = -1;
    }    
    
    public void setButton(int visible, String buttonText, int actionID) {
    	mVisible = visible;
    	mText = buttonText;
    	mActionID = actionID;
    }
    
    public void setVisible(int visible) {
    	mVisible = visible;
    }
    
    public void setActionID(String buttonText) {
    	mText = buttonText;
    }
    
    public void setActionID(int actionID) {
    	mActionID = actionID;
    }    
	
    public int getVisible() {
    	return mVisible;
    }
    public String getText() {
    	return mText;
    }
    
    public int getAction() {
    	return mActionID;
    }
    
    public Button fillRealButtonWithMeta(Button button) {
    	button.setVisibility(mVisible);
    	button.setText(mText);
    	return button;
    }
}
