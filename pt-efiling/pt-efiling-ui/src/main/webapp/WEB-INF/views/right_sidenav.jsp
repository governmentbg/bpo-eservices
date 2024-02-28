<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<ul class="nav aside-nav-list">
	<tiles:useAttribute id="sections_list" name="content" classname="java.util.List" />
	<c:if test="${not empty sections_list}">
	    <c:forEach var="section_item" items="${sections_list}">
	    	<c:if test="${not (section_item eq 'payment_iframe')}">
	            <li class="aside-nav-list-item">
	            	<a class="aside-nav-anchor" href="#${section_item}" data-target="#${section_item}">
	            		<span class="aside-nav-name">
	            			<spring:message code="layout.sideNavigation.${section_item}"/>
	            		</span>             	
	            	</a>
	            </li>
	        </c:if>
	    </c:forEach>
	</c:if>
</ul>