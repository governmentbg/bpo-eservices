<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<span class="importTrademarkWarning">
		<c:set var="errorMessageCode" value="error.importing.trademark" />
	<c:if test="${not empty errorCode}">
		<c:set var="errorMessageCode" value="${errorCode}" />
	</c:if>
	<spring:message code="${errorMessageCode}"/>
</span>