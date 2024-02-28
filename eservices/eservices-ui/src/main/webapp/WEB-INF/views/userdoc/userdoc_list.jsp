<%--
  Created by IntelliJ IDEA.
  User: Raya
  Date: 31.10.2022 г.
  Time: 18:27
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/tags/component/sp-tags.tld" prefix="tags" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<spring:eval var="dateFormat" expression="@propertyConfigurer.getProperty('iu.template.dateFormat')" />

<c:forEach items="${userdocs}" var="userdoc">
    <c:set var="userdocDate">
        <fmt:formatDate type="date" pattern="${dateFormat}" value="${userdoc.filingDate}"/>
    </c:set>
    <label style="margin-left: ${param.margin}px"><input type="radio" name="selectedUserdoc" class="selectedUserdocUserdoc" value="${userdoc.id}" ${userdoc.id == selected ? 'checked="checked"': ''} />
        <c:if test="${empty userdoc.externalSystemId}">
            <span><c:out value="${userdoc.docOrigin}/${userdoc.docLog}/${userdoc.docSeqSeries}/${userdoc.docSeqNbr} / ${userdocDate}"/></span>
        </c:if>
        <c:if test="${not empty userdoc.externalSystemId}">
            <span><c:out value="${userdoc.externalSystemId} / ${userdocDate}"/></span>
        </c:if>
        <span><c:out value=" - ${userdoc.userdocTypeName}" /></span>
    </label>
    <c:if test="${not empty userdoc.children}">
        <c:set var="userdocs" value="${userdoc.children}" scope="request"/>
        <jsp:include page="/WEB-INF/views/userdoc/userdoc_list.jsp">
            <jsp:param value="${param.margin + 20}" name="margin"/>
        </jsp:include>
    </c:if>
</c:forEach>