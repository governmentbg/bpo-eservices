<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
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
        <div class="reviewHowto">
            <span class="requiredTag">*</span> <spring:message code="review.howto"/>
        </div>
        <br/>
        <ul>
            
            <c:if test="${!flowBean.readOnly}">
	            <li>
	                <a class="navigateBtn" data-val="Previous" rel="prev">
	                    <spring:message code="layout.button.previous"></spring:message>
	                </a>
	            </li>
            </c:if>
            <li>
                <button id="paymentSubmitButton" type="button" class="navigateBtn" data-val="${buttonDataVal }" rel="next">
                    <spring:message code="${buttonMessageVal }"/>
                    <i class="next-icon"></i>
                </button>
             </li>
        </ul>
    </section>
</form:form>