package com.ibm.cicsdev.minibank.backend.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.ibm.cicsdev.minibank.backend.DTO.AccountDTO;
import com.ibm.cicsdev.minibank.backend.entities.Account;
import com.ibm.cicsdev.minibank.backend.entities.TransHist;
import com.ibm.cicsdev.minibank.backend.util.IResponseCode;
import com.ibm.cicsdev.minibank.backend.util.JPAUtil;

@Path("/")
public class AccountService {

	@GET
	@Path("/account/{id:\\d+}")
	@Produces("application/json")
	public AccountDTO queryAccount(@PathParam("id")final String id) {
		System.out.println("Query Account is being invoked....");
		// Choose the JDBC Type for db2 conn
		EntityManager em = JPAUtil.getJPAUtilInstance().getEmfType4()
				.createEntityManager();
		String acctNum = String.valueOf(id);
		Account queryAccount = em.find(Account.class, acctNum);
		// Query transHistory of account
		@SuppressWarnings("unchecked")
		List<TransHist> queryTransRecordList = em
				.createQuery(
						"SELECT r FROM TransHist r WHERE r.accountNum = :accountNum")
				.setParameter("accountNum", acctNum).getResultList();
		em.close();
		AccountDTO result=new AccountDTO();
		result.setAccountNumber(queryAccount.getAccountNumber());
		result.setBalance(queryAccount.getBalance());
		result.setCustomerID(queryAccount.getCustomerID());
		result.setLastChangeTime(queryAccount.getLastChangeTime());
		result.setTransHist(queryTransRecordList);
		return result;
	}

	@POST
	@Consumes("application/json")
	@Path("/account/")
	public Response createAccount(Account account) {
		System.out.println("Create Account is being invoked....");
		EntityManager em = JPAUtil.getJPAUtilInstance().getEmfType4()
				.createEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(account);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(IResponseCode.CODE_ACCOUNT_EXISTS)
					.entity(IResponseCode.MESSAGE_ACCOUNT_EXISTS).build();
		}
		return Response.status(IResponseCode.CODE_OPERATION_SUCCESS)
				.entity(IResponseCode.MESSAGE_OPERATION_SUCCESS).build();
	}
}
