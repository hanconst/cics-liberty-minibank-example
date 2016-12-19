package com.ibm.cics.minibank.local.webapp.managedBean;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.google.gson.Gson;
import com.ibm.cics.minibank.local.webapp.entity.Account;
import com.ibm.cics.minibank.local.webapp.util.HttpURLConnecitonUtil;
import com.ibm.cics.minibank.local.webapp.util.IConstants;

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

		Gson gson = new Gson();
		String jsonString;
		try {
			jsonString = HttpURLConnecitonUtil.getInstance().sendGet(
					IConstants.URL + "/account/"
							+ queryResult.getAccountNumber());
			if (jsonString.equals("not exists")){
				this.setOperationMessage(IConstants.MESSAGE_ACCOUNT_NOT_EXIST+queryResult.getAccountNumber());
				return "../notificationpages/notification_queryaccount_failed";
			}
			else
			queryResult = gson.fromJson(jsonString, Account.class);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "queryAccountResult";
	}

	public String getOperationMessage() {
		return operationMessage;
	}

	public void setOperationMessage(String operationMessage) {
		this.operationMessage = operationMessage;
	}

}
