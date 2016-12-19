package com.ibm.cics.minibank.local.webapp.managedBean;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.resource.cci.Connection;
import javax.resource.cci.ConnectionFactory;
import javax.resource.cci.Interaction;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.ibm.cics.minibank.local.webapp.entity.Account;
import com.ibm.cics.minibank.local.webapp.entity.TransactionPOJO;
import com.ibm.cics.minibank.local.webapp.util.HttpURLConnecitonUtil;
import com.ibm.cics.minibank.local.webapp.util.IConstants;
import com.ibm.connector2.cics.ECIChannelRecord;
import com.ibm.connector2.cics.ECIInteractionSpec;

@Named
@RequestScoped
public class DepositMB implements Serializable {
	private static final long serialVersionUID = 1L;
	private @Inject Account sourceAccount;
	private Double moneyAmount;
	private String operationResult;
	private String operationMessage;

	public DepositMB() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String deposit() {

		TransactionPOJO transPOJO = new TransactionPOJO();
		transPOJO.setSourceAccountId(sourceAccount.getAccountNumber());
		transPOJO.setMoneyAmount(moneyAmount);

		Gson gson = new Gson();
		String jsonObject = gson.toJson(transPOJO);

		Response response;

		try {
			response = HttpURLConnecitonUtil.getInstance().sendPut(
					IConstants.URL + "/trans/deposit/", jsonObject);
			System.out.println("response:" + response);
		} catch (Exception e) {
			e.printStackTrace();
			this.setOperationMessage("failed...");
			return "../notificationpages/notification_deposit_failed";
		}
		if (response.getStatus() == IConstants.CODE_OPERATION_SUCCESS) {
			this.setOperationMessage(IConstants.MESSAGE_DEPOSIT_SUCCESS
					+ response.getEntity().toString());
			return "../notificationpages/notification_deposit_success";
		} 
		else {
			switch (response.getStatus()) {
			case IConstants.CODE_ACCOUNT_NOT_EXIST:
				this.setOperationMessage(IConstants.MESSAGE_ACCOUNT_NOT_EXIST
						+ sourceAccount.getAccountNumber());
				break;
			case IConstants.CODE_INSUFFIENT_BALANCE:
				this.setOperationMessage(IConstants.MESSAGE_INSUFFIENT_BALANCE
						+ response.getEntity().toString());
				break;
			default:
				this.setOperationMessage(IConstants.MESSAGE_UNKNOWN_FAILED);
				return "../notificationpages/notification_deposit_failed";
			}
		}
		return "../notificationpages/notification_deposit_failed";

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

	public Account getSourceAccount() {
		return sourceAccount;
	}

}
