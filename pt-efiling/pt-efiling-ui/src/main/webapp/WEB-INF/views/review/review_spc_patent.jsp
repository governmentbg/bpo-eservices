<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<spring:eval var="dateFormat" expression="@propertyConfigurer.getProperty('iu.template.dateFormat')" />

<div class="your-details">
    <header>
        <spring:message code="pt.details.title.spc"/>
        <a class="edit navigateBtn" data-val="Update_Patent"><spring:message code="review.update"/></a>
    </header>
    <c:if test="${flowBean.spcPatents != null && flowBean.spcPatents.size() > 0}">
        <div class="patent">
            <table id="patentsList" class="table">
                <tr>
                    <th><spring:message code="pt.details.table.header.number"/></th>
                    <th><spring:message code="pt.details.table.header.applicationNumber"/></th>
                    <th><spring:message code="pt.details.table.header.title"/></th>
                    <th><spring:message code="pt.details.table.header.status"/></th>
                </tr>

                <c:set var="i" value="0"/>
                <c:forEach var="pt" items="${flowBean.spcPatents}">
                    <c:set var="i" value="${i+1}"/>
                    <tr id="pt_id_${pt.id}">
                        <td>${i}</td>
                        <td><c:out value="${pt.applicationNumber}"/> </td>
                        <td><c:out value="${pt.patentTitle}"/></td>
                        <td><c:out value="${pt.patentCurrentStatus}"/> </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </c:if>
</div>
