<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<sec:authorize var="uthenticatedUser" access="isAuthenticated()" />

<div class="your-details">
    <header>
        <c:if test="${flowModeId eq 'sv-efiling'}">
            <spring:message code="review.authorsDetails"/>
        </c:if>
        <c:if test="${!(flowModeId eq 'sv-efiling')}">
            <spring:message code="review.inventorsDetails"/>
        </c:if>
        <a class="edit navigateBtn" data-val="Update_Inventor"><spring:message code="review.update"/></a>
    </header>
    <div>
        <c:if test="${not empty flowBean.inventors}">

            <c:set var="sectionId" value="inventor" scope="request" />
            <component:generic path="showInventorIdInTable" checkRender="true">
                <c:set var="showInventorIdInTable" value="true" />
            </component:generic>

            <component:generic path="inventorsAreReal" checkRender="true">
                <c:set var="showInventorsAreReal" value="true" />
            </component:generic>

            <table>
                <c:if test="${flowModeId eq 'sv-efiling'}">
                    <caption><spring:message code="review.person.author.info.title"/></caption>
                </c:if>
                <c:if test="${!(flowModeId eq 'sv-efiling')}">
                    <caption><spring:message code="review.person.inventor.info.title"/></caption>
                </c:if>
                <thead>
                <tr>
                    <th><spring:message code="review.person.inventor.table.column.number.title"/></th>
                    <c:if test="${showInventorIdInTable}">
                        <th><spring:message code="review.person.inventor.table.column.id.title"/></th>
                    </c:if>
                    <th><spring:message code="review.person.inventor.table.column.name.title"/></th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${not empty showInventorsAreReal && showInventorsAreReal}">
                    <tr>
                        <p style="margin:10px; font-weight: bold">
                            <spring:message code="application.declaration.field.inventorsAreReal.${flowModeId}"/>:
                            <c:choose>
                                <c:when test="${empty flowBean.mainForm.inventorsAreReal or !flowBean.mainForm.inventorsAreReal}">
                                    <spring:message code="action.no"/>
                                </c:when>
                                <c:otherwise>
                                    <spring:message code="action.yes"/>
                                </c:otherwise>
                            </c:choose>
                        </p>
                    </tr>
                </c:if>
                <c:forEach var="index" items="${flowBean.inventors}" varStatus="status">
                    <tr>
                        <td><c:out value="${status.count}"></c:out></td>
                        <c:if test="${showInventorIdInTable}">
                            <td>
                                <c:out value="${index.imported ? index.id : '-'}"></c:out>
                            </td>
                        </c:if>
                        <td><c:out value="${index.name}"></c:out></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</div>