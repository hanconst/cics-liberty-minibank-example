/*
 Copyright IBM Corporation 2014

 LICENSE: Apache License
          Version 2.0, January 2004
          http://www.apache.org/licenses/

 The following code is sample code created by IBM Corporation.
 This sample code is not part of any standard IBM product and
 is provided to you solely for the purpose of assisting you in
 the development of your applications.  The code is provided
 'as is', without warranty or condition of any kind.  IBM shall
 not be liable for any damages arising out of your use of the
 sample code, even if IBM has been advised of the possibility
 of such damages.
*/

package com.ibm.cics.minibank.util;

import java.util.ResourceBundle;

import com.ibm.cics.minibank.util.WORPropertiesUtil;

/**
 * Utility class to read the properties 
 */
public class WORPropertiesUtil {
	private static WORPropertiesUtil propertyUtil = null;
	private ResourceBundle propertyBundle = ResourceBundle
			.getBundle("properties/MinibankWOR");

	private boolean linkToAOR = false;
	private boolean linkToLocal = false;
	private String progCreateUser = "";
	private String progQueryUser = "";
	private String progCreateAcct = "";
	private String progQueryAcct = "";
	private String progDeposit = "";
	private String progWithdraw = "";
	private String progTransfer = "";
	private String tableRequestHistory = "";
	private String fieldRequest ="";
	private String fieldTranstime = "";
	 
	
	public static WORPropertiesUtil getPropertiesUtil() {
		if ( propertyUtil == null ) {
			propertyUtil = new WORPropertiesUtil();
		}
		return propertyUtil;
	}

	public boolean isLinkToAOR() {
		return linkToAOR;
	}
	
	public boolean isLinkToLocal() {
		return linkToLocal;
	}

	public String getProgCreateUser() {
		return progCreateUser;
	}

	public String getProgQueryUser() {
		return progQueryUser;
	}

	public String getProgCreateAcct() {
		return progCreateAcct;
	}

	public String getProgQueryAcct() {
		return progQueryAcct;
	}

	public String getProgDeposit() {
		return progDeposit;
	}

	public String getProgWithdraw() {
		return progWithdraw;
	}

	public String getProgTransfer() {
		return progTransfer;
	}
	
	public String getTableRequesthistory() {
		return tableRequestHistory;
	}
	
	 
	public String getFieldRequest() {
		return fieldRequest;
	}
	
	public String getFieldTranstime() {
		return fieldTranstime;
	}

	private WORPropertiesUtil() {
		// TODO Auto-generated constructor stub
		linkToAOR = (new Boolean(getProperty("LinkToAOR"))).booleanValue();
		linkToLocal = (new Boolean(getProperty("LinkToLocal"))).booleanValue();
		progCreateUser = getProperty("PROG_CREATE_USER");
		progQueryUser = getProperty("PROG_QUERY_USER");
		progCreateAcct = getProperty("PROG_CREATE_ACCT");
		progQueryAcct = getProperty("PROG_QUERY_ACCT");
		progDeposit = getProperty("PROG_DEPOSIT");
		progWithdraw = getProperty("PROG_WITHDRAW");
		progTransfer = getProperty("PROG_TRANSFER");
		tableRequestHistory=getProperty("TABLE_REQHISTORY");
		fieldRequest = getProperty("FIELD_REQHIS_REQUEST");
		fieldTranstime = getProperty("FIELD_REQHIS_TRANSTIME");		
	}
	
	
	protected String getProperty(String key) {
		return propertyBundle.getString(key);
	}
}
