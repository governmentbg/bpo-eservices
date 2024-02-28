<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<span class="importPatentWarning">
    <c:set var="errorMessageCode" value="error.importing.patent" />
	<c:if test="${not empty errorCode}">
        <c:set var="errorMessageCode" value="${errorCode}" />
    </c:if>
	<spring:message code="${errorMessageCode}"/>
</span>
