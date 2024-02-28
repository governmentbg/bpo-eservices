<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div id="personSelectionList" class="modal modal-standard fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <header>
                <h1>
                    <spring:message code="person.select.header"/>
                </h1>
                <a class="close-icon" data-dismiss="modal"></a>
            </header>
            <!--modal-header-->
            <section class="modal-body">
                <c:if test="${empty personOptionsList}">
                   <p class="alert alert-info">
                       <spring:message code="person.select.options.empty"/>
                   </p>
                </c:if>
                <c:if test="${not empty personOptionsList}">
<%--                    <c:if test="${!fn:endsWith(flowModeId, '-invdenial')}">--%>
<%--                        <p class="alert alert-info">--%>
<%--                            <spring:message code="person.select.options.info"/>--%>
<%--                        </p>--%>
<%--                    </c:if>--%>
                    <table class="table">
                        <thead>
                        <tr>
                            <td></td>
                            <td><spring:message code="person.select.table.name"/></td>
                            <td><spring:message code="person.select.table.address"/></td>
                            <td><spring:message code="person.select.table.source"/></td>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${personOptionsList}" var="option">
                            <jsp:include page="/WEB-INF/views/person/person_select_item.jsp">
                            <jsp:param name="name" value="${option.personForm.name}"/>
                            <jsp:param name="id" value="${option.personForm.id}"/>
                            <jsp:param name="addressCountry" value="${option.personForm.address.country}"/>
                            <jsp:param name="addressProvince" value="${option.personForm.address.stateprovince}"/>
                            <jsp:param name="addressCity" value="${option.personForm.address.city}"/>
                            <jsp:param name="addressStreet" value="${option.personForm.address.street}"/>
                            <jsp:param name="addressPostal" value="${option.personForm.address.postalCode}"/>
                            <jsp:param name="field" value="${option.field}"/>
                            <jsp:param name="fieldValue" value="${option.field.value}"/>
                            <jsp:param name="alreadyAdded" value="${option.alreadyAdded}"/>
                        </jsp:include>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:if>
            </section>
            <!--modal-body-->
            <footer>
                <ul>
                    <li><a data-dismiss="modal"><spring:message code="layout.button.cancel" /></a></li>
                    <li>
                        <button id="savePersonChoiceBtn" data-section="${sectionId}"><spring:message code="person.select.save.choice" /></button>
                    </li>
                </ul>
            </footer>
        </div>
    </div>
</div>
