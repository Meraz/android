package com.example.app_android.database.interfaces;
import com.example.app_android.database.DBException;
import com.example.app_android.database.NoResultFoundDBException;
import com.example.app_android.database.NoRowsAffectedDBException;

public interface ITextTable {

	enum TextIdentifier {
		text1
	}
	
	/*
	 * Get already existing text in database
	 */
	public String getText(TextIdentifier textIdentifier) throws DBException, NoResultFoundDBException;
	
	/*
	 * Update specific text with new text and text_hash
	 */
	public void updateText(TextIdentifier textIdentifier, String text, int text_hash) throws DBException, NoRowsAffectedDBException;
	
	/*
	 * Get specific hash belonging to one text
	 */
	public int getTextHash(TextIdentifier textIdentifier) throws DBException, NoResultFoundDBException;	
}
