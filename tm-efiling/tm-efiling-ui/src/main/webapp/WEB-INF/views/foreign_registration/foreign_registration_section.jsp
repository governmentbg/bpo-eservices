<%--
  Created by IntelliJ IDEA.
  User: Raya
  Date: 5.5.2022 г.
  Time: 18:01
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section class="signature" id="foreignRegistrationSection">

    <header>
        <a id="foreignRegistration" class="anchorLink"></a>

        <h2>
            <spring:message code="foreign.registration.title"/>
        </h2>
    </header>

    <jsp:include page="/WEB-INF/views/foreign_registration/foreign_registration_card_list.jsp"/>


    <button id="foreignRegistrationTrigger" class="add-button" data-toggle="button" type="button">
        <i class="add-icon"></i>
        <spring:message code="foreign.registration.section.button"/>
    </button>


    <div id="tabForeignRegistration" class="addbox" style="display: none">
        <div id="foreignRegistrationDiv"></div>

    </div>

    <input type="hidden" id="maxForeignRegistrations"
           value="${configurationServiceDelegator.getValue('foreign.registration.add.maxNumber', 'general')}">
    <script type="text/javascript">
        checkMaxForeignRegistrations();
    </script>

</section>
