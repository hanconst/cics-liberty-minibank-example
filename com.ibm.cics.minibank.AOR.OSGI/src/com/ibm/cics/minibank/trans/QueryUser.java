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

import java.util.ArrayList;

import com.ibm.cics.minibank.common.util.ContainerUtil;
import com.ibm.cics.minibank.AOR.util.AORDBUtil;
import com.ibm.cics.minibank.common.util.IConstants;
import com.ibm.cics.minibank.AOR.util.AORPropertiesUtil;
import com.ibm.cics.server.Channel;
import com.ibm.cics.server.CommAreaHolder;

/**
 * OSGi program to query user
 */
public class QueryUser extends Transaction implements ITransaction {

	/**
	 * @param args
	 */
	public static void main(CommAreaHolder cah) {
		System.out.println("QueryUser is being invoked...");
		QueryUser txQryUsr = new QueryUser();
		txQryUsr.doTransaction(txQryUsr);
		System.out.println("QueryUser returns...");
	}

	@Override
	public void transactionLogic(Channel channel) {
		// get transaction data from container
		String customerID = ContainerUtil.getContainerData(channel, IConstants.CUST_ID);
		
		// construct SQL command to query from customer table
		String sqlCmd = "SELECT * FROM " + AORPropertiesUtil.getPropertiesUtil().getTableCustomer() 
				+ " WHERE " + AORPropertiesUtil.getPropertiesUtil().getFieldCustID() + "='" + customerID + "'";
		// query info from the customer table
		ArrayList<String> queryList = AORDBUtil.getDBUtilInstance().execQuerySQL(sqlCmd);

		String userInfo = "not available";
		if ( queryList.size() > 0 ) {
			userInfo = queryList.get(0);
		}
		// put the customer info into the return container
		ContainerUtil.putContainerData(channel, IConstants.CUST_INFO, userInfo);
		
		// construct SQL command to query the customer related accounts info
		sqlCmd = "SELECT * FROM " + AORPropertiesUtil.getPropertiesUtil().getTableAccount()
				+ " WHERE " + AORPropertiesUtil.getPropertiesUtil().getFieldAcctCustID() + "='" + customerID + "'";
		// query info from the account table
		queryList = AORDBUtil.getDBUtilInstance().execQuerySQL(sqlCmd);
		// one user may have multiple accounts. Put all the account info into multiple return containers
		String acctRecord = null;
		for ( int i=0; i<queryList.size(); i++ ) {
			acctRecord = queryList.get(i);
			ContainerUtil.putContainerData(channel, IConstants.ACCT_LIST + i, acctRecord);
		}
		
	}

}
