<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<ul class="controls">
    <li>
        <a class="cancelButton representative"><spring:message
                code="representative.form.action.cancelAdd.top"/></a>
    </li>
    <li>
        <button class="addRepresentative ${param.saveButtonClass}" type="button">
            <i class="add-icon"/>
            <spring:message code="representative.form.action.add.top"/>
        </button>
    </li>
</ul>