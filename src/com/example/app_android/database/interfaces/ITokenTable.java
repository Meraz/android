package com.example.app_android.database.interfaces;

import com.example.app_android.database.DBException;
import com.example.app_android.database.NoResultFoundDBException;
import com.example.app_android.database.NoRowsAffectedDBException;

public interface ITokenTable {
	
	/*
	 * 
	 */
	
	/*
	 * Different transactionflags, depending on what stage the app is on updating the flags
	 */
	enum TransactionFlag {
		Success,			// Success, updated and ready to use,
		Pending,			// Currently waiting on server.
		Failed,				// Tried to update with server at least once and failed.
		Unknown				// Default value. Should only be set first time or if something goes very wrong.
	}
	
	/*
	 * Return the string value of THE token.
	 */
	public String getTokenValue() throws DBException, NoResultFoundDBException;
	
	// Returns expiredate of the token
	public long getExpireDate() throws DBException, NoResultFoundDBException;
	
	// Updates token with new tokenvalue, expiredate and sets the new transaction_flag.
	public void updateToken(String tokenValue, long expireDate, TransactionFlag transaction_flag) throws DBException, NoRowsAffectedDBException;
	
	// Update transaction_flag. If it returns 
	public void updateTransactionFlag(TransactionFlag transaction_flag) throws DBException, NoRowsAffectedDBException;
	
	// Returns current transactionFlag on the token. There can only be one
	public TransactionFlag getTransactionFlag()	throws DBException, NoResultFoundDBException;
	
	/*
	 * Used for debugging.
	 */
	public void PrintEntireToken();
}
