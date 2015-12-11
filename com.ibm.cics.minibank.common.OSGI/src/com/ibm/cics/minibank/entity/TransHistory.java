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

package com.ibm.cics.minibank.entity;

import com.ibm.cics.minibank.common.util.IConstants;

/**
 * Class for the transaction history records.
 * Used for data exchanging between jsp forms and actions
 */
public class TransHistory {
	private String transName;
	private String accountNum;
	private String transAmount;
	private String transTime;
	
	public TransHistory() {
	}
	
	/**
	 * Use the passed in string to construct TransHistory object
	 */
	public TransHistory(String histRecord) {
		String[] parms = histRecord.split(IConstants.DATA_FIELD_SPLITTER);
		if ( parms.length >= 4 ) {
			transName = parms[0];
			accountNum = parms[1];
			transAmount = parms[2];
			transTime = parms[3];
		}
	}

	public String getTransName() {
		return transName;
	}

	public void setTransName(String transName) {
		this.transName = transName;
	}

	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	public String getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(String transAmount) {
		this.transAmount = transAmount;
	}

	public String getTransTime() {
		return transTime;
	}

	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}

}
