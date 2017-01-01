package com.ibm.cicsdev.minibank.frontend.managedBean;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import com.ibm.cicsdev.minibank.frontend.entity.User;
import com.ibm.cicsdev.minibank.frontend.util.IConstants;

@Named
@RequestScoped
public class UserQueryMB implements Serializable {

	private static final long serialVersionUID = 1L;
	private @Inject User queryResult;

	private String operationMessage;

	public UserQueryMB() {
		super();
	}

	public User getQueryResult() {
		return queryResult;
	}

	public String queryUser() throws UnsupportedEncodingException {

		Client client = ClientBuilder.newClient();
		try {
			queryResult =
		            client.target(IConstants.URL)
		            .path(IConstants.USEREVENT+queryResult.getUserId())
		            .request(MediaType.APPLICATION_JSON)
		            .get(User.class);
			client.close();

		} catch (Exception e) {
			e.printStackTrace();
			this.setOperationMessage(IConstants.MESSAGE_USER_NOT_EXIST
					+ queryResult.getUserId());
			return "../notificationpages/notification_queryuser_failed";
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
