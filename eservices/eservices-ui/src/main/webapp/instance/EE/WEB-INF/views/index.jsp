<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<body>
	<c:forEach var="flow" items="${flows}">
		<h2>
			<a href="<c:url value="${flow}.htm"/>"><spring:message code="${flow}.layout.mainTitle"/></a>
		</h2>
	</c:forEach>
</body>
</html>