package com.ibm.cics.minibank.local.webapp.managedBean;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.ibm.cics.minibank.local.webapp.entity.Account;
import com.ibm.cics.minibank.local.webapp.entity.TransactionPOJO;
import com.ibm.cics.minibank.local.webapp.util.HttpURLConnecitonUtil;
import com.ibm.cics.minibank.local.webapp.util.IConstants;

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
		// TODO Auto-generated constructor stub
	}

	public String transfer() {

		TransactionPOJO transPOJO = new TransactionPOJO();
		transPOJO.setSourceAccountId(sourceAccount.getAccountNumber());
		transPOJO.setTargetAccountId(targetAccount.getAccountNumber());
		transPOJO.setMoneyAmount(moneyAmount);

		Gson gson = new Gson();
		String jsonObject = gson.toJson(transPOJO);

		Response response;

		try {
			response = HttpURLConnecitonUtil.getInstance().sendPut(
					IConstants.URL + "/trans/transfer/", jsonObject);
			System.out.println(response.getStatus());
		} catch (Exception e) {
			e.printStackTrace();
			// this.setOperationMessage("Create User " + currentUser.getUserId()
			// + " Failed");
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
