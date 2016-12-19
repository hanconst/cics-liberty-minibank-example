package com.ibm.cics.minibank.local.webapp.managedBean;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.resource.ResourceException;
import javax.resource.cci.Connection;
import javax.resource.cci.ConnectionFactory;
import javax.resource.cci.Interaction;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.ibm.cics.minibank.local.webapp.entity.User;
import com.ibm.cics.minibank.local.webapp.util.HttpURLConnecitonUtil;
import com.ibm.cics.minibank.local.webapp.util.IConstants;
import com.ibm.connector2.cics.ECIChannelRecord;
import com.ibm.connector2.cics.ECIInteractionSpec;

@Named
@RequestScoped
public class UserCreateMB implements Serializable {

	private static final long serialVersionUID = 1L;
	private @Inject User currentUser;

	private String operationResult;
	private String operationMessage;

	public User getCurrentUser() {
		return currentUser;
	}

	public UserCreateMB() {
		// TODO Auto-generated constructor stub
		super();
	}

	public String createUser() {
		Gson gson = new Gson();
		String jsonObject = gson.toJson(currentUser);
		
		Response response;
		try {
			response = HttpURLConnecitonUtil.getInstance().sendPost(
					IConstants.URL + "/user/", jsonObject);
			System.out.println("response:" + response);
		} catch (Exception e) {
			e.printStackTrace();
			this.setOperationMessage("failed...");
			return "../notificationpages/notification_createuser_failed";
		}
		if (response.getStatus() == IConstants.CODE_OPERATION_SUCCESS) {
			this.setOperationMessage(IConstants.MESSAGE_CREATE_USER_SUCCESS
					+ currentUser.getUserId());
			return "../notificationpages/notification_createuser_success";
		} else {
			switch (response.getStatus()) {
			case IConstants.CODE_USER_EXISTS:
				this.setOperationMessage(IConstants.MESSAGE_USER_EXISTS
						+ currentUser.getUserId());
				break;
			case IConstants.CODE_JPA_EXCEPTION:
				this.setOperationMessage(IConstants.MESSAGE_JPA_EXCEPTION);
				break;
			default:
				this.setOperationMessage(IConstants.MESSAGE_UNKNOWN_FAILED);
			}
		}
		return "../notificationpages/notification_createuser_failed";
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
