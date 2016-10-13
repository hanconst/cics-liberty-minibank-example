<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<div class="navbar navbar-default navbar-fixed-top">

      <div class="container">
        <div class="navbar-header">
          <a href="../" class="navbar-brand">MiniBank&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</a>
          <button class="navbar-toggle" type="button" data-toggle="collapse" data-target="#navbar-main">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
        </div>
        <div class="navbar-collapse collapse" id="navbar-main">
          <ul class="nav navbar-nav">
          	<li>
              <a href="home.jsp"><i class="glyphicon glyphicon-home"></i>&nbsp Home </a>
            </li>
            <li class="dropdown">
              <a class="dropdown-toggle" data-toggle="dropdown" href="#" id="themes"><i class="fa fa-user"></i>&nbsp Users <span class="caret"></span></a>
              <ul class="dropdown-menu" aria-labelledby="themes">
                <li><a href="createuser.jsp"><i class="fa fa-plus-circle"> </i>&nbsp Create User </a></li>
                <li class="divider"></li>
                <li><a href="queryuser.jsp"><i class="fa fa-search"> </i>&nbsp Query User </a></li>
              </ul>
            </li>
            <li class="dropdown">
              <a class="dropdown-toggle" data-toggle="dropdown" href="#" id="themes"><i class="fa fa-file-text-o"></i>&nbsp Accounts <span class="caret"></span></a>
              <ul class="dropdown-menu" aria-labelledby="themes">
                <li><a href="createaccount.jsp"><i class="fa fa-plus-circle"></i>&nbsp Create Account </a></li>
                <li class="divider"></li>
                <li><a href="queryaccount.jsp"><i class="fa fa-search"></i>&nbsp  Query Account </a></li>
              </ul>
            </li>
            <li class="dropdown">
              <a class="dropdown-toggle" data-toggle="dropdown" href="#" id="themes"><i class="fa fa-credit-card"></i>&nbsp Transactions <span class="caret"></span></a>
              <ul class="dropdown-menu" aria-labelledby="themes">
                <li><a href="deposit.jsp"><i class="fa fa-download"></i>&nbsp Deposit </a></li>
                <li class="divider"></li>
                <li><a href="withdraw.jsp"><i class="fa fa-upload"></i> &nbsp Withdraw </a></li>
                <li class="divider"></li> 
                <li><a href="transfer.jsp"><i class="fa fa-exchange"></i>&nbsp Transfer </a></li>
              </ul>
            </li>
          </ul>

          <ul class="nav navbar-nav navbar-right">
            <li><a href="http://www.ibm.com"><img src ="../img/ibm_logo.png" style="width: 46px"/></a></li>
          </ul>

        </div>
      </div>
    </div>