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
    <span id="parallelApplicationCurrentNumber" class="number"></span>

    <h3><spring:message code="claim.title.parallelApplication"/></h3>
</header>


<c:set var="showWOParallel" value="false"/>
<c:set var="showNatParallel" value="false"/>
<c:set var="showEMParallel" value="false"/>

<c:set var="office" value="${configurationServiceDelegator.getOffice()}"/>
<c:set var="parallelApplicationTypes" value="${configurationServiceDelegator.getParallelApplicationTypes(flowModeId)}"/>
<c:if test="${not empty parallelApplicationTypes}">
    <c:forEach var="section_item" items="${parallelApplicationTypes}">
        <c:if test="${section_item eq 'national_parallel_application'}">
            <c:set var="showNatParallel" value="true"/>
        </c:if>
        <c:if test="${section_item eq 'international_parallel_application'}">
            <c:set var="showWOParallel" value="true"/>
        </c:if>
        <c:if test="${section_item eq 'european_parallel_application'}">
            <c:set var="showEMParallel" value="true"/>
        </c:if>
    </c:forEach>
</c:if>

<c:set var="sectionId" value="parallel_application" scope="request"/>

<label><spring:message code="parallelApplication.form.type"/><span class="requiredTag">*</span></label>
<select id="parallelApplicationType">
    <option value=""><spring:message code="parallelApplication.form.action.select"/></option>
    <c:if test="${showNatParallel}">
        <option value="${office}"><spring:message code="parallelApplication.type.national"/></option>
    </c:if>
    <c:if test="${showWOParallel}">
        <option value="${wipo_office}"><spring:message code="parallelApplication.type.wo"/></option>
    </c:if>
    <c:if test="${showEMParallel}">
        <option value="${euipo_office}"><spring:message code="parallelApplication.type.em"/></option>
    </c:if>
</select>
