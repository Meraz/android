package com.example.app_android.database;

public class NoResultFoundDBException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Parameterless Constructor
    public NoResultFoundDBException() {}

    // Constructor that accepts a message
    public NoResultFoundDBException(String message)
    {
       super(message);
    }
}
