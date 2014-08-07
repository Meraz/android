package com.example.app_android.database;

public class NoRowsAffectedDBException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Parameterless Constructor
    public NoRowsAffectedDBException() {}

    // Constructor that accepts a message
    public NoRowsAffectedDBException(String message)
    {
       super(message);
    }
}
