<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="doublesApplicant" class="modal modal-standard fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <header>
                <h1>
                    <spring:message code="person.matches.applicant.exists"/>
                </h1>
                <a class="close-icon" data-dismiss="modal"></a>
            </header>
            <!--modal-header-->
            <section class="modal-body">
                <input id="applicantMatchesExist" type="hidden" value="true">
                <p class="alert alert-info"><spring:message code="person.matches.header.info"/></p>

                <c:if test="${not empty applicantMatches}">
                    <div class="applicant">
                        <table class="table">
                            <thead>
                            <tr>
                                <th width="75%"><spring:message code="person.matches.table.details"/></th>
                                <th><spring:message code="person.matches.table.options"/></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="applicant" items="${applicantMatches}">
                                <c:if test="${not empty applicant.address}">
                                    <jsp:include page="/WEB-INF/views/applicant/applicant_match_item.jsp">
                                        <jsp:param name="name" value="${applicant.name}"/>
                                        <jsp:param name="id" value="${applicant.id}"/>
                                        <jsp:param name="type" value="${applicant.type}"/>
                                        <jsp:param name="addressCountry" value="${applicant.address.country}"/>
                                        <jsp:param name="addressProvince" value="${applicant.address.stateprovince}"/>
                                        <jsp:param name="addressCity" value="${applicant.address.city}"/>
                                        <jsp:param name="addressStreet" value="${applicant.address.street}"/>
                                        <jsp:param name="addressPostal" value="${applicant.address.postalCode}"/>
                                        <jsp:param name="correspAddress" value="${applicant.address.country}"/>
                                        <jsp:param name="phone" value="${applicant.phone}"/>
                                    </jsp:include>
                                </c:if>
                                <c:if test="${empty applicant.address}">
                                    <jsp:include page="/WEB-INF/views/applicant/applicant_match_item.jsp">
                                        <jsp:param name="name" value="${applicant.name}"/>
                                        <jsp:param name="id" value="${applicant.id}"/>
                                        <jsp:param name="type" value="${applicant.type}"/>
                                        <jsp:param name="correspAddress" value="corresp address"/>
                                        <jsp:param name="phone" value="${applicant.phone}"/>
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
                           href="#collapseAddManualApplicant" aria-expanded="false" aria-controls="collapseAddManualApplicant">
                            <spring:message code="person.matches.applicant.notListed"/>
                        </a>
                    </li>
                </ul>
                <div id="collapseAddManualApplicant" class="collapse" >
                    <a id="addApplicantAnyway" style="margin-top: 10px; text-decoration: none" href="#" class="btn btn-primary"><spring:message
                            code="person.matches.applicant.action.addAnyway"/></a>
                </div>
            </footer>
        </div>
    </div>
</div>