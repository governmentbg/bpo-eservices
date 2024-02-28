<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form:form id="saveToPCForm" method="POST" action="saveToPC.htm">
	<input type="hidden" name="flowKey" value="<%= request.getParameter("execution") %>"/>
</form:form>

<ul class="aside-options">
	<sec:authorize access="hasRole('Save_Load_Application')" var="security_save_load_application" />
	<sec:authorize access="hasRole('Save_Load_Locally')" var="security_save_load_locally" />
	
	<c:set var="service_save_load" value="${configurationServiceDelegator.isServiceEnabled('Save_Load_Service')}" />
	<c:if test="${service_save_load && (security_save_load_locally || !configurationServiceDelegator.securityEnabled)}">
		<li>
			<a class="aside-option-load fileinput-button loadButton">
				<spring:message code="layout.applicationOptions.load"/>
				<%--<input id="loadApplicationXML_trigger">                --%>
                <input name="loadApplicationXML" type="file" class="submittedAjax uploadLoadApplication" data-url="loadApplication.htm?fileWrapperPath=${fileWrapperPath}&_csrf=${_csrf.token}&flowKey=${empty param.execution?param.flowKey:param.execution}">
   			</a>
		</li>
	</c:if>
	<c:if test="${service_save_load && (security_save_load_application || !configurationServiceDelegator.securityEnabled)}">
	<sec:authorize access="isAuthenticated()">
		<li>
			<a class="aside-option-save saveButton" href="#"><spring:message code="layout.applicationOptions.save" /></a>
		</li>
	</sec:authorize>
	</c:if>
	<c:if test="${service_save_load && (security_save_load_locally || !configurationServiceDelegator.securityEnabled)}">
		<li>
			<a class="aside-option-save saveToPCButton" href="#"><spring:message code="layout.applicationOptions.saveToPC" /></a>
		</li>
	</c:if>
	<li>
		<a class="aside-option-reset resetButton" data-url="${pageContext.request.contextPath}/${flowModeId}.htm">
			<spring:message code="layout.applicationOptions.reset" />
		</a>
	</li>
	<li>
		<a class="aside-option-print printButton" href="#" ><spring:message code="layout.applicationOptions.print" /></a>
	</li>
</ul>
