package com.ibm.cicsdev.minibank.frontend.managedBean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ibm.cicsdev.minibank.frontend.entity.Account;
import com.ibm.cicsdev.minibank.frontend.util.IConstants;

@Named
@RequestScoped
public class AccountCreateMB implements Serializable {

	private static final long serialVersionUID = 1L;
	private @Inject Account currentAccount;

	private String operationResult;
	private String operationMessage;

	public AccountCreateMB() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String createAccount() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String lastChangeTime = formatter.format(new Date());
		currentAccount.setLastChangeTime(lastChangeTime);
		
		Response response;
		try {
			Client client = ClientBuilder.newClient();
			response=client.target(IConstants.URL)
				.path(IConstants.ACCOUNTEVENT)
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.json(currentAccount));
		} catch (Exception e) {
			e.printStackTrace();
			this.setOperationMessage("failed...");
			return "../notificationpages/notification_createaccount_failed";
		}

		if (response.getStatus() == IConstants.CODE_OPERATION_SUCCESS) {
			this.setOperationMessage(IConstants.MESSAGE_CREATE_ACCOUNT_SUCCESS
					+ currentAccount.getAccountNumber());
			return "../notificationpages/notification_createaccount_success";
		} else {
			switch (response.getStatus()) {
			case IConstants.CODE_ACCOUNT_EXISTS:
				this.setOperationMessage(IConstants.MESSAGE_ACCOUNT_EXISTS
						+ currentAccount.getAccountNumber());
				break;
			case IConstants.CODE_JPA_EXCEPTION:
				this.setOperationMessage(IConstants.MESSAGE_JPA_EXCEPTION);
				break;
			default:
				this.setOperationMessage(IConstants.MESSAGE_UNKNOWN_FAILED);
			}
		}
		return "../notificationpages/notification_createaccount_failed";
	}

	public Account getCurrentAccount() {
		return currentAccount;
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
