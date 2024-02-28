<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<sec:authorize var="authenticatedUser" access="isAuthenticated()" />

	<header>
		<a class="office-logo" href="https://test-teenused.just.ee"></a>
		<div class="header-row-1">
<!-- 			<div id="language-selector"> -->
<!-- 				<div class="btn-group btn-combo"> -->
<%-- 					<button type="button" class="btn btn-primary btn-value" value="${localeComponent.getLocale(pageContext.request).value}">${localeComponent.getLocale(pageContext.request).label}</button> --%>
<!-- 					<button type="button" class="btn btn-primary dropdown-toggle" -->
<!-- 							data-toggle="dropdown"> -->
<!-- 						<span class="caret"></span> -->
<!-- 					</button> -->
<!-- 					<ul class="dropdown-menu"> -->
<%-- 						<c:forEach items="${localeComponent.availableLocale}" var="available"> --%>
<%-- 							<li><a href="${available.value}">${available.label}</a></li> --%>
<%-- 						</c:forEach> --%>
<!-- 					</ul> -->
<!-- 				</div> -->
<!-- 			</div> -->
			<c:if test="${authenticatedUser}">
				
				<c:set var="detailsUser"><sec:authentication property="details"/></c:set>
				<c:choose>
					<c:when test="${fn:contains(detailsUser, 'RESTWeb')}">
						<c:set var="userHeader" value="details.dataUserName"/>
					</c:when>
					<c:otherwise>
						<c:set var="userHeader" value="principal.username"/>
					</c:otherwise>
				
				</c:choose>
				<div id="login-info">
					<span id="login-info-greeting"><spring:message code="layout.header.greeting"/></span> <span
						id="login-info-user"><sec:authentication property="${userHeader}"/> </span>
				</div>
			</c:if>
			<c:if test="${not empty configurationServiceDelegator.securityEnabled && configurationServiceDelegator.securityEnabled}">
				<c:choose>
					<c:when test="${authenticatedUser}">
						<a href="j_spring_security_logout" id="logout">
							<spring:message code="layout.header.userOptions.logout"/>
						</a>
					</c:when>
					<c:otherwise>
						<a href="#" class="loginLink" id="logout">
							<spring:message code="layout.header.userOptions.login"/>
						</a>
					</c:otherwise>
				</c:choose>
			</c:if>
		</div>
		<div class="header-row-2"></div>
		
	</header>

<input type="hidden" id="error_code" value="${error_code}" />
<input type="hidden" id="error_code_param" value="${param.error_code}" />
<script type="text/javascript">
	<c:if test="${not empty error_code}">
		$(document).ready(function() {
			showWarningModal('<fmt:message key="${error_code}"/>');
		});
	</c:if>
	<c:if test="${empty error_code && not empty param.error_code}">
		$(document).ready(function() {
			showWarningModal('<fmt:message key="${param.error_code}"/>');
		});
	</c:if>
</script>

<form class="mainForm">
	<input id="languageInput" type="hidden" name="userLanguage" value="${localeComponent.getLocale(pageContext.request).value}"/>
</form>

<!-- It will be used whenever hiddenform is not available -->
<form:form id="headerForm">
    <div class="navigationPanel">
		<input type="hidden" name="userLanguage" id="headerLanguageInput" value="${localeComponent.getLocale(pageContext.request).value}"/>
        <input id="ChangeLanguageButton" type="submit" name="_eventId" value="ChangeLanguage" style="display:none;"/>
        <input id="GlobalLoginButton" type="submit" name="_eventId" value="GlobalLogin" style="display:none;"/>
    </div>
</form:form>