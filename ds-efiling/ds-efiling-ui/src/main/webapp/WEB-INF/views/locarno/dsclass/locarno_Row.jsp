<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<td class="locarno-classification" data-val="number">${param.locarnoClassFormatted == -1? '?' : param.locarnoClassFormatted}</td>

<c:set var="termValidationClass" value="term-not-found" />
<c:choose>
    <c:when test="${param.locarnoClassValid == 'valid'}">
        <c:set var="termValidationClass" value="term-validharm"/>
    </c:when>
    <c:when test="${param.locarnoClassValid == 'editable'}">
        <c:set var="termValidationClass" value="term-modifiable"/>
    </c:when>
    <c:when test="${param.locarnoClassValid == 'invalid'}">
        <c:set var="termValidationClass" value="term-invalid"/>
    </c:when>
</c:choose>

<td data-val="text" class="${termValidationClass}"><i class="${termValidationClass}-icon"></i>
	<span class="name editable" rel="term-tooltip" title="<spring:message code="design.form.classes.locarno.modal.edit" />" data-val="${param.locarnoId}">${param.locarnoIndication}</span>
	<c:if test="${param.viewMode eq false}">
		<c:if test="${termValidationClass != 'term-validharm' and termValidationClass != 'term-invalid'}" >
			<a rel="term-tooltip" title="<spring:message code="design.form.classes.locarno.modal.view" />" class="term-search" data-val="${param.locarnoId}"></a>
		</c:if>
		<c:if test="${termValidationClass != 'term-invalid'}" >
			<a rel="term-tooltip" title="<spring:message code="design.form.classes.locarno.modal.remove" />" class="term-close" id="iconRemoveLocarno${param.rowId}" data-val="${param.locarnoId}"></a>
		</c:if>
	</c:if>
</td>
<td class="productType">

    <spring:message code="design.form.classes.product.type.short.${param.type}.hint" var="titleType"/>
    <spring:message code="design.form.classes.product.type.switch" var="switchTitle"/>
    <span title="${titleType}">
        <spring:message code="design.form.classes.product.type.short.${param.type}"/>
    </span>
    <c:if test="${param.viewMode eq false}">
        <span>
            <a href="#" class="switchType" data-val="${param.locarnoId}" title="${switchTitle}"><i class="switch-icon"></i> </a>
        </span>
    </c:if>
</td>