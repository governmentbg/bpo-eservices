<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<td data-val='number'>${param.rowId}</td>
<td data-val='signatoryName'>${param.signatoryNameParam}</td>
<td data-val='personCapacity'><spring:message code="${param.personCapacityParam}"/></td>
<td data-val='associatedText'>${param.positionParam.length() > 10 ? param.positionParam.substring(0,10) : param.positionParam}${param.positionParam.length() > 10 ? '...' : ''}</td>
<td data-val='options'>
    <a id="editSignatureButton" class="edit-icon" data-val="${param.signatureId}" data-rownum="${param.signatureId}"></a>
    <a id="deleteSignatureButton" class="remove-icon" data-val="${param.signatureId}"></a>
</td>