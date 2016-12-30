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

import com.ibm.cicsdev.minibank.frontend.entity.User;
import com.ibm.cicsdev.minibank.frontend.util.IConstants;

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
		super();
	}

	public String createUser() {
		Response response;
		try {
			Client client = ClientBuilder.newClient();
			response = client.target(IConstants.URL).path(IConstants.USEREVENT)
					.request(MediaType.APPLICATION_JSON)
					.post(Entity.json(currentUser));
			System.out.println(response.getStatus());
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
