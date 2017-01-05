package com.ibm.cicsdev.minibank.backend.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.ibm.cicsdev.minibank.backend.DTO.UserDTO;
import com.ibm.cicsdev.minibank.backend.entities.Account;
import com.ibm.cicsdev.minibank.backend.entities.User;
import com.ibm.cicsdev.minibank.backend.util.IResponseCode;
import com.ibm.cicsdev.minibank.backend.util.JPAUtil;

@Path("/")
public class UserService {

	@GET
	@Path("/user/{id}")
	@Produces("application/json")
	public UserDTO queryUser(@PathParam("id") String id) {
		System.out.println("Query User is being invoked....");
		// Choose the JDBC Type for db2 conn
		EntityManager em = JPAUtil.getJPAUtilInstance().getEmfType4()
				.createEntityManager();
		String userId = String.valueOf(id);
		User queryUser = em.find(User.class, userId);

		// Query accounts of user
		@SuppressWarnings("unchecked")
		List<Account> queryAccountList = em
				.createQuery(
						"SELECT a FROM Account a WHERE a.customerID = :customerID")
				.setParameter("customerID", userId).getResultList();
		em.close();
		UserDTO resultUser = new UserDTO();
		resultUser.setName(queryUser.getName());
		resultUser.setAge(queryUser.getAge());
		resultUser.setAddress(queryUser.getAddress());
		resultUser.setGender(queryUser.getGender());
		resultUser.setUserId(queryUser.getUserId());
		resultUser.setAccountList(queryAccountList);
		return resultUser;
	}

	@POST
	@Consumes("application/json")
	@Path("/user/")
	public Response createUser(User user) {
		System.out.println("Create User is being invoked....");
		EntityManager em = JPAUtil.getJPAUtilInstance().getEmfType4()
				.createEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(user);
			em.getTransaction().commit();
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
