package com.ibm.cics.AORprograms.service;

import java.util.List;

import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.ibm.cics.AORprograms.entities.Account;
import com.ibm.cics.AORprograms.entities.TransHist;
import com.ibm.cics.AORprograms.util.IResponseCode;
import com.ibm.cics.AORprograms.util.JPAUtil;

@Path("/")
public class AccountService {

	@GET
	@Path("/account/{id:\\d+}")
	@Produces("application/json")
	public Account queryAccount(@PathParam("id") String id) {
		System.out.println("Query Account is being invoked....");
		// Choose the JDBC Type for db2 conn
		EntityManager em = JPAUtil.getJPAUtilInstance().getEmfType4()
				.createEntityManager();
		String acctNum = String.valueOf(id);
		Account resultAccount = em.find(Account.class, acctNum);

		// Query transHistory of account
		@SuppressWarnings("unchecked")
		List<TransHist> queryTransRecordList = em
				.createQuery(
						"SELECT r FROM TransHist r WHERE r.accountNum = :accountNum")
				.setParameter("accountNum", acctNum).getResultList();
		resultAccount.setTransHist(queryTransRecordList);
		em.close();
		return resultAccount;
	}

	@POST
	@Consumes("application/json")
	@Path("/account/")
	public Response createAccount(Account account) {
		System.out.println("Create Account is being invoked....");
		EntityManager em = JPAUtil.getJPAUtilInstance().getEmfType4()
				.createEntityManager();
		InitialContext ctx;
		try {
			ctx = new InitialContext();
			UserTransaction tran = (UserTransaction) ctx
					.lookup("java:comp/UserTransaction");
			tran.begin();
			em.joinTransaction();
			em.persist(account);
			tran.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(IResponseCode.CODE_ACCOUNT_EXISTS)
					.entity(IResponseCode.MESSAGE_ACCOUNT_EXISTS).build();
		}
		return Response.status(IResponseCode.CODE_OPERATION_SUCCESS)
				.entity(IResponseCode.MESSAGE_OPERATION_SUCCESS).build();
	}
}
