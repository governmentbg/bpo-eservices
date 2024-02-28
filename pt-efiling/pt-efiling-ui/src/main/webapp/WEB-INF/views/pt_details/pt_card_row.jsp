<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<td data-val='number'>${param.rowId}</td>
<td data-val='application'>${param.applicationNumber}</td>
<td data-val='title'>${param.title}</td>

<td data-val='status'>${param.status}</td>
<td data-val='options'>
    <a class="editPTButton edit-icon" data-val="${param.id}" data-rownum="${param.rowId}"></a>
    <a class="deletePTButton remove-icon" data-val="${param.id}"></a>
</td>
