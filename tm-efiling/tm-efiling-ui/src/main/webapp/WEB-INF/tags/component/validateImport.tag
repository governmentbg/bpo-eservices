<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="fsp-tags.tld" prefix="tags"%>

<jsp:directive.attribute name="path" required="true" type="java.lang.String" description="Path for the form:tag" />

<c:set var="validateImport">
	<tags:validateImport flowModeId="${flowModeId}" sectionId="${sectionId}" />
</c:set>
    
<input type="hidden" name="${path}" value="${validateImport}" />
    