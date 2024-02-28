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
	   	<link rel="stylesheet" href="/e-services/resources/css/sp.css">
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
		<input type="hidden" id="storedFirstLang" value="sk" />
		    
		<div id="main" role="main">
			<section id="basicPage">
				<div id="centerCtn" class="noCol welcomePage">
					<h2 class="flContentHeader">
						iadost o zápis ochrannej známky do registra
					</h2>
					<form id="formEF" class="flForm formEF mainForm" action="/sp/tm/efiling/wizard.htm?execution=e1s1" method="POST">
						<input type="hidden" name="_eventId" value="Next" id="hiddenElement" />
				

						<div class="row">
							<div class="span6">
								<img src="resources/img/homepage_wizard_img.png" alt="UPV" class="flAlignLeft"/>
							</div>
							<div class="span6">
								<ul class="wizardSteps">
								    <li class="step1">Uetrite cas vyplnením jednoduchého formulára.</li>
								    <li class="step2">Ochranná známka sa prihlasuje pre uvedený ZOZNAM TOVAROV ALEBO SLUIEB, pre ktoré uvaujete pouívat ochrannú známku.</li>
								    <li class="step3">Okrem elektronického podania je potrebné iadost o zápis ochrannej známky do registra vytlacit a zaslat úradu potou.</li>
								    <li class="step4">V prípade obnovenia uloenej prihláky je potrebné stlacit tlacidlo Spustit prihláku na otvorenie uloenej prihláky.</li>
								</ul>
							</div>
						</div>

						<script type="text/javascript">
						$(document).ready(function(){
							 checkBrowserVersionWizard();
						});
						</script>

						<div class="flBox clear">
							<table class="alignCenter">
								<c:forEach var="flow" items="${flows}">
									<tr class="horizontalList slim-m">
							   			<td><spring:message code="${flow}.layout.mainTitle"/></td>
							            <td><a class="darkButton" rel="next" href="<c:url value="${flow}.htm"/>">Spustit prihláku</a></li>
							     	</tr>
						     	</c:forEach>
							</table>
						</div>
						<div class="flFormRow alignCenter" style="display: none;" id="wizard_message_error_browser">
						  <p style="margin-top: 15px">
					            <b>Unfortunately, the electronic trademark application does not work at the current browser version. This site requires Internet Explorer version 8 or later, Firefox version 3.6 or later, or Google Chrome. </b>
					       </p>
						</div>
					</form>
				</div>
			</section>
		</div>
	</div>
	<jsp:include page="/WEB-INF/views/footer.jsp" />
</html>
