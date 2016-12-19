package com.ibm.cics.minibank.local.webapp.managedBean;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.resource.ResourceException;
import javax.resource.cci.Connection;
import javax.resource.cci.ConnectionFactory;
import javax.resource.cci.Interaction;

import com.google.gson.Gson;
import com.ibm.cics.minibank.local.webapp.entity.Account;
import com.ibm.cics.minibank.local.webapp.entity.User;
import com.ibm.cics.minibank.local.webapp.util.HttpURLConnecitonUtil;
import com.ibm.cics.minibank.local.webapp.util.IConstants;
import com.ibm.connector2.cics.ECIChannelRecord;
import com.ibm.connector2.cics.ECIInteractionSpec;

@Named
@RequestScoped
public class UserQueryMB implements Serializable {

	private static final long serialVersionUID = 1L;
	private @Inject User queryResult;

	private String operationMessage;

	public UserQueryMB() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User getQueryResult() {
		return queryResult;
	}

	public String queryUser() throws UnsupportedEncodingException {

		Gson gson = new Gson();
		String jsonString;
		try {
			jsonString = HttpURLConnecitonUtil.getInstance().sendGet(
					IConstants.URL + "/user/" + queryResult.getUserId());
			if (jsonString.equals("not exists")) {
				this.setOperationMessage(IConstants.MESSAGE_USER_NOT_EXIST
						+ queryResult.getUserId());
				return "../notificationpages/notification_queryuser_failed";
			} else
				queryResult = gson.fromJson(jsonString, User.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "queryUserResult";
	}

	public String getOperationMessage() {
		return operationMessage;
	}

	public void setOperationMessage(String operationMessage) {
		this.operationMessage = operationMessage;
	}

}
