<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="sp-tags.tld" prefix="tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<jsp:directive.attribute name="path" required="true" type="java.lang.String" description="Path for the form:tag"/>
<jsp:directive.attribute name="id" required="false" type="java.lang.String" description="Id for the form:tag"/>
<jsp:directive.attribute name="checkRender" required="false" type="java.lang.Boolean"
                         description="Indicates whether the component has to be rendered"/>
<jsp:directive.attribute name="labelTextCode" required="false" type="java.lang.String"
                         description="Code for the spring:message"/>
<jsp:directive.attribute name="formTagClass" required="false" type="java.lang.String"
                         description="Class for the form:tag"/>
<jsp:directive.attribute name="items" required="false" type="java.util.Collection"
                         description="Collection of the items used in select box"/>
<jsp:directive.attribute name="itemLabel" required="true" type="java.lang.String"
                         description="Value that will appear to the user"/>
<jsp:directive.attribute name="itemValue" required="true" type="java.lang.String"
                         description="Value that will be persisted"/>
<jsp:directive.attribute name="fieldFilter" required="true" type="java.lang.String"
                         description="filtering to the collection"/>
<jsp:directive.attribute name="fieldFilterText" required="true" type="java.lang.String"
                         description="filtering text to the collection"/>
<jsp:directive.attribute name="anotherFieldRender" required="false" type="java.lang.String"
                         description="another Field to Render"/>
<jsp:directive.attribute name="anotherFieldPath" required="false" type="java.lang.String"
                         description="path of another field"/>
<jsp:directive.attribute name="anotherFieldFilter" required="false" type="java.lang.String"
                         description="filter for another field"/>
<jsp:directive.attribute name="anotherFieldComponent" required="false" type="java.lang.String"
                         description="another field component"/>
<jsp:directive.attribute name="anotherFieldId" required="false" type="java.lang.String"
                         description="another field id"/> 
<jsp:directive.attribute name="importInAnotherField" required="false" type="java.lang.Boolean"
                         description="import value in another field"/>
<jsp:directive.attribute name="visibleAnotherField" required="false" type="java.lang.Boolean"
                         description="visible another field"/>                                                                                                                                                                               
                                                                          

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
    <c:set var="showAnotherField" value="false"/>
    <c:set var="listValues" value=""/>
    <form:select path="${path}" id="${id}" disabled="${!editableImport && not empty imported && imported}" cssClass="${formTagClass}">
        <c:if test="${containsEmptyValue}">
            <form:option value=""><spring:message code="${emptyValueLabelCode}"/></form:option>
        </c:if>
        <%--<jsp:doBody /> jsp:doBody doesn't work since it says The 'option' tag can only be used inside a valid 'select' tag --%>
        
        <c:if test="${!empty items}">
            <c:forEach items="${items}" var="item">
                <c:if test="${item[fieldFilter] eq fieldFilterText}">
                	<c:if test="${item['applicationType'] eq flowModeId}">
                    <form:option value="${item[itemValue]}"><spring:message code="${item[itemLabel]}"/></form:option>  
	                    <c:if test="${!empty anotherFieldRender && !empty anotherFieldFilter}">
	                		<c:if test="${item[anotherFieldRender] eq anotherFieldFilter}">
	                			<c:set var="showAnotherField" value="true"/>
	                			<c:set var="listValues" value = "${listValues}${fn:replace(item[itemValue],'.','')};"/>
	                		
	                		</c:if>
	                	</c:if>
                	</c:if>  
                </c:if>
                
            </c:forEach>
        </c:if>
    </form:select>
    
    <c:if test="${showAnotherField eq 'true'}">
    	<c:set var="styleAnotherField" value="display:block"/>
    		
    	

    	<c:if test="${!empty visibleAnotherfield && !visibleAnotherField}">
    		<c:set var="styleAnotherField" value="display:none"/>
    	</c:if>
    	<c:if test="${empty visibleAnotherfield}">
    		<c:set var="styleAnotherField" value="display:none"/>
    	</c:if>
    	
    	
    	<div id="div${anotherFieldId}" style="${styleAnotherField}">
	    	<c:choose>
	    		<c:when test="${anotherFieldComponent eq 'input'}">	
	    			<c:choose>
	    				<c:when test="${!empty importInAnotherField && importInAnotherField}">
	    					<input id="${anotherFieldId}" value="${listValues}"></input>
	    				</c:when>
	    				<c:when test="${!empty importInAnotherField && !importInAnotherField}">
	    					<input id="${anotherFieldId}"></input>
	    				</c:when>
	    				<c:when test="${empty importInAnotherField}">
	    					<input id="${anotherFieldId}"></input>
	    				</c:when>
	    			</c:choose>
	    			
	    		</c:when>
	    	</c:choose>
		</div>    		   	
    </c:if>
    
    <c:if test="${!empty errors}">
        <c:forEach items="${errors}" var="message">
            <p class="flMessageError" id="${id}ErrorMessageServer">${message}</p>
        </c:forEach>
    </c:if>
    
</tags:render>