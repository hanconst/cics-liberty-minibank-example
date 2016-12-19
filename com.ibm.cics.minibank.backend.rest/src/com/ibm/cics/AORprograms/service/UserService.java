package com.ibm.cics.AORprograms.service;

import java.util.List;

import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.ibm.cics.AORprograms.entities.Account;
import com.ibm.cics.AORprograms.entities.TransHist;
import com.ibm.cics.AORprograms.entities.User;
import com.ibm.cics.AORprograms.util.IResponseCode;
import com.ibm.cics.AORprograms.util.JPAUtil;

@Path("/")
public class UserService {

	@GET
	@Path("/user/{id}")
	@Produces("application/json")
	public User queryUser(@PathParam("id") String id) {
		System.out.println("Query User is being invoked....");
		// Choose the JDBC Type for db2 conn
		EntityManager em = JPAUtil.getJPAUtilInstance().getEmfType4()
				.createEntityManager();
		String userId = String.valueOf(id);
		User resultUser = em.find(User.class, userId);

		// Query accounts of user
		@SuppressWarnings("unchecked")
		List<Account> queryAccountList = em
				.createQuery(
						"SELECT a FROM Account a WHERE a.customerID = :customerID")
				.setParameter("customerID", userId).getResultList();
		resultUser.setAccountList(queryAccountList);
		em.close();

		return resultUser;
	}

	@POST
	@Consumes("application/json")
	@Path("/user/")
	public Response createUser(User user) {
		System.out.println("Create User is being invoked....");
		EntityManager em = JPAUtil.getJPAUtilInstance().getEmfType4()
				.createEntityManager();
		InitialContext ctx;
		try {
			ctx = new InitialContext();
			UserTransaction tran = (UserTransaction) ctx
					.lookup("java:comp/UserTransaction");
			tran.begin();
			em.joinTransaction();
			em.persist(user);
			tran.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(IResponseCode.CODE_USER_EXISTS)
					.entity(IResponseCode.MESSAGE_USER_EXISTS).build();
		}
		return Response.status(IResponseCode.CODE_OPERATION_SUCCESS)
				.entity(IResponseCode.MESSAGE_OPERATION_SUCCESS).build();
	}

}
