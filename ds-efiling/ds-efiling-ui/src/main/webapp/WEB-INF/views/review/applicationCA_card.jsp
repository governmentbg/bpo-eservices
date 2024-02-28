<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<td data-val='number'>${param.rowId}</td>
<td data-val='name'>${param.applicationCAName}</td>
<td data-val='country'>${param.applicationCACountry}</td>
<td data-val='options'>
        <a id="editApplicationCAButton"   class="edit-icon"   data-val="${param.applicationCAId}" data-rownum="${param.rowId}"></a>
        <a id="deleteApplicationCAButton" class="remove-icon" data-val="${param.applicationCAId}"></a>
</td>