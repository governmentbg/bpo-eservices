<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tr>
    <td>
        <span><b>${param.name}</b></span><span style="margin-left:5px; font-style: italic">(${param.addressCity})</span>

        <div id="collapseDesigner${param.id}" class="collapse">
            <div>
                <b><spring:message code="person.matches.info.country"/></b>: <span>${param.addressCountry}</span>
            </div>
            <div>
                <b><spring:message code="person.matches.info.city"/></b>: <span>${param.addressCity}</span>
            </div>
            <div>
                <b><spring:message code="person.matches.info.postalcode"/></b>: <span>${param.addressPostal}</span
            </div>
            <div>
                <b><spring:message code="person.matches.info.street"/></b>: <span>${param.addressStreet}</span>
            </div>
            <c:if test="${not empty param.phone}">
                <div>
                    <b><spring:message code="person.matches.info.phone"/></b>: <span>${param.phone}</span>
                </div>
            </c:if>
        </div>
    </td>
    <td>
        <a role="button" style="margin-right: 10px"
           data-toggle="collapse" href="#collapseDesigner${param.id}" aria-expanded="false"
           aria-controls="collapseDesigner${param.id}"><spring:message code="person.matches.action.info"/></a>
        <a data-val="${param.id}" class="importDesignerFromMatches btn btn-default">
            <spring:message code="person.matches.action.import"/></a>
    </td>
</tr>