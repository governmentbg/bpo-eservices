<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<div id="previousExhibition" class="prevFilling">
    <p><spring:message code="claim.exhibition.previous"/></p>

    <ul class="declared-claim-buttons">
        <li>
            <a id="openExhibition" data-toggle="tab"><spring:message code="claim.action.yes"/></a>
        </li>
        <li>
            <a id="noExhibition" data-toggle="tab"><spring:message code="claim.action.no"/></a>
        </li>
    </ul>

    <div class="previousClaim" id="tabExhibition">
    </div>
</div>    