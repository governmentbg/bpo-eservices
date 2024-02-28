<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<div id="previousSeniority" class="prevFilling">
    <p><spring:message code="claim.seniority.previous"/></p>

    <ul class="declared-claim-buttons">
        <li>
            <a id="openSeniority" data-toggle="tab"><spring:message code="claim.action.yes"/></a>
        </li>
        <li>
            <a id="noSeniority" data-toggle="tab"><spring:message code="claim.action.no"/></a>
        </li>
    </ul>
    <%--<p class="introtext"><spring:message code="claim.seniority.previous"/></p>--%>

    <%--<div class="toggleWrap">--%>
        <%--<p class="btn-group toggleNav" data-toggle="buttons-radio">--%>
            <%--<a class="btn" id="openSeniority"> <spring:message code="claim.action.yes"/></a>--%>
            <%--<a id="noSeniority" class="btn" class="active"> <spring:message code="claim.action.no"/></a>--%>
        <%--</p>--%>

        <div class="previousClaim" id="tabSeniority">
<%--             <jsp:include page="claim_seniority_details_wizard.jsp"/> --%>
        </div>
</div>

