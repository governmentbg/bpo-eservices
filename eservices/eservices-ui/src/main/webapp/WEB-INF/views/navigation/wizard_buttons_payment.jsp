<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>


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
            <li>
                <button id="paymentSubmitButton" type="button" class="text-big navigateBtn" data-val="${buttonDataVal }" rel="next">
                    <spring:message code="${buttonMessageVal }"/>
                    <i class="next-icon"></i>
                </button>
            </li>
        </ul>
    </section>
</form:form>
