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

import com.opensymphony.xwork2.ActionSupport;

/**
 * Action class to show manu page
 */
public class MenuPageAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	public String toCatalog() {
		return SUCCESS;
	}
}
