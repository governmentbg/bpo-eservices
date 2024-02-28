<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:if test="${not empty flowBean.changeType}">
    <div class="requesttype">
        <header>
            <spring:message code="review.requesttype.details"/>
            <c:if test="${!flowBean.readOnly}">
                <a class="edit navigateBtn" data-val="Update_RequestType"><spring:message code="review.edit"/></a>
            </c:if>
        </header>
        <div><p>
            <c:choose>
                <c:when test="${fn:startsWith(flowModeId, 'tm-')}">
                    <spring:message code="dossierChangeType.tm.${flowBean.changeType}"/>
                </c:when>
                <c:when test="${fn:startsWith(flowModeId, 'pt-')}">
                    <spring:message code="dossierChangeType.pt.${flowBean.changeType}"/>
                </c:when>
                <c:when test="${fn:startsWith(flowModeId, 'um-')}">
                    <spring:message code="dossierChangeType.um.${flowBean.changeType}"/>
                </c:when>
                <c:when test="${fn:startsWith(flowModeId, 'ep-')}">
                    <spring:message code="dossierChangeType.ep.${flowBean.changeType}"/>
                </c:when>
                <c:when test="${fn:startsWith(flowModeId, 'sv-')}">
                    <spring:message code="dossierChangeType.sv.${flowBean.changeType}"/>
                </c:when>
                <c:when test="${fn:startsWith(flowModeId, 'spc-')}">
                    <spring:message code="dossierChangeType.spc.${flowBean.changeType}"/>
                </c:when>
                <c:otherwise>
                    <spring:message code="dossierChangeType.ds.${flowBean.changeType}"/>
                </c:otherwise>
            </c:choose>
        </p></div>
    </div>
</c:if>