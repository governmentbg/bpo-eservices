<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div class="form">
	<h1>
		<spring:message code="${flowModeId}.layout.mainTitle" />
	</h1>

	<tiles:useAttribute id="sections_list" name="content"
		classname="java.util.List" />
	<c:if test="${not empty sections_list}">
		<c:forEach var="section_item" items="${sections_list}">
			<tiles:insertAttribute value="${section_item}" />
		</c:forEach>
	</c:if>
</div>