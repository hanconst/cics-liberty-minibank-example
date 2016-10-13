package com.ibm.cics.minibank.local.webapp.servlet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.resource.ResourceException;
import javax.resource.cci.Connection;
import javax.resource.cci.ConnectionFactory;
import javax.resource.cci.Interaction;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.ibm.cics.minibank.local.webapp.entity.Account;
import com.ibm.cics.minibank.local.webapp.entity.TransHistory;
import com.ibm.cics.minibank.local.webapp.entity.User;
import com.ibm.connector2.cics.ECIChannelRecord;
import com.ibm.connector2.cics.ECIInteractionSpec;

/**
 * Servlet implementation class JCAServlet
 */
@WebServlet("/JCAServlet")
public class JCAServlet extends HttpServlet {
	static ServletOutputStream out;
	Map<String, String[]> requestDetails;
	
	@Resource(lookup="eis/ECI")
	private ConnectionFactory cf = null;
    
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public JCAServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// Setup the output stream to display results
		out = response.getOutputStream();
		out.println("GET method is used for test only.");
		
		// Get the parameters from the request
		requestDetails = request.getParameterMap();
		
		String action = requestDetails.get("action")[0];
		if (action.equals("deposit")) {
			doDeposit("0000000001","1000");
		} else if (action.equals("withdraw")){
			doWithdraw("0000000001","1000");			
		} else if (action.equals("transfer")) {
			doTransfer("0000000001","0000000002","1000");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
		   HttpServletResponse response) throws ServletException, IOException {
		
		String[] responseResult=null;
	
		String servletPath = request.getServletPath().toString();
		if (servletPath.equals("/minibank-pages/pages/doTransfer")) {
			String sourceAcct=request.getParameter("sourceAcct");
			String targetAcct=request.getParameter("targetAcct");
			String amount=request.getParameter("amount");
			responseResult=doTransfer(sourceAcct,targetAcct,amount);
		} else if (servletPath.equals("/minibank-pages/pages/doWithdraw")) {
			String sourceAcct=request.getParameter("sourceAcct");
			String amount=request.getParameter("amount");
			responseResult=doWithdraw(sourceAcct,amount);
		} else if (servletPath.equals("/minibank-pages/pages/doDeposit")) {
			String sourceAcct=request.getParameter("sourceAcct");
			String amount=request.getParameter("amount");
			responseResult=doDeposit(sourceAcct,amount);
		} else if (servletPath.equals("/minibank-pages/pages/doCreateUser")){
			String usrName=request.getParameter("usrName");
			String customerID=request.getParameter("customerID");
			String usrGender=request.getParameter("gender");
			String usrAddress=request.getParameter("usrAddress"); 
			String usrAge=request.getParameter("usrAge"); 
			responseResult=doCreateUser(usrName,customerID,usrGender,usrAddress,usrAge);
		}else if(servletPath.equals("/minibank-pages/pages/doCreateAccount")){
			String acctID=request.getParameter("acctID");
			String usrName=request.getParameter("usrID"); 
			String Balance=request.getParameter("Balance");
			responseResult=doCreateAccount(acctID,usrName,Balance);
		}else if(servletPath.equals("/minibank-pages/pages/doQueryUser")){
			String customerID=request.getParameter("customerID");
			//CSC breakdown...for test
			User queryUserResult=doQueryUser(customerID);
			if(queryUserResult==null){
				request.setAttribute("errorResult", "Query user failed because it doesn't exist");
				RequestDispatcher rd=request.getRequestDispatcher("notificationError.jsp");
				rd.forward(request,response);
			}
			else{
				request.setAttribute("customerID", queryUserResult.getCustomerID());
				request.setAttribute("userName", queryUserResult.getUserName());
				request.setAttribute("userGender", queryUserResult.getUserGender());
				request.setAttribute("userAge", queryUserResult.getAge());
				request.setAttribute("userAddress", queryUserResult.getAddress());
				request.setAttribute("userAccounts", queryUserResult.getAccountSet());
				
				RequestDispatcher rd=request.getRequestDispatcher("queryUserResult.jsp");
				rd.forward(request,response);
			}
			
		}else if(servletPath.equals("/minibank-pages/pages/doQueryAccount")){
			String accountID=request.getParameter("accountID");

			//send object to JSP
			Account queryAccountResult=doQueryAccount(accountID);
			
			if(queryAccountResult==null){
				request.setAttribute("errorResult", "Query account failed because it doesn't exist");
				RequestDispatcher rd=request.getRequestDispatcher("notificationError.jsp");
				rd.forward(request,response);
			}
			else{
				request.setAttribute("accountNumber", queryAccountResult.getAccountNumber());
				request.setAttribute("balance", queryAccountResult.getBalance());
				request.setAttribute("accountID", queryAccountResult.getCustomerID());
				request.setAttribute("lastChangeTime", queryAccountResult.getLastChangeTime());
				request.setAttribute("transHistory", queryAccountResult.getTransHist());

				RequestDispatcher rd=request.getRequestDispatcher("queryAccountResult.jsp");
				rd.forward(request,response);
			}
					
		}
		else{
			System.out.println("no servlet wrapped");
		}
		if (responseResult!=null){
			if (responseResult[0].equals("1")){
				request.setAttribute("successResult", responseResult[1]);
				RequestDispatcher rd=request.getRequestDispatcher("notification.jsp");
				rd.forward(request,response);
			}else
			{
				request.setAttribute("errorResult", responseResult[1]);
				RequestDispatcher rd=request.getRequestDispatcher("notificationError.jsp");
				rd.forward(request,response);
			}
		}
   	}

	private String[] doDeposit(String sourceAcct, String amount) throws IOException {
		String[] operationResult=new String[2];
		try{
			//JCA Feature
			Connection eciConn = cf.getConnection();
	        System.out.println("Create ECI connection");
	        Interaction eciInt = eciConn.createInteraction();
	        System.out.println("Create ECI interaction");			
			ECIChannelRecord inChannel = new ECIChannelRecord("BANKTRAN");
			inChannel.put(IConstants.TRAN_ACCTNM, sourceAcct.getBytes());
			inChannel.put(IConstants.TRAN_AMOUNT, amount.getBytes());
			ECIChannelRecord outChannel = new ECIChannelRecord("BANKTRAN");

	        // Setup the interactionSpec.
	        ECIInteractionSpec eSpec = new ECIInteractionSpec();
	        eSpec.setFunctionName("DEPOSIT");
	        eSpec.setInteractionVerb(ECIInteractionSpec.SYNC_SEND_RECEIVE);
	        System.out.println("Set interaction specification and Execute");
	        
			eciInt.execute(eSpec, inChannel, outChannel);
			System.out.println("Execution completed with response:");
		
			byte[] rcByte = (byte[]) outChannel.get(IConstants.TRAN_CODE);
			String rcStr = new String(rcByte,"ISO-8859-1");
			System.out.println("Return code: " + rcStr);
			
			byte[] msgByte = (byte[]) outChannel.get(IConstants.TRAN_MSG);
			String msgStr = new String(msgByte,"ISO-8859-1");
			System.out.println("Return message: "+ msgStr);
			
			operationResult[0]=rcStr;
			operationResult[1]=msgStr;
			
			
			eciInt.close();			
			eciConn.close();
			
		} catch (Exception e){
	          throw new IOException(e);
	    }
		return operationResult;
	}
	
	private String[] doWithdraw(String sourceAcct, String amount) throws IOException {
		String[] operationResult=new String[2];
		try{
			Connection eciConn = cf.getConnection();
	        System.out.println("Create ECI connection");
	        Interaction eciInt = eciConn.createInteraction();
	        System.out.println("Create ECI interaction");
	        	
			
			ECIChannelRecord inChannel = new ECIChannelRecord("BANKTRAN");
			inChannel.put(IConstants.TRAN_ACCTNM, sourceAcct.getBytes());
			inChannel.put(IConstants.TRAN_AMOUNT, amount.getBytes());
			ECIChannelRecord outChannel = new ECIChannelRecord("BANKTRAN");

	        // Setup the interactionSpec.
	        ECIInteractionSpec eSpec = new ECIInteractionSpec();
	        eSpec.setFunctionName("WITHDRAW");
	        eSpec.setInteractionVerb(ECIInteractionSpec.SYNC_SEND_RECEIVE);
	        System.out.println("Set interaction specification and Execute");
	        
			eciInt.execute(eSpec, inChannel, outChannel);
			System.out.println("Execution completed with response:");
		
			byte[] rcByte = (byte[]) outChannel.get(IConstants.TRAN_CODE);
			String rcStr = new String(rcByte,"ISO-8859-1");
			System.out.println("Return code: " + rcStr);
			
			byte[] msgByte = (byte[]) outChannel.get(IConstants.TRAN_MSG);
			String msgStr = new String(msgByte,"ISO-8859-1");
			System.out.println("Return message: "+ msgStr);
			
			
			operationResult[0]=rcStr;
			operationResult[1]=msgStr;
						
			eciInt.close();			
			eciConn.close();
			
		} catch (Exception e){
	          throw new IOException(e);
	    }
		return operationResult;
	}	
	
	protected String[] doTransfer(String sourceAcct, String targetAcct, 
			String amount) throws ServletException, IOException {
		String[] operationResult=new String[2];
	      try{
	    	  Connection eciConn = cf.getConnection();
	    	  //ECI:External Call Interface
	          System.out.println("Create ECI connection");
	          Interaction eciInt = eciConn.createInteraction();
	          System.out.println("Create ECI interaction");
	          
	          JavaStringRecord jsrIn = new JavaStringRecord();
	          jsrIn.setEncoding("IBM-1047");
	          
	          TransferCommarea transComm = new TransferCommarea();
	          transComm.setSourceAccount(sourceAcct);
	          transComm.setTargetAccount(targetAcct);
	          transComm.setAmount(amount);
	          
	          System.out.println(sourceAcct + " to " + targetAcct + ": " + amount);
	          
	          ByteArrayInputStream inStream = new ByteArrayInputStream(transComm.createCommarea());
	          jsrIn.read(inStream);
	          JavaStringRecord jsrOut = new JavaStringRecord();
	          jsrOut.setEncoding("IBM-1047");	     
	 	      	
	          // Setup the interactionSpec.
	          ECIInteractionSpec eSpec = new ECIInteractionSpec();
	          eSpec.setCommareaLength(150);
	          eSpec.setReplyLength(150);
	          //"TRANSFER" is the name of the program to execute on CICS
	          eSpec.setFunctionName("TRANSFER");
	          eSpec.setInteractionVerb(ECIInteractionSpec.SYNC_SEND_RECEIVE);
	          System.out.println("Set interaction specification and Execute");
	          
	          eciInt.execute(eSpec, jsrIn, jsrOut);
	          String[] returnMsgItems=jsrOut.getText().toString().split(" ");
	          
	          char resultCode=(returnMsgItems[0].charAt(54));
 
	          //Failed:1 INSUFFIENT AMOUNT
	          //Failed:1 THE ACCOUNT IS NOT FOUND
	          //Success:0 TRANSFER SUCCESSFULLY
	          if (resultCode=='0'){
	        	  operationResult[0]="1";
	        	  operationResult[1]="Transfer Successfully";
	          }
	          else if(returnMsgItems[1].equals("ACCOUNT")){
		        	  operationResult[0]="0";
		        	  operationResult[1]="Operation Transfer Failed. The Account "+targetAcct+" Is Not Found";
		        	  }
	          else if(returnMsgItems[1].equals("AMOUNT")){
	        	  		operationResult[0]="0";
	        	  		operationResult[1]="Insuffient Balance For Account "+sourceAcct+" To Transfer";
	          }
	          
	          else{
	        	  operationResult[0]="0";
	        	  operationResult[1]="Transfer Failed For Some Unknown Reason";
	          }
	           
	          System.out.println("Execution completed with response:" + jsrOut.getText());
	          	          
	       } catch (Exception e){
	          throw new IOException(e);
	       }
	      return operationResult;
   }
	
	//Create User
	private String[] doCreateUser(String usrName, String customerID, 
			String usrGender, String usrAddress, String usrAge) throws IOException,
			ServletException{
		String[] operationResult=new String[2];
		
		boolean inputCorrect = true;
		//Some logic here to judge if it has exists
		
		if(inputCorrect){
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String changeTime=formatter.format(new Date());
			
			try {
				Connection eciConn = cf.getConnection();
				System.out.println("Create ECI connection");
		        Interaction eciInt = eciConn.createInteraction();
		        System.out.println("Create ECI interaction");
		        
		        
		        ECIChannelRecord inChannel=new ECIChannelRecord("BANKTRAN");
		        inChannel.put(IConstants.CUST_NAME, usrName.getBytes());
		        inChannel.put(IConstants.CUST_ID, customerID.getBytes());
				inChannel.put(IConstants.CUST_GENDER, usrGender.getBytes());
				inChannel.put(IConstants.CUST_ADDR, usrAddress.getBytes());
				inChannel.put(IConstants.CUST_AGE, usrAge.getBytes());
			
				ECIChannelRecord outChannel = new ECIChannelRecord("BANKTRAN");
				
		        // Setup the interactionSpec
		        ECIInteractionSpec eSpec = new ECIInteractionSpec();
		        eSpec.setFunctionName("CRETUSER");
		        eSpec.setInteractionVerb(ECIInteractionSpec.SYNC_SEND_RECEIVE);
		        System.out.println("Set interaction specification and Execute");
		        
		        eciInt.execute(eSpec, inChannel, outChannel);
				System.out.println("Execution completed with response:");
			
				byte[] rcByte = (byte[]) outChannel.get(IConstants.TRAN_CODE);
				String rcStr = new String(rcByte,"ISO-8859-1");
				System.out.println("Return code: " + rcStr);
				
				byte[] msgByte = (byte[]) outChannel.get(IConstants.TRAN_MSG);
				String msgStr = new String(msgByte,"ISO-8859-1");
				System.out.println("Return message: "+ msgStr);
				operationResult[0]=rcStr;
				operationResult[1]=msgStr;
				
				eciInt.close();			
				eciConn.close();  
			} catch (ResourceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}		
		return operationResult;
	}
	
	//Query User
		private User doQueryUser(String customerID) throws IOException,ServletException{
			Connection eciConn;
			HashSet<Account> accountsOfUser=new HashSet<Account>();
			User queryUserResult=new User("", "", "", "", "", accountsOfUser);
			try {
				eciConn = cf.getConnection();
				System.out.println("Create ECI connection");
		        Interaction eciInt = eciConn.createInteraction();
		        System.out.println("Create ECI interaction");
		        	
				
				ECIChannelRecord inChannel = new ECIChannelRecord("BANKTRAN");
				inChannel.put(IConstants.CUST_ID, customerID.getBytes());
				ECIChannelRecord outChannel = new ECIChannelRecord("BANKTRAN");
				
		        // Setup the interactionSpec.
		        ECIInteractionSpec eSpec = new ECIInteractionSpec();
		        eSpec.setFunctionName("QURYUSER");
		        eSpec.setInteractionVerb(ECIInteractionSpec.SYNC_SEND_RECEIVE);
		        System.out.println("Set interaction specification and Execute");
		        
				eciInt.execute(eSpec, inChannel, outChannel);
				
				//get basic userInfo
				byte[] userQueryResultByte=(byte[]) outChannel.get(IConstants.CUST_INFO);
				String userQueryResultString=new String(userQueryResultByte,"ISO-8859-1");
				
				if(userQueryResultString.equals("not available")){
					return null;
				}
				else{
					String userQueryResultItems[]=userQueryResultString.split(IConstants.DATA_FIELD_SPLITTER);
					System.out.println("userUeryResult: "+userQueryResultString);
					queryUserResult.setCustomerID(userQueryResultItems[0]);
					queryUserResult.setUserName(userQueryResultItems[1]);
					if(userQueryResultItems[2].equals("f")){
						queryUserResult.setUserGender("female");
					}
					else{
						queryUserResult.setUserGender("male");
					}
					queryUserResult.setAge(userQueryResultItems[3]);
					queryUserResult.setAddress(userQueryResultItems[4]);
			
					//get containers
					Set containerSet=outChannel.keySet();
					System.out.println(containerSet.size());
					Iterator containerIterator=containerSet.iterator();
					Object container=null;
					//iterate the containers
					while(containerIterator.hasNext()){
						container=containerIterator.next();
						if(container!=null)
						{
							if(container.toString().startsWith(IConstants.ACCT_LIST)){
								String accountRec=container.toString();
								System.out.println(accountRec);
								byte[] accountByte = (byte[]) outChannel.get(accountRec);
								String accountString = new String(accountByte,"ISO-8859-1");
								
								Account accountRecord=userAccountsProcess(accountString);
								accountsOfUser.add(accountRecord);
							}
						}
						
					}
				}
				
				
				eciInt.close();			
				eciConn.close();
			} catch (ResourceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return queryUserResult;
		}
	
	//Create Account
	private String[] doCreateAccount(String acctID, String userID, 
			String balance) throws IOException,ServletException{		
		boolean inputCorrect = true;
		String[] operationResult=new String[2];

		try {
			Double.parseDouble(balance);
			inputCorrect = true;
		} catch (NumberFormatException e) {
			inputCorrect = false;
			System.out.println("Banlance should be a number, please check and input again");
		}
		
		if(inputCorrect){
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String changeTime=formatter.format(new Date());
			
			try {
				Connection eciConn = cf.getConnection();
				System.out.println("Create ECI connection");
		        Interaction eciInt = eciConn.createInteraction();
		        System.out.println("Create ECI interaction");
		        
		        ECIChannelRecord inChannel=new ECIChannelRecord("BANKTRAN");
		        inChannel.put(IConstants.ACCT_NUMBER, acctID.getBytes());
		        inChannel.put(IConstants.ACCT_CUST_ID, userID.getBytes());
				inChannel.put(IConstants.ACCT_BALANCE, balance.getBytes());
				inChannel.put(IConstants.ACCT_CHANGE, changeTime.getBytes());
				
				ECIChannelRecord outChannel = new ECIChannelRecord("BANKTRAN");
				
		        // Setup the interactionSpec
		        ECIInteractionSpec eSpec = new ECIInteractionSpec();
		        eSpec.setFunctionName("CRETACCT");
		        eSpec.setInteractionVerb(ECIInteractionSpec.SYNC_SEND_RECEIVE);
		        System.out.println("Set interaction specification and Execute");
		        
		        eciInt.execute(eSpec, inChannel, outChannel);
				System.out.println("Execution completed with response:");
			
				byte[] rcByte = (byte[]) outChannel.get(IConstants.TRAN_CODE);
				String rcStr = new String(rcByte,"ISO-8859-1");
				System.out.println("Return code: " + rcStr);
				
				byte[] msgByte = (byte[]) outChannel.get(IConstants.TRAN_MSG);
				String msgStr = new String(msgByte,"ISO-8859-1");
				System.out.println("Return message: "+ msgStr);
				operationResult[0]=rcStr;
				operationResult[1]=msgStr;

				eciInt.close();			
				eciConn.close();  
			} catch (ResourceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}	
		return operationResult;
	}
	
	//Query Account
	private Account doQueryAccount(String accountID) throws IOException,ServletException{
		HashSet<TransHistory> transHistory=new HashSet<TransHistory>();
		Account queryAccountResult=new Account("","","","", transHistory);
		Connection eciConn;
		try {
			eciConn = cf.getConnection();
			System.out.println("Create ECI connection");
	        Interaction eciInt = eciConn.createInteraction();
	        System.out.println("Create ECI interaction");
			
			ECIChannelRecord inChannel = new ECIChannelRecord("BANKTRAN");
			inChannel.put(IConstants.ACCT_NUMBER, accountID.getBytes());
			ECIChannelRecord outChannel = new ECIChannelRecord("BANKTRAN");
			
	        // Setup the interactionSpec.
	        ECIInteractionSpec eSpec = new ECIInteractionSpec();
	        eSpec.setFunctionName("QURYACCT");
	        eSpec.setInteractionVerb(ECIInteractionSpec.SYNC_SEND_RECEIVE);
	        System.out.println("Set interaction specification and Execute");
	        
			eciInt.execute(eSpec, inChannel, outChannel);
		
			//get AccountInfo
			byte[] accountInfoByte = (byte[]) outChannel.get(IConstants.ACCT_INFO);
			String accountInfoString = new String(accountInfoByte,"ISO-8859-1");
			if(accountInfoString.equals("not available")){
				return null;
			}
			else{
				String[] accountInfoItems=accountInfoString.split(IConstants.DATA_FIELD_SPLITTER);
				
				String accountNumber=accountInfoItems[0];
				String customerID=accountInfoItems[1];
				String balance=accountInfoItems[2];
				String lastChangeTime=accountInfoItems[3].substring(0, 19);
				queryAccountResult.setAccountNumber(accountNumber);
				queryAccountResult.setBalance(balance);
				queryAccountResult.setCustomerID(customerID);
				queryAccountResult.setLastChangeTime(lastChangeTime);
				

				//get containers
				Set containerSet=outChannel.keySet();
				System.out.println(containerSet.size());
				Iterator containerIterator=containerSet.iterator();
				Object container=null;
				//iterate the containers
				while(containerIterator.hasNext()){
					container=containerIterator.next();
					if(container!=null)
					{
						if(container.toString().startsWith(IConstants.HIST_LIST)){
							String histRecord=container.toString();
							System.out.println(histRecord);
							byte[] histByte = (byte[]) outChannel.get(histRecord);
							String hisString = new String(histByte,"ISO-8859-1");
							TransHistory transHistRecord=histRecordProcess(hisString);
							transHistory.add(transHistRecord);
						}
					}
					
				}
			}
			
						eciInt.close();			
						eciConn.close();
		} catch (ResourceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return queryAccountResult;
	}
	
	//convert TranHistory record to a TransHistory entity
	private TransHistory histRecordProcess(String histRecrod){
		TransHistory transHistory=new TransHistory(IConstants.RECORD_NOT_OBTAINED,
				IConstants.RECORD_NOT_OBTAINED, IConstants.RECORD_NOT_OBTAINED,
				IConstants.RECORD_NOT_OBTAINED);
		
		if(histRecrod.contains(IConstants.DATA_FIELD_SPLITTER)){
			String[] histRecordItems=histRecrod.split(IConstants.DATA_FIELD_SPLITTER);
			String transName=histRecordItems[0];
			String transAccount=histRecordItems[1];
			String transAmount=histRecordItems[2];
			String transTime=histRecordItems[3].substring(0, 19);
			
			transHistory.setAccountNum(transAccount);
			transHistory.setTransAmount(transAmount);
			transHistory.setTransName(transName);
			transHistory.setTransTime(transTime);
		}
		else{
			System.out.println("get TransHisReord Error");
		}
		return transHistory;
	}
	
	//convert userAccountRecord record to a Account entity
	private Account userAccountsProcess(String userAccountRecord){
		Account accountRecord = new Account(IConstants.RECORD_NOT_OBTAINED,
				IConstants.RECORD_NOT_OBTAINED,IConstants.RECORD_NOT_OBTAINED,
				IConstants.RECORD_NOT_OBTAINED);
		
		if(userAccountRecord.contains(IConstants.DATA_FIELD_SPLITTER)){
			String[] userAccountRecordItem=userAccountRecord.split(IConstants.DATA_FIELD_SPLITTER);
			String accountNumber=userAccountRecordItem[0];
			String customerID=userAccountRecordItem[1];
			String balance=userAccountRecordItem[2];
			String lastChangeTime=userAccountRecordItem[3].substring(0, 19);
			
			accountRecord.setAccountNumber(accountNumber);
			accountRecord.setCustomerID(customerID);
			accountRecord.setBalance(balance);
			accountRecord.setLastChangeTime(lastChangeTime);
		}
		else{
			System.out.println("get TransHisReord Error");
		}
		return accountRecord;
	}
	

   public class JavaStringRecord implements javax.resource.cci.Streamable,
         javax.resource.cci.Record {

      private static final long serialVersionUID = 1L;

      // Internal properties.
      private String recordName;
      private String desc;
      private String contents = new String("");

      /**
       * The encoding is set to ASCII to avoid platform specific conversion
       * issues.
       */
      private String enc = "ASCII"; // default commarea text encoding

      // The following methods are required for the Record interface.

      /**
       * get the name of the Record
       * 
       * @return a String representing the Record Name
       */
      public java.lang.String getRecordName() {
         return recordName;
      }

      /**
       * set the name of the Record
       * 
       * @param newName
       *            The Name of the Record
       */
      public void setRecordName(java.lang.String newName) {
         recordName = newName;
      }

      /**
       * set the record short description
       * 
       * @param newDesc
       *            The Description for this record
       */
      public void setRecordShortDescription(java.lang.String newDesc) {
         desc = newDesc;
      }

      /**
       * get the short description for this Record
       * 
       * @return a String representing the Description
       */
      public java.lang.String getRecordShortDescription() {
         return desc;
      }

      /**
       * return a hashcode for this object
       * 
       * @return hashcode
       */
      public int hashCode() {
         if (contents != null) {
            return contents.hashCode();
         } else {
            return super.hashCode();
         }
      }

      /**
       * The following determines if objects are equal. Objects are equal if
       * they have the same reference or the text contained is identical.
       * 
       * @return flag indicating true or false
       */
      public boolean equals(java.lang.Object comp) {
         if (!(comp instanceof JavaStringRecord)) {
            return false;
         }

         if (comp == this) {
            return true;
         }
         JavaStringRecord realComp = (JavaStringRecord) comp;
         return realComp.getText().equals(this.getText());
      }

      /**
       * use the superclass clone method for this class
       */
      public java.lang.Object clone() throws CloneNotSupportedException {
         return super.clone();
      }

      // The following methods are required for the streamable interface.

      /**
       * This method is invoked by the ECI Resource Adapter when it is
       * transmitting data to the Record. A commarea has been received from
       * the ECI Resource Adapter and must have been passed as an output
       * record.
       * 
       * @param is
       *            The inputStream containing the information.
       * @exception IOException
       *                if an exception occurs on the stream
       */
      public void read(java.io.InputStream is) {
         try {
            int total = is.available();
            byte[] bytes = null;

            if (total > 0) {
               bytes = new byte[total];
               is.read(bytes);
            }

            // Convert the bytes to a string based on the selected encoding.
            contents = new String(bytes, enc);
         } catch (Exception e) {
            e.printStackTrace();
         }
      }

      /**
       * This method is invoked by the ECI Resource Adapter when it wants to
       * read the record. An input record must have been passed in.
       * 
       * @param os
       *            The output stream to write the information to
       * @exception IOException
       *                if an exception occurs on the stream
       */
      public void write(java.io.OutputStream os) {
         try {
            // Output the string as bytes in the selected encoding.
            os.write(contents.getBytes(enc));
         } catch (Exception e) {
            e.printStackTrace();
         }

      }

      /**
       * Return the text of this Java record.
       * 
       * @return The text
       */
      public String getText() {
         return contents;
      }

      /**
       * Set the text for this Java record.
       * 
       * @param newStr
       *            The new text
       */
      public void setText(String newStr) {
         contents = newStr;
      }

      /**
       * Return the current Java encoding used for this record.
       * 
       * @return the Java encoding
       */
      public String getEncoding() {
         return enc;
      }

      /**
       * Set the Java encoding to be used for this record.
       * 
       * Note: no checks are made at this time to see if the encoding is a
       * valid Java encoding. If you wish you can modify the code to include
       * this.
       * 
       * @param newEnc
       *            the new Java encoding
       */
      public void setEncoding(String newEnc) {
         enc = newEnc;
      }
   }

}
