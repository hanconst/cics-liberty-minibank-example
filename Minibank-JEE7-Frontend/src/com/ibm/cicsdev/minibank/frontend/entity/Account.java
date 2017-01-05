package com.ibm.cicsdev.minibank.frontend.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author zxl
 *
 */

public class Account implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String accountNumber;
	private String customerID;
	private Double balance;
	private String lastChangeTime;

	private List<TransHistory> transHist;

	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Account(String accountNumber, String customerID, Double balance,
			String lastChangeTime, List<TransHistory> transHist) {
		super();
		this.accountNumber = accountNumber;
		this.customerID = customerID;
		this.balance = balance;
		this.lastChangeTime = lastChangeTime;
		this.transHist = transHist;
	}

	/*
	 * For queryUser method,we don't need to show transHistory details, we made
	 * another constructor for that use.
	 */
	public Account(String accountNumber, String customerID, Double balance,
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

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public String getLastChangeTime() {
		return lastChangeTime;
	}

	public void setLastChangeTime(String lastChangeTime) {
		this.lastChangeTime = lastChangeTime;
	}

	public List<TransHistory> getTransHist() {
		return transHist;
	}

	public void setTransHist(List<TransHistory> transHist) {
		this.transHist = transHist;
	}

}
