<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="sp-tags.tld" prefix="tags" %>
<jsp:directive.attribute name="path" required="true" type="java.lang.String" description="Path for the form:tag" />
<jsp:directive.attribute name="checkRender" required="false" type="java.lang.Boolean" description="Indicates whether the component has to be rendered" />
<%--<!-- The attribute flowModeId is necessary to read the properties from the specific conf file. It is set by the interceptor -->--%>
<%--<!-- Set default values for non required values if they are null -->--%>
<c:if test="${empty checkRender}">
	<c:set var="checkRender" value="false" />	
</c:if>
<%--<!-- Render the component if it has to be rendered (reads property field:visible in the CMS) -->--%>
<tags:render flowModeId="${flowModeId}" sectionId="${sectionId}" field="${path}" checkRender="${checkRender}">
	<c:set var="errors"><form:errors path="${path}"></form:errors></c:set>
	<jsp:doBody />	
</tags:render>