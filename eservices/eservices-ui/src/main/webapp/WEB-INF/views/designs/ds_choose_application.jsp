<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<div class="designChooseApplication">
    <div class="alert alert-danger">
        <spring:message code="multiple.applications.for.select.one" arguments="${registrationNumber}"/>
    </div>
    <div>
        <c:forEach var="app" items="${applicationsList}">
            <button class="importDesignByAppIdBtn btn btn-primary" style="width:100px" data-app="${app}"><c:out value="${app}"/></button>
        </c:forEach>
    </div>
</div>