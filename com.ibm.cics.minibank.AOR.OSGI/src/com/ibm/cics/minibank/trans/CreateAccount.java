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

package com.ibm.cics.minibank.trans;

import com.ibm.cics.minibank.common.util.ContainerUtil;
import com.ibm.cics.minibank.AOR.util.AORDBUtil;
import com.ibm.cics.minibank.common.util.IConstants;
import com.ibm.cics.minibank.AOR.util.AORPropertiesUtil;
import com.ibm.cics.server.Channel;
import com.ibm.cics.server.CommAreaHolder;

/**
 * OSGi program to create account
 */
public class CreateAccount extends Transaction implements ITransaction {

	/**
	 * @param args
	 */
	public static void main(CommAreaHolder cah) {
		System.out.println("CreateAccount is being invoked...");
		CreateAccount txCreAcct = new CreateAccount();
		txCreAcct.doTransaction(txCreAcct);
		System.out.println("CreateAccount returns...");
	}

	@Override
	public void transactionLogic(Channel channel) {
		// get transaction data from containers
		String acctNum = ContainerUtil.getContainerData(channel, IConstants.ACCT_NUMBER);
		String acctCustID = ContainerUtil.getContainerData(channel, IConstants.ACCT_CUST_ID);
		String balance = ContainerUtil.getContainerData(channel, IConstants.ACCT_BALANCE);
		String changeTime = ContainerUtil.getContainerData(channel, IConstants.ACCT_CHANGE);
		
		// construct SQL command
		String sqlCmd = "INSERT INTO " + AORPropertiesUtil.getPropertiesUtil().getTableAccount() + "("
				+ AORPropertiesUtil.getPropertiesUtil().getFieldAcctNummber() + ", "
				+ AORPropertiesUtil.getPropertiesUtil().getFieldAcctCustID() + ", "
				+ AORPropertiesUtil.getPropertiesUtil().getFieldAcctBalance() + ", "
				+ AORPropertiesUtil.getPropertiesUtil().getFieldAcctLastChange()
				+ ") VALUES("
				+ "'" + acctNum + "', "
				+ "'" + acctCustID + "', "
				//+ "'" + balance + "', "
				+ (new Float(balance)).floatValue() + ", "
				+ "'" + changeTime + "'"
				+ ")";
		// update the database table
		int numUpd = AORDBUtil.getDBUtilInstance().execUpdateSQL(sqlCmd);
		// put the transaction status to container 
		ContainerUtil.putContainerData(channel, IConstants.TRAN_CODE, (new Integer(numUpd)).toString());
		// put the transaction detail message to container
		String message = null;
		if ( numUpd > 0 ) {
			message = "Create account " + acctNum + " is successful";
		} else {
			message = "Create account " + acctNum + " is failed";
		}
		ContainerUtil.putContainerData(channel, IConstants.TRAN_MSG, message);
	}

}
