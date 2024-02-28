<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<p class="flMessageError">
<c:choose>
	<c:when test="${not empty exception and not empty exception.errorCode}">
		<c:choose>
			<c:when test="${not empty exception.args}">
				<spring:message code="${exception.errorCode}" argumentSeparator=";" arguments="${exception.args}"/>
			</c:when>
			<c:otherwise>
				<spring:message code="${exception.errorCode}"/>
			</c:otherwise>
		</c:choose>
	</c:when>
    <c:otherwise>
        <c:if test="${not empty exception and not empty exception.message}">
            <spring:message code="${exception.message}"/>
        </c:if>
    </c:otherwise>
</c:choose>
</p>