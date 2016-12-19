package com.ibm.cics.AORprograms.service;

import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.ibm.cics.AORprograms.entities.*;
import com.ibm.cics.AORprograms.util.*;
import com.ibm.cics.server.Wrapper.ApplicationResponse.ReturnCode;

@Path("/trans")
public class TransactionService {

	@PUT
	@Path("/withdraw/")
	@Produces("application/json")
	public Response withdraw(TransactionPOJO request) {
		System.out.println("Withdraw is being invoked....");
		// Choose the JDBC Type for db2 conn
		EntityManager em = JPAUtil.getJPAUtilInstance().getEmfType4()
				.createEntityManager();

		String acctNum = request.getSourceAccountId();
		Double moneyAmount = request.getMoneyAmount();
		int returnCode = 0;
		String returnMessage = "";

		try {
			InitialContext ctx = new InitialContext();
			UserTransaction tran = (UserTransaction) ctx
					.lookup("java:comp/UserTransaction");
			tran.begin();
			em.joinTransaction();
			Account accountUpdate = em.find(Account.class, acctNum);
			System.out.println(accountUpdate);
			if (accountUpdate == null) {
				System.out.println("cannot get this account");
				returnCode = IResponseCode.CODE_ACCOUNT_NOT_EXIST;
				returnMessage = IResponseCode.MESSAGE_ACCOUNT_NOT_EXIST;
			} else {
				Double balaceForAccountNow = Double.parseDouble(accountUpdate
						.getBalance());
				Double updateBalance = balaceForAccountNow - moneyAmount;
				if (updateBalance < 0) {
					returnCode = IResponseCode.CODE_INSUFFIENT_BALANCE;
					returnMessage = balaceForAccountNow.toString();
				} else {
					String balanceNow = String.format("%.2f", updateBalance);
					accountUpdate.setBalance(balanceNow);
					accountUpdate.setLastChangeTime(TimeStampService
							.getTimeStampInstance().getCurrentTime());

					em.merge(accountUpdate);
					// add the transRecord
					TransHist transRecord = addTransRecord(accountUpdate,
							"WITHDRAW", moneyAmount.toString());
					em.persist(transRecord);
					em.flush();
					tran.commit();
					System.out.println("Withdraw has been finished");
					returnCode = IResponseCode.CODE_OPERATION_SUCCESS;
					returnMessage =updateBalance.toString();
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			return Response.status(IResponseCode.JPA_EXCEPTION)
					.entity("JPA operation exception").build();
		}
		em.close();
		return Response.status(returnCode).entity(returnMessage).build();
	}

	@PUT
	@Path("/deposit/")
	@Produces("application/json")
	public Response deposit(TransactionPOJO request) {
		System.out.println("Deposit is being invoked....");
		// Choose the JDBC Type for db2 conn
		EntityManager em = JPAUtil.getJPAUtilInstance().getEmfType4()
				.createEntityManager();

		String acctNum = request.getSourceAccountId();
		Double moneyAmount = request.getMoneyAmount();

		int returnCode = 0;
		String returnMessage = "";

		try {
			InitialContext ctx = new InitialContext();
			UserTransaction tran = (UserTransaction) ctx
					.lookup("java:comp/UserTransaction");
			tran.begin();
			em.joinTransaction();
			Account accountUpdate = em.find(Account.class, acctNum);
			if (accountUpdate == null) {
				returnCode = IResponseCode.CODE_ACCOUNT_NOT_EXIST;
				returnMessage = IResponseCode.MESSAGE_ACCOUNT_NOT_EXIST;
			} else {
				String balanceNow = String.format("%.2f",
						(Double.parseDouble(accountUpdate.getBalance()))
								+ moneyAmount);
				accountUpdate.setBalance(balanceNow);
				accountUpdate.setLastChangeTime(TimeStampService
						.getTimeStampInstance().getCurrentTime());
				em.merge(accountUpdate);

				// add the transRecord
				TransHist transRecord = addTransRecord(accountUpdate,
						"WITHDRAW", moneyAmount.toString());

				em.persist(transRecord);
				em.flush();
				tran.commit();
				System.out.println("Withdraw has been finished");
				returnCode=IResponseCode.CODE_OPERATION_SUCCESS;
				returnMessage=balanceNow;
			}

		} catch (Exception e) {
			// TODO: handle exception
			return Response.status(IResponseCode.JPA_EXCEPTION)
					.entity("JPA operation exception").build();
		}
		em.close();
		return Response.status(returnCode).entity(returnMessage).build();
	}

	@PUT
	@Path("/transfer/")
	@Produces("application/json")
	public Response transfer(TransactionPOJO request) {
		System.out.println("Transfer is being invoked....");
		// Choose the JDBC Type for db2 conn
		EntityManager em = JPAUtil.getJPAUtilInstance().getEmfType4()
				.createEntityManager();

		String sourceAccountId = request.getSourceAccountId();
		String targetAccountId = request.getTargetAccountId();
		Double moneyAmount = request.getMoneyAmount();
		
		int returnCode = 0;
		String returnMessage = "";
		
		try {
			InitialContext ctx = new InitialContext();
			UserTransaction tran = (UserTransaction) ctx
					.lookup("java:comp/UserTransaction");
			tran.begin();
			em.joinTransaction();

			Account sourceAccount = em.find(Account.class, sourceAccountId);
			Account targetAccount = em.find(Account.class, targetAccountId);
			if(sourceAccount==null){
				returnCode=IResponseCode.CODE_SOURCE_ACCOUNT_NOT_EXISTS;
				returnMessage=IResponseCode.MESSAGE_SOURCE_ACCOUNT_NOT_EXIST;
			}
			else if(targetAccount==null){
				returnCode=IResponseCode.CODE_TARGET_ACCOUNT_NOT_EXISTS;
				returnMessage=IResponseCode.MESSAGE_TARGET_ACCOUNT_NOT_EXIST;
			}
			else {
				Double sourceAccountBalanceUpdate=(Double.parseDouble(sourceAccount.getBalance()))
						- moneyAmount;
				if (sourceAccountBalanceUpdate<0){
					returnCode=IResponseCode.CODE_INSUFFIENT_BALANCE;
					returnMessage=sourceAccount.getBalance();
				}
				else{
					String sourceAccountBanlace = String.format("%.2f",sourceAccountBalanceUpdate);
					sourceAccount.setBalance(sourceAccountBanlace);
					String targetAccountBalance = String.format("%.2f",
							(Double.parseDouble(sourceAccount.getBalance()))
									+ moneyAmount);
					targetAccount.setBalance(targetAccountBalance);

					targetAccount.setLastChangeTime(TimeStampService
							.getTimeStampInstance().getCurrentTime());
					sourceAccount.setLastChangeTime(TimeStampService
							.getTimeStampInstance().getCurrentTime());

					em.merge(targetAccount);
					em.merge(sourceAccount);
					// add the transRecord
					TransHist transRecordForSourceAcct = addTransRecord(sourceAccount,
							"TRANSFER", String.valueOf(0 - moneyAmount));
					TransHist transRecordForTargetAcct = addTransRecord(targetAccount,
							"TRANSFER", moneyAmount.toString());

					/*
					 * As for TransHistory table doesn't hava a Primary Key, we set
					 * transTime as its id for the reason that JPA requires an id, To
					 * avoid conflict we set the time different manually here.
					 */
					String time = transRecordForSourceAcct.getTransTime();
					int changeTime = Integer.valueOf(time.charAt(time.length() - 1)) + 1;
					transRecordForTargetAcct.setTransTime(time.replace(
							time.charAt(time.length() - 1), (char) changeTime));

					em.persist(transRecordForSourceAcct);
					em.persist(transRecordForTargetAcct);
					em.flush();

					tran.commit();
					System.out.println("Transfer has been finished");
					returnCode=IResponseCode.CODE_OPERATION_SUCCESS;
					returnMessage=sourceAccountBalanceUpdate.toString();
				}
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return Response.status(IResponseCode.JPA_EXCEPTION)
					.entity("JPA operation exception").build();
		}
		em.close();
		return Response.status(returnCode).entity(returnMessage).build();
	}

	// addTransRecord function
	public TransHist addTransRecord(Account account, String transType,
			String moneyAmount) {
		TransHist transRecord = new TransHist();

		transRecord.setAccountNum(account.getAccountNumber());
		transRecord.setTransAmount(moneyAmount);
		transRecord.setTransName(transType);

		transRecord.setTransTime(TimeStampService.getTimeStampInstance()
				.getCurrentTime());

		return transRecord;
	}
}
