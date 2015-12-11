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

package com.ibm.cics.minibank.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.ibm.cics.minibank.common.util.IConstants;
import com.ibm.cics.minibank.entity.Account;
import com.ibm.cics.minibank.util.TransUtil;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Action class for create account, query account
 */
public class AccountManagementAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private Account account;

	public String toCreateAccount() {
		return SUCCESS;
	}

	public String doCreateAccount() {
		String message = null;
		boolean inputCorrect = true;

		try {
			Double.parseDouble(account.getBalance());
			inputCorrect = true;
		} catch (NumberFormatException e) {
			inputCorrect = false;
			message = "The balance is not a number. Fail to create account.";
		}
		
		if ( inputCorrect ) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String changeTime = formatter.format(new Date());
			
			// Put the create user transaction data into HashMap to construct channel/container later
			HashMap<String, String> containerData = new HashMap<String, String>();
			containerData.put(IConstants.ACCT_NUMBER, account.getAccountNumber());
			containerData.put(IConstants.ACCT_CUST_ID, account.getCustomerID());
			containerData.put(IConstants.ACCT_BALANCE, account.getBalance());
			containerData.put(IConstants.ACCT_CHANGE, changeTime);
			// invoke the delegator method in the TransUtil object
			String[] result = TransUtil.getTranUtil().createAccount(containerData);
			if ( (new Integer(result[0])).intValue() > 0 ) {
				// success
				this.addActionMessage(result[1]);
			} else {
				// got problems
				this.addActionError(result[1]);
			}
		} else {
			// input incorrect parameters
			this.addActionError(message);
		}
		
		
		return SUCCESS;
	}

	public String toQueryAccount() {
		return SUCCESS;
	}

	public String doQueryAccount() {
		HashMap<String, String> containerData = new HashMap<String, String>();
		containerData.put(IConstants.ACCT_NUMBER, account.getAccountNumber());
		// invoke the delegator method in the TransUtil object
		account = TransUtil.getTranUtil().queryAccount(containerData);
		return SUCCESS;
	}


	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
}
