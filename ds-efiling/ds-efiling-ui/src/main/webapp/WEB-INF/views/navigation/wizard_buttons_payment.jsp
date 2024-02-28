<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form:form id="hiddenForm">
    <c:set var="payLaterEnabled" value="${configurationServiceDelegator.isServiceEnabled('Payment_Later')}" />
    <c:set var="payNowEnabled" value="${configurationServiceDelegator.isServiceEnabled('Payment_Now')}" />
    <c:set var="submitEnabled" value="${configurationServiceDelegator.isServiceEnabled('Submit')}" />
    <c:set var="buttonDataVal" value="${payLaterEnabled && !payNowEnabled ? 'doSubmit': 'doPayment' }" />
    <c:set var="buttonMessageVal" value="${payLaterEnabled && !payNowEnabled ? 'layout.button.submit': 'layout.button.accessPayment' }" />

    <input type="hidden" name="_eventId" id="hiddenElement"/>
    <input type="hidden" name="submitApplication" value="${submitEnabled.toString()}" />

    <section class="next">
        <ul>
            <li>
                <a class="navigateBtn" rel="prev" data-val="Previous">
                    <spring:message code="layout.button.previous"></spring:message>
                </a>
            </li>
                <%--
                <sec:authorize access="hasRole('Submit_Application')" var="security_submit_application" />
                <c:if test="${security_submit_application || !configurationServiceDelegator.securityEnabled}">
                 --%>
            <li>
                <button id="paymentSubmitButton" type="button" class="text-big navigateBtn" data-val="${buttonDataVal }" rel="next">
                    <spring:message code="${buttonMessageVal }"/>
                    <i class="next-icon"></i>
                </button>
            </li>
                <%--
            </c:if>
             --%>
        </ul>
    </section>
</form:form>
