package com.ibm.cics.minibank.local.webapp.entity;

import java.util.Set;

/**
 * @author zxl
 *
 */
public class Account {
	private String accountNumber;
	private String customerID;
	private String balance;
	private String lastChangeTime;
	private Set<TransHistory> transHist;
	
	public Account(String accountNumber, String customerID, String balance,
			String lastChangeTime, Set<TransHistory> transHist) {
		super();
		this.accountNumber = accountNumber;
		this.customerID = customerID;
		this.balance = balance;
		this.lastChangeTime = lastChangeTime;
		this.transHist = transHist;
	}

	/*
	 * For queryUser method,we don't need to show transHistory details,
	 * we made another constructor for that use.
	 */
	public Account(String accountNumber, String customerID, String balance,
			String lastChangeTime) {
		super();
		this.accountNumber = accountNumber;
		this.customerID = customerID;
		this.balance = balance;
		this.lastChangeTime = lastChangeTime;
	}

	public String getAccountNumber() {
		return accountNumber;
	}
	
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getCustomerID() {
		return customerID;
	}
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getLastChangeTime() {
		return lastChangeTime;
	}
	public void setLastChangeTime(String lastChangeTime) {
		this.lastChangeTime = lastChangeTime;
	}
	public Set<TransHistory> getTransHist() {
		return transHist;
	}
	public void setTransHist(Set<TransHistory> transHist) {
		this.transHist = transHist;
	}

}
