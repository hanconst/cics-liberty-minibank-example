package com.ibm.cicsdev.minibank.backend.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author liang
 */
@Entity
@Table(name="ACCOUNT")
public class Account implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="AccountNumber")
	private String accountNumber;
	@Column(name="CustomerID")
	private String customerID;
	@Column(name="Balance")
	private Double balance;
	@Column(name="LastChangeTime")
	private String lastChangeTime;
	
	
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
	
	
}
