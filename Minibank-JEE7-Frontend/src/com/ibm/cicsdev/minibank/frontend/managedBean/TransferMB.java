package com.ibm.cicsdev.minibank.frontend.managedBean;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ibm.cicsdev.minibank.frontend.entity.Account;
import com.ibm.cicsdev.minibank.frontend.entity.TransactionPOJO;
import com.ibm.cicsdev.minibank.frontend.util.IConstants;

@Named
@RequestScoped
public class TransferMB implements Serializable {

	private static final long serialVersionUID = 1L;
	private @Inject Account targetAccount;
	private @Inject Account sourceAccount;
	private Double moneyAmount;

	private String operationResult;
	private String operationMessage;

	public TransferMB() {
		super();
	}

	public String transfer() {

		TransactionPOJO transPOJO = new TransactionPOJO();
		transPOJO.setSourceAccountId(sourceAccount.getAccountNumber());
		transPOJO.setTargetAccountId(targetAccount.getAccountNumber());
		transPOJO.setMoneyAmount(moneyAmount);
		
		Response response;
		try {
			Client client = ClientBuilder.newClient();
			response = client.target(IConstants.URL)
		            .path(IConstants.TRANSEVENT+IConstants.TRANSFER)
		            .request(MediaType.APPLICATION_JSON)
		            .put(Entity.json(transPOJO));
			String ss=(String) response.getEntity();
			System.out.println(ss);

		} catch (Exception e) {
			e.printStackTrace();	
			this.setOperationMessage("failed...");
			return "../notificationpages/notification_transfer_failed";
		}
		if (response.getStatus() == IConstants.CODE_OPERATION_SUCCESS) {
			this.setOperationMessage(IConstants.MESSAGE_TRANSFER_SUCCESS
			+ response.getEntity().toString());
			return "../notificationpages/notification_transfer_success";
		} else {
			switch (response.getStatus()) {
			case IConstants.CODE_SOURCE_ACCOUNT_NOT_EXISTS:
				this.setOperationMessage(IConstants.MESSAGE_SOURCE_ACCOUNT_NOT_EXISTS
						+ sourceAccount.getAccountNumber());
				break;
			case IConstants.CODE_TARGET_ACCOUNT_NOT_EXISTS:
				this.setOperationMessage(IConstants.MESSAGE_TARGET_ACCOUNT_NOT_EXISTS
						+ targetAccount.getAccountNumber());
				break;
			case IConstants.CODE_INSUFFIENT_BALANCE:
				this.setOperationMessage(IConstants.MESSAGE_INSUFFIENT_BALANCE
						+ response.getEntity().toString());
				break;
			default:
				this.setOperationMessage(IConstants.MESSAGE_UNKNOWN_FAILED);
			}
		}
		return "../notificationpages/notification_transfer_failed";

	}

	public Account getTargetAccount() {
		return targetAccount;
	}

	public Account getSourceAccount() {
		return sourceAccount;
	}

	public Double getMoneyAmount() {
		return moneyAmount;
	}

	public void setMoneyAmount(Double moneyAmount) {
		this.moneyAmount = moneyAmount;
	}

	public String getOperationResult() {
		return operationResult;
	}

	public void setOperationResult(String operationResult) {
		this.operationResult = operationResult;
	}

	public String getOperationMessage() {
		return operationMessage;
	}

	public void setOperationMessage(String operationMessage) {
		this.operationMessage = operationMessage;
	}

}
