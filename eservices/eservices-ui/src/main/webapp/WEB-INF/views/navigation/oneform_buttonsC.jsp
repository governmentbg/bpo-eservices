<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form:form id="hiddenForm">
    <input type="hidden" name="_eventId" id="hiddenElement"/>
    <section class="next">
        <ul>
            <%--
            <li>
                <a class="resetButton" data-url="${pageContext.request.contextPath}/${flowModeId}.htm" rel="cancel">
                    <spring:message code="layout.button.reset"></spring:message>
                </a>
            </li>
             --%>
            
            <c:if test="${!flowBean.readOnly}">
	            <li>
	                <a class="navigateBtn" data-val="Previous" rel="prev">
	                    <spring:message code="layout.button.previous"></spring:message>
	                </a>
	            </li>
            </c:if>
            <li>
                <button id="doSubmitButton" type="button" class="navigateBtn" data-val="doSubmit" rel="prev">
                    <spring:message code="layout.button.accessPayment"></spring:message>
                    <i class="next-icon"></i>
                </button>
            </li>
        </ul>
    </section>
</form:form>