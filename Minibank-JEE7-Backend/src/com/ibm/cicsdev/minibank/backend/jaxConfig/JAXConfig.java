package com.ibm.cicsdev.minibank.backend.jaxConfig;
import java.util.HashSet;
import java.util.Set;


public class JAXConfig extends javax.ws.rs.core.Application{

	// List the JAX-RS classes which contains the annotations
	public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        
        classes.add(com.ibm.cicsdev.minibank.backend.service.AccountService.class);         
        classes.add(com.ibm.cicsdev.minibank.backend.service.TransactionService.class);         
        classes.add(com.ibm.cicsdev.minibank.backend.service.UserService.class);    

        return classes;
    }


}

