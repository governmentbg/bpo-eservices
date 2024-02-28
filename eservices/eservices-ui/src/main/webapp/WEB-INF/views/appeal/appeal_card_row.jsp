<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<td data-val='number'>${param.rowId}</td>
<td data-val='kind'><spring:message code="${flowModeId }.${param.kind }"/></td>
<td data-val="appeal_date">${empty param.date ? "-" : param.date}</td>
<c:if test="${fn:endsWith(flowModeId, '-appeal') }">
	<td data-val="appeal_num">${empty param.number ? "-" : param.number}</td>
	<td data-val="appeal_oppo_filing">${empty param.oppoDate ? "-" : param.oppoDate}</td>
</c:if>



<td data-val='options'>
        <a id="editAppealButton"   class="edit-icon"   data-val="${param.appealId}" data-rownum="${param.rowId}"></a>
        <a id="deleteAppealButton" class="remove-icon" data-val="${param.appealId}"></a>
</td>