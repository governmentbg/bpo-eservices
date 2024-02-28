<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<ul class="controls" id="topButtons">
    <li>
        <a class="cancelButton designer"><spring:message code="designer.form.action.cancelAdd.top"/></a>
    </li>
    <li>
		<button id="addDesignerTopButton" type="button">
			<spring:message code="designer.form.action.save" />
			<%--
            <i class="add-icon"></i>
            <spring:message code="designer.form.action.add.top"/>
            --%>
        </button>
    </li>
</ul>

	
