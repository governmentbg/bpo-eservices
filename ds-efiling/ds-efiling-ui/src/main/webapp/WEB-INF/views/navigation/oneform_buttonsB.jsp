<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form:form id="hiddenForm">
    <input type="hidden" name="_eventId" id="hiddenElement"/>
    <section class="next">
        <ul>
            <li>
                <a class="resetButton" data-url="${pageContext.request.contextPath}/${flowModeId}.htm" rel="cancel">
                    <spring:message code="layout.button.cancel"></spring:message>
                </a>
            </li>
            <li>
                <a class="navigateBtn" data-val="Previous" rel="prev">
                    <spring:message code="layout.button.previous"></spring:message>
                </a>
            </li>
            <%--
            <sec:authorize access="hasRole('Submit_Application')" var="security_submit_application" />
            <c:if test="${security_submit_application || !configurationServiceDelegator.securityEnabled}">
             --%>
	            <li>
	                <button id="paymentSubmitButton" type="button" class="navigateBtn" data-val="doPayment" rel="prev">
	                    <spring:message code="layout.button.accessPayment"></spring:message>
	                    <i class="next-icon"></i>
	                </button>
	            </li>
	        <%--
			</c:if>	
			--%>         
        </ul>
    </section>
</form:form>