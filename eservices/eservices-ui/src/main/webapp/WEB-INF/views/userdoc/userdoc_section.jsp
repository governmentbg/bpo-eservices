<%--
  Created by IntelliJ IDEA.
  User: Raya
  Date: 27.4.2022 г.
  Time: 15:37
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/tags/component/sp-tags.tld" prefix="tags" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:if test="${flowBean.objectIsNational}">
    <section class="licence" id="userdoc">
        <c:set var="sectionId" value="userdoc" scope="request"/>

        <header>
            <a id="userdoc_section" class="anchorLink">
            </a>

            <h2><spring:message code="userdoc.title"></spring:message></h2>
        </header>

        <c:if test="${flowBean.fetchedUserdocsError}">
            <div class="alert alert-danger">
                <spring:message code="userdoc.fetch.error"/>
            </div>
        </c:if>

        <c:if test="${!flowBean.fetchedUserdocsError}">
            <div class="alert alert-info">
                <spring:message code="userdoc.fetch.info" htmlEscape="false"/>
            </div>

            <div id="requestRelationDiv">
                <jsp:include page="userdoc_selection.jsp"/>
            </div>
        </c:if>
    </section>
</c:if>