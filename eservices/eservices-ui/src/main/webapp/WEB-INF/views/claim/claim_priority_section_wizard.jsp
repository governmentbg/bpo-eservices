<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<div id="previousPriority" class="prevFilling">
    <p><spring:message code="claim.priority.previous"/></p>

    <ul class="declared-claim-buttons">
        <li>
            <a id="openPriority" data-toggle="tab"><spring:message code="claim.action.yes"/></a>
        </li>
        <li>
            <a id="noPriority" data-toggle="tab"><spring:message code="claim.action.no"/></a>
        </li>
    </ul>


        <div class="previousClaim" id="tabPriority">
        </div>

</div>        