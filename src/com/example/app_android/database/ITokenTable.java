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
	
	// Updates token with new tokenvalue, expiredate and sets the new transaction_flag.
	// returns the amount of rows updated. 0 if none. -1 error
	public int updateToken(String tokenValue, long expireDate, TransactionFlag transaction_flag);
	
	// Update transaction_flag
	public int updateTransactionFlag(TransactionFlag transaction_flag);
	
	// Returns current transactionFlag on the token. There can only be one
	public TransactionFlag getTransactionFlag();	
	
	public void PrintEntireToken();
}
