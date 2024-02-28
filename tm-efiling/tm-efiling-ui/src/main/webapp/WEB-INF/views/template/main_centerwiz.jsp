<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="form">
    <h1><spring:message code="layout.mainTitle.${flowModeId}"/></h1>

    <!-- 	DYNAMIC NAVIGATION PATH	 -->
    <section class="steps">
        <ul class="breadcrumb-steps">
            <tiles:useAttribute id="current_state" name="currentState" classname="java.lang.String"/>
            <tiles:useAttribute id="navigation_path_list" name="navigation_path" classname="java.util.List"/>

            <c:set var="cssNumbers">zero,one,two,three,four,five</c:set>
            <c:set var="cssNumbersArray" value="${fn:split(cssNumbers, ',')}"/>

            <c:if test="${not empty navigation_path_list}">
                <c:forEach var="navigation_path_item" items="${navigation_path_list}" varStatus="rowCounter">
                    <li class="${cssNumbersArray[rowCounter.count]} ${current_state eq navigation_path_item?'active':''}">
                        <span>${rowCounter.count}</span>

                        <p><spring:message code="${flowModeId}.${navigation_path_item}"/></p>
                    </li>
                </c:forEach>
            </c:if>
        </ul>
    </section>

    <c:if test="${current_state ne 'wiz3' && current_state ne 'wiz4' }">
        <jsp:include page="/WEB-INF/views/fast_track_banner.jsp"/>
    </c:if>

    <%--<div id="formEF">--%>
        <!-- 	SECTIONS -->
        <tiles:useAttribute id="sections_list" name="content" classname="java.util.List"/>
        <c:if test="${not empty sections_list}">
            <c:forEach var="section_item" items="${sections_list}">
                <tiles:insertAttribute value="${section_item}"/>
            </c:forEach>
        </c:if>
    <%--</div>--%>
</div>