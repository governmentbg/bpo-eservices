<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>


<form:form id="hiddenForm">
    <input type="hidden" name="_eventId" id="hiddenElement"/>

    <section class="next">
        <ul>
            <li>
                <button class="text-big navigateBtn" type="button"
                        id="RefreshButton" style="display:none;" rel="refresh" data-val="Refresh">
                </button>
            </li>
            <li>
                <button type="button" class="text-big navigateBtn" rel="next" data-val="Next">
                    <spring:message code="layout.button.next"></spring:message>
                    <i class="next-icon"></i>
                </button>
            </li>
        </ul>
    </section>
</form:form>
