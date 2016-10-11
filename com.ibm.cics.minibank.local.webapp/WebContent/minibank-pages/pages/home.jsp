<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>IBM - MiniBank - Create Account </title>

    <!-- Bootstrap Core CSS -->
    <link href="../bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="../bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="../dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="../bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

</head>
<body>
<div class="container">
     <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
            <%@ include file="../include/sidebar.jsp"%>    
      </nav>
      
</div>
	<div class="row">
          <div class="col-md-10 col-md-offset-1">
			
		  <h1 style="margin-top: 50px;margin-bottom: 40px">Things you can learn from MiniBank workshop</h1>
		  <p>
				  <div class="panel-body">
		            <!-- Nav tabs -->
		            <ul class="nav nav-tabs">
		                <li class=""><a aria-expanded="false" href="#jca" data-toggle="tab"><font size="5">JCA Programming interface</font></a>
		                </li>
		                <li class=""><a aria-expanded="false" href="#remote" data-toggle="tab"><font size="5">Remote development for Java</font></a>
		                </li>
		                <li class=""><a aria-expanded="false" href="#java" data-toggle="tab"><font size="5">Porting application to CICS</font></a>
		                </li>
		            </ul>
		
		            <!-- Tab panes -->
		            <div class="tab-content">
		                <div class="tab-pane fade active in" id="jca">
		                    <h3>Developing applications using the JCA programming interface</h3>
		                    <p><font size="4">JCA connects enterprise information systems such as CICS®, to the JEE platform.</font></p>
		                    <p><font size="4">JCA supports the qualities of service for security credential management, connection pooling and transaction management, provided by the JEE application server. 
		                    Using JCA ensures these qualities of service are managed by the JEE application server and not by the application. 
		                    This means the programmer is free to concentrate on writing business code and need not be concerned with quality of service. 
		                    For information about the provided qualities of service and configuration guidance see the documentation for your JEE application server. 
		                    JCA defines a programming interface called the Common Client Interface (CCI). This interface can be used with minor changes to communicate with any enterprise information system.</font></p>
		                </div>
		                <div class="tab-pane fade" id="remote">
		                    <h3>CICS remote development feature for Java</h3>
							<p><font size="4">The CICS® remote development feature for Java™ provides an ECI resource adapter for use in Liberty running on a developers workstation. 
							The feature enables developers to rapidly test and debug Java applications that use JCA APIs to invoke programs in CICS TS. 
							When ready, the application can be deployed into Liberty running in CICS without any further changes to the application.</font></p>
		                    <p><font size="4">The feature connects to a CICS region by using an IP interconnectivity (IPIC) connection that is defined by using the TCPIPSERVICE resource. 
		                    The trace facility can be used to identify problems with the data sent and received from the program in CICS TS.</font></p>
		                </div>
		                <div class="tab-pane fade" id="java">
		                    <h3>Porting JCA ECI applications into a Liberty JVM server</h3>
		                    <p><font size="4">JCA applications can be easily ported into a Liberty JVM server using the JCA local ECI resource adapter support:</font></p>
		                    <p>
		                    <ol><li><font size="4">Add the feature <strong><em>cicsts:jcalocalEci-1.0</em></strong> to server.xml</font></li>
		                    <li><font size="4">Configure a connectionFactory in server.xml for JCA local adapter with <strong><em>properties.com.ibm.cics.wlp.jca.local.eci</em></strong></font></li>
		                    <li><font size="4">Deploy the application into CICS</font></li></ol>
		                    </p>                    
		                </div>
		                
		            </div>
		        </div>
		  </p>
		  <p><a class="btn btn-primary btn-lg" href="https://www.ibm.com/support/knowledgecenter/en/SSGMCP_5.3.0/com.ibm.cics.ts.java.doc/topics/dfhpj2_jca_overview.html" role="button" style="margin-top: 100px;margin-left: 30px">Learn More About JCA programming interface</a></p>
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