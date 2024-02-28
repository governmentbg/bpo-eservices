<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form:form modelAttribute="flowBean" cssClass="mainForm formEF"
           method="POST">
	<c:set var="sectionId" value="soundmark" scope="request"/>
	
		<header>
			<h3><spring:message code="mark.names.sound"/></h3>
		</header>	

	    <jsp:include page="../templates/mark_representation.jsp">
            <jsp:param name="helpMessageKey" value="mark.imageFile.attachment.help"/>
            <jsp:param name="helpUseFlowModeId" value="false"/>
        </jsp:include>
	    <jsp:include page="../templates/mark_sound_file.jsp">
            <jsp:param name="helpMessageKey" value="mark.soundFile.attachment.help"/>
            <jsp:param name="helpUseFlowModeId" value="false"/>
        </jsp:include>
	    <jsp:include page="../templates/mark_sound_description.jsp"/>
	    <jsp:include page="../templates/mark_sound_description_second.jsp"/>
	    <jsp:include page="../templates/mark_disclaimer.jsp"/>
	    <jsp:include page="../templates/mark_disclaimer_second.jsp"/>
	    <jsp:include page="../templates/mark_type.jsp"/>
	    <jsp:include page="../templates/mark_series.jsp"/>
	
	    <form:hidden path="mainForm.markType" value="soundmark"/>
</form:form>
