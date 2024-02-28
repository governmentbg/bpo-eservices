<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<td data-val='number'>${param.rowId}</td>
<td data-val='kind'><spring:message code="licence.field.licenceKind.${param.kind }"/>
	<c:if test="${param.kind == 'Exclusive' }">
		<spring:message code="licence.field.sublicence.${param.sublicence }"/>
	</c:if>
</td>
<td data-val="territory"><spring:message code="licence.field.territoryLimitationIndicator.${param.territory }"/></td>
<c:if test="${param.upToExpiration == false }">
	<td data-val="endDate">${param.endDate }</td>
</c:if>
<c:if test="${param.upToExpiration == true }">
	<td data-val="endDate"><spring:message code="licence.field.periodLimitationIndicator.upToExpiration.${flowModeId }"/></td>
</c:if>
<c:if test="${flowModeId == 'tm-licence' }">
	<td data-val="gs"><spring:message code="licence.field.extent.${param.extent }"/></td>
	<td data-val="GS">
		<div id="expandGshelperReview${param.rowId}" class="icon-arrow-down-gshelper" style="cursor:pointer;font-weight: normal; " data-val="${param.rowId}" data-rownum="${param.rowId}"></div>
		<div id="collapseGshelperReview${param.rowId}" class="icon-arrow-up-gshelper" style="display:none; cursor:pointer;font-weight: normal;" data-val="${param.rowId}" data-rownum="${param.rowId}"></div>
	</td>
</c:if>