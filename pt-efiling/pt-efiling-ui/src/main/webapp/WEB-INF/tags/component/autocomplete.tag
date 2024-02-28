<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="fsp-tags.tld" prefix="tags"%>

<jsp:directive.attribute name="autocompleteUrl" required="true" type="java.lang.String" description="Url to get autocomplete results from"/>
<jsp:directive.attribute name="minimumCharAutocomplete" required="false" type="java.lang.String" description="Integer that specifies the minimum character number to trigger autocomplete"/>
<jsp:directive.attribute name="textCodeWhenEmpty" required="false" type="java.lang.String" description="Specifies the message resource code of the text to display when text field is empty"/>
<jsp:directive.attribute name="id" required="false" type="java.lang.String" description="Id for the form:input"/>
<jsp:directive.attribute name="labelTextCode" required="false" type="java.lang.String" description="Code for the spring:message"/>
<jsp:directive.attribute name="component" required="false" type="java.lang.String" description="component for which we import"/>
<jsp:directive.attribute name="path" required="true" type="java.lang.String" description="the input text box name"/>
<jsp:directive.attribute name="width" required="false" type="java.lang.String" description="the input text box width"/>
<jsp:directive.attribute name="checkRender" required="false" type="java.lang.Boolean" description="Indicates whether the component has to be rendered" />

<c:if test="${width == null}">
    <c:set var="width" value="80"/>
</c:if>

<c:set var="checkRequired">
    <tags:required flowModeId="${flowModeId}" sectionId="${sectionId}"
                   field="${path}" />
</c:set>
<c:if test="${not empty labelTextCode}">
    <label for="${id}"><spring:message code="${labelTextCode}" />
        <c:if test="${checkRequired}">
            <span class="requiredTag">*</span>
        </c:if></label>
</c:if>

<c:if test="${not empty textCodeWhenEmpty}">
    <c:set var="textWhenEmpty"><spring:message code="${textCodeWhenEmpty}"/></c:set>
</c:if>

<c:set var="minimunCharactersAutocomplete"
       value="${empty minimumCharAutocomplete?3:configurationServiceDelegator.getValue(minimumCharAutocomplete, component)}"/>

<form:input
        path="${path}"
        type="text"
        size="100"
        style="width: ${width}%"
        maxlength="100"
        placeholder="${textWhenEmpty}"
        class="autocompleted"
        id="${id}"
        checkRender="${checkRender}"
        data-minchars="${minimunCharactersAutocomplete}"
        data-url="${autocompleteUrl}"/>

<c:set var="errors"><form:errors path="${path}"/></c:set>
<c:if test="${!empty errors}">
    <p class="flMessageError" id="${id}ErrorMessageServer">${errors}</p>
</c:if>