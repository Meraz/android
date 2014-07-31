package com.example.app_android.database;

public interface ITokenTable {
	
	enum TransactionFlag {
		Success,
		Pending,
		Failed,
		Unknown
	}
	
	// There should only be possible to have one entry of a token at any given time.
	// This returns the value of the token
	public String getTokenValue();
	
	// Returns expiredate of the token
	public long getExpireDate();
	
	// Update the entire token structure with n
	// Returns the row ID of the last row inserted, if this insert is successful. -1 otherwise.
	public int updateToken(String tokenValue, long expireDate, TransactionFlag transaction_flag);
	// TODO Should return fail/success
	
	// Update transaction_flag
	public void updateTransactionFlag(TransactionFlag transaction_flag);
	// TODO Should return fail/success
	
	// Returns current transactionFlag on the token. There can only be one
	public TransactionFlag getTransactionFlag();	
	
	public void PrintEntireToken();
}
