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

    <title>IBM - MiniBank - Create User </title>
	
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

    <div class="container">
     <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
            
            <%@ include file="../include/sidebar.jsp"%>    
      </nav>
            <%@ include file="../include/modal.jsp"%>    
      
      <div>
        <div class="row">
          <div class="col-md-10 col-md-offset-1">
            <div class="page-header">
              <h1 id="navbar"> Create User </h1>
            </div>

            <div class="bs-component">
              <div class="panel panel-default">
                        <div class="panel-heading">
                            <h4> Users - Create User </h4>
                        </div>
                        <div class="panel-body">
                            <div class="row" style="margin-top: 40px">
                            		<div class="col-md-2"></div>
                            		<div class="col-md-8">
	                            		<form role="form" method="post" class="form-horizontal" action="doCreateUser">
	                                        <div class="form-group has-success ">
	                                            <label class="control-label col-md-4" for="inputSuccess"> User Name </label>
	                                            <div class="col-md-6">
											      <input type="text" class="form-control required" name="usrName" 
											      id="usrName" placeholder="Username">
											    </div>
	                                        </div>
	                                        
	                                        <div class="form-group has-success ">
	                                            <label class="control-label col-md-4" for="inputSuccess"> Customer ID </label>
	                                            <div class="col-md-6">
											      <input type="text" class="form-control" name="customerID" 
											      id="customerID" placeholder="Customer ID">
											    </div>
	                                        </div>
	                                        
	                                        <div class="form-group has-success ">
	                                            <label class="control-label col-md-4" for="inputSuccess"> Gender </label>
	                                            <div class="col-md-6">
		                                            <label class="radio-inline">
														  <input type="radio" checked="true" name="gender" id="male" value="male"> Male
													</label>
													<label class="radio-inline">
														  <input type="radio" name="gender" id="female" value="female"> Female
													</label>
											    </div>
	                                        </div>
	                                        
	                                        <div class="form-group has-success ">
	                                            <label class="control-label col-md-4" for="inputSuccess"> Age </label>
	                                            <div class="col-md-4">
											      <input type="text" class="form-control required" name="usrAge" 
											      id="usrAge" placeholder="Age">
											    </div>
	                                        </div>
	                                        
	                                        <div class="form-group has-success ">
	                                            <label class="control-label col-md-4" for="inputSuccess"> Address </label>
	                                            <div class="col-md-8">
													<textarea class="form-control" rows="3" name="usrAddress" 
													id="usrAddress" placeholder="Address"></textarea>	
												</div>
	                                        </div>
	                                        <button type="submit" class="btn btn-primary center-block" id="btn" 
	                                        style="margin-bottom:40px; margin-top: 30px" onclick="return onCheck()"> Create User </button>
	                                    </form>
                                    </div>
  								    <div class="col-md-2"></div>
                            </div>
                            <!-- /.row (nested) -->
                        </div>
                        <!-- /.panel-body -->
                    </div>
            </div>

            <div class="bs-component">
              
            </div><!-- /example -->

          </div>
        </div>
      </div>

    </div>
    <script language='javascript'>
    	
    	//check userID
    	function checkUserId(userId){
     		var userIdLength=userId.length;
     		if(userId==""){
    			return "\"UserID\" is empty, ";
    		}
 			else{
 				for(var i=0;i<userIdLength;i++){
 					var ch=userId.charAt(i);
 					if(ch>=0&&ch<=9){
 					}else{
 						return "\"UserID\" input illeagal, ";
 					}
 				}
 				return "";
 			}
    	}
    	
    	//check user name
    	function checkUserName(userName){
    		if(userName==""){
    			return "\"UserName\" is empty, ";
    		}
    		else{
    			return "";
    		}
    	}
    	
    	
    	//check Age
    	function checkAge(age){
    		var re = /^[0-9]*[1-9][0-9]*$/;  

    		if(age==""){
    			return "\"Age\" is empty, ";
    		}
    	    if (re.test(age)){
    	    	return "";
    	    } 
    		else{
    			return "\"Age\" input illegal,";
    		}
    	}
    	
    	//check address not null
    	function checkAddress(address){
    		if(address==""){
    			return "\"Address\" is empty, ";
    		}
    		else{
    			return "";
    		}
    	}
    	
		function onCheck(){
		var userName=document.getElementById("usrName").value;
		var userID=document.getElementById("customerID").value;
		var userAge=document.getElementById("usrAge").value;
		var userAddress=document.getElementById("usrAddress").value;


 		var checkMessage=checkUserName(userName)+checkUserId(userID)+checkAge(userAge)+checkAddress(userAddress);
	 		if(checkMessage==""){
	 			return true;
	 		}
	 		else{
	 			var str="<br />"+checkMessage+" please check it again."+"<br /><br />";
				document.getElementById('message').innerHTML=str;
				document.getElementById('message').style.fontSize="20px"
				$("#modal").modal('show');
				return false;
	 		}
	 		return false;
		}

	</script>

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
