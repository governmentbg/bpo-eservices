<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<span>
	<span class="flMessageError">
		<c:if test="${not empty exception}">
			<spring:message code="${exception.errorCode}"/>
		</c:if>
	</span
</span>