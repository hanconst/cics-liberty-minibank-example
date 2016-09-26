<%@ page import="com.ibm.cics.minibank.local.webapp.entity.User"
import="com.ibm.cics.minibank.local.webapp.entity.TransHistory"
import="com.ibm.cics.minibank.local.webapp.entity.Account"
import="java.util.*"
language="java" contentType="text/html; charset=UTF-8"pageEncoding="UTF-8"%>
<html>

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <!-- Bootstrap Core CSS -->
    <link href="../bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="../bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="../dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="../bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    

    <title>IBM - MiniBank - UserQuery </title>
</head>

<body>

	
	<%
	String userID=request.getAttribute("customerID").toString();
	String userName=request.getAttribute("userName").toString();
	String userGender=request.getAttribute("userGender").toString();
	String userAddress=request.getAttribute("userAddress").toString();
	String userAge=request.getAttribute("userAge").toString();
	HashSet<Account> userAccounts=(HashSet<Account>)request.getAttribute("userAccounts");
	%>
	
    <div class="container">

        <!-- Navigation -->
         <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
            
            <%@ include file="../include/sidebar.jsp"%>    
      	</nav>
      	
      	<div>
        
        <div class="row">
          <div class="col-md-10 col-md-offset-1">
            <div class="page-header">
              <h1 id="navbar"> Customer Info </h1>
            </div>

            <div class="bs-component">
              <div class="panel panel-default">
                        <div class="panel-heading">
                            <h4> Customer Information </h4>
                        </div>
                        <div class="panel-body">
                            <div class="row" style="margin-top: 40px">
	                            <div class="col-md-1"></div>
	                            <div class="col-md-12">
	                            <div class="table-responsive">
                                <table class="table table-bordered table-striped">
                                    <thead>
                                        <tr>
                                            <th>customerID</th>
                                            <th>userName</th>
                                            <th>userGender</th>
                                            <th>age</th>
                                            <th>address</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td><%=userID%></td>
                                            <td><%=userName%></td>
                                            <td><%=userGender%></td>
                                            <td><%=userAge%></td>
                                            <td><%=userAddress%></td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                            </div>
                            	<div class="col-md-1"></div>
                            </div>
                            <!-- /.row (nested) -->
                        </div>
                        <!-- /.panel-body -->
                    </div>
            </div>
          </div>
        </div>
        
        
        
        
        <div class="row">
          <div class="col-md-10 col-md-offset-1">
            <div class="page-header">
              <h1 id="navbar"> Accounts of the customer </h1>
            </div>

            <div class="bs-component">
              <div class="panel panel-default">
                        <div class="panel-heading">
                            <h4> Customer Accounts </h4>
                        </div>
                        <div class="panel-body">
                            <div class="row" style="margin-top: 40px">
                            <div class="col-md-1"></div>
	                            <div class="col-md-10">
                            	<div class="table-responsive">
                                <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                        <tr>
                                            <th>account Number</th>
                                            <th>balance</th>
                                            <th>lastChangeTime</th>
                                        </tr>
                                    </thead>
                                    <tbody>
	                                    	<%
									            for (Account account : userAccounts) {
									        %>
									        <tr>
									            <td><%=account.getAccountNumber()%></td>
									            <td><%=account.getBalance()%></td>
									            <td><%=account.getLastChangeTime()%></td>
									        </tr>
									        <%
									            }
									        %>
                                    </tbody>
                                </table>
                            </div>
                            </div>
                            	<div class="col-md-1"></div>
                            </div>
                            <!-- /.row (nested) -->
                        </div>
                        <!-- /.panel-body -->
                    </div>
            </div>
          </div>
        </div>
        
        
        
        
        
      </div>
    </div>
    <!-- /#wrapper -->
    
    <%@ include file="../include/footer.jsp"%>  
    
    <!-- jQuery -->
    <script src="../bower_components/jquery/dist/jquery.min.js"></script>
    <!-- Bootstrap Core JavaScript -->
    <script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
    <!-- Metis Menu Plugin JavaScript -->
    <script src="../bower_components/metisMenu/dist/metisMenu.min.js"></script>
    <!-- DataTables JavaScript -->
    <script src="../bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
    <script src="../bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>
    <script src="../bower_components/datatables-responsive/js/dataTables.responsive.js"></script>
    

    <!-- Custom Theme JavaScript -->
    <script src="../dist/js/sb-admin-2.js"></script>
    <!-- Page-Level Demo Scripts - Tables - Use for reference -->
    <script>
    $(document).ready(function() {
        $('#dataTables-example').DataTable({
                responsive: true
        });
    });
    </script>

</body>

</html>
