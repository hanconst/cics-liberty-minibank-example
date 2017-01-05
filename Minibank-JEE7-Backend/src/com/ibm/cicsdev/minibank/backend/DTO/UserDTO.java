package com.ibm.cicsdev.minibank.backend.DTO;

import java.util.List;
import com.ibm.cicsdev.minibank.backend.entities.Account;
import com.ibm.cicsdev.minibank.backend.entities.User;


public class UserDTO extends User{

	private static final long serialVersionUID = 1L;
	private List<Account> accountList;
	

	public List<Account> getAccountList() {
		return accountList;
	}

	public void setAccountList(List<Account> accountList) {
		this.accountList = accountList;
	}

}
