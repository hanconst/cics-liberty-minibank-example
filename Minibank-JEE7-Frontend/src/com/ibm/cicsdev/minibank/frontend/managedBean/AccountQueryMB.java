package com.ibm.cicsdev.minibank.frontend.managedBean;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import com.ibm.cicsdev.minibank.frontend.entity.Account;
import com.ibm.cicsdev.minibank.frontend.util.IConstants;

@Named
@RequestScoped
public class AccountQueryMB implements Serializable {

	private static final long serialVersionUID = 1L;
	private @Inject Account queryResult;

	private String operationMessage;

	public AccountQueryMB() {
		super();
	}

	public Account getQueryResult() {
		return queryResult;
	}

	public String queryAccount() {

		Client client = ClientBuilder.newClient();
		try {
			queryResult =
		            client.target(IConstants.URL)
		            .path(IConstants.ACCOUNTEVENT+queryResult.getAccountNumber())
		            .request(MediaType.APPLICATION_JSON)
		            .get(Account.class);
			client.close();
			return "queryAccountResult";
		} catch (Exception e) {
			// TODO: handle exception
			this.setOperationMessage(IConstants.MESSAGE_ACCOUNT_NOT_EXIST+queryResult.getAccountNumber());
			return "../notificationpages/notification_queryaccount_failed";
		}
	}

	public String getOperationMessage() {
		return operationMessage;
	}

	public void setOperationMessage(String operationMessage) {
		this.operationMessage = operationMessage;
	}

}
