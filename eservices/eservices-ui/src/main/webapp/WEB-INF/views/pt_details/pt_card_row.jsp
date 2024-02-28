<%--
  Created by IntelliJ IDEA.
  User: Raya
  Date: 12.12.2019 г.
  Time: 14:51
  To change this template use File | Settings | File Templates.
--%>
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
