<%@page import="java.io.*" %>
<%@page import="org.springframework.webflow.execution.repository.NoSuchFlowExecutionException" %>
<%@page import="org.springframework.webflow.execution.repository.FlowExecutionRestorationFailureException" %>
<%@page import="org.springframework.webflow.execution.repository.BadlyFormattedFlowExecutionKeyException" %>
<%@page import="org.apache.log4j.Logger" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%
  Logger log = Logger.getLogger ("eu.ohim.sp.error_jsp");
  log.error ("Exception on JSP page");
  Throwable e = (Throwable)request.getAttribute("javax.servlet.error.exception");
  %>
<html>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/jquery-1.7.1.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/jquery-ui-1.8.18.custom.min.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/jquery.validate.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/jquery.validate.internationalization.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/application.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/common.js"></script>
<script type="text/javascript"
	src="chrome-extension://kajfghlhfkcocafkcjlajldicbikpgnp/catcher.js"><!-- script injected by Request Maker --></script>
<head>
<title>SP - Error report</title>
<style>
<!--
H1 {
	font-family: Tahoma, Arial, sans-serif;
	color: white;
	background-color: #525376;
	font-size: 22px;
}

H2 {
	font-family: Tahoma, Arial, sans-serif;
	color: white;
	background-color: #525D76;
	font-size: 16px;
}

H3 {
	font-family: Tahoma, Arial, sans-serif;
	color: white;
	background-color: #525D76;
	font-size: 14px;
}

BODY {
	font-family: Tahoma, Arial, sans-serif;
	color: black;
	background-color: white;
}

B {
	font-family: Tahoma, Arial, sans-serif;
	color: white;
	background-color: #525D76;
}

P {
	font-family: Tahoma, Arial, sans-serif;
	background: white;
	color: black;
	font-size: 12px;
}

A {
	color: black;
}

A.name {
	color: black;
}

HR {
	color: #525D76;
}
-->
</style>
</head>
<body onload="alertSessionTimeOut()">

<input id="inputUrlResponse" type="hidden" value="<%=(String)request.getAttribute("javax.servlet.forward.request_uri")%>"/>

<%
  if (e != null) {
	  String stackTrace = "";
	  if (e instanceof NoSuchFlowExecutionException || e instanceof BadlyFormattedFlowExecutionKeyException || e instanceof FlowExecutionRestorationFailureException) {
		  stackTrace = "Your session is invalid";
		 // response.sendRedirect((String)request.getAttribute("javax.servlet.forward.request_uri"));
	      }
	  
  }
%>
	<div id="session_time_out" style="display:none">
        <spring:message code="general.messages.session.timeout"/>
    </div>
</body>
</html>