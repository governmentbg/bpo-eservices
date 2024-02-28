<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!doctype html>
<!--[if lt IE 7]> <html class="ie6" lang="en"> <![endif]-->
<!--[if IE 7]> <html class="ie7" lang="en"> <![endif]-->
<!--[if IE 8]> <html class="ie8" lang="en"> <![endif]-->
<!--[if gt IE 8]><!-->
<html lang="en">
<!--<![endif]-->
<head>
    <meta charset="UTF-8">
    <title><tiles:insertAttribute name="title" ignore="true"/></title>
    <meta name="description" content="">
    <meta name="author" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/OpenSansOnly.css" type="text/css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/ext.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/sp.css">
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/jquery-1.11.3.js"></script>

    <%--VERY IMPORTANT FOR IE8 COMPATIBILITY DO NOT REMOVE--%>
    <!-- Make ie recognize html5 elements -->
    <!--[if lt IE 9]>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/html5.js"></script>
    <![endif]-->
    <%--END OF > VERY IMPORTANT FOR IE8 COMPATIBILITY DO NOT REMOVE--%>

    <style type="text/css">
        div.error-box{
            margin:50px auto;
            width: 720px;
            overflow: scroll;
        }
        div.panel {
            max-width: 50%;
            margin: auto;
            overflow: hidden;
        }
        .flMessageError {
            max-width: 600px;
        }
    </style>

</head>

<body id="normal" data-spy="scroll" data-target="aside">
<div class="panel panel-default">
    <div class="error-box alert alert-error panel-body">
        <h2>Something went wrong</h2>
        <% if (request.getAttribute("javax.servlet.error.message") != null && request.getAttribute("javax.servlet.error.message").toString().length() != 0) { %>
        <p id="error_msg" class="flMessageError"><strong>Message</strong> <%=request.getAttribute("javax.servlet.error.message") %></p>
        <% } %>
    </div>
</div>

<%
    if(request.getAttribute("session.timeout.url") != null) {
%>
<script type="text/javascript">
    jQuery('#error_msg').addClass('sessionExpired');
    var timer = setTimeout(function() {
        window.location='<%=request.getAttribute("session.timeout.url")%>';
    }, 3000);
</script>
<%
} else {
    for(Cookie c : request.getCookies()) {
        if("sessionExpired".equals(c.getName())) {
%>
<script type="text/javascript">
    jQuery('#error_msg').addClass('sessionExpired');
    jQuery('#error_msg').append(" Session expired!");
    var timer = setTimeout(function() {
        window.location='<%=c.getValue()%>';
    }, 3000);
</script>
<%
            }
        }
    }
%>
</body>
</html>