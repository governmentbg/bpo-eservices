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
                <a class="resetButton" rel="prev" data-url="${pageContext.request.contextPath}/${flowModeId}.htm">
                    <spring:message code="layout.button.cancel"></spring:message>
                </a>
            </li>
            <sec:authorize access="hasRole('Submit_Application')" var="security_submit_application" />
            <c:if test="${security_submit_application || !configurationServiceDelegator.securityEnabled}">
                <li>
                    <button type="button" class="navigateBtn" rel="next" data-val="Next">
                        <spring:message code="layout.button.next"/>
                        <i class="next-icon"></i>
                    </button>
                </li>
            </c:if>
        </ul>
    </section>
</form:form>