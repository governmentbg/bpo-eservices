<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<td data-val='number'>${param.rowId}</td>
<!-- <td data-val='nameLegal'>${param.obNameLegal}</td> -->
<td data-val='main'>
<c:choose>
    <c:when test="${empty param.earlierRightType}">
        ${param.legalGrounds}
    </c:when>
    <c:otherwise>
    	${param.earlierRightType }
    </c:otherwise>
</c:choose>
</td>

