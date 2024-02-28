<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="doublesDesigner" class="modal modal-standard fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <header>
                <h1>
                    <spring:message code="person.matches.designer.exists"/>
                </h1>
                <a class="close-icon" data-dismiss="modal"></a>
            </header>
            <!--modal-header-->
            <section class="modal-body">
                <input id="designerMatchesExist" type="hidden" value="true">
                <p class="alert alert-info"><spring:message code="person.matches.header.info"/></p>

                <c:if test="${not empty designerMatches}">
                    <div class="applicant">
                        <table class="table">
                            <thead>
                            <tr>
                                <th width="75%"><spring:message code="person.matches.table.details"/></th>
                                <th><spring:message code="person.matches.table.options"/></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="designer" items="${designerMatches}">
                                <c:if test="${not empty designer.address}">
                                    <jsp:include page="/WEB-INF/views/designers/designer_match_item.jsp">
                                        <jsp:param name="name" value="${designer.name}"/>
                                        <jsp:param name="id" value="${designer.id}"/>
                                        <jsp:param name="addressCountry" value="${designer.address.country}"/>
                                        <jsp:param name="addressProvince" value="${designer.address.stateprovince}"/>
                                        <jsp:param name="addressCity" value="${designer.address.city}"/>
                                        <jsp:param name="addressStreet" value="${designer.address.street}"/>
                                        <jsp:param name="addressPostal" value="${designer.address.postalCode}"/>
                                        <jsp:param name="correspAddress" value="${designer.address.country}"/>
                                        <jsp:param name="phone" value="${designer.phone}"/>
                                    </jsp:include>
                                </c:if>
                                <c:if test="${empty designer.address}">
                                    <jsp:include page="/WEB-INF/views/designers/designer_match_item.jsp">
                                        <jsp:param name="name" value="${designer.name}"/>
                                        <jsp:param name="id" value="${designer.id}"/>
                                        <jsp:param name="correspAddress" value="corresp address"/>
                                        <jsp:param name="phone" value="${designer.phone}"/>
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
                           href="#collapseAddManualDesigner" aria-expanded="false" aria-controls="collapseAddManualDesigner">
                            <spring:message code="person.matches.designer.notListed"/>
                        </a>
                    </li>
                </ul>
                <div id="collapseAddManualDesigner" class="collapse" >
                    <a id="addDesignerAnyway" style="margin-top: 10px; text-decoration: none" href="#" class="btn btn-primary"><spring:message
                            code="person.matches.designer.action.addAnyway"/></a>
                </div>
            </footer>
        </div>
    </div>
</div>