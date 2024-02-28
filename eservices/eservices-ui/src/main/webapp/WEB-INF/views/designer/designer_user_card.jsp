<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<td class="class-check">
	<input name="designerSelected" type="checkbox" value="${param.designerId}" />
</td>
<td data-val='number'>${param.rowId}</td>
<td data-val='type'>
    <spring:message code="designer.naturalPerson.type"/>
</td>
<td data-val='name'><div class="name">${param.designerName}</div></td>
<td data-val='country'>${param.designerCountry}</td>
