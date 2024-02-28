<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<form:form id="inventorWaiver" cssClass="formEF" modelAttribute="inventorForm">
	
	<jsp:include page="inventor_sharedCodeTop.jsp" />

	<jsp:include page="inventor_sharedCodeWaiverCheck.jsp" />
	
	<c:set var="sectionId" value="inventor" scope="request"/>
	
	<jsp:include page="inventor_sharedCodeBottom.jsp" />
	
</form:form>
