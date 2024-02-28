<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<td data-val='Number'>${param.rowId}</td>
<td data-val='Application'>${param.tmApplication}</td>
<c:choose>
	<c:when test="${empty param.tmImgURI}">	
		<td data-val='Applicant'>${param.applicant}</td>					
	</c:when>
	<c:otherwise>
		<td data-val='Applicant'><a class="imagezoom" title="<img src='${param.tmImgURI}'>">${param.applicant} <img src="resources/img/imagethumb.png"></a></td>
	</c:otherwise>
</c:choose>
<c:if test="${param.flowModeId  != 'tm-objection' && param.flowModeId  != 'tm-appeal'}">
	<td data-val='Date'>${param.tmDate}</td>
</c:if>
<td data-val='Kind'>${param.tmKind}</td>

<td data-val="GS">
	<div id="expandGSReview${param.rowId}" class="icon-arrow-down" style="cursor:pointer;font-weight: normal; " data-val="${param.rowId}" data-rownum="${param.rowId}"></div>
	<div id="collapseGSReview${param.rowId}" class="icon-arrow-up" style="display:none; cursor:pointer;font-weight: normal;" data-val="${param.rowId}" data-rownum="${param.rowId}"></div>
</td>
