<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html lang="en">
	<head>
	    <meta charset="utf-8">
	    <title></title>
	    <meta name="description" content="">
	    <meta name="author" content="">
	    <meta name="viewport" content="width=device-width, initial-scale=1.0">		
	    
		<link rel="stylesheet" href="/e-services/resources/css/OpenSansOnly.css" type="text/css">
	    <link rel="stylesheet" href="/e-services/resources/css/ext.css">
	   	<link rel="stylesheet" href="/e-services/resources/css/fsp.css">
	    <script type="text/javascript" src="/e-services/resources/scripts/jquery-1.7.1.min.js"></script>
	    <script type="text/javascript" src="/e-services/resources/scripts/jquery-ui-1.8.18.custom.min.js"></script>
	    <script type="text/javascript" src="/e-services/resources/scripts/jquery.validate.js"></script>
	    <script type="text/javascript" src="/e-services/resources/scripts/jquery.validate.internationalization.js"></script>
		<script type="text/javascript" src="/e-services/resources/scripts/developers/application.js"></script>
		<script type="text/javascript" src="/e-services/resources/scripts/developers/common.js"></script>
		<script type="text/javascript" src="/e-services/resources/scripts/developers/claims.js"></script>
		<script type="text/javascript" src="/e-services/resources/scripts/developers/nationalSearchReport.js"></script>
	</head>

	<jsp:include page="/WEB-INF/views/header.jsp" />

	<div class="main">		
		<div id="main" role="main">
			<section id="basicPage">
				<div id="centerCtn" class="noCol welcomePage">
					<h2 class="flContentHeader">
						The e-service you are trying to access does not exist
					</h2>					
				</div>
			</section>
		</div>
	</div>
	<jsp:include page="/WEB-INF/views/footer.jsp" />
</html>
