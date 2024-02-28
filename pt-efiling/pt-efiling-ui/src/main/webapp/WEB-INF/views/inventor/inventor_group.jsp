<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<form:form id="inventorGroup" cssClass="formEF" modelAttribute="inventorForm">

	<jsp:include page="inventor_sharedCodeTop.jsp" />

	<jsp:include page="inventor_sharedCodeWaiverCheck.jsp" />
	
	<div id="inventor_notWaive">

		<jsp:include page="inventor_sharedCodeBelongsCheck.jsp" />

		<div id="inventor_group">
			<c:set var="sectionId" value="inventor_group" scope="request" />
			
			<component:input path="groupName" checkRender="true" labelTextCode="inventor.field.groupName" />
		</div>	
	</div>
	
	<c:set var="sectionId" value="inventor" scope="request"/>
	
	<jsp:include page="inventor_sharedCodeBottom.jsp" />
	
</form:form>
