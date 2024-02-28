<%@page import="eu.ohim.sp.common.ui.util.constants.AppConstants" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!-- SET A SERIES OF VARIABLES WHICH INFORMS WHETHER A RADIOBUTTON SET HAS TO BE SHOWN -->
<tiles:useAttribute id="sections_list" name="claim_content" classname="java.util.List"/>
<c:set var="showPriority" value="false"/>
<c:set var="showExhibition" value="false"/>

<c:if test="${not empty sections_list}">
    <c:forEach var="section_item" items="${sections_list}">
        <c:if test="${section_item eq '/WEB-INF/views/claim/claim_priority_section_wizard.jsp'}">
            <c:set var="showPriority" value="true"/>
        </c:if>
        <c:if test="${section_item eq '/WEB-INF/views/claim/claim_exhibition_section_wizard.jsp'}">
            <c:set var="showExhibition" value="true"/>
        </c:if>
    </c:forEach>
</c:if>

<section class="claim" id="claim">
    <header>
    	<a href="#claim" class="anchorLink">
        </a>
        <h2>
            <spring:message code="claim.title.wizard"/>
        </h2>
    </header>

	
    <div id="claimTableContainer">
        <jsp:include page="../claim/claim_table_common.jsp"/>
    </div>

	

    <div class="claimSections">
        <tiles:useAttribute id="sections_list" name="claim_content"
                            classname="java.util.List"/>
        <c:if test="${not empty sections_list}">
            <c:forEach var="section_item" items="${sections_list}">
                <tiles:insertAttribute value="${section_item}"/>
            </c:forEach>
        </c:if>
    </div>
    <div class="wizard-steps-analytics" style="display:none;" data-ignore-parent="true">
        /design/register-design/claims
    </div>
</section>