<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<ul class="controls">
	<li>
		<a class="cancelButton inventor">
			<spring:message code="inventor.form.action.cancelAdd.top"/>
		</a>
	</li>
	<li>
		<button class="addInventor" type="button">
			<spring:message code="inventor.form.action.save" />
			<%--
			<i class="add-icon"></i>
			<spring:message code="inventor.form.action.add.top"/>
			--%>
		</button>
	</li>
</ul>
