<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="fsp-tags.tld" prefix="tags" %>
<jsp:directive.attribute name="listItemClass" required="true"
                         type="java.lang.String" description="Class of the list item"/>
<jsp:directive.attribute name="wrapperDivId" required="true"
                         type="java.lang.String" description="Class of the wrapper div element"/>
<jsp:directive.attribute name="titleCode" required="true"
                         type="java.lang.String" description="The spring:message code of the name of the word mark"/>
<jsp:directive.attribute name="descriptionCode" required="true"
                         type="java.lang.String" description="The spring:message code of the description of the word mark"/>
<jsp:directive.attribute name="hidden" required="false"
                         type="java.lang.Boolean" description="Whether the list item should be displayed"/>
<jsp:directive.attribute name="moreInfoRef" type="java.lang.String" required="true"
                         description="The anchor element which triggers the more info modal"/>
<jsp:directive.attribute name="showMoreInfo" type="java.lang.Boolean" required="false"
                         description="If more info link should be shown or not"/>

<c:set var="service_fasttrack" value="${configurationServiceDelegator.isServiceEnabledByFlowMode('FastTrack', flowModeId)}"/>
<c:set var="fieldIsFastTrack" value="${false}"/>

<c:if test="${service_fasttrack}">
    <tags:isFastTrack var="fieldIsFastTrack" flowModeId="${flowModeId}" sectionId="${wrapperDivId}" field=""/>
</c:if>

<c:if test="${empty showMoreInfo}">
    <c:set var="showMoreInfo" value="${true}"/>
</c:if>

<c:set var="style" value=""/>
<c:if test="${hidden}">
    <c:set var="style" value="display:none"/>
</c:if>
<c:set var="liClass" value="${listItemClass}"/>
<c:if test="${flowBean.mainForm.markType eq wrapperDivId}">
    <c:set var="liClass" value="${liClass} active"/>
</c:if>
<c:if test="${fieldIsFastTrack}">
    <c:set var="liClass" value="${liClass} fasttrack"/>
</c:if>

<c:set var="selectable">
    <tags:selectable flowModeId="${flowModeId}" sectionId="${wrapperDivId}" field="" />
</c:set>

<li class="${liClass}" style="${style}">
    <div id="${wrapperDivId}" class="${selectable ? 'markMenuWiz' : 'markMenuWizDisabled'}">
        <i></i>
        <span class="title"><spring:message code="${titleCode}"/></span>

        <p>
            <spring:message code="${descriptionCode}"/>
        </p>
    </div>
    <c:if test="${fieldIsFastTrack}">
        <c:set var="fasttrackConditionsUrl" value="${configurationServiceDelegator.getValue('fasttrack.conditions.url', 'general')}"/>
        <a target="_blank" href="${fasttrackConditionsUrl}" style="float:left">
            <spring:message code="fasttrack.fast.track"/>
        </a>
        <a target="_blank" href="${fasttrackConditionsUrl}" style="float: left">
            <span class="fasttrack-icon-after"></span>
        </a>
    </c:if>
    <c:if test="${showMoreInfo}">
        <a data-toggle="modal" href="${moreInfoRef}">
            <spring:message code="mark.readMore"/>
        </a>
    </c:if>
    <c:if test="${not showMoreInfo}">
        <br>
    </c:if>
</li>