<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="doublesInventor" class="modal modal-standard fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <header>
                <h1>
                    <spring:message code="person.matches.inventor.exists"/>
                </h1>
                <a class="close-icon" data-dismiss="modal"></a>
            </header>
            <!--modal-header-->
            <section class="modal-body">
                <input id="inventorMatchesExist" type="hidden" value="true">
                <p class="alert alert-info"><spring:message code="person.matches.header.info"/></p>

                <c:if test="${not empty inventorMatches}">
                    <div class="applicant">
                        <table class="table">
                            <thead>
                            <tr>
                                <th width="75%"><spring:message code="person.matches.table.details"/></th>
                                <th><spring:message code="person.matches.table.options"/></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="inventor" items="${inventorMatches}">
                                <c:if test="${not empty inventor.address}">
                                    <jsp:include page="/WEB-INF/views/inventor/inventor_match_item.jsp">
                                        <jsp:param name="name" value="${inventor.name}"/>
                                        <jsp:param name="id" value="${inventor.id}"/>
                                        <jsp:param name="addressCountry" value="${inventor.address.country}"/>
                                        <jsp:param name="addressProvince" value="${inventor.address.stateprovince}"/>
                                        <jsp:param name="addressCity" value="${inventor.address.city}"/>
                                        <jsp:param name="addressStreet" value="${inventor.address.street}"/>
                                        <jsp:param name="addressPostal" value="${inventor.address.postalCode}"/>
                                        <jsp:param name="correspAddress" value="${inventor.address.country}"/>
                                        <jsp:param name="phone" value="${inventor.phone}"/>
                                    </jsp:include>
                                </c:if>
                                <c:if test="${empty inventor.address}">
                                    <jsp:include page="/WEB-INF/views/inventor/inventor_match_item.jsp">
                                        <jsp:param name="name" value="${inventor.name}"/>
                                        <jsp:param name="id" value="${inventor.id}"/>
                                        <jsp:param name="correspAddress" value="corresp address"/>
                                        <jsp:param name="phone" value="${inventor.phone}"/>
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
                           href="#collapseAddManualInventor" aria-expanded="false" aria-controls="collapseAddManualInventor">
                            <spring:message code="person.matches.inventor.notListed"/>
                        </a>
                    </li>
                </ul>
                <div id="collapseAddManualInventor" class="collapse" >
                    <a id="addInventorAnyway" style="margin-top: 10px; text-decoration: none" href="#" class="btn btn-primary"><spring:message
                            code="person.matches.inventor.action.addAnyway"/></a>
                </div>
            </footer>
        </div>
    </div>
</div>