package com.ibm.cicsdev.minibank.backend.util;

public interface IResponseCode {
	
	public static final int CODE_OPERATION_SUCCESS=203;
	public static final int CODE_OPERATION_FAILED=204;
	public static final int CODE_ACCOUNT_NOT_EXIST=205;
	public static final int CODE_SOURCE_ACCOUNT_NOT_EXISTS=206;
	public static final int CODE_TARGET_ACCOUNT_NOT_EXISTS=207;
	public static final int CODE_ACCOUNT_EXISTS=208;
	public static final int CODE_USER_NOT_EXIST=209;
	public static final int CODE_USER_EXISTS=210;
	public static final int CODE_INSUFFIENT_BALANCE=211;
	
	public static final String MESSAGE_OPERATION_SUCCESS="operation success";
	public static final String MESSAGE_OPERATION_FAILED="operation failed";
	public static final String MESSAGE_ACCOUNT_NOT_EXIST="account doesn't exist";
	public static final String MESSAGE_SOURCE_ACCOUNT_NOT_EXIST="sourceAccount doesn't exist";
	public static final String MESSAGE_TARGET_ACCOUNT_NOT_EXIST="targetAccount doesn't exist";
	public static final String MESSAGE_ACCOUNT_EXISTS="acount already exists";
	public static final String MESSAGE_USER_NOT_EXIST="user doesn't exist";
	public static final String MESSAGE_USER_EXISTS="user already exists";
	public static final String MESSAGE_INSUFFIENT_BALANCE="balance is insuffient";

	
	public static final int JPA_EXCEPTION=301;

}
