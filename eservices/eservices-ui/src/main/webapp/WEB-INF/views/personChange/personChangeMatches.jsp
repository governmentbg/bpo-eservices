<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="doublesPersonChange" class="modal fade">
<div class="modal-dialog">
<div class="modal-content">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">x</button>
        <div class="hgroup">
            <h3>
                <dt><spring:message code="person.matches.personChange.exists"/></dt>
            </h3>
            <p><spring:message code="person.matches.header.firstLine"/></p>

            <p><spring:message code="person.matches.header.secondLine"/></p>
        </div>
        <!--hgroup-->
    </div>
    <!--modal-header-->
    <div class="modal-body">
        <c:if test="${not empty personChangeMatches}">
            <c:forEach var="personChange" items="${personChangeMatches}">
                <c:if test="${not empty personChange.address}">
                    <jsp:include page="/WEB-INF/views/personChange/personChange_match_item.jsp">
                        <jsp:param name="name" value="${personChange.name}"/>
                        <jsp:param name="id" value="${personChange.id}"/>
                        <jsp:param name="type" value="${personChange.type}"/>
                        <jsp:param name="addressCountry" value="${personChange.address.country}"/>
                        <jsp:param name="addressProvince" value="${personChange.address.stateprovince}"/>
                        <jsp:param name="addressCity" value="${personChange.address.city}"/>
                        <jsp:param name="addressStreet" value="${personChange.address.street}"/>
                        <jsp:param name="addressPostal" value="${personChange.address.postalCode}"/>
                        <jsp:param name="correspAddress" value="${personChange.address.street}"/>
                    </jsp:include>
                </c:if>
                <c:if test="${empty personChange.address}">
                    <jsp:include page="/WEB-INF/views/personChange/personChange_match_item.jsp">
                        <jsp:param name="name" value="${personChange.name}"/>
                        <jsp:param name="id" value="${personChange.id}"/>
                        <jsp:param name="type" value="${personChange.type}"/>
                        <jsp:param name="correspAddress" value=""/>
                    </jsp:include>
                </c:if>
            </c:forEach>
        </c:if>
    </div>
    <!--modal-body-->
    <div class="modal-footer">
        <spring:message code="person.matches.personChange.notListed"/>
        <a href="#"><spring:message code="person.matches.personChange.notListed.contactUs"/></a>
        <span id="addPersonChangeAnyway" class="btn btn-mini">
            <spring:message code="person.matches.personChange.action.addAnyway"/></span>
    </div>
</div>
</div>
</div>