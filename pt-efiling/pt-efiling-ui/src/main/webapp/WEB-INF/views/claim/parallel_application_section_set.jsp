<%--
  Created by IntelliJ IDEA.
  User: Raya
  Date: 14.5.2019 г.
  Time: 14:55
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<spring:eval scope="request" var="euipo_office" expression="@propertyConfigurer.getProperty('euipo.office')"/>
<spring:eval scope="request" var="wipo_office" expression="@propertyConfigurer.getProperty('wipo.office')"/>

<c:choose>
    <c:when test="${country eq wipo_office}">
        <c:set var="sectionId" value="international_parallel_application" scope="request"/>
        <c:set var="importParallelCall" value="importParallelWO" scope="request"/>
    </c:when>
    <c:when test="${country eq euipo_office}">
        <c:set var="sectionId" value="european_parallel_application" scope="request"/>
        <c:set var="importParallelCall" value="importParallelEM" scope="request"/>
    </c:when>
    <c:otherwise>
        <c:set var="sectionId" value="national_parallel_application" scope="request"/>
        <c:set var="importParallelCall" value="importParallelNational" scope="request"/>
    </c:otherwise>
</c:choose>