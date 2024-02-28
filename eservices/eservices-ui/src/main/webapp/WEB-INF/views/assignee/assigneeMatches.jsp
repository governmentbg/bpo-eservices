<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="doublesAssignee" class="modal modal-standard fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <header>
                <h1>
                    <spring:message code="person.matches.assignee.exists"/>
                </h1>
                <a class="close-icon" data-dismiss="modal"></a>
            </header>
            <!--modal-header-->
            <section class="modal-body">
                <input id="assigneeMatchesExist" type="hidden" value="true">
                <p class="alert alert-info"><spring:message code="person.matches.header.info"/></p>

                <c:if test="${not empty assigneeMatches}">
                    <div class="applicant">
                        <table class="table">
                            <thead>
                            <tr>
                                <th width="75%"><spring:message code="person.matches.table.details"/></th>
                                <th><spring:message code="person.matches.table.options"/></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="assignee" items="${assigneeMatches}">
                                <c:if test="${not empty assignee.address}">
                                    <jsp:include page="/WEB-INF/views/assignee/assignee_match_item.jsp">
                                        <jsp:param name="name" value="${assignee.name}"/>
                                        <jsp:param name="id" value="${assignee.id}"/>
                                        <jsp:param name="type" value="${assignee.type}"/>
                                        <jsp:param name="addressCountry" value="${assignee.address.country}"/>
                                        <jsp:param name="addressProvince" value="${assignee.address.stateprovince}"/>
                                        <jsp:param name="addressCity" value="${assignee.address.city}"/>
                                        <jsp:param name="addressStreet" value="${assignee.address.street}"/>
                                        <jsp:param name="addressPostal" value="${assignee.address.postalCode}"/>
                                        <jsp:param name="correspAddress" value="${assignee.address.country}"/>
                                        <jsp:param name="phone" value="${assignee.phone}"/>
                                    </jsp:include>
                                </c:if>
                                <c:if test="${empty assignee.address}">
                                    <jsp:include page="/WEB-INF/views/assignee/assignee_match_item.jsp">
                                        <jsp:param name="name" value="${assignee.name}"/>
                                        <jsp:param name="id" value="${assignee.id}"/>
                                        <jsp:param name="type" value="${assignee.type}"/>
                                        <jsp:param name="correspAddress" value="corresp address"/>
                                        <jsp:param name="phone" value="${assignee.phone}"/>
                                    </jsp:include>
                                </c:if>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:if>
            </section>
            <!--modal-body-->
            <footer>
                <ul>
                    <li>
                        <a role="button" data-toggle="collapse"
                           href="#collapseAddManualAssignee" aria-expanded="false" aria-controls="collapseAddManualAssignee">
                            <spring:message code="person.matches.assignee.notListed"/>
                        </a>
                    </li>
                </ul>
                <div id="collapseAddManualAssignee" class="collapse" >
                    <a id="addAssigneeAnyway" style="margin-top: 10px; text-decoration: none" href="#" class="btn btn-primary"><spring:message
                            code="person.matches.assignee.action.addAnyway"/></a>
                </div>
            </footer>
        </div>
    </div>
</div>