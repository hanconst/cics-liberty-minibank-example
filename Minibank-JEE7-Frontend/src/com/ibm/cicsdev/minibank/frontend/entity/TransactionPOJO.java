package com.ibm.cicsdev.minibank.frontend.entity;

import java.io.Serializable;

public class TransactionPOJO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String sourceAccountId;
	private String targetAccountId;
	private Double moneyAmount;
	
	public TransactionPOJO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getSourceAccountId() {
		return sourceAccountId;
	}
	public void setSourceAccountId(String sourceAccountId) {
		this.sourceAccountId = sourceAccountId;
	}
	public String getTargetAccountId() {
		return targetAccountId;
	}
	public void setTargetAccountId(String targetAccountId) {
		this.targetAccountId = targetAccountId;
	}
	public Double getMoneyAmount() {
		return moneyAmount;
	}
	public void setMoneyAmount(Double moneyAmount) {
		this.moneyAmount = moneyAmount;
	}
	
	
}
