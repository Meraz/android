package com.example.app_android.database.interfaces;

public interface ITextTable {

	enum TextIdentifier {
		text1
	}
	
	public void getText(TextIdentifier textIdentifier);
	
	public void setText(TextIdentifier textIdentifier, String text, int text_hash);
	
	
}
