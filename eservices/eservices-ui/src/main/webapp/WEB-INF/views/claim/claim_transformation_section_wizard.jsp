<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<div id="previousTransformation">
    <p><spring:message code="claim.transformation.previous"/></p>

    <ul class="declared-claim-buttons">
        <li>
            <a id="openTransformation" data-toggle="tab"><spring:message code="claim.action.yes"/></a>
        </li>
        <li>
            <a id="noTransformation" data-toggle="tab"><spring:message code="claim.action.no"/></a>
        </li>
    </ul>

    <div class="previousClaim" id="tabTransformation">

    </div>
</div>