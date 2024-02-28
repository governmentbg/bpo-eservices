<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="fsp-tags.tld" prefix="tags" %>

<jsp:directive.attribute name="path" required="true" type="java.lang.String" description="Path for the form:tag" />
<jsp:directive.attribute name="labelTextCode" required="true" type="java.lang.String" description="Code for the spring:message" />
                         
<tags:render flowModeId="${flowModeId}" sectionId="${sectionId}" field="${path}" checkRender="true">
	<c:set var="checkRequired">
		<tags:required flowModeId="${flowModeId}" sectionId="${sectionId}" field="${path}" />
	</c:set>

	<label>
		<spring:message code="${labelTextCode}" />
		<c:if test="${checkRequired}">
			<span class="requiredTag">*</span>
		</c:if>
	</label>
</tags:render>
