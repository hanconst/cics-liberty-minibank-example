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
	String accountNumber=request.getAttribute("accountNumber").toString();
	String balance=request.getAttribute("balance").toString();
	String accountID=request.getAttribute("accountID").toString();
	String lastChangeTime=request.getAttribute("lastChangeTime").toString();
	HashSet<TransHistory> transHisory=(HashSet<TransHistory>)request.getAttribute("transHistory");
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
              <h1 id="navbar"> Account Info </h1>
            </div>

            <div class="bs-component">
              <div class="panel panel-default">
                        <div class="panel-heading">
                            <h4> Account Information </h4>
                        </div>
                        <div class="panel-body">
                            <div class="row" style="margin-top: 40px">
	                            <div class="col-md-1"></div>
	                            <div class="col-md-10">
	                            <div class="table-responsive">
                                <table class="table table-bordered table-striped">
                                    <thead>
                                        <tr>
                                            <th>accountNumber</th>
                                            <th>Customer ID</th>
                                            <th>balance</th>
                                            <th>lastChangeTime</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td><%=accountNumber%></td>
                                            <td><%=accountID%></td>
                                            <td><%=balance%></td>
                                            <td><%=lastChangeTime%></td>
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
              <h1 id="navbar"> Account's Transaction History  </h1>
            </div>

            <div class="bs-component">
              <div class="panel panel-default">
                        <div class="panel-heading">
                            <h4> Transaction History </h4>
                        </div>
                        <div class="panel-body">
                            <div class="row" style="margin-top: 40px">
                            <div class="col-md-1"></div>
	                            <div class="col-md-12">
                            	<div class="table-responsive">
                                <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                        <tr>
                                            <th>transType</th>
                                            <th>transAmount</th>
                                            <th>transTime</th>
                                        </tr>
                                    </thead>
                                    <tbody>
	                                    	<%
									            for (TransHistory transHistRecord : transHisory) {
									        %>
									        <tr>
									            <td><%=transHistRecord.getTransName()%></td>
									            <td><%=transHistRecord.getTransAmount()%></td>
									            <td><%=transHistRecord.getTransTime()%></td>
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
