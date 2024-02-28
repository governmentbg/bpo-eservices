<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<td data-val="number">${param.rowId}</td>
<c:if test="${param.showInventorIdInTable}">
	<td data-val='id'>
		<c:choose>
			<c:when test="${param.inventorIsImported}">${param.inventorId}</c:when>
			<c:otherwise>-</c:otherwise>
		</c:choose>
	</td>
</c:if>
<td data-val="name">${param.inventorName}</td>
<td data-val="options">
    <a class="edit-icon editInventorButton" data-val="${param.inventorId}" data-rownum=${param.rowId}></a>
    <a class="remove-icon deleteInventorButton" data-val="${param.inventorId}"></a>
</td>