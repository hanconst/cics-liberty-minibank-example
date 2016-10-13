<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include/base.jsp"%>
<html>

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>IBM - MiniBank - Notification </title>

    <!-- Bootstrap Core CSS -->
    <link href="../bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="../bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="../dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="../bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>

<%String successResult=request.getAttribute("successResult").toString();%>
<div class="container">
     <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
            
            <%@ include file="../include/sidebar.jsp"%>    
      </nav>
      <div>
        <div class="row">
          <div class="col-md-10 col-md-offset-1">
            <div class="page-header">
              <h1 id="navbar"> Operation Progress </h1>
            </div>

            <div class="bs-component">
            
            
              <div class="panel panel-default">
                        <div class="panel-heading">
                            <h4> Operation Success! </h4>
                        </div>
                        <div class="panel-body">
                            <div class="row" style="margin-top: 40px; margin-bottom:30px" >
                            		<div class="col-md-2"></div>
                            		<div class="col-md-8">
	                            		<div class="alert alert-success alert-dismissable">
	                            				<font size="5"><%=successResult%>!</font>
			                            </div>
                                    </div>
  								    <div class="col-md-2"></div>
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
    

    <%@ include file="../include/footer.jsp"%>  

    <!-- jQuery -->
    <script src="../bower_components/jquery/dist/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="../bower_components/metisMenu/dist/metisMenu.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="../dist/js/sb-admin-2.js"></script>


</body>

</html>
