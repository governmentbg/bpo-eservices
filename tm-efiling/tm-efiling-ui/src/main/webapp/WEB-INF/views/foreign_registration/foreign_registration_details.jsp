<%--
  Created by IntelliJ IDEA.
  User: Raya
  Date: 5.5.2022 г.
  Time: 18:00
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<section id="foreignRegistrationFormSection" class="review">
    <form:form id="foreignRegistrationForm" cssClass="formEF" modelAttribute="foreignRegistrationForm">

        <c:set var="sectionId" value="foreign_registration" scope="request"/>

        <input type="hidden" id="formReturned" value="true"/>
        <form:hidden id="hiddenFormId" path="id"/>

        <component:input path="registrationNumber" checkRender="true" labelTextCode="foreign.registration.registrationNumber" />
        <component:input path="registrationDate" checkRender="true" id="frDate"
                         labelTextCode="foreign.registration.registrationDate"
                         formTagClass="filing-date-input"/>
        <component:select
                items="${configurationServiceDelegator['countries']}"
                labelTextCode="foreign.registration.registrationCountry"
                path="registrationCountry" itemLabel="value" itemValue="code"
                itemFilter="isPriority" checkRender="true"/>

        <ul class="controls">
            <li>
                <a class="cancelForeignRegistration"><spring:message
                        code="foreign.registration.form.action.cancelAdd.top"/></a>
            </li>
            <li>
                <button class="addForeignRegistration" type="button">
                    <i class="add-icon"/>
                    <spring:message code="foreign.registration.action.save"/>
                </button>
            </li>
        </ul>

    </form:form>
</section>