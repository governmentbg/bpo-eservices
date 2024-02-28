<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<td data-val='Number'>${param.rowId}</td>
<td data-val='Application'>${param.tmApplication}</td>
<c:if test="${flowScopeDetails.flowModeId != 'tm-opposition' && flowScopeDetails.flowModeId != 'tm-objection'&& flowScopeDetails.flowModeId != 'tm-withdrawal' && flowScopeDetails.flowModeId != 'tm-appeal'}">
	<td data-val='RegNumber' style="word-break: break-all;">${param.tmRegistration}</td>
</c:if>
<c:choose>
	<c:when test="${empty param.tmImgURI}">
		<td data-val='Applicant'>${param.applicant}</td>
	</c:when>
	<c:otherwise>
		<td data-val='Applicant'>
			<c:choose>
				<c:when test="${param.tmRepresentationType == 'application/pdf'}">
					<a>${param.applicant} <img src="resources/img/pdf.png"></a>
				</c:when>
				<c:when test="${param.tmRepresentationType == 'image/png' || param.tmRepresentationType == 'image/jpeg'}">
					<a class="imagezoom" title="<img src='${param.tmImgURI}'>">${param.applicant} <img src="resources/img/imagethumb.png"></a>
				</c:when>
				<c:otherwise>
					<a class="imagezoom" title="<img src='${param.tmImgURI}'>">${param.applicant} <img src="resources/img/imagethumb.png"></a>
				</c:otherwise>
			</c:choose>
		</td>
	</c:otherwise>
</c:choose>
<c:if test="${flowScopeDetails.flowModeId  != 'tm-objection' && flowScopeDetails.flowModeId  != 'tm-withdrawal'&& flowScopeDetails.flowModeId  != 'tm-appeal'}">
	<td data-val='Date'>${param.tmDate}</td>
</c:if>
<td data-val='type'>${param.tmType}</td>
<td data-val='Status'>${param.tmStatus}</td>
<td data-val='Options'>
	<a id="editTMButton" class="edit-icon" data-val="${param.tmId}" data-rownum="${param.rowId}"></a>
	<a id="deleteTMButton" class="remove-icon" data-val="${param.tmId}"></a>
</td>