package com.example.app_android.database;

public interface ITokenTable {
	
	public String getToken();
	
	public int getExpireDate();
	
	// returns the row ID of the last row inserted, if this insert is successful. -1 otherwise.
	public void updateToken(String token, int expireDate, int transaction_flag);
	
	// Not tested yet
	public void updateTransactionFlag(int transaction_flag);
	
	public int getTransactionFlag();	
}
