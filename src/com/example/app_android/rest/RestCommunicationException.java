package com.example.app_android.rest;


public class RestCommunicationException extends Exception{

	// Default?
	private static final long serialVersionUID = 1L;
	
	// Parameterless Constructor
    public RestCommunicationException() {}

    // Constructor that accepts a message
    public RestCommunicationException(String message)
    {
       super(message);
    }
}
