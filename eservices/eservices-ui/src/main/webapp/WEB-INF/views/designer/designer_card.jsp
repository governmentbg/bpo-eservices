<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<td data-val='number'>${param.rowId}</td>
<td data-val='type'>
	<spring:message code="designer.naturalPerson.type"/>
</td>
<td data-val='name'>
<c:set var="storename" value="${param.designerName}" scope="request" />
									<c:choose>
										<c:when test="${storename.length()>10}">
											<c:out value="${storename.substring(0,10)}..." />
										</c:when>
										<c:otherwise>
											<c:out value="${storename}" />
										</c:otherwise>
									</c:choose>
</td>
<td data-val='country'>${param.designerCountry}</td>
<td data-val='options'>
        <a id="editDesignerButton" class="edit-icon"
           data-val="${param.designerId}" data-rownum="${param.rowId}"></a>
        <a id="deleteDesignerButton" class="remove-icon" data-val="${param.designerId}"></a>
</td>
