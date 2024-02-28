<%--
  Created by IntelliJ IDEA.
  User: Raya
  Date: 14.5.2019 г.
  Time: 14:19
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<header>
    <span id="transformationCurrentNumber" class="number"></span>

    <h3><spring:message code="claim.title.transformation.${flowModeId}"/></h3>
</header>


<c:set var="showWOTransformation" value="false"/>
<c:set var="showNatTransformation" value="false"/>
<c:set var="showEMTransformation" value="false"/>

<c:set var="office" value="${configurationServiceDelegator.getOffice()}"/>
<c:set var="transformationTypes" value="${configurationServiceDelegator.getTransformationTypes(flowModeId)}"/>
<c:if test="${not empty transformationTypes}">
    <c:forEach var="section_item" items="${transformationTypes}">
        <c:if test="${section_item eq 'transformation_national'}">
            <c:set var="showNatTransformation" value="true"/>
        </c:if>
        <c:if test="${section_item eq 'transformation_international'}">
            <c:set var="showWOTransformation" value="true"/>
        </c:if>
        <c:if test="${section_item eq 'conversion'}">
            <c:set var="showEMTransformation" value="true"/>
        </c:if>
    </c:forEach>
</c:if>

<c:set var="sectionId" value="transformation" scope="request"/>

<label><spring:message code="transformation.form.type"/><span class="requiredTag">*</span></label>
<select id="transformationType">
    <option value=""><spring:message code="transformation.form.action.select"/></option>
    <c:if test="${showNatTransformation}">
        <option value="${office}"><spring:message code="transformation.type.national"/></option>
    </c:if>
    <c:if test="${showWOTransformation}">
        <option value="${wipo_office}"><spring:message code="transformation.type.wo"/></option>
    </c:if>
    <c:if test="${showEMTransformation}">
        <option value="${euipo_office}"><spring:message code="transformation.type.em"/></option>
    </c:if>
</select>
