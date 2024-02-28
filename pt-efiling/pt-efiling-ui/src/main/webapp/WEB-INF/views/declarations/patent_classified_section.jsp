<%--
  Created by IntelliJ IDEA.
  User: Raya
  Date: 18.4.2019 г.
  Time: 11:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.Arrays" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<section class="classified" id="classified">
    <c:set var="sectionId" value="patent_classified" scope="request" />
    <header>
        <a href="#classified" class="anchorLink">
        </a>

        <h2>
            <spring:message code="classified.patent.title.${flowModeId}"/>
        </h2>
    </header>

    <form:form class="mainForm formEf" modelAttribute="flowBean.mainForm">
        <div>

            <c:if test="${flowModeId eq 'pt-efiling' || flowModeId eq 'um-efiling'}">
                <div class="alert alert-danger">
                    <spring:message code="${flowModeId}.classified.forbidden"/>
                </div>
            </c:if>

            <component:checkbox path="classifiedForNationalSecurity" checkRender="true"
                                labelTextCode="application.classified.field.classifiedForNationalSecurity" id="classifiedForNationalSecurity"/>

            <component:checkbox path="classifiedForDefense" checkRender="true"
                                labelTextCode="application.classified.field.classifiedForDefense" id="classifiedForDefense"/>

        </div>
    </form:form>
</section>
