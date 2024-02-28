<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<td data-val='number'>${param.rowId}</td>
<td data-val='regNumber'>${param.regRegNumber}</td>
<td data-val='regDate'>${param.regRegDate}</td>
<td data-val='country'>${param.regCountry}</td>
<td data-val='options'>
    <a id="deleteOBButton" class="remove-icon" data-val="${param.regId}"></a>
</td>