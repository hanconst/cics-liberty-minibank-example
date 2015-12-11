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

import com.ibm.cics.server.Channel;

/**
 * interface for all transactions.
 * Different transaction must override the abstract method transactionLogic to perform its own business logic
 */
public interface ITransaction {
	
	/**
	 * Different transaction must override transactionLogic to perform its own business logic
	 */
	public void transactionLogic(Channel channel);

}
