<%--
  Created by IntelliJ IDEA.
  User: Raya
  Date: 29.4.2022 г.
  Time: 16:10
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/tags/component/sp-tags.tld" prefix="tags" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<div>
    <c:set var="selected" value="" scope="request"/>
    <c:choose>
        <c:when test="${flowBean.relateRequestToObject != null && flowBean.relateRequestToObject}">
            <c:set var="selected" value="${flowBean.userdocMainObject}" scope="request"/>
        </c:when>
        <c:when test="${flowBean.selectedUserdoc != null}">
            <c:set var="selected" value="${flowBean.selectedUserdoc.id}" scope="request"/>
        </c:when>
    </c:choose>

    <div style="margin-top: 10px">
        <c:set var="hasObject" value="${false}"/>
        <c:if test="${flowBean.userdocRelationRestriction == 'USERDOC_RELATION_OPTIONAL' || flowBean.userdocRelationRestriction == 'OBJECT_ONLY'}">
            <label><input type="radio" name="selectedUserdoc" id="selectedUserdocObject" value="${flowBean.userdocMainObject}" ${flowBean.userdocMainObject == selected ? 'checked="checked"': ''} />
                <span>${flowBean.userdocMainObject}</span>
            </label>
            <c:set var="hasObject" value="${true}"/>
        </c:if>

        <c:if test="${empty flowBean.fetchedUserdocs}">
            <div class="alert alert-info" style="margin: 10px 0px">
                <spring:message code="userdoc.fetch.no.results"/>
            </div>
        </c:if>
        <c:if test="${not empty flowBean.fetchedUserdocs}">
            <c:set var="userdocs" value="${flowBean.fetchedUserdocsHierarchy}" scope="request"/>
            <jsp:include page="/WEB-INF/views/userdoc/userdoc_list.jsp">
                <jsp:param value="${hasObject ? 20 : 0}" name="margin"/>
            </jsp:include>
        </c:if>
    </div>
</div>