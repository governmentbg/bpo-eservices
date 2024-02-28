<%--
  Created by IntelliJ IDEA.
  User: Raya
  Date: 16.8.2019 г.
  Time: 11:08
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="originalSectionId" value="${sectionId}"/>
<c:set var="sectionId" value="${sectionId}_markViews" scope="request"/>
<component:generic path="markViews" checkRender="true">

    <label>
        <spring:message code="mark.form.views.attach"/>
        <a rel="file-help-tooltip" class="attachment-disclaimer-icon"></a>
        <div data-tooltip="help" class="hidden">
            <a class="close-popover"></a>
            <component:helpMessage textCode="mark.form.views.help"/>
        </div>
    </label>

    <button class="btn btn-default" id="addViewMarkBtn">
        <i class="list-icon"></i>
        <spring:message code="mark.form.views.button.add"/>
    </button>

    <div id="markViews">
        <jsp:include page="/WEB-INF/views/marks/views/view_list.jsp" />
    </div>

    <jsp:include page="/WEB-INF/views/marks/views/view_section.jsp"/>


</component:generic>

<c:set var="sectionId" value="${originalSectionId}" scope="request"/>