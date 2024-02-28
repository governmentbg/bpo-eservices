<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="functions" uri="/WEB-INF/tags/functions/functions.tld" %>

<td data-val="number">${param.rowId}</td>
<c:if test="${param.showDesignerIdInTable}">
	<td data-val='id'>
		<c:choose>
			<c:when test="${param.designerIsImported}">${param.designerId}</c:when>
			<c:otherwise>-</c:otherwise>
		</c:choose>
	</td>
</c:if>
<td data-val="name">${param.designerName}</td>
<c:if test="${functions:isMultipleDesignApplication(flowBean)}">
	<td data-val="designNumbers">${param.designerDesignNumbers}</td>
</c:if>	
<td data-val="options">
    <a class="edit-icon" id="editDesignerButton" data-val="${param.designerId}" data-rownum=${param.rowId}></a>
    <a class="remove-icon" id="deleteDesignerButton" data-val="${param.designerId}"></a>
</td>