<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="fsp-tags.tld" prefix="tags"%>
<jsp:directive.attribute name="path" required="true"
                         type="java.lang.String" description="Path for the form:tag" />
<jsp:directive.attribute name="id" required="false"
                         type="java.lang.String" description="Id for the form:tag" />
<jsp:directive.attribute name="checkRender" required="false"
                         type="java.lang.Boolean"
                         description="Indicates whether the component has to be rendered" />
<jsp:directive.attribute name="labelTextCode" required="false"
                         type="java.lang.String" description="Code for the spring:message" />
<jsp:directive.attribute name="formTagClass" required="false"
                         type="java.lang.String" description="Class for the form:tag" />
<jsp:directive.attribute name="autocompleteUrl" required="false"
                         type="java.lang.String"
                         description="Url to get autocomplete results from" />
<jsp:directive.attribute name="minimumCharAutocomplete" required="false"
                         type="java.lang.String"
                         description="Configuration string for minimum number of characters for autocomplete"/>
<jsp:directive.attribute name="maxlength" required="false"
                         type="java.lang.String"
                         description="Configuration string for the maximum allowed lenght of the entered text"/>

<jsp:directive.attribute name="readOnly" required="false"
                         type="java.lang.Boolean"
                         description="Should the imput be readonly" />
<jsp:directive.attribute name="fieldLength" required="false"
                         type="java.lang.Integer"
                         description="Indicates field max length" />
                         
<jsp:directive.attribute name="showDateHelp" required="false" type="java.lang.Boolean" description="For a date field allows show a help message with the format" />
                        
<%--<!-- Performance better to put it inside the jsp that renders these inputs -->--%>
<%-- 	<c:set var="nestedDomainPath" value="${pageContext.request.getAttribute('nestedPath')}"/> --%>
<%-- 	<c:set var="nestedDomainPath" value="${nestedDomainPath.substring(0,nestedDomainPath.lastIndexOf('.'))}"/> --%>
<%-- 	<c:set var="imported" value="${pageContext.request.getAttribute(nestedDomainPath).imported}"/> --%>
<%-- The attribute flowModeId is necessary to read the properties from the specific conf file. It is set by the interceptor --%>
<%-- Set default values for non required values if they are null --%>
<c:if test="${empty id}">
    <c:set var="id" value="${path}" />
</c:if>
<c:if test="${empty checkRender}">
    <c:set var="checkRender" value="false" />
</c:if>

<%-- Render the component if it has to be rendered (reads property field:visible in the CMS) --%>
<tags:render flowModeId="${flowModeId}" sectionId="${sectionId}"
             field="${path}" checkRender="${checkRender}">

    <c:set var="errors">
        <form:errors path="${path}"></form:errors>
    </c:set>
    <%--Reads from the CMS whether the field has to be filled (returns true or false)--%>
    <c:set var="checkRequired">
        <tags:required flowModeId="${flowModeId}" sectionId="${sectionId}"
                       field="${path}" />
    </c:set>
    <%--Reads from the CMS whether the field has to be filled (returns the format)--%>
    <c:set var="checkFormat">
        <tags:formatted flowModeId="${flowModeId}" sectionId="${sectionId}"
                        field="${path}" />
    </c:set>
    <%--Reads from the CMS whether the field is editable when the entity has been imported (returns true or false)--%>
    <c:set var="editableImport">
        <tags:editableImport flowModeId="${flowModeId}"
                             sectionId="${sectionId}" field="${path}" />
    </c:set>

    <c:if test="${not empty labelTextCode}">
        <label for="${id}"><spring:message code="${labelTextCode}" />
            <c:if test="${checkRequired && (empty imported || !imported || editableImport)}">
                <span class="requiredTag">*</span>
            </c:if></label>
    </c:if>

    <%--Set the required class if it is necessary --%>
    <c:if test="${checkRequired && (empty imported || !imported || editableImport)}">
        <c:set var="formTagClass" value="${formTagClass} mandatory" />
    </c:if>
    <%--Set the editableImport class if it is necessary --%>
    <c:if test="${(!editableImport && not empty imported && imported) || (not empty readOnly && readOnly)}">
        <c:set var="formTagClass" value="${formTagClass} readonlyImport" />
        <form:hidden path="${path}" cssClass="hiddenImported" />
    </c:if>
    <%--Set the format class if it is necessary --%>
    <c:if test="${!empty checkFormat}">
        <c:set var="formTagClass" value="${formTagClass} matchPattern" />
        <%--         <input type="hidden" id="${id}Format" value="${checkFormat}"/> --%>
    </c:if>
    <%--Set the error class if it is necessary --%>
    <c:if test="${!empty errors}">
        <c:set var="formTagClass" value="${formTagClass} error" />
    </c:if>
    
    <c:if test="${not empty showDateHelp and showDateHelp}">
    	<spring:eval var="dateFormat" expression="@propertyConfigurer.getProperty('iu.template.dateFormat.help')" />
    	<c:set var="dateHelp">
        	<span class="tip">${dateFormat}</span>
    	</c:set>
    </c:if>
    
    <c:choose>
        <c:when
                test="${formTagClass.contains('autocompleted') && !empty autocompleteUrl}">
            <form:input path="${path}" id="${id}" cssClass="${formTagClass}"
                        size="${not empty maxlength ? maxlength : '100'}"
                        maxlength="${not empty maxlength ? maxlength : '100'}"
                        data-pattern="${checkFormat}"
                        data-minchars="${empty minimumCharAutocomplete?3:configurationServiceDelegator.getValue(minimumCharAutocomplete, component)}"
                        data-url="${autocompleteUrl}" />
			<c:if test="${not empty dateHelp}">
				<c:out value="${dateHelp}" escapeXml="false" />
			</c:if>	
        </c:when>
        <c:when
                test="${!formTagClass.contains('autocompleted') || empty autocompleteUrl}">
            <c:choose>
                <c:when test="${not empty fieldLength && fieldLength != null}">
                    <form:input path="${path}" id="${id}" disabled="${(!editableImport && not empty imported && imported) || (not empty readOnly && readOnly)}" cssClass="${formTagClass}"
                                size="${not empty maxlength ? maxlength : fieldLength}"
                                maxlength="${not empty maxlength ? maxlength : fieldLength}"
                                data-pattern="${checkFormat}" />
                </c:when>
                <c:otherwise>
                    <form:input path="${path}" id="${id}" disabled="${(!editableImport && not empty imported && imported) || (not empty readOnly && readOnly)}" cssClass="${formTagClass}"
                                size="${not empty maxlength ? maxlength : '100'}"
                                maxlength="${not empty maxlength ? maxlength : '100'}"
                                data-pattern="${checkFormat}" />
                </c:otherwise>
            </c:choose>
			<c:if test="${not empty dateHelp}">
				<div class="dateHelp">
					<ul>
						<li><c:out value="${dateHelp}" escapeXml="false" /></li>
					</ul>
				</div>
			</c:if>
		</c:when>	
    </c:choose>

    <c:if test="${!empty errors}">
        <p class="flMessageError" id="${id}ErrorMessageServer">${errors}</p>
    </c:if>
</tags:render>