package com.ibm.cicsdev.minibank.frontend.util;

public interface IConstants {

	public final String URL="http://localhost:9381/com.ibm.cicsdev.minibank.backend/rest";
	public static final String ACCOUNTEVENT="/account/";
	public static final String USEREVENT="/user/";
	public static final String TRANSEVENT="/trans/";
	
	public static final String TRANSFER="transfer";
	public static final String WITHDRAW="withdraw";
	public static final String DEPOSIT="deposit";


	


//	public final String URL="http://winmvs2c.hursley.ibm.com:12349/com.ibm.cics.minibank_jaxrs.war/rest";
	
	//ResponseCode
	public static final int CODE_OPERATION_SUCCESS=203;
	public static final int CODE_OPERATION_FAILED=204;
	public static final int CODE_ACCOUNT_NOT_EXIST=205;
	public static final int CODE_SOURCE_ACCOUNT_NOT_EXISTS=206;
	public static final int CODE_TARGET_ACCOUNT_NOT_EXISTS=207;
	public static final int CODE_ACCOUNT_EXISTS=208;
	public static final int CODE_USER_NOT_EXIST=209;
	public static final int CODE_USER_EXISTS=210;
	public static final int CODE_INSUFFIENT_BALANCE=211;
	
	public static final int CODE_JPA_EXCEPTION=301;

	
	//ResponseMessage
	public static final String MESSAGE_CREATE_ACCOUNT_SUCCESS="Create Account successful of AccountID ";
	public static final String MESSAGE_CREATE_USER_SUCCESS="Create User successful of UserID ";
	public static final String MESSAGE_TRANSFER_SUCCESS="Transfer successfully, the balance now is ";
	public static final String MESSAGE_DEPOSIT_SUCCESS="Deposit successfully, the balance now is ";
	public static final String MESSAGE_WITHDRAW_SUCCESS="Withdraw successfully, the balance now is ";

	public static final String MESSAGE_UNKNOWN_FAILED="Operation Filed due to some unknown reason...";
	public static final String MESSAGE_ACCOUNT_NOT_EXIST="Account doesn't exist for input Account ";
	public static final String MESSAGE_ACCOUNT_EXISTS="Create Account failed because it already exists for input Account ";
	public static final String MESSAGE_SOURCE_ACCOUNT_NOT_EXISTS="Account not exists for input Source Account ";
	public static final String MESSAGE_TARGET_ACCOUNT_NOT_EXISTS="Account not exists for input Target Account ";
	public static final String MESSAGE_USER_NOT_EXIST="User doesn't exist for input User ";
	public static final String MESSAGE_USER_EXISTS="Create User failed because it already exists for input User ";
	public static final String MESSAGE_INSUFFIENT_BALANCE="Balance insuffient for the input Account, balance available now is ";
	public static final String MESSAGE_JPA_EXCEPTION="Operation failed due to JPA exceptions...";


}
