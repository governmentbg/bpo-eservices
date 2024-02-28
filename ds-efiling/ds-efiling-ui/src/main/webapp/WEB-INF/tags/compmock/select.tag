<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="fsp-tags.tld" prefix="tags" %>
<jsp:directive.attribute name="path" required="true" type="java.lang.String" description="Path for the formTag"/>
<jsp:directive.attribute name="id" required="false" type="java.lang.String" description="Id for the formTag"/>
<jsp:directive.attribute name="checkRender" required="false" type="java.lang.Boolean"
                         description="Indicates whether the component has to be rendered"/>
<jsp:directive.attribute name="labelTextCode" required="false" type="java.lang.String"
                         description="Code for the spring:message"/>
<jsp:directive.attribute name="formTagClass" required="false" type="java.lang.String"
                         description="Code for the spring:message"/>
<jsp:directive.attribute name="items" required="false" type="java.util.Collection"
                         description="Collection of the items used in select box"/>
<jsp:directive.attribute name="itemLabel" required="true" type="java.lang.String"
                         description="Value that will appear to the user"/>
<jsp:directive.attribute name="itemValue" required="true" type="java.lang.String"
                         description="Value that will be persisted"/>
<jsp:directive.attribute name="itemFilter" required="false" type="java.lang.String"
                         description="filtering to the collection"/>
<jsp:directive.attribute name="containsEmptyValue" required="false" type="java.lang.Boolean"
                         description="if true, an empty value will also be available in the select"/>
<jsp:directive.attribute name="emptyValueLabelCode" required="false" type="java.lang.String"
                         description="used for the label of the option with an empty value"/>
<jsp:directive.attribute name="textSelectedOption" required="false" type="java.lang.Boolean"
                         description="the value of the option equals the text of the option"/>
<jsp:directive.attribute name="dataaux" required="false" type="java.lang.String"
                         description="auxiliar variable to get a specific value from the items list"/>

<%--<!-- Set default values for non required values if they are null -->--%>
<c:if test="${empty id}">
    <c:set var="id" value="${path}"/>
</c:if>
<c:if test="${empty checkRender}">
    <c:set var="checkRender" value="false"/>
</c:if>
<c:if test="${containsEmptyValue and empty emptyValueLabelCode}">
    <c:set var="emptyValueLabelCode" value="selectBox.SELECT"/>
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
    
    <c:if test="${not empty labelTextCode}">
        <label><spring:message code="${labelTextCode}"/>
            <c:if test="${checkRequired && (empty imported || !imported || editableImport)}">
                <span class="requiredTag">*</span>
            </c:if>
        </label>
    </c:if>
    <%--<!-- Set the required class if it is necessary -->--%>
    <c:if test="${checkRequired && (empty imported || !imported || editableImport)}">
        <c:set var="formTagClass" value="${formTagClass} mandatory"/>
    </c:if>
    <%--Set the editableImport class if it is necessary --%>
    <c:if test="${!editableImport && not empty imported && imported}">
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
        <c:set var="formTagClass" value="${formTagClass} error"/>
    </c:if>
	<form:select path="${path}" id="${id}" disabled="${!editableImport && not empty imported && imported}" cssClass="${formTagClass}">
        <c:if test="${containsEmptyValue}">
            <form:option value=""><spring:message code="${emptyValueLabelCode}"/></form:option>
        </c:if>
  			<%--<jsp:doBody /> jsp:doBody doesn't work since it says The 'option' tag can only be used inside a valid 'select' tag --%>
        <c:if test="${!empty items}">
            <c:forEach items="${items}" var="item">
                <c:if test="${empty itemFilter || item[itemFilter]}">
                	<c:if test="${(empty valueText || !valueText)&&empty textSelectedOption}">
                		<c:if test="${not empty dataaux}">
                			<form:option value="${item[itemValue]}" data-aux="${item[dataaux]}"><spring:message code="${item[itemLabel]}"/></form:option>
                		</c:if>
                    	<c:if test="${empty dataaux}">
                    		<form:option value="${item[itemValue]}"><spring:message code="${item[itemLabel]}"/></form:option>
                    	</c:if>
                    </c:if>
                    					
                    <c:if test="${not empty textSelectedOption && textSelectedOption}">
                    	<c:set var="exhibitionValue"><spring:message code="${item[itemValue]}"/></c:set>
                    	<c:if test="${not empty dataaux}">
                   			<form:option value="${exhibitionValue}" data-aux="${item[dataaux]}"><spring:message code="${item[itemLabel]}"/></form:option>
                   		</c:if>
                    	<c:if test="${empty dataaux}">
                    		<form:option value="${exhibitionValue}"><spring:message code="${item[itemLabel]}"/></form:option>
                    	</c:if>
                    </c:if>
                </c:if>
            </c:forEach>
        </c:if>
	</form:select>
    <c:if test="${!empty errors}">
        <c:forEach items="${errors}" var="message">
            <p class="flMessageError" id="${id}ErrorMessageServer">${message}</p>
        </c:forEach>
    </c:if>
</tags:render>