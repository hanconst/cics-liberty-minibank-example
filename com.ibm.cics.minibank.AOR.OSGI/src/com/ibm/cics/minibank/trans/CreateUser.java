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
 * OSGi program to create user
 */
public class CreateUser extends Transaction implements ITransaction {

	public static void main(CommAreaHolder cah) {
		System.out.println("\nCreateUser is being invoked...");
		CreateUser txCreUsr = new CreateUser();
		txCreUsr.doTransaction(txCreUsr);
		System.out.println("CreateUser returns...");
	}

	@Override
	public void transactionLogic(Channel channel) {
		// get transaction data from containers
		String userId = ContainerUtil.getContainerData(channel, IConstants.CUST_ID);
		String name = ContainerUtil.getContainerData(channel, IConstants.CUST_NAME);
		String gender = ContainerUtil.getContainerData(channel, IConstants.CUST_GENDER);
		String age = ContainerUtil.getContainerData(channel, IConstants.CUST_AGE);
		String address = ContainerUtil.getContainerData(channel, IConstants.CUST_ADDR);

		// construct the SQL command
		String sqlCmd = "INSERT INTO " + AORPropertiesUtil.getPropertiesUtil().getTableCustomer() + "("
				+ AORPropertiesUtil.getPropertiesUtil().getFieldCustID() + ", "
				+ AORPropertiesUtil.getPropertiesUtil().getFieldCustName() + ", "
				+ AORPropertiesUtil.getPropertiesUtil().getFieldCustGender() + ", "
				+ AORPropertiesUtil.getPropertiesUtil().getFieldCustAge() + ", "
				+ AORPropertiesUtil.getPropertiesUtil().getFieldCustAddress()
				+ ") VALUES("
				+ "'" + userId + "', "
				+ "'" + name + "', "
				+ "'" + gender.charAt(0) + "', "
				+ "'" + age + "', "
				+ "'" + address + "'"
				+ ")";
		// update the database table
		int numUpd = AORDBUtil.getDBUtilInstance().execUpdateSQL(sqlCmd);
		// put the transaction status to return container
		String tranCode = (new Integer(numUpd)).toString();
		ContainerUtil.putContainerData(channel, IConstants.TRAN_CODE, tranCode);
		// put the detail transaction message to return container
		String message = null;
		if ( numUpd > 0 ) {
			message = "Create user " + userId + " is successful";
		} else {
			message = "Create user " + userId + " is failed";
		}
		ContainerUtil.putContainerData(channel, IConstants.TRAN_MSG, message);

	}

}
