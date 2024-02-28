<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component"%>
<%@ taglib uri="sp-tags.tld" prefix="tags" %>

<jsp:directive.attribute name="path" required="true" type="java.lang.String" description="Path for the formTag"/>
<jsp:directive.attribute name="sectionId" required="true" type="java.lang.String" description="Path for the formTag"/>
<jsp:directive.attribute name="id" required="false" type="java.lang.String" description="Id for the formTag"/>
<jsp:directive.attribute name="labelTextCode" required="false" type="java.lang.String"
                         description="Code for the spring:message"/>
<jsp:directive.attribute name="formTagClass" required="false" type="java.lang.String"
                         description="Code for the spring:message"/>
<jsp:directive.attribute name="items" required="true" type="java.util.Collection"
                         description="Collection of the items used in select box"/>
<jsp:directive.attribute name="itemLabel" required="true" type="java.lang.String"
                         description="Value that will appear to the user"/>
<jsp:directive.attribute name="itemValue" required="true" type="java.lang.String"
                         description="Value that will be persisted"/>
<jsp:directive.attribute name="itemFilter" required="false" type="java.lang.String"
                         description="filtering to the collection"/>
						 

<%--<!-- Set default values for non required values if they are null -->--%>
<c:if test="${empty id}">
    <c:set var="id" value="${path}"/>
</c:if>
<c:if test="${empty labelTextCode}">
    <c:set var="labelTextCode" value=""/>
</c:if>
<c:if test="${empty formTagClass}">
    <c:set var="formTagClass" value=""/>
</c:if>
<c:if test="${empty itemFilter}">
    <c:set var="itemFilter" value=""/>
</c:if>
<c:set var="sectionId" value="${sectionId}" scope="request" />
<component:select path="${path}" id="${id}" checkRender="false" 
					labelTextCode="${labelTextCode}" formTagClass="${formTagClass}" 
					items="${items}" itemLabel="${itemLabel}" itemValue="${itemValue}" itemFilter="${itemFilter}"/>