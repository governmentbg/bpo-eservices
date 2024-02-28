<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<sec:authorize var="authenticatedUser" access="isAuthenticated()" />

	<header>
		<%
			if("true".equals(System.getProperty("jboss.cluster.debug"))) {
		%>
				<p>Debug is: <%=System.getProperty("jboss.cluster.debug") %> / nodeId: <%=System.getProperty("jboss.server.name") %></p>
		<%
			}
		%>
		<spring:eval expression="@propertyConfigurer.getProperty('environment')" var="env"/>
		<spring:eval expression="@propertyConfigurer.getProperty('portal.main.page.eservices')" var="portalUrl"/>
		<a class="${env eq 'test' ? 'office-logo-test' : 'office-logo'}" href="${portalUrl}"></a>
		<div class="header-row-1">
			<div id="language-selector">
				<div class="btn-group btn-combo">
					<button type="button" class="btn btn-default btn-bpo-customized btn-value" value="${localeComponent.getLocale(pageContext.request).value}">${localeComponent.getLocale(pageContext.request).label}</button>
					<button type="button" class="btn btn-default btn-bpo-customized dropdown-toggle"
							data-toggle="dropdown">
						<span class="caret"></span>
					</button>
					<ul class="dropdown-menu">
						<c:forEach items="${localeComponent.availableLocale}" var="available">
							<li><a href="${available.value}">${available.label}</a></li>
						</c:forEach>
					</ul>
				</div>
			</div>
			<div class="login-box">
				<div><a href=" <spring:message code='layout.header.user.manual.url' />" target="_blank"style="float: left; margin-right: 40px; font-size: 14pt; font-style: italic; color: rgb(68, 68, 68);">
					<spring:message code="layout.header.user.manual" /> </a></div>
				<c:if test="${authenticatedUser}">
					<div id="login-info">
							<%--<span id="login-info-greeting"><spring:message code="layout.header.greeting"/></span> --%>
						<span
								id="login-info-user"><sec:authentication property="principal.username"/> </span>
					</div>
				</c:if>
				<c:if test="${not empty configurationServiceDelegator.securityEnabled && configurationServiceDelegator.securityEnabled}">
					<c:choose>
						<c:when test="${authenticatedUser}">
							<a href="j_spring_security_logout" class="logoutLink" id="logout">
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
	<c:if test="${not empty _csrf.parameterName}">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	</c:if>
</form>

<!-- It will be used whenever hiddenform is not available -->
<form:form id="headerForm">
    <div class="navigationPanel">
		<input type="hidden" name="userLanguage" id="headerLanguageInput" value="${localeComponent.getLocale(pageContext.request).value}"/>
        <input id="ChangeLanguageButton" type="submit" name="_eventId" value="ChangeLanguage" style="display:none;"/>
        <input id="GlobalLoginButton" type="submit" name="_eventId" value="GlobalLogin" style="display:none;"/>
    </div>
</form:form>
