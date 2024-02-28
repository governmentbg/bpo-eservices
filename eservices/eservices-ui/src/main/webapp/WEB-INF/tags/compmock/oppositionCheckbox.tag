<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="sp-tags.tld" prefix="tags" %>
<jsp:directive.attribute name="path" required="true" type="java.lang.String" description="Path for the formTag"/>
<jsp:directive.attribute name="id" required="false" type="java.lang.String" description="Id for the formTag"/>
<jsp:directive.attribute name="checkRender" required="false" type="java.lang.Boolean"
                         description="Indicates whether the component has to be rendered"/>
<jsp:directive.attribute name="labelTextCode" required="false" type="java.lang.String"
                         description="Code for the spring:message"/>
<jsp:directive.attribute name="labelClass" required="false" type="java.lang.String"
                         description="Code for the label tag"/>
<jsp:directive.attribute name="formTagClass" required="false" type="java.lang.String"
                         description="Code for the spring:message"/>
<jsp:directive.attribute name="value" required="false" type="java.lang.String"
                         description="value for checkbox"/>         
<jsp:directive.attribute name="excludedLaw" required="false" type="java.util.Collection"
                         description="Collection of the excluded law article Reference"/>
<jsp:directive.attribute name="exclusivity" required="false" type="java.lang.Boolean"
                         description="Indicates if exists exclusivity"/>                         
<jsp:directive.attribute name="reputationClaim" required="false" type="java.lang.Boolean"
                         description="Indicates if reputation Claim"/>
<jsp:directive.attribute name="onclick" required="false" type="java.lang.String"
                         description="onclick function"/>                         

<%--<!-- Set default values for non required values if they are null -->--%>
<c:if test="${empty id}">
    <c:set var="id" value="${path}"/>
</c:if>
<c:if test="${empty checkRender}">
    <c:set var="checkRender" value="false"/>
</c:if>
<c:if test="${empty labelClass}">
    <c:set var="labelClass" value="correspondence-address"/>
</c:if>
<%--<!-- Render the component if it has to be rendered (reads property field:visible in the CMS) -->--%>
<tags:render flowModeId="${flowModeId}" sectionId="${sectionId}" field="${path}" checkRender="${checkRender}">
    <%--Reads from the CMS whether the field is editable when the entity has been imported (returns true or false)--%>
    <c:set var="editableImport">
        <tags:editableImport flowModeId="${flowModeId}" sectionId="${sectionId}" field="${path}"/>
    </c:set>

    <c:set var="fieldIsImported" value="${not empty imported && imported}"/>
    <c:set var="fieldIsEditable" value="${!fieldIsImported || editableImport}"/>

    <c:set var="errors"><form:errors path="${path}"></form:errors></c:set>
    <%--<!-- Reads from the CMS whether the field has to be filled (returns true or false) -->--%>
    <c:set var="fieldIsRequired">
        <tags:required flowModeId="${flowModeId}" sectionId="${sectionId}" field="${path}"/>
    </c:set>
    <%--<!-- Reads from the CMS whether the field has to be filled (returns the format) -->--%>
    <c:set var="checkFormat">
        <tags:formatted flowModeId="${flowModeId}" sectionId="${sectionId}" field="${path}"/>
    </c:set>

    <%--<!-- Set the required class if it is necessary -->--%>
    <c:if test="${fieldIsRequired && fieldIsEditable}">
        <c:set var="formTagClass" value="${formTagClass} checkRequired"/>
    </c:if>
    <%--Set the editableImport class if it is necessary --%>


    <c:if test="${!fieldIsEditable}">
        <c:set var="formTagClass" value="${formTagClass} readonlyImport"/>
        <form:hidden path="${path}" cssClass="hiddenImported"/>
    </c:if>
    <%--<!-- Set the format class if it is necessary -->--%>
    <c:if test="${!empty checkFormat}">
        <c:set var="formTagClass" value="${formTagClass} checkFormat"/>
        <input type="hidden" id="${id}Format" value="${checkFormat}"/>
    </c:if>
    <%--<!-- Set the error class if it is necessary -->--%>
    <c:if test="${!empty errors}">
        <c:set var="formTagClass" value="${formTagClass} errorValCheckbox"/>
    </c:if>

    <c:if test="${not empty labelTextCode}">
        <label class="${labelClass}" for="${id}">
            <form:checkbox path="${path}" id="${id}" value="${value}" cssClass="${formTagClass}" onclick="${onclick}('${id}')" disabled="${!fieldIsEditable}"/>
            <span><spring:message code="${labelTextCode}"/></span>
            <c:if test="${fieldIsRequired && fieldIsEditable}">
                <span class="requiredTag">*</span>
            </c:if>
        </label>
    </c:if>

    <c:if test="${empty labelTextCode}">
        <form:checkbox path="${path}" id="${id}" value="${value}" disabled="${!fieldIsEditable}"
            cssClass="${formTagClass}" onclick="${onclick}(${id})" />
    </c:if>
    <c:if test="${!empty errors}">
        <c:forEach items="${errors}" var="message">
            <p class="flMessageError" id="${id}ErrorMessageServer">${message}</p>
        </c:forEach>
    </c:if>
    
    
    <c:set var="excLaw" value=""/>
    <c:set var="repClaim" value=""/>

    <c:if test="${exclusivity eq 'true'}">
    	<c:forEach var="excLawItem" items="${excludedLaw}" >
    		<c:set var="excLaw" value="${excLaw}${excLawItem.replace('.','')};"/>
    	</c:forEach>
    </c:if>
    
    <c:if test="${!empty reputationClaim}">
    	<c:set var="repClaim" value="${reputationClaim}"/>
    </c:if>
    <c:if test="${empty reputationClaim}">
    	<c:set var="repClaim" value="false"/>
    </c:if>
    
    <div style="display:none">
    	<input id="input_excLaw_${id}" value="${excLaw}"/>
    	<input id="input_repClaim_${id}" value="${reputationClaim}"/>
    </div>

</tags:render>