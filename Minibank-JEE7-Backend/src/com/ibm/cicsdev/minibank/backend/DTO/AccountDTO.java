package com.ibm.cicsdev.minibank.backend.DTO;

import java.util.List;

import com.ibm.cicsdev.minibank.backend.entities.Account;
import com.ibm.cicsdev.minibank.backend.entities.TransHist;


public class AccountDTO extends Account{
	
	private static final long serialVersionUID = 1L;
	private List<TransHist> transHist;
	
	public List<TransHist> getTransHist() {
		return transHist;
	}
	public void setTransHist(List<TransHist> transHist) {
		this.transHist = transHist;
	}
	
	
}
