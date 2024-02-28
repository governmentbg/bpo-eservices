<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:directive.attribute name="autocompleteServiceEnabled" required="false" type="java.lang.String"
                         description="Boolean that says if the autocomplete functionality enabled"/>
<jsp:directive.attribute name="autocompleteUrl" required="true" type="java.lang.String"
                         description="Url to get autocomplete results from"/>
<jsp:directive.attribute name="permissionAutocomplete" required="true" type="java.lang.Boolean"
                         description="check for the permission of the functionality of autocomplete"/>
<jsp:directive.attribute name="minimumCharAutocomplete" required="false" type="java.lang.String"
                         description="Integer that specifies the minimum character number to trigger autocomplete"/>
<jsp:directive.attribute name="importTextfieldClass" required="false" type="java.lang.String"
                         description="String specifying the import textbox's class"/>
<jsp:directive.attribute name="textCodeWhenEmpty" required="false" type="java.lang.String"
                         description="Specifies the message resource code of the text to display when text field is empty"/>
<jsp:directive.attribute name="id" required="false" type="java.lang.String" description="Id for the form:input"/>
<jsp:directive.attribute name="labelTextCode" required="false" type="java.lang.String" description="Code for the spring:message"/>
<jsp:directive.attribute name="importButtonClass" required="true" type="java.lang.String" description="Class for the import button"/>
<jsp:directive.attribute name="dataask" required="false" type="java.lang.String" description="data-ask for the import button"/>
<jsp:directive.attribute name="importButtonId" required="false" type="java.lang.String" description="Id for the button"/>
<jsp:directive.attribute name="importButtonTextCode" required="false" type="java.lang.String"
                         description="Code for import button for spring:message"/>
<jsp:directive.attribute name="component" required="false" type="java.lang.String" description="component for which we import"/>
<jsp:directive.attribute name="autocompleteClass" required="false" type="java.lang.String"
                         description="when the component uses autocomplete, this class is provided as a css class to identify it"/>
<jsp:directive.attribute name="footerTextCode" required="false" type="java.lang.String" description="the text code of the footer" />

<c:if test="${permissionAutocomplete && (empty autocompleteServiceEnabled?true:configurationServiceDelegator.isServiceEnabled(autocompleteServiceEnabled))}">
    <c:set var="importInputClass" value="autocompleted ${importTextfieldClass}"/>
</c:if>

<c:if test="${not empty labelTextCode}">
    <label for="${id}"> <spring:message code="${labelTextCode}"/>
    </label>
</c:if>

<c:if test="${not empty textCodeWhenEmpty}">
    <c:set var="textWhenEmpty"><spring:message code="${textCodeWhenEmpty}"/></c:set>
</c:if>
<input type="text"
       name="${inputTextName}"
       size="100"
       maxlength="100"
       placeholder="${textWhenEmpty}"
       class="${importInputClass}"
       id="${id}"
       data-minchars="${empty minimumCharAutocomplete?3:configurationServiceDelegator.getValue(minimumCharAutocomplete, component)}"
       data-url="${autocompleteUrl}">
<button type="button"
        data-ask="${empty dataask?false:configurationServiceDelegator.getValue(dataask, component)}"
        id="${importButtonId}" class="${importButtonClass}">
    <spring:message code="${importButtonTextCode}"/>
</button>

<c:set var="errors"><form:errors/></c:set>
<c:if test="${!empty errors}">
    <c:forEach items="${errors}" var="message">
        <p class="flMessageError" id="${id}ErrorMessageServer">${message}</p>
    </c:forEach>
</c:if>