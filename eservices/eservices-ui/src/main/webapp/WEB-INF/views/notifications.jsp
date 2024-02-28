<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<section class="reference" id="notifications">
    <div class="alert alert-info" id="providePOW-notification">
        <spring:message code="eservice.dossier.change.changeType.info.providePOW.${flowModeId}"/>
    </div>
</section>
