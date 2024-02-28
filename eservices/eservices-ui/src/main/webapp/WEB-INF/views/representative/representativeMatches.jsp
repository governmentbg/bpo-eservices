<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="doublesRepresentative" class="modal modal-standard fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <header>
                <h1>
                    <spring:message code="person.matches.representative.exists"/>
                </h1>
                <a class="close-icon" data-dismiss="modal"></a>
            </header>
            <!--modal-header-->
            <section class="modal-body">
                <input id="representativeMatchesExist" type="hidden" value="true">
                <p class="alert alert-info"><spring:message code="person.matches.header.info"/></p>

                <c:if test="${not empty representativeMatches}">
                    <div class="representative">
                        <table class="table">
                            <thead>
                            <tr>
                                <th width="75%"><spring:message code="person.matches.table.details"/></th>
                                <th><spring:message code="person.matches.table.options"/></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="representative" items="${representativeMatches}">
                                <c:if test="${not empty representative.address}">
                                    <jsp:include page="/WEB-INF/views/representative/representative_match_item.jsp">
                                        <jsp:param name="name" value="${representative.name}"/>
                                        <jsp:param name="id" value="${representative.id}"/>
                                        <jsp:param name="type" value="${representative.type}"/>
                                        <jsp:param name="addressCountry" value="${representative.address.country}"/>
                                        <jsp:param name="addressProvince" value="${representative.address.stateprovince}"/>
                                        <jsp:param name="addressCity" value="${representative.address.city}"/>
                                        <jsp:param name="addressStreet" value="${representative.address.street}"/>
                                        <jsp:param name="addressPostal" value="${representative.address.postalCode}"/>
                                        <jsp:param name="correspAddress" value="${representative.address.country}"/>
                                        <jsp:param name="phone" value="${representative.phone}"/>
                                    </jsp:include>
                                </c:if>
                                <c:if test="${empty representative.address}">
                                    <jsp:include page="/WEB-INF/views/representative/representative_match_item.jsp">
                                        <jsp:param name="name" value="${representative.name}"/>
                                        <jsp:param name="id" value="${representative.id}"/>
                                        <jsp:param name="type" value="${representative.type}"/>
                                        <jsp:param name="correspAddress" value="corresp address"/>
                                        <jsp:param name="phone" value="${representative.phone}"/>
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
                           href="#collapseAddManualRepr" aria-expanded="false" aria-controls="collapseAddManualRepr">
                            <spring:message code="person.matches.representative.notListed"/>
                        </a>
                    </li>
                </ul>
                <div id="collapseAddManualRepr" class="collapse" >
                    <a id="addRepresentativeAnyway" style="margin-top: 10px; text-decoration: none" href="#" class="btn btn-primary"><spring:message
                            code="person.matches.representative.action.addAnyway"/></a>
                </div>
            </footer>
        </div>
    </div>
</div>