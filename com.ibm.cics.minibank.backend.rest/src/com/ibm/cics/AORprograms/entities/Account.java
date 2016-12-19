package com.ibm.cics.AORprograms.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.ws.rs.BeanParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.NameBinding;

import org.apache.openjpa.persistence.query.OrderBy;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonValue;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.schema.JsonSerializableSchema;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
	private String balance;
	@Column(name="LastChangeTime")
	private String lastChangeTime;
	
	private List<TransHist> transHist;
	
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
	public List<TransHist> getTransHist() {
		return transHist;
	}
	public void setTransHist(List<TransHist> transHist) {
		this.transHist = transHist;
	}
	
	
}
