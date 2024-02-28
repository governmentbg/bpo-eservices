<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="fsp-tags.tld" prefix="tags" %>

<jsp:directive.attribute name="sectionId" required="false" type="java.lang.String" description="Section id on the configuration"/>
<jsp:directive.attribute name="fieldId" required="false" type="java.lang.String" description="Field id on the configuration"/>
<jsp:directive.attribute name="helpMessageKey" required="false" type="java.lang.String" description="The help message key"/>
<jsp:directive.attribute name="isButton" required="false" type="java.lang.Boolean" description="Indicates whether the component is a button, in order to add the tooltip"/>

<c:set var="service_fasttrack" value="${configurationServiceDelegator.isServiceEnabledByFlowMode('FastTrack', flowModeId)}"/>
<c:set var="fieldIsFastTrack" value="${true}"/>
<c:if test="${not empty sectionId}">
	<tags:isFastTrack var="fieldIsFastTrack" flowModeId="${flowModeId}" sectionId="${sectionId}" field="${fieldId}"/>
</c:if>
<c:if test="${empty isButton}">
    <c:set var="isButton" value="false"/>
</c:if>
<c:if test="${isButton}">
    <c:set var="conditions" value="conditions"/>
</c:if>

<c:if test="${service_fasttrack and fieldIsFastTrack}">
	<c:choose>
		<c:when test="${helpMessageKey!=null && not empty helpMessageKey}">
			<c:set var="fasttrackConditionsUrl" value="${configurationServiceDelegator.getValue('fasttrack.conditions.url', 'general')}"/>
			<c:if test="${!isButton}">
				<a rel="file-help-tooltip" class="fasttrack-icon-after"></a>
			</c:if>
			<div data-tooltip="help" class="hidden">
				<div class="fasttrack-icon-before-white">
					<a class="close-popover"></a>
					<spring:message code="${helpMessageKey}"/><br/>
					<a target="_blank" href="${fasttrackConditionsUrl}" class="${conditions}">
						<spring:message code="fasttrack.help.learnmore"/>
					</a>
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<c:if test="${!isButton}">
				<span class="fasttrack-icon-after"></span>
			</c:if>
		</c:otherwise>
	</c:choose>
</c:if>