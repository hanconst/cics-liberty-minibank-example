package com.ibm.cics.minibank.local.webapp.managedBean;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.resource.ResourceException;
import javax.resource.cci.Connection;
import javax.resource.cci.ConnectionFactory;
import javax.resource.cci.Interaction;
import javax.ws.rs.core.Response;

import org.eclipse.persistence.sessions.serializers.JSONSerializer;
import org.jboss.weld.context.http.HttpRequestContext;

import com.google.gson.Gson;
import com.google.gson.internal.bind.JsonAdapterAnnotationTypeAdapterFactory;
import com.ibm.cics.minibank.local.webapp.entity.Account;
import com.ibm.cics.minibank.local.webapp.entity.TransHistory;
import com.ibm.cics.minibank.local.webapp.util.HttpURLConnecitonUtil;
import com.ibm.cics.minibank.local.webapp.util.IConstants;
import com.ibm.connector2.cics.ECIChannelRecord;
import com.ibm.connector2.cics.ECIInteractionSpec;
import com.ibm.json.java.JSON;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

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
		Gson gson = new Gson();
		String jsonObject = gson.toJson(currentAccount);

		Response response;
		try {
			response = HttpURLConnecitonUtil.getInstance().sendPost(
					IConstants.URL + "/account/", jsonObject);
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
