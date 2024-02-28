<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<td data-val='number'>${param.rowId}</td>
<td data-val='tmAppNum'>${param.tmApplicationNumber}</td>
<td data-val='extent'><spring:message code="${flowModeId }.gshelper.form.extent.${param.extent}"/></td>

<td data-val='options'>
        <a id="editGshelperButton"   class="edit-icon"   data-val="${param.gshelperId}" data-rownum="${param.rowId}"></a>
        <a id="deleteGshelperButton" class="remove-icon" data-val="${param.gshelperId}"></a>
</td>