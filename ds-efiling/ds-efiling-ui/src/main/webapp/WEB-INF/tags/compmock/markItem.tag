<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="fsp-tags.tld" prefix="tags" %>
<jsp:directive.attribute name="listItemClass" required="true"
                         type="java.lang.String" description="Class of the list item"/>
<jsp:directive.attribute name="wrapperDivClass" required="true"
                         type="java.lang.String" description="Class of the wrapper div element"/>
<jsp:directive.attribute name="titleCode" required="true"
                         type="java.lang.String" description="The spring:message code of the name of the word mark"/>
<jsp:directive.attribute name="descriptionCode" required="true"
                         type="java.lang.String" description="The spring:message code of the description of the word mark"/>
<jsp:directive.attribute name="hidden" required="false"
                         type="java.lang.Boolean" description="Whether the list item should be displayed"/>
<jsp:directive.attribute name="moreInfoRef" type="java.lang.String" required="true"
                         description="The anchor element which triggers the more info modal"/>

<c:set var="style" value=""/>
<c:if test="${hidden}">
    <c:set var="style" value="display:none"/>
</c:if>

<li class="${listItemClass}" style="${style}">
    <div id="${wrapperDivClass}" class="markMenuWiz">
        <i></i>
        <span class="title"><spring:message code="${titleCode}"/></span>

        <p>
            <spring:message code="${descriptionCode}"/>
        </p>
    </div>
    <a data-toggle="modal" href="${moreInfoRef}">
        <spring:message code="mark.readMore"/>
    </a>
</li>