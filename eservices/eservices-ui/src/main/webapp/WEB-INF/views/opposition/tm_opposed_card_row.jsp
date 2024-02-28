<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<td data-val='Number'>${param.rowId}</td>
<td data-val='Application'>${param.tmoApplication}</td>
<td data-val='Date'>${param.tmoDate}</td>
<td data-val='Type'>${param.tmoType}</td>
<td data-val='Name'>${param.tmoName}</td>
<td data-val='Options'>
	<a id="editOpposedTMButton" class="edit-icon" data-val="${param.tmoId}" data-rownum="${param.rowId}"></a>
    <a id="deleteOpposedTMButton" class="remove-icon" data-val="${param.tmoId}"></a>
</td>