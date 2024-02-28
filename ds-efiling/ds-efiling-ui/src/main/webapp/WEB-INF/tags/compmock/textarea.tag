<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="fsp-tags.tld" prefix="tags" %>
<jsp:directive.attribute name="path" required="true" type="java.lang.String" description="Path for the form:tag"/>
<jsp:directive.attribute name="id" required="false" type="java.lang.String" description="Id for the form:tag"/>
<jsp:directive.attribute name="checkRender" required="false" type="java.lang.Boolean"
                         description="Indicates whether the component has to be rendered"/>
<jsp:directive.attribute name="labelTextCode" required="false" type="java.lang.String"
                         description="Code for the spring:message"/>
<jsp:directive.attribute name="formTagClass" required="false" type="java.lang.String"
                         description="Class for the form:tag"/>
<jsp:directive.attribute name="divClass" required="false" type="java.lang.String"
                         description="Class for the parent div"/>
<jsp:directive.attribute name="rightLabel" required="false" type="java.lang.String"
                         description="Code for the right spring:message"/>
<%--<!-- The attribute flowModeId is necessary to read the properties from the specific conf file. It is set by the interceptor -->--%>
<%--<!-- Set default values for non required values if they are null -->--%>
<c:if test="${empty id}">
    <c:set var="id" value="${path}"/>
</c:if>
<c:if test="${empty checkRender}">
    <c:set var="checkRender" value="false"/>
</c:if>
<%--<!-- Render the component if it has to be rendered (reads property field:visible in the CMS) -->--%>
<tags:render flowModeId="${flowModeId}" sectionId="${sectionId}" field="${path}" checkRender="${checkRender}">
    <c:set var="errors"><form:errors path="${path}"></form:errors></c:set>
    <%--<!-- Reads from the CMS whether the field has to be filled (returns true or false) -->--%>
    <c:set var="checkRequired">
        <tags:required flowModeId="${flowModeId}" sectionId="${sectionId}" field="${path}"/>
    </c:set>
    <%--<!-- Reads from the CMS whether the field has to be filled (returns the format) -->--%>
    <c:set var="checkFormat">
        <tags:formatted flowModeId="${flowModeId}" sectionId="${sectionId}" field="${path}"/>
    </c:set>
        <%--Reads from the CMS whether the field is editable when the entity has been imported (returns true or false)--%>
    <c:set var="editableImport">
        <tags:editableImport flowModeId="${flowModeId}" sectionId="${sectionId}" field="${path}"/>
    </c:set>
    <c:if test="${not empty rightLabel}">
    	<div class="note">
    </c:if>
    <c:if test="${not empty divClass}">
        <div class="${divClass}">
    </c:if>

    <c:if test="${not empty labelTextCode}">
        <label for="${id}"><spring:message code="${labelTextCode}"/>
            <c:if test="${checkRequired && (empty imported || !imported || editableImport)}">
                <em class="requiredTag">(*)</em>
            </c:if>
        </label>
    </c:if>

    <%--Set the required class if it is necessary --%>
    <c:if test="${checkRequired && (empty imported || !imported || editableImport)}">
        <c:set var="formTagClass" value="${formTagClass} mandatory"/>
    </c:if>
    <%--Set the editableImport class if it is necessary --%>
    <c:if test="${!editableImport && not empty imported && imported}">
        <c:set var="formTagClass" value="${formTagClass} readonlyImport"/>
        <form:hidden path="${path}" cssClass="hiddenImported"/>
    </c:if>
    <%--Set the format class if it is necessary --%>
    <c:if test="${!empty checkFormat}">
        <c:set var="formTagClass" value="${formTagClass} checkFormat"/>
        <input type="hidden" id="${id}Format" value="${checkFormat}"/>
    </c:if>
    <%--Set the error class if it is necessary --%>
    <c:if test="${!empty errors}">
        <c:set var="formTagClass" value="${formTagClass} error"/>
    </c:if>

    <form:textarea path="${path}" maxlength="500" id="${id}" disabled="${!editableImport && not empty imported && imported}" cssClass="${formTagClass}"/>

    <c:if test="${!empty errors}">
        <c:forEach items="${errors}" var="message">
            <p class="flMessageError" id="${id}ErrorMessageServer">${message}</p>
        </c:forEach>
    </c:if>
    <c:if test="${not empty divClass}">
        </div>
    </c:if>
    <c:if test="${not empty rightLabel}">
    	</div>
    	<label class="note" for="${id}">
        	<spring:message code="${rightLabel}"/>
    	</label>
    </c:if>
</tags:render>