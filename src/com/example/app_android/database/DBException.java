package com.example.app_android.database;

public class DBException extends Exception{

	/**
	 * This is a custom made error. All errors from the database module should be returned with this. 
	 * This because all error management should be done by the database. 
	 * If an error occurs in database and exceptions has to be thrown up to the application.
	 * Then these exceptions should be repackaged as an DBException and re-thrown with additional informatiopn.
	 * One exception to this rule is "NoRowsAffectedDBException". Which is thrown when everything works but no result is given.
	 * 
	 * Always log errors
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
