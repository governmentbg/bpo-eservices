<%--
  Created by IntelliJ IDEA.
  User: Raya
  Date: 4.5.2022 г.
  Time: 16:37
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>


<section class="type">
    <header>
        <a id="typemark" class="anchorLink"></a>

        <h2>
            <spring:message code="gi.type"/><span class="requiredTag">*</span>
            <input type="hidden" value="${flowBean.mainForm.markType}" id="markType"/>
        </h2>
    </header>

    <c:set var="sectionId" value="typemark" scope="request"/>
    <p>
        <spring:message code="gi.type.which"/>
    </p>
    <tiles:useAttribute id="sections_list" name="typeMark_content" classname="java.util.List"/>
    <ul id="wizardTypes" class="mark-types-wizard">
        <c:if test="${not empty sections_list}">
            <c:forEach var="section_item" items="${sections_list}">
                <c:if test="${section_item eq '/WEB-INF/views/marks/wizard/geoindication.jsp'}">
                    <component:markItem listItemClass="word-mark" wrapperDivId="geoindication" moreInfoRef="#moreGeoindication" showMoreInfo="${false}"
                                        titleCode="mark.names.geoindication" descriptionCode="mark.description.geoindication"/>
                </c:if>
                <c:if test="${section_item eq '/WEB-INF/views/marks/wizard/oriname.jsp'}">
                    <component:markItem listItemClass="word-mark" wrapperDivId="oriname" moreInfoRef="#moreOriname" showMoreInfo="${false}"
                                        titleCode="mark.names.oriname" descriptionCode="mark.description.oriname"/>
                </c:if>
            </c:forEach>
        </c:if>
    </ul>
    <jsp:include page="additional_info.jsp"/>
    <div id="markType_content" class="add-edit-mark">
        <c:if test="${flowBean.mainForm.markType!='0'}">
            <c:set var="mode" value="wizard"/>
            <jsp:include page="${flowBean.mainForm.markType}.jsp"/>
        </c:if>
    </div>
</section>
