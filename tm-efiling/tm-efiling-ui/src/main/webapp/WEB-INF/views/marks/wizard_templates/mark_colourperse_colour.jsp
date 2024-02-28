<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<div id="colourSection">

	<input type="hidden" name="sectionId" id="sectionId" value="${sectionId}" />

	
	<span id="colourSection_internal" style="display:block">
		<jsp:include page="../mark_colormark_internal.jsp" />
	</span>

</div>