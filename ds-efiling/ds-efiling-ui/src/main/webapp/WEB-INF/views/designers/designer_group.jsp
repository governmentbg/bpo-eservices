<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<form:form id="designerGroup" cssClass="formEF" modelAttribute="designerForm">

	<jsp:include page="designer_sharedCodeTop.jsp" /> 

	<jsp:include page="designer_sharedCodeWaiverCheck.jsp" />
	
	<div id="designer_notWaive">

		<jsp:include page="designer_sharedCodeBelongsCheck.jsp" />

		<div id="designer_group">
			<c:set var="sectionId" value="designer_group" scope="request" />
			
			<component:input path="groupName" checkRender="true" labelTextCode="designer.field.groupName" />
		</div>	
	</div>
	
	<c:set var="sectionId" value="designers" scope="request"/>
	<tiles:insertDefinition name="designs_associate_to">
		<tiles:putAttribute name="containsDesignsLinkForm" value="${designerForm}"/>
		<tiles:putAttribute name="partialId" value="Designer" />		
	</tiles:insertDefinition>

	
	<jsp:include page="designer_sharedCodeBottom.jsp" />
	
</form:form>
