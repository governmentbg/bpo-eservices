<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<div id="selectAppToImport" class="import-options">
    <c:if test="${not empty applicationsList}">
        <div class="alert alert-danger" id="warningMultipleAppsDiv" style="margin-top:20px">
            <spring:message code="multiple.applications.for.select.one" arguments="${registrationNumber}"/>
        </div>
        <ul>
            <c:forEach var="app" items="${applicationsList}">
                <li>
                    <label>
                        <input type="radio" name="selectedApplicationId" value="${app}" />
                        <c:out value="${app}"/>
                    </label>
                </li>
            </c:forEach>
        </ul>
    </c:if>

</div>