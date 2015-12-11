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

/**
 * 
 */
package com.ibm.cics.minibank.trans;

import java.util.ArrayList;

import com.ibm.cics.minibank.AOR.util.AORDBUtil;
import com.ibm.cics.minibank.common.util.IConstants;
import com.ibm.cics.minibank.AOR.util.AORPropertiesUtil;
import com.ibm.cics.server.Channel;
import com.ibm.cics.server.Task;

/**
 * Bank transaction base class
 * It implements basic methods common to all transaction types
 */
public abstract class Transaction implements ITransaction {

	public void doTransaction(ITransaction tran) {
		Task task = Task.getTask();
		Channel channel = task.getCurrentChannel();
		if (channel != null) {
			tran.transactionLogic(channel);
		} else {
			System.out.println("There is no Current Channel");
		}
	}

	/**
	 * Get balance of an account
	 */
	protected double getAccountBalance(String acctNum) {
		String balance = "0";
		
		String sqlCmd = "SELECT "
				+ AORPropertiesUtil.getPropertiesUtil().getFieldAcctBalance()
				+ " FROM "
				+ AORPropertiesUtil.getPropertiesUtil().getTableAccount()
				+ " WHERE "
				+ AORPropertiesUtil.getPropertiesUtil().getFieldAcctNummber()
				+ "='" + acctNum + "'";
		ArrayList<String> queryList = AORDBUtil.getDBUtilInstance().execQuerySQL(sqlCmd);
		if ( queryList.size() > 0 ) {
			String record = queryList.get(0);
			if ( record.contains(IConstants.DATA_FIELD_SPLITTER) ) {
				String[] items = record.split(IConstants.DATA_FIELD_SPLITTER);
				balance = items[0];
			} else {
				balance = record;
			}
		}
		
		double value = (new Double(balance)).doubleValue(); 
		return value;
	}

	/**
	 * Update an account balance
	 */
	protected int setAccountBalance(String acctNum, double newBalance) {

		String sqlCmd = "UPDATE "
				+ AORPropertiesUtil.getPropertiesUtil().getTableAccount()
				+ " SET "
				+ AORPropertiesUtil.getPropertiesUtil().getFieldAcctBalance()
				+ "='" + newBalance + "' WHERE "
				+ AORPropertiesUtil.getPropertiesUtil().getFieldAcctNummber()
				+ "='" + acctNum + "'";
		int numUpd = AORDBUtil.getDBUtilInstance().execUpdateSQL(sqlCmd);
		return numUpd;
	}

	/**
	 * Add a transaction record into transaction history table
	 */
	protected int addTranHistRecord(String tranName, String acctNum, float amount, String txTime) {
		int numUpd = 0;
		String sqlCmd = "INSERT INTO " + AORPropertiesUtil.getPropertiesUtil().getTableTranHist() + "("
				+ AORPropertiesUtil.getPropertiesUtil().getFieldHistTranName() + ", "
				+ AORPropertiesUtil.getPropertiesUtil().getFieldHistAcctNum() + ", "
				+ AORPropertiesUtil.getPropertiesUtil().getFieldHistAmount() + ", "
				+ AORPropertiesUtil.getPropertiesUtil().getFieldHistTime()
				+ ") VALUES("
				+ "'" + tranName + "', "
				+ "'" + acctNum + "', "
				+ amount + ", "
				+ "'" + txTime + "'"
				+ ")";
		numUpd = AORDBUtil.getDBUtilInstance().execUpdateSQL(sqlCmd);
		return numUpd;
	}

}
