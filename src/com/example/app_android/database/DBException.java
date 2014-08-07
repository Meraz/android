package com.example.app_android.database;

public class DBException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Parameterless Constructor
    public DBException() {}

    // Constructor that accepts a message
    public DBException(String message)
    {
       super(message);
    }
}
