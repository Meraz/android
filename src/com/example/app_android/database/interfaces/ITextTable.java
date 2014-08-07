package com.example.app_android.database.interfaces;

import com.example.app_android.database.DBException;

public interface ITextTable {

	enum TextIdentifier {
		text1
	}
	
	/*
	 * Get text
	 * 
	 */
	public String getText(TextIdentifier textIdentifier);
	
	/*
	 * Get text
	 * 
	 */
	public void setText(TextIdentifier textIdentifier, String text, int text_hash) throws DBException;
	
	
}
