<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<td data-val='number'>${param.rowId}</td>
<td data-val='tmAppNum'>${param.tmApplicationNumber}</td>
<td data-val='extent'><spring:message code="${flowModeId }.gshelper.form.extent.${param.extent}"/></td>
<td data-val="GS">
	<div id="expandGshelperReview${param.rowId}" class="icon-arrow-down-gshelper" style="cursor:pointer;font-weight: normal; " data-val="${param.rowId}" data-rownum="${param.rowId}"></div>
	<div id="collapseGshelperReview${param.rowId}" class="icon-arrow-up-gshelper" style="display:none; cursor:pointer;font-weight: normal;" data-val="${param.rowId}" data-rownum="${param.rowId}"></div>
</td>