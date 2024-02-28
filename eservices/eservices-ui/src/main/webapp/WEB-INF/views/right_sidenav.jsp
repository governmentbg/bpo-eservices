<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<ul class="nav aside-nav-list">
	<tiles:useAttribute id="sections_list" name="content" classname="java.util.List" />
	<c:if test="${not empty sections_list}">
	    <c:forEach var="section_item" items="${sections_list}">
			<c:choose>
				<c:when test="${fn:endsWith(flowModeId, '-appeal') && section_item == 'applicant' }">
					<li class="aside-nav-list-item"><a class="aside-nav-anchor" href="#${section_item}">
						<span class="aside-nav-name"><spring:message code="${flowModeId}.layout.sideNavigation.${section_item}"/></span>
					</a></li>
				</c:when>

				<c:when test="${(fn:endsWith(flowModeId, '-changerep'))&& section_item == 'representative' }">
					<li class="aside-nav-list-item"><a class="aside-nav-anchor" href="#${section_item}">
						<span class="aside-nav-name"><spring:message code="layout.sideNavigation.newRepresentatives"/></span>
					</a></li>
				</c:when>

				<c:when test="${section_item == 'representative_intellectual' }">
					<c:if test="${fn:endsWith(flowModeId, '-changerep')}">
						<li class="aside-nav-list-item"><a class="aside-nav-anchor" href="#representative">
							<span class="aside-nav-name"><spring:message code="layout.sideNavigation.newRepresentatives.intlp"/></span>
						</a></li>
					</c:if>
					<c:if test="${not fn:endsWith(flowModeId, '-changerep')}">
						<li class="aside-nav-list-item"><a class="aside-nav-anchor" href="#representative">
							<span class="aside-nav-name"><spring:message code="layout.sideNavigation.${section_item}"/></span>
						</a></li>
					</c:if>

				</c:when>

				<c:when test="${(fn:endsWith(flowModeId, '-generic'))&& section_item == 'otherAttachments' }">
					<li class="aside-nav-list-item"><a class="aside-nav-anchor" href="#${section_item}">
						<span class="aside-nav-name"><spring:message code="layout.sideNavigation.otherAttachments.generic"/></span>
					</a></li>
				</c:when>

				<c:when test="${section_item == 'patent'}">
					<li class="aside-nav-list-item"><a class="aside-nav-anchor" href="#${section_item}">
						<span class="aside-nav-name"><spring:message code="layout.sideNavigation.${section_item}.${formUtil.getMainType()}"/></span>
					</a></li>
				</c:when>

				<c:otherwise>
					<c:if test="${not (section_item eq 'payment_iframe')}">
						<li class="aside-nav-list-item"><a class="aside-nav-anchor" href="#${section_item}">
							<span class="aside-nav-name"><spring:message code="layout.sideNavigation.${section_item}"/></span>
						</a></li>
					</c:if>
				</c:otherwise>
			</c:choose>
	    </c:forEach>
	</c:if>
</ul>