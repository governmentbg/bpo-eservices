<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<c:choose>
	<c:when test="${inventorForm.mayWaiver}">
		<c:set var="checkWaiverDisabled" value="false" />
	</c:when>
	<c:otherwise>
		<c:set var="checkWaiverDisabled" value="true" />
	</c:otherwise>
</c:choose>

<input type="hidden" id="inventorWaiverHidden" value="${inventorForm.waiver}"/>

<c:set var="sectionId" value="inventor" scope="request"/>
<component:checkbox id="checkWaiver" path="waiver" checkRender="true" labelTextCode="inventor.field.waiverRightToBeCited" disabled="${checkWaiverDisabled}" />
