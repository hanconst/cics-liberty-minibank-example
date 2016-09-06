package com.ibm.cics.minibank.local.webapp.servlet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.resource.cci.Connection;
import javax.resource.cci.ConnectionFactory;
import javax.resource.cci.Interaction;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		
		String servletPath = request.getServletPath().toString();
		if (servletPath.equals("/minibank-pages/pages/doTransfer")) {
			String sourceAcct=request.getParameter("sourceAcct");
			String targetAcct=request.getParameter("targetAcct");
			String amount=request.getParameter("amount");
			doTransfer(sourceAcct,targetAcct,amount);
		} else if (servletPath.equals("/minibank-pages/pages/doWithdraw")) {
			String sourceAcct=request.getParameter("sourceAcct");
			String amount=request.getParameter("amount");
			doWithdraw(sourceAcct,amount);
		} else if (servletPath.equals("/minibank-pages/pages/doDeposit")) {
			String sourceAcct=request.getParameter("sourceAcct");
			String amount=request.getParameter("amount");
			doDeposit(sourceAcct,amount);
		}
		response.sendRedirect(request.getContextPath()+"/minibank-pages/pages/notification.html");

   	}

	private void doDeposit(String sourceAcct, String amount) throws IOException {
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
	        eSpec.setFunctionName("DEPOSIT");
	        eSpec.setInteractionVerb(ECIInteractionSpec.SYNC_SEND_RECEIVE);
	        eSpec.setTPNName("DPTJ");
	        System.out.println("Set interaction specification and Execute");
	        
			eciInt.execute(eSpec, inChannel, outChannel);
			System.out.println("Execution completed with response:");
		
			byte[] rcByte = (byte[]) outChannel.get(IConstants.TRAN_CODE);
			String rcStr = new String(rcByte,"ISO-8859-1");
			System.out.println("Return code: " + rcStr);
			
			byte[] msgByte = (byte[]) outChannel.get(IConstants.TRAN_MSG);
			String msgStr = new String(msgByte,"ISO-8859-1");
			System.out.println("Return message: "+ msgStr);
			
			eciInt.close();			
			eciConn.close();
			
			
		} catch (Exception e){
	          throw new IOException(e);
	    }		
	}
	
	private void doWithdraw(String sourceAcct, String amount) throws IOException {
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
	        eSpec.setTPNName("WDRJ");
	        System.out.println("Set interaction specification and Execute");
	        
			eciInt.execute(eSpec, inChannel, outChannel);
			System.out.println("Execution completed with response:");
		
			byte[] rcByte = (byte[]) outChannel.get(IConstants.TRAN_CODE);
			String rcStr = new String(rcByte,"ISO-8859-1");
			System.out.println("Return code: " + rcStr);
			
			byte[] msgByte = (byte[]) outChannel.get(IConstants.TRAN_MSG);
			String msgStr = new String(msgByte,"ISO-8859-1");
			System.out.println("Return message: "+ msgStr);
						
			eciInt.close();			
			eciConn.close();
			
		} catch (Exception e){
	          throw new IOException(e);
	    }

	}	
	
	protected void doTransfer(String sourceAcct, String targetAcct, String amount) throws ServletException, IOException {
	      try{
	    	  Connection eciConn = cf.getConnection();
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
	          eSpec.setFunctionName("TRANSFER");
	          eSpec.setTPNName("TSFJ");
	          eSpec.setInteractionVerb(ECIInteractionSpec.SYNC_SEND_RECEIVE);
	          System.out.println("Set interaction specification and Execute");
	          
	          eciInt.execute(eSpec, jsrIn, jsrOut);
	          System.out.println("Execution completed with response:" + jsrOut.getText());
	          	          
	       } catch (Exception e){
	          throw new IOException(e);
	       }
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
