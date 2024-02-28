<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>


<form:form id="hiddenForm">
    <input type="hidden" name="_eventId" id="hiddenElement"/>

    <section class="next">
        <ul>
            <li>
                <a class="navigateBtn" rel="prev" data-val="Previous">
                    <spring:message code="layout.button.previous"></spring:message>
                </a>
            </li>
            <li>
                <button id="paymentSubmitButton" type="button" class="text-big" data-val="doPayment" rel="next">
                    <spring:message code="layout.button.accessPayment"/>
                    <i class="next-icon"></i>
                </button>
            </li>
        </ul>
    </section>
</form:form>
