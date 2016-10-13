package com.ibm.cics.minibank.local.webapp.entity;

import java.util.Set;

public class User {

	private String customerID;
	private String userName;
	private String userGender;
	private String age;
	private String address;
	private Set<Account> accountSet;
	

	public User(String customerID, String userName, String userGender,
			String age, String address,
			Set<com.ibm.cics.minibank.local.webapp.entity.Account> accountSet) {
		super();
		this.customerID = customerID;
		this.userName = userName;
		this.userGender = userGender;
		this.age = age;
		this.address = address;
		this.accountSet = accountSet;
	}
	
	public String getCustomerID() {
		return customerID;
	}
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserGender() {
		return userGender;
	}
	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Set<Account> getAccountSet() {
		return accountSet;
	}

	public void setAccountSet(Set<Account> accountSet) {
		this.accountSet = accountSet;
	}

	
	
}
